package de.spinanddrain.simpleauth.utils;

import de.spinanddrain.simpleauth.utils.TimeFormatUtils.TimeFormat;

public class Collection {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	/**
	 * 
	 * Storeroom
	 */
	
	private int a;
	private TimeFormat t;
	
	public Collection(int a, TimeFormat t) {
		this.a = a;
		this.t = t;
	}
	
	public int getValue() {
		return a;
	}
	
	public TimeFormat getFormat() {
		return t;
	}
	
}
