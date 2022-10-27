package com.model;
import javax.persistence.*;
import javax.persistence.Id;

import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Likes {
	@Id
	@GeneratedValue
	private int likeId;
	private String likeBy;
}
