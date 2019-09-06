package de.spinanddrain.simpleauth.config;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class Database {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */
	
	private File file;
	private YamlConfiguration config;
	
	public Database(File file) {
		this.file = file;
		if(!this.file.getParentFile().exists()) {
			this.file.getParentFile().mkdirs();
		}
		if(!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(this.file);
	}
	
	/**
	 * 
	 * @param entry
	 * @return if the specified entry is registered
	 */
	public boolean isRegistered(String entry) {
		return (config.get(entry) != null);
	}
	
	/**
	 * 
	 * Registers an entry with the specified @long
	 * @param entry
	 * @param raw
	 */
	public void registerEntry(String entry, long raw) {
		config.set(entry, raw);
		save();
	}

	/**
	 * 
	 * Unregisters an entry
	 * @param entry
	 */
	public void unregisterEntry(String entry) {
		config.set(entry, null);
		save();
	}
	
	/**
	 * 
	 * @param entry
	 * @return returns the long of the specified entry
	 * @see this#registerEntry
	 */
	public long getValue(String entry) {
		return config.getLong(entry);
	}
	
	/**
	 * 
	 * @return all entries of the database
	 */
	public String[] getKeys() {
		Set<String> set =  config.getKeys(false);
		return set.toArray(new String[set.size()]);
	}
	
	/**
	 * 
	 * @return reloaded configuration
	 */
	public YamlConfiguration reload() {
		save();
		this.config = YamlConfiguration.loadConfiguration(this.file);
		return this.config;
	}
	
	/**
	 * Saves the configuration
	 */
	public void save() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
