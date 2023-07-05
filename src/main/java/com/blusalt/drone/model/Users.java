package com.blusalt.drone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_USERS")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PX")
	private String px;

	@Column(name = "ENTERED_BY")
	private String enteredBy;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Getter
	@Basic
	@CreationTimestamp
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Column(name = "ENTERED_DATE")
	private java.time.LocalDateTime enteredDate;

	@Getter
	@Basic
	@UpdateTimestamp
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Column(name = "MODIFIED_DATE")
	private java.time.LocalDateTime modifiedDate;

}