package com.model;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class FriendRequest {
	@Id
	@GeneratedValue
	private int requestId;
	private String requestedBy;
	private String requestedTo;
	private Date date;
}
