package com.pillartech.raffle.domain;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RaffleTest {

	private Raffle raffle;
	private Set<Entry> entrySet;
	
	@BeforeMethod
	public void setup_entries_and_raffle() {
		
		raffle = new Raffle();
		entrySet = new HashSet<Entry>();
		
		int i = 0;
		while (i<20) {
			Entry e = new Entry();
			e.setName("entryName" + i);
			e.setEmail("email" + i);
			e.setCreated(new Date());
			entrySet.add(e);
			raffle.addEntry(e);
			i++;
		}
		raffle.setNumberOfWinners(1);
	}
	
	@Test
	public void test_add_new_entry() {
		Entry e = new Entry();
		e.setName("NewGuy");
		raffle.addEntry(e);
		assertTrue(raffle.getEntries().contains(e));
		assertEquals(entrySet.size(), raffle.getEntries().size() - 1);
	}
	
	@Test
	public void test_add_entry_to_null_set_works() {
		Entry e = new Entry();
		e.setName("NewGuy");
		raffle.setEntries(null);
		raffle.addEntry(e);
		assertTrue(raffle.getEntries().contains(e));
		assertEquals(1, raffle.getEntries().size());
	}
	
	@Test
	public void test_add_duplicate_entry() {
		Entry e = (Entry)entrySet.toArray()[0];
		raffle.addEntry(e);
		assertTrue(raffle.getEntries().contains(e));
		assertEquals(entrySet.size(), raffle.getEntries().size());
	}
	
	@Test
	public void test_pick_and_remove_random_entry_should_remove_the_entry() {
		Entry e = raffle.pickAndRemoveRandomEntry(entrySet);
		assertFalse(entrySet.contains(e));
	}
	
	@Test
	public void test_pick_winners_draws_the_correct_amount() {
		Set<Entry> results = raffle.pickWinners();
		assertEquals(results.size(), raffle.getNumberOfWinners());
	}

	@Test
	public void test_pick_winners_draws_a_valid_set() {
		raffle.setNumberOfWinners(5);
		Set<Entry> results = raffle.pickWinners();
		assertEquals(results.size(), raffle.getNumberOfWinners());
	}

	@Test
	public void test_entries_same_size_as_winners_draws_everyone() {
		raffle.setNumberOfWinners(raffle.getEntries().size());
		Set<Entry> results = raffle.pickWinners();
		assert_all_entrants_are_winners(results);
	}

	@Test
	public void test_entries_smaller_than_winners_draws_everyone() {
		raffle.setNumberOfWinners(entrySet.size()*2);
		Set<Entry> results = raffle.pickWinners();
		assert_all_entrants_are_winners(results);
	}
	
	public void assert_all_entrants_are_winners(Set<Entry> results) {
		assertEquals(entrySet.size(), results.size());
		assertTrue(raffle.getEntries().isEmpty());
		for (Entry winner : results) {
			assertTrue(entrySet.contains(winner));
		}		
	}
}
