package de.spinanddrain.simpleauth.command;

import org.bukkit.entity.Player;

import de.spinanddrain.simpleauth.SimpleAuth;
import de.spinanddrain.simpleauth.config.Config;
import de.spinanddrain.simpleauth.config.Database;
import de.spinanddrain.simpleauth.config.Messages;

public class Register extends ChatCommand {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	public Register() {
		super(SimpleAuth.provide(), "register");
	}
	
	@Override
	public void run(Player player, String[] arguments) {
		if(arguments.length == 2) {
			Database db = SimpleAuth.provide().getDatabases()[0];
			String id = player.getUniqueId().toString();
			if(!db.isRegistered(id)) {
				int pw = arguments[0].hashCode();
				if(pw == arguments[1].hashCode()) {
					db.registerEntry(id, (int) pw);
					if(!Config.registration_kick) {
						player.sendMessage(Messages.prefix + Messages.succ_registered);
					} else {
						SimpleAuth.provide().getServer().getScheduler().runTask(SimpleAuth.provide(), new Runnable() {
							@Override
							public void run() {
								player.kickPlayer(Messages.succ_registered_kick);
							}
						});
					}
				} else
					player.sendMessage(Messages.prefix + Messages.password_not_match);
			} else
				player.sendMessage(Messages.prefix + Messages.already_registered);
		} else
			player.sendMessage(Messages.prefix + Messages.please_register);
	}
	
}
