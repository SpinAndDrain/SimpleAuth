package de.spinanddrain.simpleauth.command;

import org.bukkit.entity.Player;

import de.spinanddrain.simpleauth.SimpleAuth;
import de.spinanddrain.simpleauth.config.Database;
import de.spinanddrain.simpleauth.config.Messages;

public class Unregister extends ChatCommand {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	public Unregister() {
		super(SimpleAuth.provide(), "*unregister", "sa.unregister");
	}
	
	@Override
	public void run(Player player, String[] arguments) {
		if(arguments.length == 1) {
			Player target = SimpleAuth.provide().getServer().getPlayer(arguments[0]);
			if(target != null) {
				String id = target.getUniqueId().toString();
				for(Database i : SimpleAuth.provide().getDatabases()) {
					if(i.isRegistered(id)) {
						i.unregisterEntry(id);
					}
				}
				player.sendMessage(Messages.prefix + Messages.succ_unregistered);
				SimpleAuth.provide().getServer().getScheduler().runTask(SimpleAuth.provide(), new Runnable() {
					@Override
					public void run() {
						target.kickPlayer(Messages.target_kick);
					}
				});
			} else
				player.sendMessage(Messages.prefix + Messages.target_not_found);
		} else
			player.sendMessage(Messages.prefix + Messages.syntax);
	}
	
}
