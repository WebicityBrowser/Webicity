package everyos.browser.javadom.imp;

public class TimeUtil {
	public static double now() {
		//HD Time with timed attack prevention, as according to WhatWG
		int deficiency = 10000; //This should be at least 5000. We use a higher value
		return System.nanoTime()/1000000+(Math.random()*2-1)*deficiency;
	}
}
