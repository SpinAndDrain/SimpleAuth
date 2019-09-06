package de.spinanddrain.simpleauth;

import org.bukkit.entity.Player;

public class AuthPlayer {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	private Player player;
	private boolean logged;
	private boolean verified;
	private String code;
	
	public AuthPlayer(Player player) {
		this.player = player;
		this.logged = false;
		this.verified = false;
		SimpleAuth.provide().getPlayers().add(this);
	}
	
	/**
	 * 
	 * @return getter for {logged}
	 */
	public boolean isLogged() {
		return logged;
	}
	
	/**
	 * 
	 * @return getter for {verified}
	 */
	public boolean isVerified() {
		return verified;
	}
	
	/**
	 * 
	 * @return getter for {player}
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * 
	 * @return getter for {code}
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * 
	 * @return setter for {code}
	 */
	public void setCode(String code) {
		this.code = code;
		update();
	}
	
	/**
	 * 
	 * @return setter for {logged}
	 */
	public void setLogged(boolean logged) {
		this.logged = logged;
		update();
	}
	
	/**
	 * 
	 * @return setter for {verified}
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
		update();
	}
	
	/**
	 * Updates instance
	 */
	private void update() {
		if(SimpleAuth.provide().getPlayers().contains(this)) {
			SimpleAuth.provide().getPlayers().remove(this);
		}
		SimpleAuth.provide().getPlayers().add(this);
	}
	
}
