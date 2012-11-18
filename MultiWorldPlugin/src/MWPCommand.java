import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class MWPCommand {
	Logger log = Logger.getLogger("Minecraft");
	
	String[] split;
	Player player;
	
	public MWPCommand(String[] split, Player player){
		this.split = split;
		this.player = player;
	}

	public void processCommand() {

		if (player != null) {

			if (split[0].equalsIgnoreCase("/world")) {

				if (split.length == 1) {
					player.notify("use '/world help' - to see the help page");
					return;
				}

				if (split[1].equalsIgnoreCase("help")) {
					this.LogIt(player.getName(), "/world help");
					player.notify("-------------MultiworldPlugin-------------");
					player.notify("world create <name> <type> (seed) - to create a world");
					player.notify("world join <world> - to join a world");
					player.notify("world load <world> - loads a world");
					player.notify("world leave - to leave to the normal world");
					player.notify("world list - to see all worlds <active & inactive>");
					player.notify("world flag - to set a flag of a world ex. GM, Mobspawn etc");
					player.notify("notes:");
					player.notify("worldtype must be flat or default.");
					player.notify("only numbers allowed as seed.");
					
				}

				if (split[1].equalsIgnoreCase("create")) {
					this.LogIt(player.getName(), "/world create");
					if (split.length < 4 || split.length > 5) {
						player.notify("The correct usage is '/world create <worldname> <worldtype> (seed)'");
						return;
					}

					if (MWPRestVoids.existWorld(split[2])) {
						player.notify("There is already a world with this name please choose another name!");
						return;
					}

					if (!MWPRestVoids.isValidWorldType(split[3])) {
						player.notify("This is not a valid worldtype valid worldtypes are [flat|default]");
						return;
					}

					if (split.length == 4) {
						MWPCreateWorld world = new MWPCreateWorld(split[2],split[3]);
						world.createWorldProperties();
						player.sendMessage("§4" + split[2]+ "§2 is Successfully created.");
						return;
					}

					if (split.length == 5) {

						if (MWPRestVoids.isValidLong(split[4])) {
							player.notify("The seed you entered is not a valid long seed!");
							return;
						}

						MWPCreateWorld world = new MWPCreateWorld(split[2],split[3], Long.parseLong(split[4]));
						world.createWorldProperties();
						player.sendMessage("§4" + split[2]+ "§2 is Successfully created.");
						return;
					}

				}
				if (split[1].equalsIgnoreCase("join")) {
					this.LogIt(player.getName(), "/world join");
					if (split.length < 3 || split.length > 4) {
						player.notify("The correct usage is '/world join <worldname>'");
						return;
					}

					if (!etc.getServer().isWorldLoaded(split[2])) {
						player.notify("This world doesnt exist or is not loaded!");
						return;
					}
					
					MWPPlayer mwplayer = new MWPPlayer(player, split[2]);
					
					if (!mwplayer.isAllowedToEnter()){
						player.notify("You are not allowed to enter this world!");
						return;
					}

					MWPCreateWorld world = new MWPCreateWorld(split[2]);
					world.switchWorld(player, 0);
					MWPFiler filer = new MWPFiler(split[2]);
					if (filer.getGameMode()){
					player.setCreativeMode(1);
					}
					player.setCreativeMode(0);
					player.notify("§2You have been teleported to §4" + split[2]);
					return;
				}

				if (split[1].equalsIgnoreCase("leave")) {
					this.LogIt(player.getName(), "/world leave");
					if (split.length > 2 || split.length < 2) {
						player.notify("The correct usage is '/world leave'");
						return;
					}

					if (player
							.getWorld()
							.getName()
							.equals(etc.getServer().getDefaultWorld().getName())) {
						player.notify("You cant leave the default world!");
						return;
					}

					player.teleportTo(etc.getServer().getDefaultWorld()
							.getSpawnLocation());
					player.sendMessage("§2You left to the default world");
					return;
				}

				if (split[1].equalsIgnoreCase("load")) {
					this.LogIt(player.getName(), "/world load");
					if (split.length < 3 || split.length > 3) {
						player.notify("The correct usage is '/world load <worldname>'");
						return;
					}

					if (!MWPRestVoids.existWorld(split[2])) {
						player.notify("This world does not exist to load.");
						return;
					}
					new MWPCreateWorld(split[2]).loadWorld();
					player.sendMessage("§4" + split[2] + " §2 is loaded");
					return;
				}

				if (split[1].equalsIgnoreCase("list")) {
					this.LogIt(player.getName(), "/world list");
					int pageNum = 1;
					if (split.length == 3){
					     pageNum = Integer.parseInt(split[2]);
					}
			          if ((pageNum <= 0) || (pageNum > getPages())) {
			              player.sendMessage("§aPage §b- §aof §b" + getPages());
			              player.notify("Unknown page number!");
			              return;
			            }
			          player.sendMessage("§aPage §b" + pageNum + " §aof §b" + getPages());
			          sendPages(player, Integer.valueOf(pageNum));
			          return;
				}
				
				if (split[1].equalsIgnoreCase("listflag")){
					this.LogIt(player.getName(), "/world listflag");
					if (split.length < 2 || split.length > 2) {
						player.notify("The correct usage is /world listflag , to se a list of flags.");
						return;
						}
					player.notify("Mobspawn");
					player.notify("Animalspawn");
					player.notify("Squidspawn");
					player.notify("Npcspawn");
					player.notify("Creative");
					player.notify("Pvp");
					player.notify("Health");
					player.notify("Food");
					player.notify("Exp");
					player.notify("LoadOnStartup");
					return;
				}
				
				if (split[1].equalsIgnoreCase("flag")){
					this.LogIt(player.getName(), "/world flag");
					if (split.length < 5 || split.length > 5) {
					player.notify("The correct usage is /world flag <world> <flag> <true|false> ");
					player.notify("Use /world listflag , to se a list of flags.");
					return;
					}
					MWPFiler worldProperties = new MWPFiler(split[2]);
					if (worldProperties.existFile()){
						if (!split[3].equalsIgnoreCase("mobspawn") && !split[3].equalsIgnoreCase("animalspawn") && !split[3].equalsIgnoreCase("squidspawn") && 
								!split[3].equalsIgnoreCase("npcspawn") && !split[3].equalsIgnoreCase("creative") && !split[3].equalsIgnoreCase("pvp") && 
								!split[3].equalsIgnoreCase("health") && !split[3].equalsIgnoreCase("food") && !split[3].equalsIgnoreCase("exp") && !split[3].equalsIgnoreCase("loadonstartup")){
							player.notify("Unknown Flag!");
							player.notify("Use /world listflag , to se a list of flags.");
							return;
							
						}
						if (!split[4].equalsIgnoreCase("true")&&!split[4].equalsIgnoreCase("false")){
							player.notify("The correct usage is /world flag <world> <flag> <true|false>");
							
						}
						Boolean b = Boolean.parseBoolean(split[4]);
						World[] w = etc.getServer().getWorld(split[2]);
						MWPRestVoids v = new MWPRestVoids();
						
						if (split[3].equalsIgnoreCase("Mobspawn")){
							worldProperties.setMobSpawn(b,player);
							v.killmobs(w);
						}
						if (split[3].equalsIgnoreCase("Animalspawn")){
							worldProperties.setAnimalSpawn(b);
							v.killAnimals(w);
						}
						if (split[3].equalsIgnoreCase("Squidspawn")){
							worldProperties.setSquidSpawn(b);
							v.killAnimals(w);
						}
						if (split[3].equalsIgnoreCase("Npcspawn")){
							worldProperties.setNPCSpawn(b);
						}
						
						if (split[3].equalsIgnoreCase("Creative")){
							worldProperties.setGameMode(b);
						}
						if (split[3].equalsIgnoreCase("Pvp")){
							worldProperties.setPvp(b);
							v.setCreative(split[2], b);
						}
						if (split[3].equalsIgnoreCase("Health")){
							worldProperties.setHealth(b);
						}
						if (split[3].equalsIgnoreCase("Food")){
							worldProperties.setFood(b);
						}
						if (split[3].equalsIgnoreCase("Exp")){
							worldProperties.setExp(b);
						}
						if (split[3].equalsIgnoreCase("LoadOnStartup")){
							worldProperties.LOS(b);
						}
						player.notify("flag: "+split[3]+" has been toggeled to: "+split[4]);
						this.LogIt(player.getName(), "/world flag "+split[2]+" "+split[3]+" "+split[4]);
						return;
					}
				}
			
			}

		}
		
		
		
		
		
		
		
		
		if (player == null) {

			if (split.length == 1) {
				log.info("use 'world help' - to see the help page");
				return;
			}

			if (split[1].equalsIgnoreCase("help")) {

				log.info("-------------MultiworldPlugin-------------");
				log.info("world create <name> <type> (seed) - to create a world");
				log.info("world teleport <player> <world> - to teleport a player to a world");
				log.info("world load <world> - loads a world");
				log.info("world list - to see all worlds <active & inactive>");
				log.info("world flag - to set a flag of a world ex. GM, Mobspawn etc");
				log.info("notes:");
				log.info("worldtype must be flat or default.");
				log.info("only numbers allowed as seed.");
			}

			if (split[1].equalsIgnoreCase("create")) {

				if (split.length < 4 || split.length > 5) {
					log.info("The correct usage is 'world create <worldname> <worldtype> (seed).'");
					return;
				}

				if (MWPRestVoids.existWorld(split[2])) {
					log.info("There is already a world with this name please choose another name!");
					return;
				}

				if (!MWPRestVoids.isValidWorldType(split[3])) {
					log.info("This is not a valid worldtype valid worldtypes are [flat|default].");
					return;
				}

				if (split.length == 4) {
					MWPCreateWorld world = new MWPCreateWorld(split[2],
							split[3]);
					world.createWorldProperties();
					log.info("§4" + split[2] + "§2 is Successfully created.");
					return;
				}

				if (split.length == 5) {

					if (MWPRestVoids.isValidLong(split[4])) {
						log.info("The seed you entered is not a valid long seed!");
						return;
					}

					MWPCreateWorld world = new MWPCreateWorld(split[2],
							split[3], Long.parseLong(split[4]));
					world.createWorldProperties();
					log.info("§4" + split[2] + "§2 is Successfully created.");
					return;
				}

			}

			if (split[1].equalsIgnoreCase("teleport")) {

				if (split.length > 3 || split.length < 3) {
					log.info("The correct usage is 'world teleport <player> <world>'.");
					return;
				}

				Player player2 = etc.getServer().matchPlayer(split[2]);
				if (player2 == null) {
					log.info("This player does not exist or is not online!");
					return;
				}
			

				if (!etc.getServer().isWorldLoaded(split[3])) {
					log.info("This world does not exist or is not loaded!");
					return;
				}

				MWPPlayer mwplayer = new MWPPlayer(player2, split[2]);
				
				if (!mwplayer.isAllowedToEnter()){
					log.info("This player is not allowed to enter this world!");
					return;
				}
				
			}

			if (split[1].equalsIgnoreCase("load")) {

				if (split.length < 3 || split.length > 3) {
					log.info("The correct usage is '/world load <worldname>'");
					return;
				}

				if (!MWPRestVoids.existWorld(split[2])) {
					log.info("This world does not exist to load.");
					return;
				}
				new MWPCreateWorld(split[2]).loadWorld();
				log.info(split[2] + " §2 is loaded.");
				return;
			}

			if (split[1].equalsIgnoreCase("list")) {
				int pageNum = 1;
				if (split.length == 3){
				     pageNum = Integer.parseInt(split[2]);
				}
		          if ((pageNum <= 0) || (pageNum > getPages())) {
		              player.sendMessage("§aPage §b- §aof §b" + getPages());
		              player.notify("Unknown page number!");
		              return;
		            }
		          player.sendMessage("§aPage §b" + pageNum + " §aof §b" + getPages());
		          sendPages(player, Integer.valueOf(pageNum));
		          return;
				}

		}
	}
	
	  public int getPages() {
		    int size = getWorlds().size();
		    int pages = (int)Math.ceil(size / 7.0D);
		    return pages;
		  }
	  
	  public void sendPages(Player player, Integer int1)
	  {
	    int i = 0;
	    if ((int1.intValue() != 1) && (int1.intValue() != 0)) {
	      i = int1.intValue() * 7 - 7;
	      int1 = Integer.valueOf(Math.min(int1.intValue() * 7,  getWorlds().size()));
	    } else {
	      int1 = Integer.valueOf(Math.min(7,  getWorlds().size()));
	    }
	    while (i < int1.intValue()) {
	      if ( getWorlds().get(i) != null) {
	    	  String[] s =  getWorlds().get(i).split(":");
	    	  String colorc = "f";
	    	  String enabled = "Unknown";
	    	  if (Boolean.parseBoolean(s[1])){colorc = "2"; enabled = "Enabled";}else{colorc = "4"; enabled = "Disabled";}
	        player.sendMessage("§"+colorc+s[0]+" is "+enabled);
	      }
	      i++;
	    }
	  }
	  
	  public List<String> getWorlds(){
			List<File> list = Arrays.asList(new File("plugins/config/multiworld").listFiles());
			List<String> toret = new ArrayList<String>();
			toret.add(etc.getServer().getDefaultWorld().getName()+":"+"true");
			for (File f : list){
				PropertiesFile file = new PropertiesFile(f.toString());
				if (file.containsKey("Name")){
					toret.add(file.getString("Name")+":"+etc.getServer().isWorldLoaded(file.getProperty("Name")));
				}
	  }
			return toret;
	  }
	  
	  public void LogIt(String player, String command){
		  log.info("[MultiWorldPlugin] "+player+" Issued the command "+ command);
	  }
	  
}