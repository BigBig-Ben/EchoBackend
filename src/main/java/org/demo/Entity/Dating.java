package org.demo.Entity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Dating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int maxMale;

    private int maxFemale;

    private Date time;

    private String content;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    private Set<String> imgs = new HashSet<String>();        //imgURL

    @ManyToOne(fetch = FetchType.EAGER)     // dating host
    @JoinColumn
    private User host;

    @ManyToMany(fetch = FetchType.EAGER)    // dating participants
    @JoinTable(name = "t_participant_user", joinColumns = @JoinColumn(name = "dating_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<User>();

    @ManyToOne(fetch = FetchType.LAZY)      // dating tag
    @JoinColumn
    private Tag tag;

    @OneToMany(mappedBy = "belong", fetch = FetchType.EAGER)
    private Set<DateComment> comments = new HashSet<DateComment>();

    @Override
    public String toString() {
        return "{id=" + id + ", time=" + time + ", content=" + content + ", imgs=" + imgs
                + ", host=" + host + ", tag=" + tag + ", comments=" + comments + "}";
    }

    public Dating() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public Set<DateComment> getComments() {
        return comments;
    }

    public int getMaxMale() {
        return maxMale;
    }

    public void setMaxMale(int maxMale) {
        this.maxMale = maxMale;
    }

    public int getMaxFemale() {
        return maxFemale;
    }

    public void setMaxFemale(int maxFemale) {
        this.maxFemale = maxFemale;
    }
}
