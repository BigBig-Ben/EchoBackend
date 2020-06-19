package org.demo.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DateDiscussion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int floor;		//floor number

    private String content;

    private Date time;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
    private User host;		//评论归属用户

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Dating belong;	//评论归属留言

    @Override
    public String toString() {
        return "{id=" + id + ", floor=" + floor + ", content=" + content + ", time=" + time
                 + ", host=" + host + ", belong=" + belong + "}";
    }

    public DateDiscussion() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Dating getBelong() {
        return belong;
    }

    public void setBelong(Dating belong) {
        this.belong = belong;
    }
}
