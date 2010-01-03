package com.pillartech.raffle.persistence.internal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.EntryDAO;
import com.pillartech.raffle.persistence.RaffleDAO;

@ContextConfiguration(locations = { "/META-INF/spring/persistence-context.xml" })
public class EntryDAOImplTest extends
		AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	EntryDAO target;

	@Autowired
	RaffleDAO raffleDao;

	Entry entryFixture;
	Raffle raffleFixture;

	@BeforeMethod
	public void create_entry() {
		raffleFixture = new Raffle();
		raffleDao.create(raffleFixture);
		entryFixture = RaffleIntegrationTestHelper.constructEntry("Todd",
				"toddkaufman@gmail.com", raffleFixture);
	}

	@Test
	public void test_create_entry_adds_a_row() {
		int beforeCount = super.countRowsInTable("ENTRIES");
		target.create(entryFixture);
		int afterCount = super.countRowsInTable("ENTRIES");
		assertEquals(beforeCount + 1, afterCount);
	}

	@Test
	public void test_create_entry_populates_the_id() {
		Entry result = target.create(entryFixture);
		assertNotNull(result.getId());
	}

	@Test
	public void test_create_entry_populates_all_fields() {
		Entry result = target.create(entryFixture);
		RaffleIntegrationTestHelper.assertDbFieldsMatch(simpleJdbcTemplate,
				result);
	}

	@Test
	public void test_update_leaves_the_same_number() {
		Entry toUpdate = target.create(entryFixture);
		int beforeCount = super.countRowsInTable("ENTRIES");
		toUpdate.setName("The Todd");
		target.update(toUpdate);
		int afterCount = super.countRowsInTable("ENTRIES");
		assertEquals(beforeCount, afterCount);
	}

	@Test
	public void test_update_changes_the_row() {
		Entry toUpdate = target.create(entryFixture);
		toUpdate.setName("The Todd");
		target.update(toUpdate);
		RaffleIntegrationTestHelper.assertDbFieldsMatch(simpleJdbcTemplate,
				toUpdate);
	}

	@Test
	public void test_delete_entry_removes_a_row() {
		Entry toDelete = target.create(entryFixture);
		int beforeCount = super.countRowsInTable("ENTRIES");
		target.delete(toDelete);
		int afterCount = super.countRowsInTable("ENTRIES");
		assertEquals(beforeCount, afterCount + 1);
	}

	@Test
	public void test_delete_entry_leaves_no_trace() {
		Entry result = target.create(entryFixture);
		target.delete(result);
		int rowsFound = simpleJdbcTemplate.queryForInt(
				"select count(0) from entries where id = ?", result.getId());
		assertEquals(0, rowsFound);
	}

	@Test
	public void test_find_all_returns_null_with_nothing_in_the_db() {
		List<Entry> results = target.findAll();
		assertTrue(results.isEmpty());
	}

	@Test
	public void test_find_all_returns_the_correct_row() {
		Entry source = target.create(entryFixture);
		List<Entry> results = target.findAll();
		assertEquals(1, results.size());
		RaffleIntegrationTestHelper.assertTwoEntriesMatch(source, results
				.get(0));
	}

	@Test
	public void test_find_all_returns_multiples() {
		for (int i = 0; i < 3; i++) {
			Entry e = RaffleIntegrationTestHelper.constructEntry("Name" + i,
					"toddkaufman@gmail.com", raffleFixture);
			target.create(e);
		}
		List<Entry> results = target.findAll();
		assertEquals(3, results.size());
	}

}
