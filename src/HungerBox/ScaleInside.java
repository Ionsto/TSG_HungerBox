import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
 
public class ScaleInside extends BukkitRunnable {
    @Override
    public void run() {
        // What you want to schedule goes here
        HungerBox.SizeX -= Decay;
        HungerBox.SizeY -= Decay;
		if(HungerBox.SizeX < Min)
		{
			HungerBox.SizeX = Min;
		}
		if(HungerBox.SizeY < Min)
		{
			HungerBox.SizeY = Min;
		}
		//plugin.getServer().broadcastMessage("Welcome to Bukkit! Remember to read the documentation!");
    }
}