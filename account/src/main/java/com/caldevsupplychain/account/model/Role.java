package com.caldevsupplychain.account.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.caldevsupplychain.account.vo.RoleName;

@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
public class Role {
	public Role(String name){
		this.name = RoleName.valueOf(name);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	@Enumerated(EnumType.STRING)
	private RoleName name;

	@ManyToMany
	@JoinTable(name = "role_2_permission", joinColumns = @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<Permission> permissions = new ArrayList<>();
}
