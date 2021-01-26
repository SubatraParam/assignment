/**
 * 
 */
package com.abc.account.statement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.abc.account.statement.model.AccountStatement;
import com.abc.account.statement.model.UserRequest;
import com.abc.account.statement.service.AccountStatementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Subatra Shankar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class AccountStatementControllerTest {

	private static final String ACCOUNT_TYPE = "CURRENT";

	private static final String ACCOUNT_NUMBER = "123456789";

	@InjectMocks
	AccountStatementController accountStatmentController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	AccountStatementService accountStatementService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		when(accountStatementService.getAccountStatement(ArgumentMatchers.any(UserRequest.class)))
				.thenReturn(mockAccount());
	}

	private AccountStatement mockAccount() {
		AccountStatement accountStatement = new AccountStatement();
		accountStatement.setAccountNumber(ACCOUNT_NUMBER);
		accountStatement.setAccountType(ACCOUNT_TYPE);
		accountStatement.setAccountId(1);
		accountStatement.setMessage("Retrieved account statement details successfully");
		return accountStatement;
	}

	@Test
	@WithMockUser(username = "testadmin", password = "adminpassword", roles = "ADMIN")
	public void testGetAccountStatmentByAdmin() throws Exception {
		final MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/securedadmin/account/statement/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		final String expectedJson = this.mapToJson(mockAccount());
		final String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	@Test
	@WithMockUser(username = "testadmin", password = "adminpassword", roles = "ADMIN")
	public void testGetAccountStatmentByAdminNoAccess() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/secureduser/account/statement/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "testadmin", password = "adminpassword", roles = "ADMIN")
	public void testGetAccountStatmentByAdminInvalidDateRange() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/secureduser/account/statement/1?fromDate=12.01.2021&toDate=30.12.2020")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "testadmin", password = "adminpassword", roles = "ADMIN")
	public void testGetAccountStatmentByAdminInvalidAmountRange() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/secureduser/account/statement/1?fromAmount=100&toAmount=60")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "testadmin", password = "adminpassword", roles = "ADMIN")
	public void testGetAccountStatmentByAdminInValidAccountId() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/securedadmin/account/statement/0").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "testuser", password = "userpassword", roles = "USER")
	public void testGetAccountStatmentByUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/secureduser/account/statement/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "testuser", password = "userpassword", roles = "USER")
	public void testGetAccountStatmentByUserNoAccess() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/securedadmin/account/statement/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@WithMockUser(username = "testuser", password = "userpassword", roles = "USER")
	public void testGetAccountStatmentByUserInValidAccountId() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/secureduser/account/statement/0").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
