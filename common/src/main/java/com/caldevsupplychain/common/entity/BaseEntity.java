package com.caldevsupplychain.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
