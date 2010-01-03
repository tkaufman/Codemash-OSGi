package com.pillartech.raffle.service.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.pillartech.raffle.service.RaffleService;

public class RaffleServiceActivator implements BundleActivator {


	public void start(BundleContext bc) throws Exception {
		bc.registerService(RaffleService.class.getName(),
				new RaffleServiceImpl(), null);
	}

	public void stop(BundleContext bc) throws Exception {
	}
}
