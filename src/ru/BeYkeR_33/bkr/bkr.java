package ru.BeYkeR_33.bkr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.BeYkeR_33.bkr.commands.ChangePassCommand;
import ru.BeYkeR_33.bkr.commands.LoginCommand;
import ru.BeYkeR_33.bkr.commands.RmpassCommand;
import ru.BeYkeR_33.bkr.commands.SetpassCommand;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class bkr extends JavaPlugin{
	public static String Version = "1.5.5";
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;
	public Logger log = Logger.getLogger("Minecraft");
	public List<String> invalid = new ArrayList<String>();
	public List<String> ignore = new ArrayList<String>();

	@Override
	public void onEnable(){
		log.info("[BkRegister] Plugin Started ;)");
		getServer().getPluginManager().registerEvents(new bkrLogin(this), this);
		getServer().getPluginManager().registerEvents(new LogginSession(this), this);
		final FileConfiguration config = this.getConfig();
		getCustomConfig().options().header("please do not remove the the data file");
		config.addDefault("options.password-required", false);
		config.addDefault("options.updateNotificationOnLogin", true);
		config.addDefault("options.use-MD5 Enryption", true);
		config.addDefault("options.blindness", true);
		config.options().copyDefaults(true);
		saveConfig();
		reloadCustomConfig();
		saveCustomConfig();
		getCommand("setpass").setExecutor(new SetpassCommand(this));
		getCommand("login").setExecutor(new LoginCommand(this));
		getCommand("rmpass").setExecutor(new RmpassCommand(this));
		getCommand("changepass").setExecutor(new ChangePassCommand(this));
		log.info("[BkRegister] " + ChatColor.GREEN + "successful load");
	}
	public void onDisable(){
		log.info("[BkRegister] Plugin Off");
		reloadConfig();
		saveConfig();
		reloadCustomConfig();
		saveCustomConfig();
	}
	public void reloadCustomConfig() {
        if (customConfigFile == null) {
        customConfigFile = new File(getDataFolder(), "data.yml");
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        java.io.InputStream defConfigStream = this.getResource("data.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }
	public FileConfiguration getCustomConfig(){
        if (customConfig == null) {
            this.reloadCustomConfig();
        }
        return customConfig;
    }
	public void saveCustomConfig() {
        if (customConfig == null || customConfigFile == null) {
        return;
        }
        try {
            getCustomConfig().save(customConfigFile);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);}
	}
}
