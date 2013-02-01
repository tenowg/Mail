
package com.thedemgel.mail.database;

public class MySql extends DBO {

	@Override
	public void connect(String username, String password, String domain, Integer port, String database) {
		this.setUrl("jdbc:mysql://" + domain + ":" + port + "/" + database);
		this.setDriver("com.mysql.jdbc.Driver");
		super.connect(username, password, domain, port, database);
		
		INSERT = "INSERT INTO Messages (body, sender, receiver) VALUES (?, ?, ?)";
		SELECT = "SELECT * FROM Messages WHERE receiver = ?";
		COUNT = "SELECT COUNT(*) as count FROM Messages WHERE receiver = ?";
	}
}
