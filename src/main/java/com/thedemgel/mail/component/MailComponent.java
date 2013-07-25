package com.thedemgel.mail.component;

import com.thedemgel.mail.Mail;
import com.thedemgel.mail.component.hud.MailWidget;
import com.thedemgel.mail.data.CheckMail;
import com.thedemgel.mail.data.CheckMessage;
import com.thedemgel.mail.data.GetMessages;
import com.thedemgel.mail.data.Message;
import java.util.Date;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import org.spout.api.Spout;
import org.spout.api.component.type.EntityComponent;
import org.spout.api.entity.Player;
import org.spout.vanilla.component.entity.player.HUD;

public class MailComponent extends EntityComponent {

	private ConcurrentSkipListMap<Date, Message> messages = new ConcurrentSkipListMap<Date, Message>();
	private CompletionService<CheckMessage> ecs = new ExecutorCompletionService(Mail.getInstance().getExecutor());
	private long lastupdate = System.currentTimeMillis();
	private long tickCount = 0;
	private Integer numMessages = 0;

	@Override
	public void onAttached() {
		getOwner().get(HUD.class).setDefault(MailWidget.class);
	}

	@Override
	public void onTick(float dt) {
		// Two things here... every 10 seconds run database check.
		if (tickCount % (20 * 10) == 0) {
			ecs.submit(new CheckMail((Player) getOwner()));
			tickCount = 0;
		}
		tickCount++;
		// Every tick check if something is done (poll()).
		CheckMessage check = null;
		try {
			Future<CheckMessage> checkMessage = ecs.poll();
			if (checkMessage != null) {
				check = checkMessage.get();
			}
		} catch (InterruptedException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		} catch (ExecutionException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		}
		if (check != null) {
			if (check.getLastUpdate() > lastupdate) {
				this.lastupdate = check.getLastUpdate();
				this.numMessages = check.getNumMessages();
			}
		}
		
		getOwner().get(HUD.class).getWidget(MailWidget.class).update();
	}

	public void retrieveMessages() {
		Mail.getInstance().getExecutor().execute(new GetMail());
	}

	public int getNumMessage() {
		return numMessages;
	}

	public ConcurrentSkipListMap<Date, Message> getMessages() {
		return messages;
	}

	public class GetMail implements Runnable {

		@Override
		public void run() {
			GetMessages getMessage = new GetMessages((Player)getOwner());
			messages = getMessage.getMessages();
		}
	}
}
