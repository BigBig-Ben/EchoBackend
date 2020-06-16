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
	
	private int stars; 		//点赞
	
	private int type;		//type 0:comment 1:reply
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	private User commented;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	private User host;		//评论归属用户
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn
	private Voice belong;	//评论归属留言

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_comment_like", joinColumns = @JoinColumn(name="comment_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
	private Set<User> whoLikes = new HashSet<User>();
	
	public void starsInc()
	{
		stars++;
	}
	
	public void starsDec()
	{
		stars--;
	}
	

	@Override
	public String toString() {
		return "{id=" + id + ", floor=" + floor + ", content=" + content + ", time=" + time + ", stars=" + stars
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

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public User getCommented() {
		return commented;
	}

	public void setCommented(User commented) {
		this.commented = commented;
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
