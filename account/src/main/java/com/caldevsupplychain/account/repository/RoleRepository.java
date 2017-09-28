package com.caldevsupplychain.account.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.vo.RoleName;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
	Role findByName(RoleName name);
}
