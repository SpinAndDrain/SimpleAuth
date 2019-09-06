package de.spinanddrain.simpleauth.config;

import de.spinanddrain.configuration.Configurable;
import de.spinanddrain.configuration.Header;

@Header("Offical SimpleAuth Config for 1.5F | Time formats: ms, s, m, h, d, w, mo, y")
public class Config {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	@Configurable(path = "developer-mode")
	public static boolean developer_mode = false;
	
	@Configurable(path = "registration-kick")
	public static boolean registration_kick = true;
	
	@Configurable(path = "login-expire")
	public static String login_expire = "1d";
	
	@Configurable(path = "verify-expire")
	public static String verirfy_expire = "1h";
	
}
