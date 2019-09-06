package de.spinanddrain.simpleauth.command;

import org.bukkit.entity.Player;

import de.spinanddrain.simpleauth.AuthPlayer;
import de.spinanddrain.simpleauth.SimpleAuth;
import de.spinanddrain.simpleauth.config.Database;
import de.spinanddrain.simpleauth.config.Messages;

public class Login extends ChatCommand {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	public Login() {
		super(SimpleAuth.provide(), "login");
	}
	
	@Override
	public void run(Player player, String[] arguments) {
		if(arguments.length == 1) {
			Database db = SimpleAuth.provide().getDatabases()[0];
			Database ldb = SimpleAuth.provide().getDatabases()[2];
			String id = player.getUniqueId().toString();
			if(db.isRegistered(id)) {
				AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
				if(!ap.isLogged()) {
					if(arguments[0].hashCode() == db.getValue(id)) {
						ap.setLogged(true);
						ldb.registerEntry(id, System.currentTimeMillis());
						player.sendMessage(Messages.prefix + Messages.succ_logged_in);
					} else
						player.sendMessage(Messages.prefix + Messages.wrong_password);
				} else
					player.sendMessage(Messages.prefix + Messages.already_logged_in);
			} else
				player.sendMessage(Messages.prefix + Messages.not_registered_yet);
		} else
			player.sendMessage(Messages.prefix + Messages.please_login);
	}
	
}
