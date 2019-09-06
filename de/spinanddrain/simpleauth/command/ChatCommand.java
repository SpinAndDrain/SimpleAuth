package de.spinanddrain.simpleauth.command;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import de.spinanddrain.simpleauth.AuthPlayer;
import de.spinanddrain.simpleauth.SimpleAuth;
import de.spinanddrain.simpleauth.config.Messages;

public abstract class ChatCommand implements Listener {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	private String name;
	private String permission;
	
	public ChatCommand(Plugin plugin, String name, String permission) {
		this.name = name;
		this.permission = permission;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public ChatCommand(Plugin plugin, String name) {
		this.name = name;
		this.permission = null;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * 
	 * Runs the command
	 * @param player
	 * @param arguments
	 */
	public abstract void run(Player player, String[] arguments);
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChatCommand(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			if(command.equals(ap.getCode())) {
				ap.setVerified(true);
				SimpleAuth.provide().getDatabases()[1].registerEntry(player.getUniqueId().toString(), System.currentTimeMillis());
				player.sendMessage(Messages.prefix + Messages.succ_verified);
			} else
				Messages.pleaseVerify(player, ap.getCode());
		} else if(command.startsWith(this.name)) {
			event.setCancelled(true);
			String[] args = command.split(" ");
			String[] finalArgs = new String[args.length - 1];
			for(int i = 1; i < args.length; i++) {
				finalArgs[i - 1] = args[i];
			}
			if(this.permission == null) {
				run(player, finalArgs);
			} else if(player.hasPermission(this.permission)) {
				run(player, finalArgs);
			} else {
				player.sendMessage(Messages.prefix + Messages.no_permission);
			}
		}
	}
	
}
