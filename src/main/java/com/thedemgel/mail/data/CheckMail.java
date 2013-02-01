package com.thedemgel.mail.data;

import java.util.concurrent.Callable;
import org.spout.api.entity.Player;

public class CheckMail implements Callable {
	private Player player;

	public CheckMail(Player player) {
		this.player = player;
	}
	
	public CheckMessage call() throws Exception {
		// get all current messages
		// return List ordered by date
		return new CheckMessage(player);
	}
}