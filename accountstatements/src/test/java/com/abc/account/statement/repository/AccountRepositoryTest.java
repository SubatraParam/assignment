/**
 * 
 */
package com.abc.account.statement.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.abc.account.statement.entity.Account;

/**
 * @author Subatra Shankar
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {
	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	public void testFindAllByAccountId() {
		List<Integer> ids = new ArrayList<>();
		ids.add(1);
		final List<Account> retrievedAccount = accountRepository.findAllById(ids);

		assertNotNull(retrievedAccount);
	}
	
	@Test
	public void testFindAllByAccouuntIdNoRecords() {
		List<Integer> ids = new ArrayList<>();
		ids.add(8);
		final List<Account> retrievedAccount = accountRepository.findAllById(ids);

		assertTrue(retrievedAccount.getClass().equals(ArrayList.class));
	}

}
