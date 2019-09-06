package de.spinanddrain.simpleauth;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import de.spinanddrain.configuration.ConfigurationFile;
import de.spinanddrain.configuration.ConfigurationManager;
import de.spinanddrain.simpleauth.command.ChatCommand;
import de.spinanddrain.simpleauth.command.Login;
import de.spinanddrain.simpleauth.command.Register;
import de.spinanddrain.simpleauth.command.Unregister;
import de.spinanddrain.simpleauth.config.Config;
import de.spinanddrain.simpleauth.config.Database;
import de.spinanddrain.simpleauth.config.Messages;
import de.spinanddrain.simpleauth.listener.EventsHandler;
import de.spinanddrain.simpleauth.utils.MessageDelay;
import de.spinanddrain.simpleauth.utils.TimeFormatUtils;

public class SimpleAuth extends JavaPlugin {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */
	
	private static SimpleAuth instance;
	
	private final File config = new File("plugins/SimpleAuth/config.yml");
	private final File messages = new File("plugins/SimpleAuth/messages.yml");
	private ConfigurationManager manager;
	
	private final String dir = "plugins/SimpleAuth/databases/";
	private Database data;
	private Database verify;
	private Database login;
	
	private List<AuthPlayer> players;
	private List<MessageDelay> delays;
	
	private EventsHandler handler;
	private Updater u;
	private ChatCommand[] commands;
	
	@Override
	public void onEnable() {
		instance = this;

		players = new ArrayList<>();
		delays = new ArrayList<>();
		commands = new ChatCommand[3];
		
		manager = new ConfigurationManager();
		manager.registerClass(Config.class, new ConfigurationFile(config));
		manager.registerClass(Messages.class, new ConfigurationFile(messages));
		manager.reloadAll();
		
		if(Config.developer_mode) {
			out("§eDeveloper mode enabled!");
			return;
		}
		
		data = new Database(new File(dir + "dat.db.sa"));
		verify = new Database(new File(dir + "ver.db.sa"));
		login = new Database(new File(dir + "log.db.sa"));
		
		commands[0] = new Register();
		commands[1] = new Login();
		commands[2] = new Unregister();
		handler = new EventsHandler();
		
		u = new Updater(this);
		u.checkUpdate();
		
		for(Player p : getServer().getOnlinePlayers()) {
			AuthPlayer ap = new AuthPlayer(p);
			String id = p.getUniqueId().toString();
			if(System.currentTimeMillis() <= (verify.getValue(id) + TimeFormatUtils.toMilliseconds(
					TimeFormatUtils.getCollectionFromString(Config.verirfy_expire)))) {
				ap.setVerified(true);
			} else {
				ap.setCode(generateRandomCode());
			}
			if(login.isRegistered(id) && System.currentTimeMillis() <= (login.getValue(id) + TimeFormatUtils.toMilliseconds(
					TimeFormatUtils.getCollectionFromString(Config.login_expire)))) {
				ap.setLogged(true);
			}
		}
	}
	
	/**
	 * 
	 * @return SimpleAuth instance
	 */
	public static SimpleAuth provide() {
		return instance;
	}
	
	/**
	 * 
	 * Generates a random code with 4 characters from A-z, and 0-9
	 * @return the random code as String
	 */
	public String generateRandomCode() {
		char[] code = new char[4];
		for(int i = 0; i < code.length; i++) {
			if(new Random().nextBoolean()) {
				code[i] = Character.forDigit(new Random().nextInt(9), 10);
			} else {
				int rnd = (int) (Math.random() * 52);
				code[i] = (char) ((char) ((rnd < 26) ? 'A' : 'a') + rnd % 26);
			}
		}
		return new String(code);
	}
	
	/**
	 * 
	 * @return a Database[] with all plugin-based databases
	 */
	public Database[] getDatabases() {
		return new Database[]{this.data,this.verify,this.login};
	}
	
	/**
	 * 
	 * @return all online AuthPlayers
	 */
	public List<AuthPlayer> getPlayers() {
		return players;
	}
	
	/**
	 * 
	 * @return all message delays from all online players
	 */
	public List<MessageDelay> getDelays() {
		return delays;
	}
	
	/**
	 * 
	 * @return Updater instance
	 */
	public Updater getUpdater() {
		return u;
	}
	
	/**
	 * 
	 * @return all plugin-based chat commands
	 */
	public ChatCommand[] getCommands() {
		return commands;
	}
	
	/**
	 * 
	 * @param player
	 * @return the AuthPlayer of the specified player
	 */
	public AuthPlayer getPlayer(Player player) {
		for(AuthPlayer i : players) {
			if(i.getPlayer() == player) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param player
	 * @return the MessageDelay of the specified player
	 */
	public MessageDelay getDelay(Player player) {
		for(MessageDelay i : delays) {
			if(i.getPlayer() == player) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Disables all spigot listeners of SimpleAuth
	 */
	public void unregisterSimpleAuthListeners() {
		HandlerList.unregisterAll(handler);
		HandlerList.unregisterAll(commands[0]);
		HandlerList.unregisterAll(commands[1]);
		HandlerList.unregisterAll(commands[2]);
	}
	
	/**
	 * Prints out a plugin message
	 * @param message
	 */
	private void out(String message) {
		getServer().getConsoleSender().sendMessage("§7[§cSimpleAuth§7] " + message);
	}
	
}
