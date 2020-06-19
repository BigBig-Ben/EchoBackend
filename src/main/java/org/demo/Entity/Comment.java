package org.demo.Entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
public class Comment {	//the second layer
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int floor;		//floor number
	
	private String content;
	
	private Date time;

	private int type;		//type 0:comment 1:reply

    // 展示废置，后期该提示时使用
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	private User commented;

	private int commentedFloor;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	private User host;		//评论归属用户
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	private Voice belong;	//评论归属留言

	@ManyToMany(mappedBy = "likeComments", fetch = FetchType.EAGER)
	private Set<User> whoLikes = new HashSet<User>();


	@Override
	public String toString() {
		return "{id=" + id + ", floor=" + floor + ", content=" + content + ", time=" + time
				+ ", type=" + type + ", commented=" + commented + ", host=" + host + ", belong=" + belong + "}";
	}

	public Comment() {

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

    public int getCommentedFloor() {
        return commentedFloor;
    }

    public void setCommentedFloor(int commentedFloor) {
        this.commentedFloor = commentedFloor;
    }

    public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public Voice getBelong() {
		return belong;
	}

	public void setBelong(Voice belong) {
		this.belong = belong;
	}

	public Set<User> getWhoLikes() {
		return whoLikes;
	}
}
