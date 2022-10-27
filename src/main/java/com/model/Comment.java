package com.model;
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
public class Comment {
	@Id
	@GeneratedValue
	private int commentId;
	private String commentText;
	private String commentBy;
	private CommentStatus status;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Likes> likes;
	
	public enum CommentStatus{
		ACTIVE,
		BLOCKED;
	}
}
