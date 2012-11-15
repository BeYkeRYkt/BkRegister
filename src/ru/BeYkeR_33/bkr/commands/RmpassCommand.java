package ru.BeYkeR_33.bkr.commands;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ru.BeYkeR_33.bkr.bkr;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RmpassCommand implements CommandExecutor{
	public static bkr plugin;
	public RmpassCommand(bkr instance) { plugin = instance; }
	
	public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args){
		String pname = sender.getName();
		String password = plugin.getCustomConfig().getString("password.password." + pname);
		if (args.length == 0){
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Не хватает аргументов, используйте /rmpass <Пароль>");
			return true;
		}
		if (args.length > 1){
			sender.sendMessage("[BkRegister] " +  ChatColor.RED + "Слишком много аргументов, используйте /rmpass <Пароль>");
			return true;
		}
		if(plugin.getConfig().getBoolean("options.password-required") == true)
		{
			sender.sendMessage("[BkRegister] " + ChatColor.RED + "Извините, пароль не требуется в данном сервере");
			return true;
		}
		if(plugin.getConfig().getBoolean("options.use-MD5 Enryption") == true)
		{
			try{
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(args[0].getBytes(), 0, args[0].length());
				if(new BigInteger(1, md.digest()).toString(16).equals(password)){
					plugin.getCustomConfig().set("password.use." + pname, false);
					plugin.getCustomConfig().options().copyDefaults(true);
					plugin.saveCustomConfig();
					sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Успешно удален пароль!");
				}
				else
				{
					sender.sendMessage("[BkRegister] " + ChatColor.RED + "Неверный пароль, используйте /rmpass <Пароль>");
					return true;
				}
				return true;
			} catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
		}else
		{
			if(args[0].equals(password)){
				plugin.getCustomConfig().set("password.use." + pname, false);
				plugin.getCustomConfig().options().copyDefaults(true);
				plugin.saveCustomConfig();
				sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Успешно удален пароль!");
			}
			else
			{
				sender.sendMessage("[BkRegister] " + ChatColor.RED + "Неверный пароль, используйте /rmpass <Пароль>");
				return true;
			}
		}
		return true;
	}
}
