
package com.thedemgel.mail.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Connection {
	private java.sql.Connection con;

	public Connection (java.sql.Connection con) {
		this.con = con;
	}
	
	public void close() {}
	
	public java.sql.Connection getConnection() {
		return con;
	}
	
	public PreparedStatement prepareStatement(String sql) {
		try {
			return con.prepareStatement(sql);
		} catch (SQLException ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return null;
	}
}
