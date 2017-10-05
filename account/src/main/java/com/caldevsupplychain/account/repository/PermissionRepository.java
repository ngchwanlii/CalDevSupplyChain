package com.caldevsupplychain.account.repository;

import com.caldevsupplychain.account.model.Permission;
import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.vo.PermissionName;
import com.caldevsupplychain.account.vo.RoleName;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
	Permission findByName(PermissionName name);
}
