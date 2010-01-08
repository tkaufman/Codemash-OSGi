package com.pillartech;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SuperSimple implements BundleActivator {

	public void start(BundleContext ctx) throws Exception {
		System.out.println("Starting Up!");
	}
	
	public void stop(BundleContext ctx) throws Exception {
		System.out.println("Shutting Down!");
	}
}