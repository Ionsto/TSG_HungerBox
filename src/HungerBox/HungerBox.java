package HungerBox;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.yaml.snakeyaml.Yaml;

public class HungerBox extends JavaPlugin implements Listener{
	public static float SizeX = 100,SizeZ = 100;//The starting size from the scenter
	float DecayTicks = 50;//How often the scheduled event happens
	public static float Decay = 1;//How much the hunger box shrinks by each scheduled event
	public static float MinX = 10;//The minimum the non hunger box can shrink to
	public static float MinZ = 10;//The minimum the non hunger box can shrink to
    public boolean Start = false;
    BukkitTask Timersystem = null;
	@Override
    public void onEnable() {
    	this.getServer().getPluginManager().registerEvents(this, this);
    }
 
    @Override
    public void onDisable() {
    } 
    public float ZDiv(float x,float y)
    {
    	if(x == 0 || y == 0)
    	{
    		return 0;
    	}
    	return x/y;
    }
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent evt) {
    	if(Start)
    	{
	        Player player = evt.getPlayer(); // The player who joined
			//Check if inside box
	        if(Math.abs(player.getLocation().getX()) > SizeX || Math.abs(player.getLocation().getZ()) > SizeZ)
	        {
	        	float DiffX = (float) Math.max(Math.abs(player.getLocation().getX()) - SizeX,0);
	        	float DiffZ = (float) Math.max(Math.abs(player.getLocation().getZ()) - SizeZ,0);
				float Hungereffect = 1 * ((DiffX*DiffX)+(DiffZ * DiffZ));
				player.removePotionEffect(PotionEffectType.HUNGER);
				player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 9999, (int) Hungereffect));
	    		//getServer().broadcastMessage("Set" + String.valueOf(Hungereffect));
	        }
	    	else
	    	{
	    		//getServer().broadcastMessage("Set 0");
				player.removePotionEffect(PotionEffectType.HUNGER);
	    	}
    	}
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
		if(args.length == 1)
		{
			if(args[1].matches("-?\\d+"))//Regex for checking if string is number
			{
				if (cmd.getName().equalsIgnoreCase("HungerDecayTick")) 
				{
					DecayTicks = Float.parseFloat(args[0]);
	    	    	return true;
				}
				if (cmd.getName().equalsIgnoreCase("HungerDecay")) 
				{
	    			Decay = Float.parseFloat(args[0]);
	    	    	return true;
	    		}
    		}
    	}
		else if(args.length == 3)
		{
			if(args[0].matches("-?\\d+") && args[1].matches("-?\\d+") )//Regex for checking if string is number
			{
				if (cmd.getName().equalsIgnoreCase("HungerStartSize")) 
				{
	    			SizeX = Float.parseFloat(args[0]);
	    			SizeZ = Float.parseFloat(args[1]);
	    	    	return true;
	    		}
				if (cmd.getName().equalsIgnoreCase("HungerEndSize")) 
				{
	    			SizeX = Float.parseFloat(args[0]);
	    			SizeZ = Float.parseFloat(args[1]);
	    	    	return true;
	    		}
			}
		}
		if (cmd.getName().equalsIgnoreCase("HungerStart")) 
		{
			Start = true;
			if(Timersystem != null)
			{
				Timersystem.cancel();
			}
			Timersystem = new ScaleInside(this).runTaskTimer(this, 0, (long) DecayTicks);
			return true;
		}
    	return false;
    }
}
