package com.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Post {
	@Id
	@GeneratedValue
	private int postId;
	private String postedBy;
	private Date date;
	private String description;
	private PostStatus status;
    public enum PostStatus {
		ACTIVE,
		BLOCKED;
	}
	@ElementCollection(targetClass=Integer.class)
	private List<Integer> comments;
	@ElementCollection(targetClass=Integer.class)
	private List<Integer> likes;
}
