import java.util.HashMap;
import java.util.logging.Logger;


public class MWPPlayerHandler {
	String dWorldName = etc.getServer().getDefaultWorld().getName();
	Player player;
	
	public MWPPlayerHandler(Player player){
		this.player = player;
	}
	
	public boolean processFood(){
		if (player.getWorld().getName().equals(dWorldName)){
			return false;
		}
		MWPFiler f = new MWPFiler(player.getWorld().getName());
		if (!f.isFoodbarEnabled()){
			return true;
		}
		return false;
	}
	
	public boolean processHealth(){
		if (player.getWorld().getName().equals(dWorldName)){
			return false;
		}
		MWPFiler f = new MWPFiler(player.getWorld().getName());
		if (!f.isHealthEnabled()){
			return true;
		}
		return false;
	}
	
	public boolean processExp(){
		if (player.getWorld().getName().equals(dWorldName)){
			return false;
		}
		MWPFiler f = new MWPFiler(player.getWorld().getName());
		if (!f.isExpEnabled()){
			return true;
		}
		return false;
	}
	
	public boolean processBlockPlace(Block block){
		if (player.getWorld().getName().equals(dWorldName)){
			return false;
		}
		MWPFiler f = new MWPFiler(player.getWorld().getName());
		if (block.getY() >= f.getMaxHeight()){
			return true;
		}
		return false;
	}
	
	public void saveLocation(Player player){
		MWPMysqlFiler mfiler = new MWPMysqlFiler();
		if (mfiler.useMysql()){
		MWPMysql locs = new MWPMysql();
		HashMap<String,String> locz = locs.getLocationArray();
		locz.put(player.getName(), player.getX()+","+player.getY()+","+player.getZ()+","+player.getLocation().dimension+","+player.getWorld().getName());
		locs.UploadHashMap(locz);
		 Logger.getLogger("Minecraft").info("[MultiWorldPlugin] Location of "+player.getName()+" is Saved");
		}else{
			MWPLocationsFile file = new MWPLocationsFile();
			HashMap<String,String> locz = file.getLocationArray();
			locz.put(player.getName(), player.getX()+","+player.getY()+","+player.getZ()+","+player.getLocation().dimension+","+player.getWorld().getName());
		 file.UploadHashMap(locz);
		 Logger.getLogger("Minecraft").info("[MultiWorldPlugin] Location of "+player.getName()+" is Saved");
		}
	}
	
	public void respawnPlayer(Player player){
		MWPMysqlFiler mfiler = new MWPMysqlFiler();
		if (mfiler.useMysql()){
		MWPMysql locs = new MWPMysql();
		HashMap<String,String> locz = locs.getLocationArray();
		if (locz.containsKey(player.getName())){
			String[] loc = locz.get(player.getName()).split(",");
		if (etc.getServer().isWorldLoaded(loc[4])){
			World[] w = etc.getServer().getWorld(loc[4]);
			Location l = new Location(w[Integer.parseInt(loc[3])], Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
			player.teleportTo(l);
			 Logger.getLogger("Minecraft").info("[MultiWorldPlugin] "+player.getName()+" is Teleported to his last location");
			return;
		}
		player.teleportTo(etc.getServer().getDefaultWorld().getSpawnLocation());
		Logger.getLogger("Minecraft").info("[MultiWorldPlugin] "+player.getName()+" is Teleported to Spawn of world : Default, World");
		return;
		}
	}
		MWPLocationsFile file = new MWPLocationsFile();
		HashMap<String,String> locz = file.getLocationArray();
		Logger.getLogger("Minecraft").info(locz.get("spenk96"));
		if (locz.containsKey(player.getName())){
			String[] loc = locz.get(player.getName()).split(",");
			if (etc.getServer().isWorldLoaded(loc[4])){
			World[] w = etc.getServer().getWorld(loc[4]);
			Location l = new Location(w[Integer.parseInt(loc[3])], Double.parseDouble(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]));
			player.teleportTo(l);
			 Logger.getLogger("Minecraft").info("[MultiWorldPlugin] "+player.getName()+" is Teleported to his last location");
			return;
		}
			player.teleportTo(etc.getServer().getDefaultWorld().getSpawnLocation());
			Logger.getLogger("Minecraft").info("[MultiWorldPlugin] "+player.getName()+" is Teleported to Spawn of world : Default, World");
			return;
		}
	}
}
