package com.thedemgel.mail.data;

import com.thedemgel.mail.Mail;
import com.thedemgel.mail.database.Connection;
import com.thedemgel.mail.database.DBO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import org.spout.api.Spout;
import org.spout.api.entity.Player;

public class CheckMessage {

	private long lastupdate;
	private Connection con;
	private int numMessages;

	public CheckMessage(Player player) {
		lastupdate = System.currentTimeMillis();
		con = Mail.getInstance().getDatabase().getConnection(player);
		PreparedStatement pstatement = con.prepareStatement(DBO.COUNT);

		try {
			pstatement.setString(1, player.getName());
			ResultSet result = pstatement.executeQuery();

			if (result == null) {
				// Throw error instead of return
				return;
			} else if (result.isBeforeFirst()) {
				result.next();
				do {
					numMessages = result.getInt("count");
					Spout.getLogger().log(Level.INFO, String.valueOf(numMessages));
				} while (result.next());
			}
		} catch (SQLException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		}
		Mail.getInstance().getDataPool().close(con);
	}

	public long getLastUpdate() {
		return lastupdate;
	}

	public int getNumMessages() {
		return numMessages;
	}
}