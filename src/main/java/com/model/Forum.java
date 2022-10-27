package com.model;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Forum {
	
	@Id
	private String forumId;
	private String forumName;
	private String createdBy;
	private String createdOn;
	private Status status = Status.PENDING;
	/*public enum ForumStatus{
		ACTIVE,
		BLOCKED,
		PENDING;		
	}*/
	@JsonIgnore
	@ManyToOne
	private Users users;
}