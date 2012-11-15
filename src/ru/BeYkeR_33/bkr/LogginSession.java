package ru.BeYkeR_33.bkr;

import java.util.WeakHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogginSession implements Listener{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	WeakHashMap<Player, Integer> session = new WeakHashMap();
	public static bkr plugin;
	public LogginSession(bkr instance) { plugin = instance; }

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		String pname = player.getName();
		
		if(plugin.getCustomConfig().getBoolean("password.use." + pname) == true && !plugin.invalid.contains(pname))
		{
			session.put(player, 80);
			plugin.getCustomConfig().set("ip." + pname, player.getAddress().getAddress().toString());
			plugin.saveCustomConfig();
		}
		plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable()
		{
			public void run()
			{
				for (Player player : LogginSession.this.session.keySet())
				{
					String pname = player.getName();
					int current = ((Integer)LogginSession.this.session.get(player)).intValue();
					
					if(current > 1)
					{
						LogginSession.this.session.put(player, Integer.valueOf(current - 1));
						if(!plugin.ignore.contains(pname))
						{
							plugin.ignore.add(pname);
						}
					}
					else
					{
						LogginSession.this.session.remove(player);
						if(plugin.ignore.contains(pname))
						{
							plugin.ignore.remove(pname);
						}
					}
				}
			}
		}
		, 20L, 20L);
	}
}
