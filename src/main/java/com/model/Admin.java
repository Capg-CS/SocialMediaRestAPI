package com.model;

import javax.persistence.*;
import javax.persistence.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Admin {
	@Id
	private String adminId;
	private String password;
	
}
