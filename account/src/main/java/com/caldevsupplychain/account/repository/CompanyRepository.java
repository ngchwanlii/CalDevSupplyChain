package com.caldevsupplychain.account.repository;

import com.caldevsupplychain.account.model.Company;
import com.caldevsupplychain.account.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
	Company findByUser(User user);
}
