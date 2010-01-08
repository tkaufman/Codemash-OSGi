package com.pillartech.raffle.persistence.internal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.RaffleDAO;

@ContextConfiguration(locations = { "/META-INF/spring/persistence-context.xml" })
public class RaffleDAOImplTest extends
		AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	RaffleDAO target;

	Raffle raffleFixture;

	@BeforeMethod
	public void create_raffle() {
		super.deleteFromTables("entries", "raffles");

		raffleFixture = RaffleIntegrationTestHelper.constructRaffle("Todd", "toddkaufman@gmail.com");
	}

	@Test
	public void test_create_raffle_adds_a_row() {
		int beforeCount = super.countRowsInTable("RAFFLES");
		target.create(raffleFixture);
		int afterCount = super.countRowsInTable("RAFFLES");
		assertEquals(beforeCount + 1, afterCount);
	}

	@Test
	public void test_create_raffle_saves_the_values() {
		Raffle result = target.create(raffleFixture);
		RaffleIntegrationTestHelper.assertDbFieldsMatch(simpleJdbcTemplate, result);
	}

	@Test
	public void test_create_raffle_adds_child_entity() {
		Raffle result = target.create(raffleFixture);
		Entry entry = (Entry) result.getEntries().toArray()[0];
		RaffleIntegrationTestHelper.assertDbFieldsMatch(simpleJdbcTemplate,entry);
	}

	@Test
	public void test_update_raffle_leaves_num_of_rows() {
		target.create(raffleFixture);
		
		int beforeCount = super.countRowsInTable("RAFFLES");
		raffleFixture.setNumberOfWinners(10);
		target.update(raffleFixture);
		int afterCount = super.countRowsInTable("RAFFLES");
		
		assertEquals(beforeCount, afterCount);
	}

	@Test
	public void test_update_raffle_saves_the_values() {
		Raffle result = target.create(raffleFixture);
		raffleFixture.setNumberOfWinners(10);
		target.update(raffleFixture);
		RaffleIntegrationTestHelper.assertDbFieldsMatch(simpleJdbcTemplate, result);
	}

	@Test
	public void test_delete_raffle_removes_a_row() {
		Raffle toDelete = target.create(raffleFixture);
		int beforeCount = super.countRowsInTable("RAFFLES");
		target.delete(toDelete);
		int afterCount = super.countRowsInTable("RAFFLES");
		assertEquals(beforeCount, afterCount + 1);
	}

	@Test
	public void test_delete_raffle_leaves_no_trace() {
		Raffle result = target.create(raffleFixture);
		target.delete(result);
		int rowsFound = simpleJdbcTemplate.queryForInt(
				"select count(0) from raffles where id = ?", result.getId());
		assertEquals(0, rowsFound);
	}

	@Test
	public void test_delete_raffle_also_deletes_entities() {
		Raffle result = target.create(raffleFixture);
		Entry entry = (Entry) result.getEntries().toArray()[0];
		target.delete(result);
		int rowsFound = simpleJdbcTemplate.queryForInt(
				"select count(0) from entries where id = ?", entry.getId());
		assertEquals(0, rowsFound);
	}

	@Test
	public void test_find_all_returns_null_with_nothing_in_the_db() {
		List<Raffle> results = target.findAll();
		assertTrue(results.isEmpty());
	}

	@Test
	public void test_find_all_returns_the_correct_row() {
		Raffle source = target.create(raffleFixture);
		List<Raffle> results = target.findAll();
		assertEquals(1, results.size());
		RaffleIntegrationTestHelper.assertTwoRafflesMatch(source, results.get(0));
	}


	@Test
	public void test_find_all_returns_multiples() {
		for (int i=0; i<3; i++) {
			Raffle r = RaffleIntegrationTestHelper.constructRaffle("Name" + i, "toddkaufman@gmail.com");
			target.create(r);
		}
		List<Raffle> results = target.findAll();
		assertEquals(3, results.size());
	}

}
