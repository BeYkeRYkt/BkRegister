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
import org.bukkit.potion.PotionEffectType;

public class LoginCommand implements CommandExecutor{
	public static bkr plugin;
	public LoginCommand(bkr instance) { plugin = instance; }
	
	public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args){
		String pname = sender.getName();
		String password = plugin.getCustomConfig().getString("password.password." + pname);
		Player player = null;
	    if ((sender instanceof Player))
	    {
	      player = (Player)sender;
	    }
		if(args.length == 0){
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
				if(new BigInteger(1, md.digest()).toString(16).equals(password)){
					plugin.invalid.remove(pname);
					sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Успешно залоген");
					if(player.hasPotionEffect(PotionEffectType.BLINDNESS))
					{
						player.removePotionEffect(PotionEffectType.BLINDNESS);
					}
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
			if(args[0].equals(password)){
				plugin.invalid.remove(pname);
				sender.sendMessage("[BkRegister] " + ChatColor.GREEN + "Успешно залоген");
			}
			else
			{
				sender.sendMessage("[BkRegister] " + ChatColor.BLUE + "Неверный пароль");	
				return true;
			}
		}
		return true;
	}
}
