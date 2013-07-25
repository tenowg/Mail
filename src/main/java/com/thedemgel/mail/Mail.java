package com.thedemgel.mail;

import com.thedemgel.mail.database.DBConnectionPool;
import com.thedemgel.mail.database.DBO;
import com.thedemgel.mail.database.MySql;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import org.spout.api.Engine;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.RootCommand;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.PluginLogger;

public class Mail extends CommonPlugin {

	private Engine engine;
	private static Mail instance;
	private DBO database;
	private DBConnectionPool dataPool;
	
	private ExecutorService executor;

	@Override
	public void onLoad() {
		setInstance(this);
		((PluginLogger) getLogger()).setTag(new ChatArguments(ChatStyle.RESET, "[", ChatStyle.GOLD, "Mail", ChatStyle.RESET, "] "));
		engine = getEngine();
		initExecutor();
		database = new MySql();
		database.connect("tenowg", "Crivitz1", "localhost", 3306, "Mail");
		dataPool = new DBConnectionPool(database);
		getLogger().info("loaded");
	}

	@Override
	public void onEnable() {
		//Commands
		final CommandRegistrationsFactory<Class<?>> commandRegFactory = new AnnotatedCommandRegistrationFactory(getEngine(), new SimpleInjector(this), new SimpleAnnotatedCommandExecutorFactory());
		final RootCommand root = engine.getRootCommand();
		//root.addSubCommands(this, PlayerCommands.class, commandRegFactory);
		//root.addSubCommands(this, AdminCommands.class, commandRegFactory);

		engine.getEventManager().registerEvents(new PlayerListener(this), this);

		getLogger().log(Level.INFO, "v{0} enabled.", getDescription().getVersion());
	}

	@Override
	public void onDisable() {
		getLogger().log(Level.INFO, "v{0} disabled.", getDescription().getVersion());
	}
	
	private static void setInstance(Mail instance) {
		Mail.instance = instance;
	}
	
	public static Mail getInstance() {
		return instance;
	}
	
	public void initExecutor() {
		executor = Executors.newCachedThreadPool();
	}
	
	public ExecutorService getExecutor() {
		return executor;
	}
	
	public DBO getDatabase() {
		return database;
	}
	
	public DBConnectionPool getDataPool() {
		return dataPool;
	}
}
