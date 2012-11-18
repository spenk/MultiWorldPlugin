import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class MWPFiler {
	Writer writer;
	String worldName;
	Long seed;
	PropertiesFile file;
	
	public MWPFiler(String name){
		worldName = name;
		this.seed = 0L;
		file = new PropertiesFile("plugins/config/multiworld/"+worldName+".properties");
	}
	
	public MWPFiler(String name, long seed){
		worldName = name;
		this.seed = seed;
		file = new PropertiesFile("plugins/config/multiworld/"+worldName+".properties");
	}
	
	public boolean existFile(){
		return new File("plugins/config/multiworld/"+worldName+".properties").exists();
	}
	
	public List<String> getUnallowedPlayers() {
		if (this.existFile()) {
			if (file.getProperty("UnallowedPlayers") == null) {
				return null;
			}
			List<String> toret = null;
			toret = Arrays.asList(file.getProperty("UnallowedPlayers").split(
					","));
			return toret;
		}
		return null;
	}

	public List<String> getUnallowedCommands() {
		if (this.existFile()) {
			if (file.getProperty("UnallowedCommands") == null) {
				return null;
			}
			List<String> toret = null;
			toret = Arrays.asList(file.getProperty("UnallowedCommands").split(
					","));
			return toret;
		}
		return null;
	}

	public List<String> getUnallowedGroups() {
		if (this.existFile()) {
			if (file.getProperty("UnallowedGroups") == null) {
				return null;
			}
			List<String> toret = null;
			toret = Arrays.asList(file.getProperty("UnallowedGroups")
					.split(","));
			return toret;
		}
		return null;
	}
	
	public boolean removeUnallowedParam(String param,String key){
		if (!MWPRestVoids.isValidPropsParam(param)){
			return false;
		}
		if (this.existFile()){
			List<String> s = Arrays.asList(file.getProperty(param).split(","));
			if (s.contains(key)){
				s.remove(key);
				if (this.writeList(s, param)){
				return true;
				}
			}
		}
		return false;
	}
	
	public boolean addUnallowedParam(String param,String key){
		if (!MWPRestVoids.isValidPropsParam(param)){
			return false;
		}
		if (this.existFile()){
			List<String> s = Arrays.asList(file.getProperty(param).split(","));
			if (!s.contains(key)){
				s.add(key);
				if (this.writeList(s, param)){
				return true;
				}
			}
		}
		return false;
	}
	
	public boolean writeList(List<String> list,String key){
	if (file.containsKey(key)){
		file.setString(key, "");
			for (String s : list){
				if (file.getString(key).equals("")){
					file.setString(key, s);
				}
				file.setString(key, file.getString(key)+","+s);
			}
			return true;
	}
	return false;
	}
	
	public boolean spawnMobs(){
		if (!this.existFile()){return false;}
		else {
		return file.getBoolean("SpawnMonsters");
		}
	}
	
	public void setMobSpawn(boolean mobspawn, Player p){
		file.setBoolean("SpawnMonsters", mobspawn);
		return;
	}
	
	public boolean spawnAnimals(){
		if (!this.existFile()){return false;}
		return file.getBoolean("SpawnAnimals");
	}
	
	public void setAnimalSpawn(boolean animalspawn){
		file.setBoolean("SpawnAnimals", animalspawn);
		return;
	}
	
	public boolean spawnSquids(){
		if (!this.existFile()){return false;}
		return file.getBoolean("SpawnSquids");
	}
	
	public void setSquidSpawn(boolean squidspawn){
		file.setBoolean("SpawnSquids", squidspawn);
		return;
	}
	
	public boolean SpawnNPC(){
		if (!this.existFile()){return false;}
		return file.getBoolean("spawn-npcs");
	}
	
	public void setNPCSpawn(boolean NPC){
		file.setBoolean("spawn-npcs", NPC);
		return;
	}
	
	public boolean isPvpEnabled(){
		if (!this.existFile()){return false;}
		return file.getBoolean("Pvp");
	}
	
	public void setPvp(boolean pvp){
	file.setBoolean("Pvp", pvp);
	return;
	}
	
	public boolean isFoodbarEnabled(){
		if (!this.existFile()){return false;}
		return file.getBoolean("Enable-Food");
	}
	
	public void setFood(boolean food){
		file.setBoolean("Enable-Food", food);
		return;
	}
	
	public boolean isHealthEnabled(){
		if (!this.existFile()){return false;}
		return file.getBoolean("Enable-Health");
	}
	
	public void setHealth(boolean health){
		file.setBoolean("Enable-Health", health);
		return;
	}
	
	public boolean isExpEnabled(){
		if (!this.existFile()){return false;}
		return file.getBoolean("Enable-Experience");
	}
	
	public void setExp(boolean exp){
		file.setBoolean("Enable-Experience", exp);
		return;
	}
	
	public int getMaxHeight(){
		return file.getInt("MaxBuildHeigt");
	}
	
	public boolean getGameMode(){
		return file.getBoolean("CreativeMode");
	}
	
	public void setGameMode(boolean GM){
		file.setBoolean("CreativeMode", GM);
		return;
	}
	
	public void LOS(boolean los){
		file.setBoolean("LoadOnServerStartup", los);
		return;
	}
	public void createFile(){
		if (this.existFile()){return;}
		File par = new File("plugins/config/multiworld/"+worldName+".properties");
		World[] w = etc.getServer().getWorld(worldName);
		try{
		 writer = new FileWriter(par, true);
		writer.write("######################MultiWorldPlugin######################\n");
		writer.write("Name="+worldName+"\n");
		writer.write("Seed="+seed+"\n");
		writer.write("----------Mobs----------\n");
		writer.write("SpawnMonsters=true\n");
		writer.write("SpawnAnimals=true\n");
		writer.write("SpawnSquids=true\n");
		writer.write("spawn-npcs=true\n");
		writer.write("----------GlobalConfig----------\n");
		writer.write("SpawnLocation="+w[0].getSpawnLocation().x+","+w[0].getSpawnLocation().y+","+w[0].getSpawnLocation().z+"\n");
		writer.write("CreativeMode=false\n");
		writer.write("Pvp=true\n");
		writer.write("Enable-Health=true\n");
		writer.write("Enable-Food=true\n");
		writer.write("Enable-Experience=true\n");
		writer.write("LoadOnServerStartup=true\n");
		writer.write("MaxBuildHeigt=256\n");
		writer.write("----------PermissionConfig----------\n");
		writer.write("UnallowedCommands=/command1,/command2\n");
		writer.write("UnallowedPlayers=player1,player2\n");
		writer.write("UnallowedGroups=group1,group2\n");
		writer.close();
	} catch (IOException e) {
		Logger.getLogger("Minecraft").info("An error occured while creating propertiesfile for multiworld world "+worldName);
		e.printStackTrace();
	}
	}
	
}
