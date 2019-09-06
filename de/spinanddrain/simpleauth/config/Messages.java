package de.spinanddrain.simpleauth.config;

import org.bukkit.entity.Player;

import de.spinanddrain.configuration.Configurable;
import de.spinanddrain.configuration.Placeholder;

public class Messages {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	@Configurable(path = "prefix")
	public static String prefix = "§7[§eSimpleAuth§7] §r";
	
	@Configurable(path = "no-permission")
	public static String no_permission = "§cNo permission!";

	@Configurable(path = "syntax")
	public static String syntax = "§cSyntax: §e*unregister <player>";
	
	@Placeholder
	@Configurable(path = "please-verify")
	public static String please_verify = "§cPlease verify with the following code: §a[code]";
	
	@Configurable(path = "please-login")
	public static String please_login = "§cPlease login with §elogin <password>§c!";
	
	@Configurable(path = "please-register")
	public static String please_register = "§cPlease register with §eregister <password> <confirm-password>";

	@Configurable(path = "already-registered")
	public static String already_registered = "§cYou are already registered! Please use §elogin <password>§c!";
	
	@Configurable(path = "not-registered-yet")
	public static String not_registered_yet = "§cYou are not registered yet! Please use §eregister <password> <confirm-password>§c!";
	
	@Configurable(path = "already-logged-in")
	public static String already_logged_in = "§cYou are already logged in!";
	
	@Configurable(path = "successfully-verified")
	public static String succ_verified = "§aSuccess!";
	
	@Configurable(path = "successfully-logged-in")
	public static String succ_logged_in = "§aSuccess!";
	
	@Configurable(path = "password-not-match")
	public static String password_not_match = "§cThe entered passwords are not matching!";
	
	@Configurable(path = "wrong-password")
	public static String wrong_password = "§cThe entered password is not correct!";
	
	@Configurable(path = "successfully-registered")
	public static String succ_registered = "§aYou registered successfully! Please login with §elogin <password>§a!";
	
	@Configurable(path = "successfully-registered-kick")
	public static String succ_registered_kick = "§a§lSuccessfully registered! \n §ePlease rejoin the server!";
	
	@Configurable(path = "target-not-found")
	public static String target_not_found = "§cThe player could not be found!";
	
	@Configurable(path = "successfully-unregistered")
	public static String succ_unregistered = "§aSuccess!";
	
	@Configurable(path = "target-kick-on-unregister")
	public static String target_kick = "§cYou could not be authenticated \n §ePlease rejoin the server!";
	
	public static void pleaseVerify(Player p, String code) {
		p.sendMessage(prefix + please_verify.replace("[code]", code));
	}
}
