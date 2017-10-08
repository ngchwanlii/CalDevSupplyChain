package com.caldevsupplychain.account.repository;

import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.vo.RoleName;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
	Role findByName(RoleName name);

	List<Role> findByName(List<RoleName> name);
}
