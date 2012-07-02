package com.tekkitcraft.sped.nocaps;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoCaps extends JavaPlugin implements Listener {
	
	Logger log = Logger.getLogger("Minecraft"); //Logger to print stuff in console
	FileConfiguration config; //Tell the server we're going to use a config
	
	@Override
	public void onEnable(){ 
		getServer().getPluginManager().registerEvents(this, this);
		log = this.getLogger();
		loadConfiguration();
		log.info("NoCaps enabled!");
	}
	
	@Override
	public void onDisable(){ 
		log.info("NoCaps disabled!");
	}
	
	public void loadConfiguration() {//to load config file
        config = getConfig();//get the configuration

        addDefault("msg.caps", "�4Please don't caps :-(");//add and set default

        config.options().copyDefaults(true);//copy the default from above
        saveConfig();//write them to file
    }
	
	private void addDefault(String o, String p) {//add and set default
        File configFile = new File("plugins" + File.separator + this.getDescription().getName() + File.separator + "config.yml");//config file
        config.addDefault(o, p);//add default to config
        if (configFile.exists() == false) {//if there is no config file, set defaults as well!
            config.set(o, p);
        }
    }
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event) {
		String msg = event.getMessage();
		Player sender = event.getPlayer();
		
//		if(msg.startsWith("&")) { //detect if a player is trying to color chat *sigh*
//			
//		}
		if(msg.toUpperCase().equals(msg) && (msg.length() >= 4) && !msg.contains("...") && !msg.contains("-") && !msg.contains("^") && !sender.hasPermission("nocaps.caps")) { //detect if msg is full caps on a shitty way
			event.setMessage(msg.substring(0,1).toUpperCase()+msg.substring(1).toLowerCase());
			sender.sendMessage(config.getString("msg.caps"));
			
		}
	}
}