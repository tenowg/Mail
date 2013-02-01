
package com.thedemgel.mail.database;

import com.thedemgel.mail.Mail;
import org.spout.api.entity.Player;


public abstract class DBO {
	private String username;
	private String password;
	private String url;
	private String driver;
	
	public static String INSERT;
	public static String SELECT;
	public static String COUNT;
	public static String DELETE;
	
	public void connect(String username, String password, String domain, Integer port, String database) {
		this.username = username;
		this.password = password;
	}
	
	public Connection getConnection(Player player) {
		return Mail.getInstance().getDataPool().getConnection(player);
	}
	
	public String getDriver() {
		return driver;
	}
	
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUsername(String value) {
		this.username = value;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setDriver(String value) {
		this.driver = value;
	}
}
