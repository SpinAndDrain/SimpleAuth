package de.spinanddrain.simpleauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

public class Updater {
	
	private Plugin plugin;
	private int resource = 64810;
	private String prefix = "§7[§eSimpleAuth§7] §r";
	private String link = "https://bit.ly/2UWTE5w";
	
	public Updater(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void checkUpdate() {
		ConsoleCommandSender c = plugin.getServer().getConsoleSender();
		c.sendMessage(prefix + "§eSearching for updates...");
		try {
            HttpURLConnection con = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resource).openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream().write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource).getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if(version.equalsIgnoreCase(plugin.getDescription().getVersion())) {
            	c.sendMessage(prefix + "§eNo updates found.");
            } else {
            	c.sendMessage(prefix + "§eA newer version is available: §b" + version);
            	c.sendMessage(prefix + "§eDownload: §b" + link);
            }
        } catch (IOException e) {
        	c.sendMessage(prefix + "§cAn error occurred while searching for updates! (404)");
        }
	}
	
}
