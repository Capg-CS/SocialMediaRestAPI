package com.model;

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
public class Groups {
	
	@Id
	private String groupId;
	private String groupName;
	private String createdBy;
	private Status status;
	/*public enum GroupStatus{
		ACTIVE,
		BLOCKED,
		PENDING;
	}
	*/
	@JsonIgnore
	@ManyToOne
	private Users users;
	
	
	
	
	
}
