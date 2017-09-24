package com.caldevsupplychain.account.repository;


import com.caldevsupplychain.account.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUuid(String uuid);

    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    User findByToken(String token);
}
