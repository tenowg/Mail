package com.thedemgel.mail.data;

import com.thedemgel.mail.Mail;
import com.thedemgel.mail.database.Connection;
import com.thedemgel.mail.database.DBO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import org.spout.api.Spout;
import org.spout.api.entity.Player;

public class GetMessages {

	private ConcurrentSkipListMap<Date, Message> messages = new ConcurrentSkipListMap<Date, Message>();
	private Connection con;

	public GetMessages(Player player) {
		con = Mail.getInstance().getDatabase().getConnection(player);
		PreparedStatement pstatement = con.prepareStatement(DBO.SELECT);

		try {
			pstatement.setString(1, player.getName());
			ResultSet result = pstatement.executeQuery();

			if (result == null) {
				// Throw error instead of return
				return;
			} else if (result.isBeforeFirst()) {
				result.next();
				do {
					Message message = new Message();
					message.setBody(result.getString("body"));
					message.setDate(result.getDate("sent"));
					message.setID(result.getLong("id"));
					message.setSender("sender");
					message.setRead(result.getBoolean("received"));
					messages.put(message.getDate(), message);
				} while (result.next());
			}
		} catch (SQLException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		}
		Mail.getInstance().getDataPool().close(con);
	}

	public ConcurrentSkipListMap<Date, Message> getMessages() {
		return messages;
	}
}