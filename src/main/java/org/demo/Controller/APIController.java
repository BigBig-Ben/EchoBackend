package org.demo.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
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

import static org.demo.EchoApplication.getRandomName;

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
    private DateDiscussionService dateDiscussionService;

    @Autowired
    private MessageService messageService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH时mm分");

    @RequestMapping(path = "/getUserInfo")
    public JSONObject getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int id = Integer.parseInt(request.getParameter("UserId"));

        User user = userService.findById(id);
        json.put("Gender", user.getGender());
        json.put("Icon", user.getIcon());
        json.put("Motto", user.getDescription());
        json.put("BgImg", user.getBackground());
        return json;
    }

    @RequestMapping(path = "/getUserVoices")
    public JSONObject getUserVoices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));

        User user = userService.findById(userId);
        JSONArray array = new JSONArray();
        List<Voice> voices = new ArrayList<Voice>(voiceService.getVoicesByUserId(userId));
        for (Voice voice : voices) {
            JSONObject temp = new JSONObject();
            temp.put("VoiceId", voice.getId());
            temp.put("VoiceIcon", user.getIcon());
            temp.put("VoiceDesc", voice.getContent());
            temp.put("VoiceImgs", voice.getImgs());
            temp.put("VoiceLike", voice.getWhoLikes().size());
            temp.put("TagStr", voice.getTags());
            temp.put("Time", voice.getTime().getTime());
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

        json.put("Name", getRandomName());
        json.put("HostId", voice.getHost().getId());
        json.put("VoiceID", voice.getId());
        json.put("VoiceIcon", user.getIcon());
        json.put("VoiceDesc", voice.getContent());
        json.put("VoiceImgs", voice.getImgs());
        json.put("VoiceLike", voice.getWhoLikes().size());
        json.put("Time", voice.getTime().getTime());
        json.put("TagStr", voice.getTags());
        boolean flag = false;
        Set<Voice> likedVoices = user.getLikeVoices();
        for (Voice v : likedVoices) {
            if (v.getId() == voice.getId()) {
                flag = true;
                break;
            }
        }
        if (flag)
            json.put("VoiceHasLiked", Boolean.TRUE);
        else json.put("VoiceHasLiked", Boolean.FALSE);
        return json;
    }

    @RequestMapping(path = "/getVoiceComment")
    public JSONArray getVoiceComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        int voiceId = Integer.parseInt(request.getParameter("VoiceId"));
        int userId = Integer.parseInt(request.getParameter("UserId"));
        User user = userService.findById(userId);

        List<Comment> comments = new ArrayList<Comment>(commentService.getCommentsByVoiceId(voiceId));
        Set<Comment> likedComments = user.getLikeComments();
        for (Comment comment : comments) {
            JSONObject json = new JSONObject();
            json.put("Name", getRandomName());
            json.put("UserId", comment.getHost().getId());
            json.put("CommentID", comment.getId());
            json.put("CommentIcon", comment.getHost().getIcon());
            if (comment.getType() == 0)
                json.put("CommentType", "REPLY");
            else json.put("CommentType", "COMMENT");
            json.put("CommentDesc", comment.getContent());
            json.put("CommentLike", comment.getWhoLikes().size());
            json.put("Floor", comment.getFloor());
            json.put("Time", comment.getTime().getTime());
            json.put("CommentedFloor", comment.getCommentedFloor());
            boolean flag = false;
            for (Comment c : likedComments) {
                if (c.getId() == comment.getId()) {
                    flag = true;
                    break;
                }
            }
            if (flag)
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
        int userId = Integer.parseInt(request.getParameter("UserId"));

        User user = userService.findById(userId);
        Set<Voice> likedVoices = user.getLikeVoices();
        List<Voice> voices = new ArrayList<Voice>();
        if (sortType.equals("HOT")) {
            voices = voiceService.getVoicesOrderByStarsDesc();
        } else {
            voices = voiceService.getVoicesOrderByTimeDesc();
        }
        //voices = voiceService.getAll();
        for (Voice voice : voices) {
            JSONObject json = new JSONObject();
            json.put("Name", getRandomName());
            json.put("VoiceId", voice.getId());
            json.put("VoiceIcon", voice.getHost().getIcon());
            json.put("VoiceDesc", voice.getContent());
            List<String> imgs = voice.getImgs();
            String[] imgsss = imgs.toArray(new String[imgs.size()]);
            json.put("VoiceImgs", imgsss);
            json.put("VoiceLike", voice.getWhoLikes().size());
            json.put("Time", voice.getTime().getTime());
            json.put("CommentCnt", voice.getComments().size());
            json.put("TagStr", voice.getTags());

            boolean flag = false;
            for (Voice v : likedVoices) {
                if (v.getId() == voice.getId()) {
                    flag = true;
                    break;
                }
            }
            if (flag)
                json.put("HasLike", Boolean.TRUE);
            else json.put("HasLike", Boolean.FALSE);

            array.add(json);
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
        String tags = request.getParameter("TagStr");

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
        voice.setStars(0);
        voice.setTime(new Date());
        voice.setTags(tags);
        for (int i = 0; i < temp.size(); i++) {
            System.out.println(temp.get(i));
            voice.getImgsSet().add(temp.get(i).toString());
        }
        voiceService.save(voice);
        return json;
    }

    @RequestMapping(path = "/deleteVoice")
    public JSONObject deleteVoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        JSONObject json = new JSONObject();
        int voiceId = Integer.parseInt(request.getParameter("VoiceId"));

        voiceService.delete(voiceId);
        return json;
    }

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public JSONObject addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String description = request.getParameter("description");
        int gender = Integer.parseInt(request.getParameter("gender"));
        User user = userService.findById(userId);

        //TODO
        if (gender == 0)
            user.setIcon("../imgs/girl.png");   //gender decide
        else if (gender == 1)
            user.setIcon("../imgs/boy.png");
        else user.setIcon("../imgs/unknown.png");
        user.setDescription(description);
        user.setGender(gender);
        userService.save(user);
        return json;
    }

    @RequestMapping(path = "/addComment")
    public JSONObject addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        Comment comment = new Comment();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        int voiceBelongId = Integer.parseInt(request.getParameter("VoiceBelong"));
        String type = request.getParameter("CommentType");
        String content = request.getParameter("CommentDesc");
        int floor = Integer.parseInt(request.getParameter("CommentNo"));

        String res = SensitiveWordTree.censorWords(content);
        if (!res.equals("未匹配到敏感词")) {
            json.put("type", "illegal");
            return json;
        } else json.put("type", "success");
        User user = userService.findById(userId);
        Voice voice = voiceService.getVoiceById(voiceBelongId);

        if (type.equals("REPLY")) {
            int commentedFloor = Integer.parseInt(request.getParameter("CommentedFloor"));
            int commentedUser = Integer.parseInt(request.getParameter("CommentedUser"));
            User commented = userService.findById(commentedUser);
            comment.setCommented(commented);
            comment.setCommentedFloor(commentedFloor);
            comment.setType(0);
        } else
            comment.setType(1);

        comment.setHost(user);
        comment.setBelong(voice);
        comment.setContent(content);
        comment.setTime(new Date());
        comment.setFloor(floor);
        commentService.save(comment);
        //message
        List<Comment> newOnes = new ArrayList<Comment>(commentService.getTheNewCommentByVoiceFloor(voiceBelongId, floor));
        Comment theNewOne = newOnes.get(0);
        if (type.equals("REPLY")) {
            int commentedUser = Integer.parseInt(request.getParameter("CommentedUser"));
            messageService.addMessage(commentedUser, voiceBelongId, theNewOne.getId());
        } else {
            messageService.addMessage(voice.getHost().getId(), voiceBelongId, theNewOne.getId());
        }
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
        } else {
            // dislike
            userService.dislikeVoice(userId, voiceId);
        }
        voice.setStars(voice.getWhoLikes().size());
        voiceService.update(voice);
        return json;
    }

    @RequestMapping(path = "/CommentLike")
    public JSONObject commentLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int commentId = Integer.parseInt(request.getParameter("CommentId"));
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String type = request.getParameter("type");

        Comment comment = commentService.findById(commentId);
        User user = userService.findById(userId);

        if (type.equals("like")) {
            // like
            userService.likeComment(userId, commentId);
        } else {
            // dislike
            userService.dislikeComment(userId, commentId);
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
        dating.getParticipants().add(user);
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
            datings = datingService.getAllDatings();
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
                json.put("Time", dating.getTime().getTime());
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
                json.put("Time", dating.getTime().getTime());
                array.add(json);
            }
        }
        return array;
    }

    @RequestMapping(path = "/getMyDates")
    public JSONArray getMyDates(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        Boolean isAvailable = Boolean.parseBoolean(request.getParameter("HasChooseSeat"));
        int sortTag = Integer.parseInt(request.getParameter("TagId"));

        User user = userService.findById(userId);
        List<Dating> myDatings = new ArrayList<Dating>(datingService.getDatingsByUserId(userId));
        if (sortTag == 0) {
            // all tags
            if (!isAvailable) {
                for (Dating dating : myDatings) {
                    JSONObject json = new JSONObject();
                    json.put("DateId", dating.getId());
                    json.put("TagId", dating.getTag().getId());
                    json.put("TagName", dating.getTag().getContent());
                    json.put("DateDesc", dating.getContent());
                    json.put("Time", dating.getTime().getTime());
                    array.add(json);
                }
            } else {
                for (Dating dating : myDatings) {
                    //check if available
                    if (dating.getParticipants().size() >= dating.getMaxFemale() + dating.getMaxMale())
                        continue;
                    JSONObject json = new JSONObject();
                    json.put("DateId", dating.getId());
                    json.put("TagId", dating.getTag().getId());
                    json.put("TagName", dating.getTag().getContent());
                    json.put("DateDesc", dating.getContent());
                    json.put("Time", dating.getTime().getTime());
                    array.add(json);
                }
            }
        } else {
            //sort by tags
            if (!isAvailable) {
                for (Dating dating : myDatings) {
                    if (dating.getTag().getId() != sortTag)
                        continue;
                    JSONObject json = new JSONObject();
                    json.put("DateId", dating.getId());
                    json.put("TagId", dating.getTag().getId());
                    json.put("TagName", dating.getTag().getContent());
                    json.put("DateDesc", dating.getContent());
                    json.put("Time", dating.getTime().getTime());
                    array.add(json);
                }
            } else {
                for (Dating dating : myDatings) {
                    if (dating.getTag().getId() != sortTag)
                        continue;
                    //check if available
                    if (dating.getParticipants().size() >= dating.getMaxFemale() + dating.getMaxMale())
                        continue;
                    JSONObject json = new JSONObject();
                    json.put("DateId", dating.getId());
                    json.put("TagId", dating.getTag().getId());
                    json.put("TagName", dating.getTag().getContent());
                    json.put("DateDesc", dating.getContent());
                    json.put("Time", dating.getTime().getTime());
                    array.add(json);
                }
            }
        }
        return array;
    }

    @RequestMapping(path = "/getOneDate")
    public JSONObject getOneDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject res = new JSONObject();
        int dateId = Integer.parseInt(request.getParameter("DateId"));

        Dating dating = datingService.getDatingById(dateId);
        List<User> users = new ArrayList<User>(dating.getParticipants());
        JSONArray participants = new JSONArray();
        for (User user : users) {
            JSONObject json = new JSONObject();
            json.put("UserId", user.getId());
            json.put("UserIcon", user.getIcon());
            json.put("UserGender", user.getGender());
            participants.add(json);
        }
        res.put("Users", participants);
        JSONObject jsonDating = new JSONObject();
        jsonDating.put("DateDesc", dating.getContent());
        jsonDating.put("DateImgs", dating.getImgs());
        jsonDating.put("Time", dating.getTime().getTime());
        jsonDating.put("MaleNum", dating.getMaxMale());
        jsonDating.put("FemaleNum", dating.getMaxFemale());
        jsonDating.put("DateHost", dating.getHost().getId());
        JSONObject loc = new JSONObject();
        loc.put("Name", dating.getLocationName());
        loc.put("Latitude", dating.getLatitude());
        loc.put("Longitude", dating.getLongitude());
        jsonDating.put("Location", loc);
        res.put("Date", jsonDating);
        return res;
    }

    @RequestMapping(path = "/joinDate")
    public JSONObject participateDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        int datingId = Integer.parseInt(request.getParameter("DateId"));

        User user = userService.findById(userId);
        Dating dating = datingService.getDatingById(datingId);
        List<User> participants = new ArrayList<User>(dating.getParticipants());
        int hasJoinedFemale = 0;
        int hasJoinedMale = 0;
        for (User one : participants) {
            if (one.getGender() == 0)
                hasJoinedFemale++;
            else if (one.getGender() == 1)
                hasJoinedMale++;
        }
        if (user.getGender() == 0) {
            //female
            if (hasJoinedFemale < dating.getMaxFemale()) {
                dating.getParticipants().add(user);
                datingService.update(dating);
            } else {
                json.put("msg", "seat for female is not available");
                return json;
            }
        } else if (user.getGender() == 1) {
            //male
            if (hasJoinedMale < dating.getMaxMale()) {
                dating.getParticipants().add(user);
                datingService.update(dating);
            } else {
                json.put("msg", "seat for male is not available");
                return json;
            }
        } else {
            //unknown
            if (dating.getParticipants().size() < dating.getMaxMale() + dating.getMaxFemale()) {
                dating.getParticipants().add(user);
                datingService.update(dating);
            } else {
                json.put("msg", "no seat is available");
                return json;
            }
        }
        json.put("msg", "success");
        return json;
    }

    @RequestMapping(path = "/leaveDate")
    public JSONObject leaveDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        int datingId = Integer.parseInt(request.getParameter("DateId"));

        User user = userService.findById(userId);
        Dating dating = datingService.getDatingById(datingId);

        if (userId != dating.getHost().getId()) {
            // not host
            datingService.leaveDatingByOne(datingId, userId);
            System.out.println(datingId + "--" + userId);
        } else {
            // host leaves
            datingService.delete(datingId);
        }
        json.put("msg", "success");
        return json;
    }

    @RequestMapping(path = "/addDiscussion")
    public JSONObject addDiscussion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        int dateId = Integer.parseInt(request.getParameter("DateId"));
        String content = request.getParameter("DisDesc");
        String res = SensitiveWordTree.censorWords(content);
        if (!res.equals("未匹配到敏感词")) {
            json.put("type", "illegal");
            return json;
        } else json.put("type", "success");

        Dating dating = datingService.getDatingById(dateId);
        User user = userService.findById(userId);
        DateDiscussion discussion = new DateDiscussion();
        discussion.setHost(user);
        discussion.setBelong(dating);
        discussion.setContent(content);
        discussion.setTime(new Date());
        discussion.setFloor(dating.getDiscussions().size() + 1);
        dating.getDiscussions().add(discussion);
        dateDiscussionService.save(discussion);
        datingService.update(dating);
        return json;
    }

    @RequestMapping(path = "/getDiscussions")
    public JSONArray getDiscussions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        int dateId = Integer.parseInt(request.getParameter("DateId"));

        List<DateDiscussion> discussions = new ArrayList<DateDiscussion>(dateDiscussionService.getDateDiscussionsByDatingId(dateId));
        for (DateDiscussion discussion : discussions) {
            JSONObject json = new JSONObject();
            User owner = discussion.getHost();
            json.put("UserIcon", owner.getIcon());
            json.put("UserName", getRandomName());
            json.put("Time", discussion.getTime().getTime());
            json.put("Desc", discussion.getContent());
            array.add(json);
        }
        return array;
    }

    @RequestMapping(path = "/getIcons")
    public JSONArray getIcons(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        for (int i = 1; i < 60; i++) {
            JSONObject json = new JSONObject();
            json.put("Id", i);
            json.put("URL", "http://121.89.204.192:8080/images/icon/" + i + ".png");
            array.add(json);
        }
        return array;
    }

    @RequestMapping(path = "/setIcon")
    public JSONObject setIcon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String imgURL = request.getParameter("Icon");

        User user = userService.findById(userId);
        user.setIcon(imgURL);
        userService.update(user);
        json.put("msg", "success");
        return json;
    }

    @RequestMapping(path = "/setUser")
    public JSONObject setUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String imgURL = request.getParameter("Icon");
        String motto = request.getParameter("Motto");
        int gender = Integer.parseInt(request.getParameter("Gender"));

        User user = userService.findById(userId);
        user.setIcon(imgURL);
        user.setDescription(motto);
        user.setGender(gender);
        userService.update(user);
        json.put("msg", "success");
        return json;
    }

    @RequestMapping(path = "/setBg")
    public JSONObject setBackground(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));
        String imgURL = request.getParameter("BgImg");

        User user = userService.findById(userId);
        user.setBackground(imgURL);
        userService.update(user);
        json.put("msg", "success");
        return json;
    }

    @RequestMapping(path = "/getMsgComments")
    public JSONArray getMessageComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray array = new JSONArray();
        int userId = Integer.parseInt(request.getParameter("UserId"));

        List<Message> messages =messageService.getMessagesByUser(userId);
        for(Message message:messages){
            // comment detail
            Comment comment = commentService.findById(message.getCommentId());
            JSONObject json = new JSONObject();
            json.put("Icon", comment.getHost().getIcon());
            json.put("Time", message.getTime().getTime());
            json.put("Name", getRandomName());
            json.put("Desc", comment.getContent());
            //voice detail
            Voice voice = voiceService.getVoiceById(message.getVoiceId());
            JSONObject jsonVoice = new JSONObject();
            jsonVoice.put("VoiceId", voice.getId());
            jsonVoice.put("VoiceDesc", voice.getContent());
            List<String> imgs = voice.getImgs();
            String[] images = imgs.toArray(new String[imgs.size()]);
            jsonVoice.put("VoiceImgs", images);
            jsonVoice.put("TagStr", voice.getTags());
            json.put("Voice", jsonVoice);
            array.add(json);
            // set read
            messageService.readMessage(userId, message.getCommentId());
        }
        return array;
    }

    @RequestMapping(path = "/getMsgCnt")
    public JSONObject getMsgCnt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        JSONObject json = new JSONObject();
        int userId = Integer.parseInt(request.getParameter("UserId"));

        json.put("Count", messageService.getUnreadMessagesCount(userId));
        return json;
    }

}