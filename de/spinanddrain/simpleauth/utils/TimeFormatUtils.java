package de.spinanddrain.simpleauth.utils;

public class TimeFormatUtils {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	public enum TimeFormat {
		MILLISECONDS("ms"),
		SECONDS("s"),
		MINUTES("m"),
		HOURS("h"),
		DAYS("d"),
		WEEKS("w"),
		MONTHS("mo"),
		YEARS("y");
		
		String cont;
		
		TimeFormat(String cont) {
			this.cont = cont;
		}
		
		public String getContraction() {
			return cont;
		}
		
		public static TimeFormat fromContraction(String cont) {
			for(TimeFormat i : values()) {
				if(i.getContraction().equals(cont)) {
					return i;
				}
			}
			return null;
		}
		
	}
	
	public static Collection getCollectionFromString(String format) {
		char[] chars = format.toCharArray();
		String vals = "";
		String conts = "";
		for(int i = 0; i < chars.length; i++) {
			if(isNumeric(chars[i])) {
				vals += chars[i];
			} else {
				conts += chars[i];
			}
		}
		return new Collection(Integer.parseInt(vals), TimeFormat.fromContraction(conts));
	}
	
	public static long toMilliseconds(Collection c) {
		int v = c.getValue();
		switch(c.getFormat()) {
		case MILLISECONDS:
			return v;
		case SECONDS:
			return v * 1000;
		case MINUTES:
			return v * 1000 * 60;
		case HOURS:
			return v * 1000 * 60 * 60;
		case DAYS:
			return v * 1000 * 60 * 60 * 24;
		case WEEKS:
			return v * 1000 * 60 * 60 * 24 * 7;
		case MONTHS:
			return v * 1000 * 60 * 60 * 24 * 7 * 4;
		case YEARS:
			return v * 1000 * 60 * 60 * 24 * 7 * 4 * 12;
		default:
			return 0;
		}
	}
	
	private static boolean isNumeric(char c) {
		char[] num = {'0','1','2','3','4','5','6','7','8','9'};
		for(int i = 0; i < num.length; i++) {
			if(c == num[i]) {
				return true;
			}
		}
		return false;
	}
	
}
