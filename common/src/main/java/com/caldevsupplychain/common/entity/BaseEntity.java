package com.caldevsupplychain.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true, updatable = false)
	private Long id;

	@Column(updatable = false)
	private Date createdOn;

	@Column(updatable = true)
	private Date lastModified;

	@PrePersist
	protected void onCreate() {
		setCreatedOn(new Date());
	}

	@PreUpdate
	protected void onPersist() {
		setLastModified(new Date());
	}
}
