package com.pillartech.raffle.service.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.equinox;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.provision;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.EntryDAO;
import com.pillartech.raffle.persistence.RaffleDAO;
import com.pillartech.raffle.service.RaffleService;

@RunWith(JUnit4TestRunner.class)
public class ServiceBundleTest {

	static final String NAME = "Todd";
	static final String EMAIL = "toddkaufman@gmail.com";

	@Inject
	private BundleContext bundleContext;

	@Configuration
	public static Option[] configuration() {
		return options(equinox(), provision(
				mavenBundle().groupId("com.pillartech.raffle").artifactId("domain"),
				mavenBundle().groupId("com.pillartech.raffle").artifactId("persistence"),
				mavenBundle().groupId("com.pillartech.raffle").artifactId("service")
		));
	}

	@Test
	public void bundle_context_should_not_be_null() {
		assertNotNull(bundleContext);
	}
	
	@Test
	public void test_single_winner_orchestration() throws Exception {
		RaffleService raffleService = retrieveRaffleService();
		raffleService.addEntry(NAME, EMAIL);
		Set<String> winners = raffleService.pickWinners(1);
		
		assertEquals(1, winners.size());
		assertEquals(NAME, winners.toArray()[0]);
	}

	@Test
	public void test_multiple_entrants_with_single_winner() throws Exception {
		RaffleService raffleService = retrieveRaffleService();
		for (int i=0; i<10; i++) {
			raffleService.addEntry(NAME + i, EMAIL);
		}
		Set<String> winners = raffleService.pickWinners(1);
		
		assertEquals(1, winners.size());
		String winnerName = (String) winners.toArray()[0]; 
		assertTrue(winnerName.indexOf(NAME) >= 0);
	}
	
	@Test
	public void test_adding_entrants_saves_a_raffle() throws Exception {
		RaffleService raffleService = retrieveRaffleService();
		raffleService.addEntry(NAME, EMAIL);
		
		RaffleDAO raffleDao = retrieveRaffleDao();
		List<Raffle> raffles = raffleDao.findAll();
		assertEquals(1, raffles.size());
	}

	@Test
	public void test_adding_entrants_saves_entries() throws Exception {
		RaffleService raffleService = retrieveRaffleService();
		raffleService.addEntry(NAME, EMAIL);
		
		EntryDAO entryDao = retrieveEntryDao();
		List<Entry> entries = entryDao.findAll();
		assertEquals(1, entries.size());
		Entry result = (Entry) entries.toArray()[0];
		assertEquals(NAME, result.getName());
		assertEquals(EMAIL, result.getEmail());
	}

	private RaffleService retrieveRaffleService() throws InterruptedException {
		ServiceTracker tracker = new ServiceTracker(bundleContext, RaffleService.class.getName(), null);
		tracker.open();
		RaffleService service = (RaffleService) tracker.waitForService(2000);
		tracker.close();
		assertNotNull(service);
		return service;
	}

	private RaffleDAO retrieveRaffleDao() throws InterruptedException {
		ServiceTracker tracker = new ServiceTracker(bundleContext, RaffleDAO.class.getName(), null);
		tracker.open();
		RaffleDAO dao = (RaffleDAO) tracker.waitForService(2000);
		tracker.close();
		assertNotNull(dao);
		return dao;
	}

	private EntryDAO retrieveEntryDao() throws InterruptedException {
		ServiceTracker tracker = new ServiceTracker(bundleContext, EntryDAO.class.getName(), null);
		tracker.open();
		EntryDAO dao = (EntryDAO) tracker.waitForService(2000);
		tracker.close();
		assertNotNull(dao);
		return dao;
	}
}