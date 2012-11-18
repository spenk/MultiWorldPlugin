/**
 * @project MultiWorld
 * @author Spenk
 * @canary 5.1.11
 */



import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
public class MultiWorldPlugin extends Plugin{
	  String name = "MultiWorldPlugin ";
	  String version = "1.9.2";
	  String author = "by spenk ";
	  static Logger log = Logger.getLogger("Minecraft");

	  public void initialize()
	  {
		  MultiWorldPluginListener listener = new MultiWorldPluginListener();
	    log.info(this.name + " version " + this.version + " by " + this.author + " is initialized!");
	    etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.COMMAND_CHECK, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.ATTACK, listener, this, PluginListener.Priority.HIGH);
	    etc.getLoader().addListener(PluginLoader.Hook.MOB_SPAWN, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.HEALTH_CHANGE, listener, this, PluginListener.Priority.HIGH);
	    etc.getLoader().addListener(PluginLoader.Hook.FOODLEVEL_CHANGE, listener, this, PluginListener.Priority.HIGH);
	    etc.getLoader().addListener(PluginLoader.Hook.EXPERIENCE_CHANGE, listener, this, PluginListener.Priority.HIGH);
	    etc.getLoader().addListener(PluginLoader.Hook.SERVERCOMMAND, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.PLAYER_MOVE, listener, this, PluginListener.Priority.HIGH);
	    etc.getLoader().addListener(PluginLoader.Hook.BLOCK_PLACE, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.LOGIN, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.DISCONNECT, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.KICK, listener, this, PluginListener.Priority.MEDIUM);
	    etc.getLoader().addListener(PluginLoader.Hook.BAN, listener, this, PluginListener.Priority.MEDIUM);
	    File f = new File("plugins/config");
	    f.mkdir();
	    File f2 = new File("plugins/config/multiworld");
	    f2.mkdir();
	    loadWorlds();
	    if (new MWPMysqlFiler().useMysql()){
	    new MWPMysqlFiler();
	    new MWPMysql();
	    }else{
	    	new MWPLocationsFile();
	    }
	    File f3 = new File("plugins/config/multiworld/Locations.properties");
	    if (!f3.exists()){
	    	try {
				f3.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  }

	  public void enable() {
	    log.info(this.name + " version " + this.version + " by " + this.author + " is enabled!");
	    etc.getInstance().addCommand("/createworld", "let you create an world");
	    etc.getInstance().addCommand("/worldtp", "let you or another player tp arround worlds");
	    etc.getInstance().addCommand("/loadworld", "loads an existing world");
	    
	  }

	  public void disable() {
	    log.info(this.name + " version " + this.version + " is disabled!");
	    etc.getInstance().removeCommand("/createworld");
	    etc.getInstance().removeCommand("/worldtp");
	    etc.getInstance().removeCommand("/loadworld");
	  }
	  
	  public void loadWorlds(){
		 List<File> list = Arrays.asList(new File("plugins/config/multiworld").listFiles());
		 if (list != null){
		 int i = 0;
		 int length = list.size();
		 while(length > i){
			 PropertiesFile f = new PropertiesFile(list.get(i).toString());
			 if (f.containsKey("LoadOnServerStartup")){
			Boolean loadme = Boolean.parseBoolean(f.getProperty("LoadOnServerStartup"));
			String name = f.getProperty("Name");
			if (loadme){log.info("[MultiWorldPlugin] - Loading World "+f.getString("Name")); if (!etc.getServer().isWorldLoaded(name)){etc.getServer().loadWorld(name);i++;}
			 }
			i++;
			 }else{
			i++;
			 }
		 }
		 return;
	    }
	  }
}
