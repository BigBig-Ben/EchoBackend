package org.demo.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DateComment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int floor;		//floor number

    private String content;

    private Date time;

    private int type;		//type 0:comment 1:reply

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn
    private User dateCommented;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn
    private User host;		//评论归属用户

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn
    private Dating belong;	//评论归属留言

    @Override
    public String toString() {
        return "{id=" + id + ", floor=" + floor + ", content=" + content + ", time=" + time
                + ", type=" + type + ", commented=" + dateCommented + ", host=" + host + ", belong=" + belong + "}";
    }

    public DateComment() {

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getDateCommented() {
        return dateCommented;
    }

    public void setDateCommented(User dateCommented) {
        this.dateCommented = dateCommented;
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
