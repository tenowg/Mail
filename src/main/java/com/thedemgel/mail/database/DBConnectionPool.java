package com.thedemgel.mail.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import org.spout.api.Spout;
import org.spout.api.entity.Player;

public class DBConnectionPool {

	private ConcurrentLinkedQueue<Connection> pool = new ConcurrentLinkedQueue<Connection>();
	private DBO conInfo;
	private Integer POOL_LIMIT = 10;

	public DBConnectionPool(DBO conInfo) {
		this.conInfo = conInfo;
	}
	
	public java.sql.Connection createConnection() {
		java.sql.Connection con = null;
		try {
			Class.forName(conInfo.getDriver());
			con = DriverManager.getConnection(conInfo.getUrl(), conInfo.getUsername(), conInfo.getPassword());
		} catch (SQLException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		} catch (ClassNotFoundException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		}
		
		return con;
	}
	
	public Connection getConnection(Player player) {
		Connection con = pool.poll();
		if (con == null) {
			con = new Connection(createConnection());
		}
		try {
			if (!con.getConnection().isValid(5)) {
				con = new Connection(createConnection());
			}
		} catch (SQLException ex) {
			Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
		}
		return con;
	}
	
	public void close(Connection con) {
		if (getSize() <= POOL_LIMIT) {
			pool.add(con);
		} else {
			try {
				con.getConnection().close();
			} catch (SQLException ex) {
				Spout.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}
	
	public Integer getSize() {
		return pool.size();
	}
}
