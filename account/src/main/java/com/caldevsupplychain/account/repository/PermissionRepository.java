package com.caldevsupplychain.account.repository;

import com.caldevsupplychain.account.model.Permission;
import com.caldevsupplychain.account.vo.PermissionName;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
	Permission findByName(PermissionName name);
}
