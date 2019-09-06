package de.spinanddrain.simpleauth.utils;

import org.bukkit.entity.Player;

import de.spinanddrain.simpleauth.SimpleAuth;

public class MessageDelay {

	/*
	 * Created by SpinAndDrain on 05.09.2019
	 */

	/**
	 * 
	 * Prevents an not logged in player from getting spammed
	 */
	
	private Player player;
	private long delay;
	
	private long last;
	
	public MessageDelay(Player player, long delay) {
		this.player = player;
		this.delay = delay;
		this.last = System.currentTimeMillis();
		SimpleAuth.provide().getDelays().add(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean canSend() {
		return (System.currentTimeMillis() >= (this.last + this.delay));
	}
	
	public void send() {
		this.last = System.currentTimeMillis();
		update();
	}
	
	private void update() {
		if(SimpleAuth.provide().getDelays().contains(this)) {
			SimpleAuth.provide().getDelays().remove(this);
		}
		SimpleAuth.provide().getDelays().add(this);
	}
	
}
