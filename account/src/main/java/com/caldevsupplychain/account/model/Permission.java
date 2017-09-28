package com.caldevsupplychain.account.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.caldevsupplychain.account.vo.PermissionName;

@Data
@Entity
@NoArgsConstructor
@Table(name = "permissions")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	@Enumerated(EnumType.STRING)
	private PermissionName name;

	Permission(String name) {
		this.name = PermissionName.valueOf(name);
	}
}
