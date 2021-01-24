package com.abc.account.statement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.account.statement.entity.Account;

/**
 * @author Subatra Shankar
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
