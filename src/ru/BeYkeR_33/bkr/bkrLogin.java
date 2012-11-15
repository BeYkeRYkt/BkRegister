package ru.BeYkeR_33.bkr;

import ru.BeYkeR_33.bkr.bkr;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class bkrLogin implements Listener{
	public boolean enable;
	public static bkr plugin;
	public bkrLogin(bkr instance) { plugin = instance; }

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent join){
		Player player = join.getPlayer();
		String pname = player.getName();
		if(plugin.ignore.contains(pname) && player.getAddress().getAddress().toString().equals(plugin.getCustomConfig().getString("ip." + pname)))
		{
			player.sendMessage("[BkRegister] Продолжаете последнию сессию.");
			return;
		}
		if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
			player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
			if(!plugin.invalid.contains(pname)){
				plugin.invalid.add(pname);
			}
			if(plugin.getConfig().getBoolean("options.blindness") == true)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1728000, 15));
			}
		}
		else if(!plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
			if(plugin.invalid.contains(pname)){
				plugin.invalid.remove(pname);
			}
			return;
		}
		else{
			player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			if(!plugin.invalid.contains(pname)){
				plugin.invalid.add(pname);
			}
			if(plugin.getConfig().getBoolean("options.blindness") == true)
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1728000, 15));
			}
		}
	}
	@EventHandler
	public void onPlayerMoving(PlayerMoveEvent move){
		Player player = move.getPlayer();
		String pname = player.getName();
		Location loc = player.getLocation();
		if(plugin.invalid.contains(pname)){
			player.teleport(loc);
		}else if(player.hasPotionEffect(PotionEffectType.BLINDNESS))
			player.removePotionEffect(PotionEffectType.BLINDNESS);
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent chat){
		Player player = chat.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname)){
			if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
				player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>");
			}else
			{
				player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			}
			chat.setCancelled(true);
		}
	}
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent drop)
	{
		Player player = drop.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname))
		{
			if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
				player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
			}else
			{
				player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			}
			drop.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent bbreak)
	{
		Player player = bbreak.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname))
		{
			if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
				player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
			}else
			{
				player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			}
			bbreak.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent place)
	{
		Player player = place.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname))
		{
			if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
				player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
			}else
			{
				player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			}
			place.setCancelled(true);
		}
		if(place.getBlock().getType() == Material.getMaterial(22))
		{
			
		}
	}
	@EventHandler
	public void onBucketFill(PlayerBucketFillEvent fill)
	{
		Player player = fill.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname))
		{
			if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
				player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
			}else
			{
				player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			}
			fill.setCancelled(true);
		}
	}
	@EventHandler
	public void onBucketFill(PlayerBucketEmptyEvent empty)
	{
		Player player = empty.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname))
		{
			if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
				player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
			}else
			{
				player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
			}
			empty.setCancelled(true);
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent command){
		Player player = command.getPlayer();
		String pname = player.getName();
		if(plugin.invalid.contains(pname)){
		    if(!command.getMessage().startsWith("/login") && !command.getMessage().startsWith("/setpass"))
		  	{
		    	command.setCancelled(true);
				if(plugin.getConfig().getBoolean("options.password-required") == true && !plugin.getCustomConfig().getBoolean("password.use." + pname) == true){
					player.sendMessage(ChatColor.RED + "Для регистрации используйте: /setpass <Пароль>!");
				}else
				{
					player.sendMessage(ChatColor.RED + "Пожалуйста проверьте свой пароль: /login <Пароль>");
				}
		  	}
		}
	}
}
