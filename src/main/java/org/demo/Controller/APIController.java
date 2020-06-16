package org.demo.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.demo.Entity.*;
import org.demo.service.*;
import org.demo.util.SensitiveWordTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
        for (Voice v : voices) {
            JSONObject temp = new JSONObject();
            temp.put("VoiceId", v.getId());
            temp.put("VoiceIcon", icon);
            temp.put("VoiceDesc", v.getContent());
            temp.put("VoiceImgs", v.getImgs());
            temp.put("VoiceLike", v.getStars());
            //temp.put("VoiceTime", format1.format(v.getTime().getTime()));
            temp.put("VoiceTime", v.getTime().getTime());
            array.add(temp);
        }
        json.put("Voices", array);
        return json;
    }

    @RequestMapping(path = "/getOneVoice")
    public JSONObject getOneVoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject obj = new JSONObject();
        int id = Integer.parseInt(request.getParameter("VoiceId"));
        Voice voice = voiceService.getVoiceById(id);
        User user = voice.getHost();
        obj.put("VoiceID", voice.getId());
        obj.put("VoiceIcon", user.getIcon());
        obj.put("VoiceDesc", voice.getContent());
        obj.put("VoiceImgs", voice.getImgs());
        obj.put("VoiceLike", voice.getStars());
        //obj.put("VoiceTime", format1.format(voice.getTime().getTime()));
        obj.put("VoiceTime", voice.getTime().getTime());

        return obj;
    }

    @RequestMapping(path = "/getVoiceComment")
    public JSONArray getVoiceComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        int id = Integer.parseInt(request.getParameter("VoiceId"));
        Voice voice = voiceService.getVoiceById(id);
        List<Comment> comments = new ArrayList<Comment>(voice.getComments());
        Collections.reverse(comments);
        for (Comment c : comments) {
            JSONObject temp = new JSONObject();
            temp.put("UserId", c.getHost().getId());
            temp.put("CommentID", c.getId());
            temp.put("CommentIcon", c.getHost().getIcon());
            temp.put("CommentType", c.getType());
            temp.put("CommentDesc", c.getContent());
            temp.put("CommentLike", c.getStars());
            //if(c.getType() == 0)
            //temp.put("CommentedUserID", c.getCommented().getId());
            temp.put("CommentTime", c.getTime());
            //temp.put("CommentTime", format1.format(c.getTime()));
            temp.put("CommentedNo", c.getFloor());
            array.add(temp);
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
        for (Voice v : voices) {
            JSONObject temp = new JSONObject();
            temp.put("VoiceId", v.getId());
            temp.put("VoiceIcon", v.getHost().getIcon());
            temp.put("VoiceDesc", v.getContent());
            List<String> imgs = v.getImgs();
            String[] imgsss = imgs.toArray(new String[imgs.size()]);
            temp.put("VoiceImgs", imgsss);
            temp.put("VoiceLike", v.getStars());
            //temp.put("VoiceTime", format1.format(v.getTime().getTime()));
            temp.put("VoiceTime", v.getTime().getTime());
            temp.put("CommentCnt", v.getComments().size());
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
//		for(int i=0; i<java.lang.reflect.Array.getLength(imgs); i++)
//		{
//			System.out.println(imgs[i]);
//			voice.getImgs().add(imgs[i]);
//		}
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
        System.out.println("voice like start");
        JSONObject json = new JSONObject();
        int voiceId = Integer.parseInt(request.getParameter("VoiceId"));
        String type = request.getParameter("type");
        Voice voice = voiceService.getVoiceById(voiceId);
        if (type.equals("true")) {
            voice.starsInc();
        } else voice.starsDec();
        voiceService.update(voice);
        return json;
    }

    @RequestMapping(path = "/CommentLike")
    public JSONObject commentLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("comment like start");
        JSONObject json = new JSONObject();
        int voiceId = Integer.parseInt(request.getParameter("CommentId"));
        String type = request.getParameter("type");
        Comment comment = commentService.findById(voiceId);
        if (type.equals("true")) {
            comment.starsInc();
        } else comment.starsDec();
        commentService.update(comment);
        return json;
    }
}