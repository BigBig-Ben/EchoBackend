package org.demo.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.demo.Entity.*;
import org.demo.service.*;
import org.demo.util.SensitiveWordTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.Perf;

@RestController
@RequestMapping(path = "/api")
public class APIController {
    @Autowired
    private UserService userService;

    @Autowired
    private VoiceService voiceService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @Autowired
    private DatingService datingService;

    @Autowired
    private DateCommentService dateCommentService;

    SimpleDateFormat format1 = new SimpleDateFormat("MM月dd日 HH时mm分");

    @RequestMapping(path = "/getUserInfo")
    public JSONObject getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int id = Integer.parseInt(request.getParameter("UserId"));
        User user = userService.findById(id);
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("Gender", user.getGender());
        temp.put("Icon", user.getIcon());
        temp.put("Motto", user.getDescription());
        json = JSONObject.fromObject(temp);
        return json;
    }

    @RequestMapping(path = "/getUserVoices")
    public JSONObject getUserVoices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int id = Integer.parseInt(request.getParameter("UserId"));
        User user = userService.findById(id);
        String icon = user.getIcon();
        JSONArray array = new JSONArray();
        List<Voice> voices = new ArrayList<Voice>(user.getVoices());
        for (Voice voice : voices) {
            JSONObject temp = new JSONObject();
            temp.put("VoiceId", voice.getId());
            temp.put("VoiceIcon", icon);
            temp.put("VoiceDesc", voice.getContent());
            temp.put("VoiceImgs", voice.getImgs());
            temp.put("VoiceLike", voice.getWhoLikes().size());
            //temp.put("VoiceTime", format1.format(v.getTime().getTime()));
            temp.put("VoiceTime", voice.getTime().getTime());
            array.add(temp);
        }
        json.put("Voices", array);
        return json;
    }

    @RequestMapping(path = "/getOneVoice")
    public JSONObject getOneVoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int voiceId = Integer.parseInt(request.getParameter("VoiceId"));
        int userId = Integer.parseInt(request.getParameter("UserId"));
        Voice voice = voiceService.getVoiceById(voiceId);
        User user = userService.findById(userId);

        json.put("VoiceID", voice.getId());
        json.put("VoiceIcon", user.getIcon());
        json.put("VoiceDesc", voice.getContent());
        json.put("VoiceImgs", voice.getImgs());
        json.put("VoiceLike", voice.getWhoLikes().size());
        //obj.put("VoiceTime", format1.format(voice.getTime().getTime()));
        json.put("VoiceTime", voice.getTime().getTime());
        Set<Voice> likedVoices = user.getLikeVoices();
        if (likedVoices.contains(voice))
            json.put("VoiceHasLiked", Boolean.TRUE);
        else json.put("VoiceHasLiked", Boolean.FALSE);
        return json;
    }

    @RequestMapping(path = "/getVoiceComment")
    public JSONArray getVoiceComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        int voiceId = Integer.parseInt(request.getParameter("VoiceId"));
        int userId = Integer.parseInt(request.getParameter("UserId"));
        Voice voice = voiceService.getVoiceById(voiceId);
        User user = userService.findById(userId);

        List<Comment> comments = new ArrayList<Comment>(voice.getComments());
        Collections.reverse(comments);
        for (Comment comment : comments) {
            JSONObject json = new JSONObject();
            json.put("UserId", comment.getHost().getId());
            json.put("CommentID", comment.getId());
            json.put("CommentIcon", comment.getHost().getIcon());
            json.put("CommentType", comment.getType());
            json.put("CommentDesc", comment.getContent());
            json.put("CommentLike", comment.getWhoLikes().size());
            //if(c.getType() == 0)
            //temp.put("CommentedUserID", c.getCommented().getId());
            json.put("CommentTime", comment.getTime().getTime());
            //temp.put("CommentTime", format1.format(c.getTime()));
            json.put("CommentedNo", comment.getFloor());

            Set<Comment> likedComments = user.getComments();
            if (likedComments.contains(comment))
                json.put("CommentHasLiked", Boolean.TRUE);
            else json.put("CommentHasLiked", Boolean.FALSE);
            array.add(json);
        }
        return array;
    }

    @RequestMapping(path = "/getSquareVoice")
    public JSONArray getSquareVoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        String sortType = request.getParameter("SquareSort");
        List<Voice> voices = new ArrayList<Voice>();
        if (sortType.equals("HOT")) {
            voices = voiceService.findOrderByStarsDesc();
        } else {
            voices = voiceService.findOrderByTimeDesc();
        }
        //voices = voiceService.getAll();
        for (Voice voice : voices) {
            JSONObject temp = new JSONObject();
            temp.put("VoiceId", voice.getId());
            temp.put("VoiceIcon", voice.getHost().getIcon());
            temp.put("VoiceDesc", voice.getContent());
            List<String> imgs = voice.getImgs();
            String[] imgsss = imgs.toArray(new String[imgs.size()]);
            temp.put("VoiceImgs", imgsss);
            temp.put("VoiceLike", voice.getWhoLikes().size());
            //temp.put("VoiceTime", format1.format(v.getTime().getTime()));
            temp.put("VoiceTime", voice.getTime().getTime());
            temp.put("CommentCnt", voice.getComments().size());
            array.add(temp);
        }
        return array;
    }

    @RequestMapping(path = "/addVoice")
    public JSONObject addVoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("add voice start");
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String content = request.getParameter("VoiceDesc");
        String res = SensitiveWordTree.censorWords(content);
        System.out.println("sensitive tree res: " + res);
        if (!res.equals("未匹配到敏感词")) {
            json.put("type", "illegal");
            return json;
        } else json.put("type", "success");
        String[] imgs = request.getParameterValues("VoiceImgs");//
        JSONArray temp = JSONArray.fromObject(imgs[0]);
        User user = userService.findById(userId);
        Voice voice = new Voice();
        voice.setContent(content);
        voice.setHost(user);
        voice.setTime(new Date());
        voice.setStars(0);
        for (int i = 0; i < temp.size(); i++) {
            System.out.println(temp.get(i));
            voice.getImgsSet().add(temp.get(i).toString());
        }
        voiceService.save(voice);
        return json;
    }

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public JSONObject addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        String userid = request.getParameter("UserId");
        int userId = Integer.parseInt(userid);
        //String icon=request.getParameter("icon");
        String description = request.getParameter("description");
        int gender = Integer.parseInt(request.getParameter("gender"));
        User user = userService.findById(userId);
        if (gender == 0)
            user.setIcon("../imgs/girl.png");//gender decide
        else if (gender == 1)
            user.setIcon("../imgs/boy.png");
        else user.setIcon("../imgs/unknown.png");
        user.setDescription(description);
        user.setGender(gender);
        //System.out.println("user save ok");
        userService.save(user);
        return json;
    }

    @RequestMapping(path = "/addComment")
    public JSONObject addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("add voice start");
        JSONObject json = new JSONObject();
        Comment comment = new Comment();
        int userid = Integer.parseInt(request.getParameter("UserId"));
        User user = userService.findById(userid);
        comment.setHost(user);
        int voiceBelongId = Integer.parseInt(request.getParameter("VoiceBelong"));
        Voice voice = voiceService.getVoiceById(voiceBelongId);
        comment.setBelong(voice);
        String type = request.getParameter("CommentType");
        if (type.equals("REPLY")) {
            int commentedUserId = Integer.parseInt(request.getParameter("CommentedUserID"));
            user = userService.findById(commentedUserId);
            comment.setCommented(user);
            comment.setType(0);
        } else
            comment.setType(1);
        String content = request.getParameter("CommentDesc");
        String res = SensitiveWordTree.censorWords(content);
        if (!res.equals("未匹配到敏感词")) {
            json.put("type", "illegal");
            return json;
        } else json.put("type", "success");
        comment.setContent(content);
        comment.setStars(0);
        Date time = new Date();
        comment.setTime(time);
        int floor = Integer.parseInt(request.getParameter("CommentNo"));
        comment.setFloor(floor);
        //楼号保存
        commentService.save(comment);
        //json.put("time", time);
        return json;
    }

    @RequestMapping(path = "/VoiceLike")
    public JSONObject voiceLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int voiceId = Integer.parseInt(request.getParameter("VoiceId"));
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String type = request.getParameter("type");

        Voice voice = voiceService.getVoiceById(voiceId);
        User user = userService.findById(userId);

        if (type.equals("like")) {
            // like
            userService.likeVoice(userId, voiceId);
            voice.starsInc();
            System.out.println("like");
        } else {
            // dislike
            userService.dislikeVoice(userId, voiceId);
            voice.starsDec();
            System.out.println("dislike");
        }
        voiceService.update(voice);
        return json;
    }

    @RequestMapping(path = "/CommentLike")
    public JSONObject commentLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("comment like start");
        JSONObject json = new JSONObject();
        int commentId = Integer.parseInt(request.getParameter("CommentId"));
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String type = request.getParameter("type");

        Comment comment = commentService.findById(commentId);
        User user = userService.findById(userId);

        if (type.equals("like")) {
            userService.likeComment(userId, commentId);
            comment.starsInc();
        } else {
            userService.dislikeComment(userId, commentId);
            comment.starsDec();
        }
        commentService.update(comment);
        return json;
    }

    @RequestMapping(path = "/getDateTags")
    public JSONArray getDateTags(HttpServletRequest request, HttpServletRequest response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        List<Tag> tags = tagService.getAll();
        for (Tag tag : tags) {
            int id = tag.getId();
            String content = tag.getContent();
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("name", content);
            array.add(json);
        }
        return array;
    }

    @RequestMapping(path = "/addDate")
    public JSONObject addDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        int tagId = Integer.parseInt(request.getParameter("TagId"));
        int maleMax = Integer.parseInt(request.getParameter("MaleNum"));
        int femaleMax = Integer.parseInt(request.getParameter("FemaleNum"));
        String content = request.getParameter("DateDesc");
        String res = SensitiveWordTree.censorWords(content);
        if (!res.equals("未匹配到敏感词")) {
            json.put("type", "illegal");
            return json;
        } else json.put("type", "success");
        String[] imgs = request.getParameterValues("DateImgs");
        JSONArray temp = JSONArray.fromObject(imgs[0]);
        String loc = request.getParameter("Location");
        JSONObject location = JSONObject.fromObject(loc);

        User user = userService.findById(userId);
        Tag tag = tagService.findById(tagId);
        Dating dating = new Dating();
        dating.setContent(content);
        dating.setHost(user);
        dating.setTime(new Date());
        dating.setMaxFemale(femaleMax);
        dating.setMaxMale(maleMax);
        dating.setTag(tag);
        dating.setLocationName(location.get("Name").toString());
        dating.setLatitude(Float.parseFloat(location.get("Latitude").toString()));
        dating.setLongitude(Float.parseFloat(location.get("Longitude").toString()));
        for (int i = 0; i < temp.size(); i++) {
            dating.getImgsSet().add(temp.get(i).toString());
        }
        datingService.save(dating);
        return json;
    }

    @RequestMapping(path = "/getDates")
    public JSONArray getDates(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        Boolean isAvailable = Boolean.parseBoolean(request.getParameter("HasChooseSeat"));
        int sortTag = Integer.parseInt(request.getParameter("TagId"));

        List<Dating> datings = new ArrayList<Dating>();
        if (sortTag == 0)
            datings = datingService.getAll();
        else {
            datings = datingService.getDatingsByTag(sortTag);
        }
        if (isAvailable == false) {
            for (Dating dating : datings) {
                JSONObject json = new JSONObject();
                json.put("DateId", dating.getId());
                json.put("TagId", dating.getTag().getId());
                json.put("TagName", dating.getTag().getContent());
                json.put("DateDesc", dating.getContent());
                json.put("DateTime", dating.getTime().getTime());
                array.add(json);
            }
        } else {
            for (Dating dating : datings) {
                //check if available
                if (dating.getParticipants().size() >= dating.getMaxFemale() + dating.getMaxMale())
                    continue;
                JSONObject json = new JSONObject();
                json.put("DateId", dating.getId());
                json.put("TagId", dating.getTag().getId());
                json.put("TagName", dating.getTag().getContent());
                json.put("DateDesc", dating.getContent());
                json.put("DateTime", dating.getTime().getTime());
                array.add(json);
            }
        }
        return array;
    }

    @RequestMapping(path = "/myDates")
    public JSONArray getMyDates(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();


        return array;
    }
}