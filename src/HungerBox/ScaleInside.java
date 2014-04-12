package HungerBox;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
 
public class ScaleInside extends BukkitRunnable {
	JavaPlugin plugin;
	public ScaleInside(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}
    @Override
    public void run() {
        // What you want to schedule goes here
        HungerBox.SizeX -= HungerBox.Decay;
        HungerBox.SizeZ -= HungerBox.Decay;
		if(HungerBox.SizeX < HungerBox.MinX)
		{
			HungerBox.SizeX = HungerBox.MinX;
		}
		if(HungerBox.SizeZ < HungerBox.MinZ)
		{
			HungerBox.SizeZ = HungerBox.MinZ;
		}
		plugin.getServer().broadcastMessage("Box Shrink Size:" + String.valueOf(HungerBox.SizeX) + ":" + String.valueOf(HungerBox.SizeZ));
    }
}