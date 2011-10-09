package me.Guga.Guga_SERVER_MOD;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class GugaRegionHandler 
{
	public static void SetPlugin(Guga_SERVER_MOD plugin)
	{
		GugaRegionHandler.plugin = plugin;
	}
	public static void AddRegion(String name, String[] owners, int x1, int x2, int z1, int z2)
	{
		GugaRegionHandler.regions.add(new GugaRegion(name, owners, x1, x2, z1, z2));
		GugaRegionHandler.SaveRegions();
	}
	public static boolean CanInteract(String pName, int x, int z)
	{
		GugaRegion region = GugaRegionHandler.GetRegionByCoords(x, z);
		if (region != null)
		{
			String[] owners = region.GetOwners();
			int i = 0;
			while (i < owners.length)
			{
				if (owners[i].equalsIgnoreCase(pName))
				{
					return true;
				}
				i++;
			}
			return false;
		}
		return true;
	}
	public static void RemoveRegion(GugaRegion region)
	{
		GugaRegionHandler.regions.remove(region);
	}
	public static ArrayList<GugaRegion> GetAllRegions()
	{
		return GugaRegionHandler.regions;
	}
	public static GugaRegion GetRegionByName(String name)
	{
		Iterator<GugaRegion> i = GugaRegionHandler.regions.iterator();
		while (i.hasNext())
		{
			GugaRegion region = i.next();
			if (region.GetName().equalsIgnoreCase(name))
			{
				return region;
			}
		}
		return null;
	}
	public static GugaRegion GetRegionByCoords(int x, int z)
	{
		Iterator<GugaRegion> i = GugaRegionHandler.regions.iterator();
		while (i.hasNext())
		{
			GugaRegion region = i.next();
			if (region.IsInRegion(x, z))
			{
				return region;
			}
		}
		return null;
	}
	public static void SaveRegions()
	{
		plugin.log.info("Saving Regions file...");
		GugaFile file = new GugaFile("plugins/Regions.dat", GugaFile.WRITE_MODE);
		file.Open();
		GugaRegion region;
		String name;
		String[] owners;
		int[] coords;
		String line;
		Iterator<GugaRegion> i = GugaRegionHandler.regions.iterator();
		while (i.hasNext())
		{
			String ownersString = "";
			region = i.next();
			name = region.GetName();
			owners = region.GetOwners();
			coords = region.GetCoords();
			int i2 = 0;
			while (i2 < owners.length)
			{
				if (i2 == owners.length - 1)
					ownersString += owners[i2];
				else
					ownersString += owners[i2] + ",";
				
				i2++;
			}
			line = name + ";" + ownersString + ";" + coords[GugaRegion.X1] + ";" + coords[GugaRegion.X2] + ";" + coords[GugaRegion.Z1] + ";" + coords[GugaRegion.Z2];
			file.WriteLine(line);
		}
		file.Close();
	}
	public static void LoadRegions()
	{
		plugin.log.info("Loading Regions file...");
		GugaFile file = new GugaFile("plugins/Regions.dat", GugaFile.READ_MODE);
		file.Open();
		String line;
		String name;
		String[] owners;
		int x1;
		int x2;
		int z1;
		int z2;
		String[] splittedString;
		while ((line = file.ReadLine()) != null)
		{
			splittedString = line.split(";");
			name = splittedString[0];
			owners = splittedString[1].split(",");
			x1 = Integer.parseInt(splittedString[2]);
			x2 = Integer.parseInt(splittedString[3]);
			z1 = Integer.parseInt(splittedString[4]);
			z2 = Integer.parseInt(splittedString[5]);
			GugaRegionHandler.regions.add(new GugaRegion(name, owners, x1, x2, z1, z2));
		}
		file.Close();
	}
	private static Guga_SERVER_MOD plugin;
	private static ArrayList<GugaRegion> regions = new ArrayList<GugaRegion>();
}