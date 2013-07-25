
package com.thedemgel.mail;

import com.thedemgel.mail.component.MailComponent;
import org.spout.api.Client;
import org.spout.api.Platform;
import org.spout.api.Spout;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.engine.EngineStartEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.vanilla.component.entity.player.HUD;


public class PlayerListener implements Listener {
	private Mail plugin;
	
	public PlayerListener(Mail instance) {
		this.plugin = instance;
	}
	
	@EventHandler(order = Order.LATE)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.add(MailComponent.class);
	}
	
	@EventHandler
	public void onGameStart(EngineStartEvent event) {
		if (Spout.getPlatform() != Platform.CLIENT) {
			return;
		}

		Player player = ((Client) Spout.getEngine()).getPlayer();
		player.add(HUD.class);
		player.add(MailComponent.class);
	}

}
