package org.demo.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Voice {    //the first layer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date time;

    private String content;

    private int stars;        //点赞

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    private Set<String> imgs = new HashSet<String>();        //imgURL

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User host;

    //@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn
	//private Tag dateTag;
    private String tags;

    @OneToMany(mappedBy = "belong", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<Comment>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_voice_like", joinColumns = @JoinColumn(name="voice_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> whoLikes = new HashSet<User>();

    public void starsInc() {
        stars++;
    }

    public void starsDec() {
        stars--;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", time=" + time + ", content=" + content + ", stars=" + stars + ", imgs=" + imgs
                + ", host=" + host + ", msgLabel=" + tags + ", comments=" + comments + "}";
    }

    public Voice() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
        this.stars = stars;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgs() {
        List<String> res = new ArrayList<String>(imgs);
        return res;
    }

    public Set<String> getImgsSet() {
        return imgs;
    }

    public Set<User> getWhoLikes() {
        return whoLikes;
    }
}
