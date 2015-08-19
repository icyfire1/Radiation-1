package com.icy.sunrad;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Radiation extends JavaPlugin implements Listener{
	Logger logger = Logger.getLogger("minecraft");
	public MyConfigManager ConfigManager;
	public MyConfig Config;
	@Override
	public void onEnable(){
		logger.info("Radiation has been activated. Starting radiation effect.");
		//Starts up the repeating task.
		ConfigManager = new MyConfigManager(this);
		Config = ConfigManager.getNewConfig("Radiation.yml", new String[] {"Radiation Plugin", "Made by icyfire1", "If you need help, please contact me!", "Radiation NOTES - Version"
				+ " 2.2.3",
				"Version Update -", "Added block drop increases and distance", "Stuff you can edit: Distance, Location, Starting level"});
		if(Config.getInt("Radiation.Level") <= 0){
			Config.set("Radiation.Level", 17);
		}
		if(Config.getInt("Radiation.Distance") <= 0){
			Config.set("Radiation.Distance", 200);
		}
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			@Override
			public void run() {

				int cX = Config.getInt("Radiation.X");
				int cY = Config.getInt("Radiation.Y");

				int Distance = Config.getInt("Radiation.Distance");
				for (Player player : Bukkit.getOnlinePlayers()) {
					final World w = player.getWorld();
					if(w.getName() == Config.getString("Radiation.World")){
						findXZ(player);
					}
				}
			}

		}, 0L, 100L);
		Config.saveConfig();
	}
	@Override
	public void onDisable(){
		logger.info("Radiation disabled.");
		Config.saveConfig();
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player p = (Player)sender;
		if(p.hasPermission("rad.cmd")){
			if(cmd.getName().equalsIgnoreCase("rad")){
				World w = p.getWorld();
				if(args[0].equalsIgnoreCase("spawn")){
					Block b = w.getBlockAt(p.getLocation());
					Config.set("Radiation.X", b.getX());
					Config.set("Radiation.Y", b.getY());
					Config.set("Radiation.Z", b.getZ());
					Config.set("Radiation.World", w.getName());
					Config.saveConfig();
					return false;
				}
				if(args[0].equalsIgnoreCase("delete")){
					Config.set("Radiation.World", "delete");
					Config.saveConfig();
					return false;
				}
			}
		}
		return false;
	}
	public void findXZ(Player player){
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();

		int cX = Config.getInt("Radiation.X");
		int cZ = Config.getInt("Radiation.Z");

		int Distance = Config.getInt("Radiation.Distance");
		int level = getLevel(player);
			if(cX - (6 * Distance) >= x || cX + (6 * Distance) <= x || cZ - (6 * Distance) >= z || cZ + (6 * Distance) <= z){
				player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 160, level));
			}
			if(cX - (5 * Distance) >= x || cX + (5 * Distance) <= x || cZ - (5 * Distance) >= z || cZ + (5 * Distance) <= z){
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));
			}
			if(cX - (4 * Distance) >= x || cX + (4 * Distance) <= x || cZ - (4 * Distance) >= z || cZ + (4 * Distance) <= z){
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
			}
			if(cX - (3 * Distance) >= x || cX + (3 * Distance) <= x || cZ - (3 * Distance) >= z || cZ + (3 * Distance) <= z){
				player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 160, level));
			}
			if(cX - (2 * Distance) >= x || cX + (2 * Distance) <= x || cZ - (2 * Distance) >= z || cZ + (2 * Distance) <= z){
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 1));
			}
			if(cX - (1 * Distance) >= x || cX + (1 * Distance) <= x || cZ - (1 * Distance) >= z || cZ + (1 * Distance) <= z){
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 160, 2));
			}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void BlockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();

		int x = p.getLocation().getBlockX();
		int z = p.getLocation().getBlockZ();

		int cX = Config.getInt("Radiation.X");
		int cZ = Config.getInt("Radiation.Z");

		int Distance = Config.getInt("Radiation.Distance");
		String cWorld = Config.getString("Radiation.World");
		int multiplier = 0;
		if(cX != 69){
			if(p.getWorld().getName() == cWorld){
				if(cX - (6 * Distance) >= x || cX + (6 * Distance) <= x || cZ - (6 * Distance) >= z || cZ + (6 * Distance) <= z){
					multiplier += 1;
				}
				if(cX - (5 * Distance) >= x || cX + (5 * Distance) <= x || cZ - (5 * Distance) >= z || cZ + (5 * Distance) <= z){
					multiplier += 1;
				}
				if(cX - (4 * Distance) >= x || cX + (4 * Distance) <= x || cZ - (4 * Distance) >= z || cZ + (4 * Distance) <= z){
					multiplier += 1;
				}
				if(cX - (3 * Distance) >= x || cX + (3 * Distance) <= x || cZ - (3 * Distance) >= z || cZ + (3 * Distance) <= z){
					multiplier += 1;
				}
				if(cX - (2 * Distance) >= x || cX + (2 * Distance) <= x || cZ - (2 * Distance) >= z || cZ + (2 * Distance) <= z){
					multiplier += 1;
				}
				if(cX - (1 * Distance) >= x || cX + (1 * Distance) <= x || cZ - (1 * Distance) >= z || cZ + (1 * Distance) <= z){
					multiplier += 1;
				}
				for(int i = 0; i < multiplier; i++){
					p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(e.getBlock().getType()));
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	//Finds level of radiation/poison to apply to player.
	public int getLevel(Player p){

		//Helmet TODO
		int level = Config.getInt("Radiation.Level");
		if(p.getInventory().getHelmet() != null){
			switch (p.getInventory().getHelmet().getTypeId()) {
			case 298:
				level -= 1;
				break;
			case 302:
				level -= 1;
				break;
			case 306:
				level -= 2;
				break;
			case 310:
				level -= 3;
				break;
			case 314:
				level -= 3;
				break;
			case 8893:
				level -= 2;
				break;
			case 5561:
				level -= 4;
				break;
			case 10168:
				level -= 4;
				break;
			case 25027:
				level -= 4;
				break;
			case 20413:
				level -= 4;
				break;
			default:
				break;
			}
		}
		if(p.getInventory().getChestplate() != null){
			switch (p.getInventory().getChestplate().getTypeId()) {
			case 299:
				level -= 1;
				break;
			case 303:
				level -= 1;
				break;
			case 307:
				level -= 2;
				break;
			case 311:
				level -= 3;
				break;
			case 315:
				level -= 3;
				break;
			case 8891:
				level -= 2;
				break;
			case 5560:
				level -= 4;
				break;
			case 10169:
				level -= 4;
				break;
			case 25028:
				level -= 4;
				break;
			case 20410:
				level -= 4;
				break;
			default:
				break;
			}
		}
		if(p.getInventory().getLeggings() != null){
			switch (p.getInventory().getLeggings().getTypeId()) {
			case 300:
				level -= 1;
				break;
			case 304:
				level -= 1;
				break;
			case 308:
				level -= 2;
				break;
			case 312:
				level -= 3;
				break;
			case 316:
				level -= 3;
				break;
			case 8892:
				level -= 2;
				break;
			case 5562:
				level -= 4;
				break;
			case 10170:
				level -= 4;
				break;
			case 25029:
				level -= 4;
				break;
			case 20412:
				level -= 4;
				break;
			default:
				break;
			}
		}
		if(p.getInventory().getBoots() != null){
			switch (p.getInventory().getBoots().getTypeId()) {
			case 301:
				level -= 1;
				break;
			case 305:
				level -= 1;
				break;
			case 309:
				level -= 2;
				break;
			case 313:
				level -= 3;
				break;
			case 317:
				level -= 3;
				break;
			case 8842:
				level -= 2;
				break;
			case 5559:
				level -= 4;
				break;
			case 10171:
				level -= 4;
				break;
			case 25030:
				level -= 4;
				break;
			case 20411:
				level -= 4;
				break;
			default:
				break;
			}
		}
		return level;
	}
	/* Armor Ids TODO
	 *------Leather 1
	 * 298
	 * 299
	 * 300
	 * 301
	 *------Chain 1
	 * 302
	 * 303
	 * 304
	 * 305
	 *------Iron 2
	 * 306
	 * 307
	 * 308
	 * 309
	 *------Diamond 3
	 * 310
	 * 311
	 * 312
	 * 313
	 *------Gold 3
	 * 314
	 * 315
	 * 316
	 * 317
	 *------Invar 2
	 * 8891
	 * 8892
	 * 8893
	 * 8894
	 *------Heavy 4
	 * 5559
	 * 5560
	 * 5561
	 * 5562
	 *------Desh 4
	 * 10168
	 * 10169
	 * 10170
	 * 10171
	 *------Power 4
	 * 25027
	 * 25028
	 * 25029
	 * 25030
	 * Armor Ids TODO
	 * */
}