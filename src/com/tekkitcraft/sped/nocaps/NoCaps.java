package com.tekkitcraft.sped.nocaps;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Sped (Whattehp406, loled2)
 */

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

        addDefault("msg.caps", "§4Please don't caps :-(");//add and set default
        addDefault("msg.minimumlenght", "5");
        addDefault("command.executeoncaps", "/warn <sender> NoCaps: Caps");
        addDefault("command.enableexecution", "false"); //Defaults to false

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
	public void onPlayerChat(PlayerChatEvent event) { //called when player chats.
		String msg = event.getMessage(); //The chat message the player sent
		Player sender = event.getPlayer(); //The player who sent the chat message
		
		if(msg.toUpperCase().equals(msg) && (msg.length() >= config.getInt("msg.minmumlength")) && !msg.contains("...") && !msg.contains("-") && !msg.contains("^") && !sender.hasPermission("nocaps.caps")) { //detect if msg is full caps on a shitty way
			event.setMessage(msg.substring(0,1).toUpperCase()+msg.substring(1).toLowerCase());
			sender.sendMessage(config.getString("msg.caps"));
			if(config.getBoolean("command.enableexecution")) { //if commend execution is enabled
			String command = config.getString("command.executeoncaps").replaceAll("<sender>" , sender.getDisplayName());
			getServer().dispatchCommand(Bukkit.getConsoleSender(), command); //execute command
			}
			
		}
	}
}