package com.caldevsupplychain.account.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.caldevsupplychain.account.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUuid(String uuid);

	User findByUsername(String username);

	User findByEmailAddress(String emailAddress);

	User findByToken(String token);
}
