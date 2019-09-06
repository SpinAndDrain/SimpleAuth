package de.spinanddrain.simpleauth.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.spinanddrain.simpleauth.AuthPlayer;
import de.spinanddrain.simpleauth.SimpleAuth;
import de.spinanddrain.simpleauth.config.Config;
import de.spinanddrain.simpleauth.config.Database;
import de.spinanddrain.simpleauth.config.Messages;
import de.spinanddrain.simpleauth.utils.MessageDelay;
import de.spinanddrain.simpleauth.utils.TimeFormatUtils;

public class EventsHandler implements Listener {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	public EventsHandler() {
		SimpleAuth.provide().getServer().getPluginManager().registerEvents(this, SimpleAuth.provide());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = new AuthPlayer(player);
		Database ver = SimpleAuth.provide().getDatabases()[1];
		Database log = SimpleAuth.provide().getDatabases()[2];
		if(log.isRegistered(id) && System.currentTimeMillis() <= (log.getValue(id) + TimeFormatUtils.toMilliseconds(
				TimeFormatUtils.getCollectionFromString(Config.login_expire)))) {
			ap.setLogged(true);
		}
		if(ver.isRegistered(id) && System.currentTimeMillis() <= (ver.getValue(id) + TimeFormatUtils.toMilliseconds(
				TimeFormatUtils.getCollectionFromString(Config.verirfy_expire)))) {
			ap.setVerified(true);
		}
		if(!ap.isVerified()) {
			ap.setCode(SimpleAuth.provide().generateRandomCode());
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		SimpleAuth.provide().getPlayers().remove(SimpleAuth.provide().getPlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		String id = p.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(p);
		MessageDelay md = SimpleAuth.provide().getDelay(p);
		if(md == null) {
			md = new MessageDelay(p, 3000);
			if(!ap.isVerified()) {
				event.setTo(event.getFrom());
				Messages.pleaseVerify(p, ap.getCode());
			} else if(!ap.isLogged()) {
				event.setTo(event.getFrom());
				if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
					p.sendMessage(Messages.prefix + Messages.please_login);
				} else {
					p.sendMessage(Messages.prefix + Messages.please_register);
				}
			}
		}
		if(!ap.isVerified()) {
			event.setTo(event.getFrom());
		} else if(!ap.isLogged()) {
			event.setTo(event.getFrom());
		}
		if(md.canSend()) {
			md.send();
			if(!ap.isVerified()) {
				event.setTo(event.getFrom());
				Messages.pleaseVerify(p, ap.getCode());
			} else if(!ap.isLogged()) {
				event.setTo(event.getFrom());
				if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
					p.sendMessage(Messages.prefix + Messages.please_login);
				} else {
					p.sendMessage(Messages.prefix + Messages.please_register);
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onBuild(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onBuild(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			event.setResult(Result.DENY);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			event.setResult(Result.DENY);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		String id = player.getUniqueId().toString();
		AuthPlayer ap = SimpleAuth.provide().getPlayer(player);
		if(!ap.isVerified()) {
			event.setCancelled(true);
			Messages.pleaseVerify(player, ap.getCode());
		} else if(!ap.isLogged()) {
			event.setCancelled(true);
			if(SimpleAuth.provide().getDatabases()[0].isRegistered(id)) {
				player.sendMessage(Messages.prefix + Messages.please_login);
			} else {
				player.sendMessage(Messages.prefix + Messages.please_register);
			}
		}
	}
	
}
