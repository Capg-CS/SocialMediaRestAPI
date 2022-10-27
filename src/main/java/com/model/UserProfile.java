package com.model;
import javax.persistence.*;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class UserProfile {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int userProfileId;
    private String bio;
    private String status;    
    
    @OneToOne
    @JsonBackReference
    private Users user;
	
}
