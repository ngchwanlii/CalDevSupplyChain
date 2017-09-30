package com.caldevsupplychain.account.repository;

import com.caldevsupplychain.account.model.Company;
import com.caldevsupplychain.account.model.Permission;
import com.caldevsupplychain.account.model.Role;
import com.caldevsupplychain.account.model.User;
import com.caldevsupplychain.account.vo.PermissionName;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
	Company findByUser(User user);
}
