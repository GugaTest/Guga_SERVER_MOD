package me.Guga.Guga_SERVER_MOD;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class GugaChests 
{
	GugaChests(Guga_SERVER_MOD gugaSM)
	{
		plugin = gugaSM;
		LoadChests();
	}
	public void LockChest(Block chest,String chestOwner)
	{
		int i = 0;
		while (location[i] != null)
		{
			i++;
		}
		location[i] = chest.getLocation();
		owner[i] = chestOwner;
		SaveChests();
	}
	public void UnlockChest(Block chest,String chestOwner)
	{
		Location bufferLoc[] = new Location[10000];
		String bufferOwn[] = new String[10000];
		int i = 0;
		int i2 = 0;
		while (location[i2] != null)
		{
			if (LocationEquals(location[i],chest.getLocation()))
			{
				i2++;
			}
			bufferLoc[i] = location[i2];
			bufferOwn[i] = owner[i2];
			i++;
			i2++;
		}
		i=0;
		location = new Location[10000];
		owner = new String[10000];
		while (bufferLoc[i] != null)
		{
			location[i] = bufferLoc[i];
			owner[i] = bufferOwn[i];
			i++;
		}
		SaveChests();
	}
	public String GetChestOwner(Block chest)
	{
		int i = 0;
		while (location[i] != null)
		{
			if (LocationEquals(location[i],chest.getLocation()))
			{
				return owner[i];
			}
			i++;
		}
		return "notFound";
	}
	public void LoadChests()
	{
		plugin.log.info("Loading Chest Data...");
		GugaFile file = new GugaFile(chestsFile, GugaFile.READ_MODE);
		file.Open();
		String line;
		int i = 0;
		double locX;
		double locY;
		double locZ;
		while ((line = file.ReadLine()) != null)
		{
			locX = Double.parseDouble(line.split(";")[0]);
			locY = Double.parseDouble(line.split(";")[1]);
			locZ = Double.parseDouble(line.split(";")[2]);
			location[i] = new Location(plugin.getServer().getWorld("world"),locX, locY, locZ);
			owner[i] = line.split(";")[3];
			i++;
		}
		file.Close();
	}
	public boolean LocationEquals(Location loc1, Location loc2)
	{
		if (loc1.getBlockX() == loc2.getBlockX())
		{
			if (loc1.getBlockY() == loc2.getBlockY())
			{
				if (loc1.getBlockZ() == loc2.getBlockZ())
				{
					return true;
				}
			}
		}
		return false;
	}
	public void SaveChests()
	{
		plugin.log.info("Saving Chest Data...");
		GugaFile file = new GugaFile(chestsFile, GugaFile.WRITE_MODE);
		file.Open();
		int i = 0;
		while (location[i] != null)
		{
			String x = Integer.toString(location[i].getBlockX());
			String y = Integer.toString(location[i].getBlockY());
			String z = Integer.toString(location[i].getBlockZ());
			
			String line;
			line = x+";"+y+";"+z+";"+owner[i];
			file.WriteLine(line);
			i++;
		}
		file.Close();
	}
	
	private String owner[] = new String[10000];
	private Location[] location = new Location[10000];
	private String chestsFile = "plugins/Chests.dat";
	public static Guga_SERVER_MOD plugin;
}
