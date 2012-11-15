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

public class ChangePassCommand implements CommandExecutor{
	public static bkr plugin;
	public ChangePassCommand(bkr instance) { plugin = instance; }
	
	public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args){
		String pname = sender.getName();
		String password = plugin.getCustomConfig().getString("password.password." + pname);
	    if (!(sender instanceof Player)) {
	        return true;
	    }
		if(args.length < 2){
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Не хватает аргументов, используйте /changepass <Старый пароль> <Новый пароль>");
			return true;
		}
		if(args.length > 2){
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Слишком много аргументов, используйте /changepass <Старый пароль> <Новый пароль>");
			return true;
		}
		if(plugin.getConfig().getBoolean("options.use-MD5 Enryption") == true)
		{
		try{
				MessageDigest md1 = MessageDigest.getInstance("MD5");
				MessageDigest md2 = MessageDigest.getInstance("MD5");
				md1.update(args[0].getBytes(), 0, args[0].length());
				md2.update(args[1].getBytes(), 0, args[1].length());
				if(new BigInteger(1, md1.digest()).toString(16).equals(password)){
					plugin.getCustomConfig().set("password.password." + pname, new BigInteger(1, md2.digest()).toString(16));
					plugin.saveCustomConfig();
					sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Ваш пароль изменен на: " + ChatColor.DARK_GREEN + args[1]);
				}
				else
				{
					sender.sendMessage("[BkRegister] " + ChatColor.BLUE + "Неверный пароль");	
					return true;
				}
			} catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
		}else
		{
			if(args[0].equals(password))
			{
				plugin.getCustomConfig().set("password.password." + pname, args[1]);
				plugin.saveCustomConfig();
				sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Ваш пароль изменен на: " + ChatColor.DARK_GREEN + args[1]);
			}else
			{
				sender.sendMessage("[BkRegister] " + ChatColor.BLUE + "Неверный пароль");	
				return true;
			}
		}
		return true;
	}
}
