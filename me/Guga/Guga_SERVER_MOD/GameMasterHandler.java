package me.Guga.Guga_SERVER_MOD;

import java.util.ArrayList;
import java.util.Iterator;

import me.Guga.Guga_SERVER_MOD.GameMaster.Rank;

public abstract class GameMasterHandler 
{
	public static void SetPlugin(Guga_SERVER_MOD plugin)
	{
		GameMasterHandler.plugin = plugin;
	}
	public static void AddGM(String name, String rank)
	{
		Rank r = Rank.GetRankByName(rank);
		GameMasterHandler.gameMasters.add(new GameMaster(name, r));
	}
	public static GameMaster GetGMByName(String name)
	{
		Iterator<GameMaster> i = GameMasterHandler.gameMasters.iterator();
		while (i.hasNext())
		{
			GameMaster gm = i.next();
			if (gm.GetName().matches(name))
				return gm;
		}
		return null;
	}
	public static boolean IsAtleastGM(String name)
	{
		GameMaster gm;
		if ((gm = GameMasterHandler.GetGMByName(name)) != null)
		{
			if (gm.IsAtleastGM())
				return true;
		}
		return false;
	}
	public static boolean IsAdmin(String name)
	{
		GameMaster gm;
		if ((gm = GameMasterHandler.GetGMByName(name)) != null)
		{
			if (gm.IsAdmin())
				return true;
		}
		return false;
	}
	public static void LoadGMs()
	{
		GugaFile file = new GugaFile(GameMasterHandler.gmFile, GugaFile.READ_MODE);
		file.Open();
		String line;
		while ((line = file.ReadLine()) != null)
		{
			String[] split = line.split(";");
			GameMasterHandler.AddGM(split[0], split[1]);
		}
		file.Close();
	}
	public static void SaveGMs()
	{
		GugaFile file = new GugaFile(GameMasterHandler.gmFile, GugaFile.WRITE_MODE);
		file.Open();
		Iterator<GameMaster> i = GameMasterHandler.gameMasters.iterator();
		while (i.hasNext())
		{
			GameMaster gm = i.next();
			String line = gm.GetName() + ";" + gm.GetRank().GetRankName();
			file.WriteLine(line);
		}
		file.Close();
	}
	private static ArrayList<GameMaster> gameMasters = new ArrayList<GameMaster>();
	@SuppressWarnings("unused")
	private static Guga_SERVER_MOD plugin;
	private static String gmFile = "plugins/GameMasters.dat";
}