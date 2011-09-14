package me.Guga.Guga_SERVER_MOD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
		File chests = new File(chestsFile);
		if (!chests.exists())
		{
			try 
			{
				chests.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
    	else
		{
			try 
			{
				FileInputStream fRead = new FileInputStream(chests);
				DataInputStream inStream = new DataInputStream(fRead);
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));		
				String line;
				int i = 0;
				double locX;
				double locY;
				double locZ;
				while ((line = bReader.readLine()) != null)
				{
					locX = Double.parseDouble(line.split(";")[0]);
					locY = Double.parseDouble(line.split(";")[1]);
					locZ = Double.parseDouble(line.split(";")[2]);
					location[i] = new Location(plugin.getServer().getWorld("world"),locX, locY, locZ);
					owner[i] = line.split(";")[3];
					i++;
				}
				bReader.close();
				inStream.close();
				fRead.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
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
		File chests = new File(chestsFile);
		if (!chests.exists())
		{
			try 
			{
				chests.createNewFile();
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
			try {
				int i = 0;
				FileWriter fStream = new FileWriter(chests, false);
				BufferedWriter bWriter;
				bWriter = new BufferedWriter(fStream);
				while (location[i] != null)
				{
					String x = Integer.toString(location[i].getBlockX());
					String y = Integer.toString(location[i].getBlockY());
					String z = Integer.toString(location[i].getBlockZ());
					
					String line;
					line = x+";"+y+";"+z+";"+owner[i];
					bWriter.write(line);
					bWriter.newLine();
					i++;
				}
					bWriter.close();
					fStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	private String owner[] = new String[10000];
	private Location[] location = new Location[10000];
	private String chestsFile = "plugins/Chests.dat";
	public static Guga_SERVER_MOD plugin;
}
