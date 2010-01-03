package com.pillartech.raffle.rigged.internal;

import java.util.Set;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;

public final class SimpleRiggedRaffleActivator implements BundleActivator {

	private Raffle raffle = null;

	public void start(BundleContext bc) throws Exception {
		System.out.println("Adding entrants");
		raffle = new Raffle();
		raffle.setNumberOfWinners(1);
		for (int i = 0; i < 10; i++) {
			Entry e = new Entry();
			e.setName("The Todd(" + i + ")");
			e.setEmail("toddkaufman@gmail.com");
			raffle.addEntry(e);
		}
		System.out.println(raffle.getEntries().size()
				+ " entries added to the raffle.");
	}

	public void stop(BundleContext bc) throws Exception {
		System.out.println("And the winner of the raffle is ...");
		Set<Entry> winners = raffle.pickWinners();
		for (Entry winner : winners) {
			System.out.println(winner.getName());
		}
	}
}
