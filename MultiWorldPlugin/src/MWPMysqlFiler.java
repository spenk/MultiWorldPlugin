
public class MWPMysqlFiler {
	
	   public MWPMysqlFiler(){
		PropertiesFile f = new PropertiesFile("plugins/config/multiworld/Mysql.properties");
		f.getBoolean("Use-MySQL", false);
		f.getBoolean("Use-Canary-SQLConn", false);
		f.getString("SQLdriver", "com.mysql.jdbc.Driver");
		f.getString("SQLuser", "root");
		f.getString("SQLpass", "root");
		f.getString("SQLdb", "jdbc:mysql://localhost:3306/minecraft");
	}
	
	PropertiesFile f = new PropertiesFile("plugins/config/multiworld/Mysql.properties");
	public boolean useMysql(){
		return f.getBoolean("Use-MySQL");
	}
	
	public boolean useCanaryDb(){
		return f.getBoolean("Use-Canary-SQLConn");
	}
	
	public String SqlDriver(){
		return f.getString("SQLdriver");
	}
	
	public String SqlUser(){
		return f.getString("SQLuser");
	}
	
	public String SqlPass(){
		if (f.getString("SQLpass").equalsIgnoreCase("")||f.getString("SQLpass") == null){
		return "";
		}
		return f.getString("SQLpass");
	}
	
	public String SqlDb(){
		return f.getString("SQLdb");
	}
	
}
