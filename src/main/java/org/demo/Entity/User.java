package org.demo.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String openId;    //小程序用户id

    private String icon;    //img-url

    private String description;

    private int gender;        //0-female 1-male 2-unknown

    //voice
    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER)    //user owns a msg voice
    private Set<Voice> voices = new HashSet<Voice>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_voice_like", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "voice_id"))
    private Set<Voice> likeVoices = new HashSet<Voice>();

    //comment
    @OneToMany(mappedBy = "commented")    //user be commented
    private Set<Comment> beComments = new HashSet<Comment>();

    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER)    //user owns a msg Comment
    private Set<Comment> comments = new HashSet<Comment>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_comment_like", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> likeComments = new HashSet<Comment>();

    //dating
    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER)    //user owns a msg voice
    private Set<Dating> hostDatings = new HashSet<Dating>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private Set<Dating> participatedDatings = new HashSet<Dating>();

    //date comment
    @OneToMany(mappedBy = "dateCommented")    //user be commented in dating
    private Set<DateComment> beDateComments = new HashSet<DateComment>();

    @OneToMany(mappedBy = "host", fetch = FetchType.EAGER)    //user owns a msg Comment in dating
    private Set<DateComment> dateComments = new HashSet<DateComment>();

    @Override
    public String toString() {
        return "User [id=" + id + ", openId=" + openId + ", icon=" + icon + ", description=" + description + ", gender="
                + gender + ", beComments=" + beComments + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Set<Voice> getVoices() {
        return voices;
    }

    public Set<Comment> getBeComments() {
        return beComments;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Set<Dating> getHostDatings() {
        return hostDatings;
    }

    public Set<Dating> getParticipatedDatings() {
        return participatedDatings;
    }

    public Set<DateComment> getBeDateComments() {
        return beDateComments;
    }

    public Set<DateComment> getDateComments() {
        return dateComments;
    }

    public Set<Voice> getLikeVoices() {
        return likeVoices;
    }

    public Set<Comment> getLikeComments() {
        return likeComments;
    }
/*@ManyToMany(cascade=CascadeType.PERSIST)	//user map a piece of comment
	@JoinTable(name="t_comment_user", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="comment_id"))
	private Set<Comment> myComments= new HashSet<Comment>();*/
}
