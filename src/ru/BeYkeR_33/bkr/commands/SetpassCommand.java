package ru.BeYkeR_33.bkr.commands;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.BeYkeR_33.bkr.bkr;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetpassCommand implements CommandExecutor{
	public static bkr plugin;
	public SetpassCommand(bkr instance) { plugin = instance; }

	public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args){
		String pname = sender.getName();
	    if (!(sender instanceof Player)) {
	        return true;
	    }
	    if(plugin.getCustomConfig().getBoolean("password.use." + pname) == true)
		{
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Вы уже зарегистрированы");
			return true;
		}
		if (args.length == 0){
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Не хватает аргументов");
			return true;
		}
		if(args.length > 1){
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Слишком много аргументов");
			return true;
		}
		if(plugin.getConfig().getBoolean("options.use-MD5 Enryption") == true)
		{
			try{
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(args[0].getBytes(), 0, args[0].length());
				plugin.invalid.remove(pname);
				plugin.getCustomConfig().set("password.use." + pname, true);
				plugin.getCustomConfig().set("password.password." + pname, new BigInteger(1, md.digest()).toString(16));
				plugin.saveCustomConfig();
				sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Ваш пароль: " + ChatColor.DARK_GREEN + args[0]);
			} catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
		}else
		{
			plugin.invalid.remove(pname);
			plugin.getCustomConfig().set("password.use." + pname, true);
			plugin.getCustomConfig().set("password.password." + pname, args[0]);
			plugin.saveCustomConfig();
			sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Ваш пароль: " + ChatColor.DARK_GREEN + args[0]);
		}
	    return true;
	}
}
