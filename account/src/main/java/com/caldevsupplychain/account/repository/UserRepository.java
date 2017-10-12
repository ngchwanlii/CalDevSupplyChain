package com.caldevsupplychain.account.repository;


import com.caldevsupplychain.account.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUuid(String uuid);

	User findByEmailAddress(String emailAddress);

	User findByToken(String token);

	Page<User> findAll(Pageable pageable);

}

