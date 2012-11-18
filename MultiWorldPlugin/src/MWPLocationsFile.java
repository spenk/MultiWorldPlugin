import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MWPLocationsFile {
	public HashMap<String,String> locations = new HashMap<String,String>();
	
public MWPLocationsFile(){
	this.createFile();
}

public void loadLocations(){
	PropertiesFile f = new PropertiesFile("plugins/config/multiworld/Locations.properties");
	Map<String,String> map = new HashMap<String, String>();
	try {
		map = f.returnMap();
	} catch (Exception e) {
		e.printStackTrace();
	}
	for(String key : map.keySet()){
		String[] sa = map.get(key).split(",");
        locations.put(key,sa[0]+","+sa[1]+","+sa[2]+","+sa[3]+","+sa[4]);

	}
}

public void saveLocations(){
	File f = new File("plugins/config/multiworld/Locations.properties");
	f.delete();
	try {
		f.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	PropertiesFile f1 = new PropertiesFile("plugins/config/multiworld/Locations.properties");
for (String key : locations.keySet()){
	String[] l = locations.get(key).split(",");
	f1.setString(key, l[0]+","+l[1]+","+l[2]+","+l[3]+","+l[4]);
}
return;
}

public void createFile(){
	File f = new File("plugins/config/multiworld/Locations.properties");
	if (!f.exists()){
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public HashMap<String,String> getLocationArray(){
	  return locations;
}

public void UploadHashMap(HashMap<String,String> map){
	  this.locations = map;
	  this.saveLocations();
	  this.loadLocations();
}
}
// x y z w world
// 0 1 2 3 4