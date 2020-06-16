package org.demo.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Tag {
	@Id
	@GeneratedValue
	private int id;
	
	private String content;

	@OneToMany(mappedBy="tag")
	private Set<Dating> datings = new HashSet<Dating>();
	
	
	@Override
	public String toString() {
		return "Tag [id=" + id + ", content=" + content + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Dating> getDatings() {
		return datings;
	}
	
	
}
