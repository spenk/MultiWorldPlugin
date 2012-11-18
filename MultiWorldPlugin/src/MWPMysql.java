import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Logger;


public class MWPMysql {

	public HashMap<String, String> locations = new HashMap<String,String>();
	public MWPMysqlFiler filer = new MWPMysqlFiler();
	
	Logger log = Logger.getLogger("Minecraft");

	  public MWPMysql()
	  {
	    CreateSqlTable();
	    loaddeathlocs();
	  }

	  public void loaddeathlocs()
	  {
	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;
	    try {
	      conn = getSQLConn();
	    } catch (SQLException SQLE) {
	      log.severe("[MultiWorldPlugin] Could not connect to Database!");
	      SQLE.printStackTrace();
	      log.severe("[MultiWorldPlugin] Terminating...");
	    }
	    if (conn != null)
	      try {
	        st = conn.createStatement();
	        rs = st.executeQuery("SELECT * from MWP_Locations");
	        while (rs.next()) {
	          double x = rs.getDouble("x");
	          double y = rs.getDouble("y");
	          double z = rs.getDouble("z");
	          int w = rs.getInt("w");
	          String world = rs.getString("world");
	          locations.put(rs.getString("player"),x+","+y+","+z+","+w+","+world);
	        }
	      } catch (SQLException SQLE) {
	        log.severe("[MultiWorldPlugin] Issue loading MultiWorldPlugin_Locations");
	        SQLE.printStackTrace();
	        log.severe("[MultiWorldPlugin] Terminating...");
	        try
	        {
	          if (rs != null)
	            rs.close();
	          if (conn != null)
	            conn.close();
	        } catch (SQLException SQLE2) {
	          log.warning("[MultiWorldPlugin] Could not close SQL Connection!");
	          SQLE.printStackTrace();
	        }
	      }
	      finally
	      {
	        try
	        {
	          if (rs != null)
	            rs.close();
	          if (conn != null)
	            conn.close();
	        } catch (SQLException SQLE) {
	          log.warning("[MultiWorldPlugin] Could not close SQL Connection!");
	          SQLE.printStackTrace();
	        }
	      }
	  }

	public void savedeathlocs()
	  {
	    PreparedStatement ps = null;
	    Connection conn = null;
	    try {
	      conn = getSQLConn();
	    } catch (SQLException SQLE) {
	      log.severe("[MultiWorldPlugin] Could not connect to Database!");
	      SQLE.printStackTrace();
	      log.severe("[MultiWorldPlugin] Terminating...");
	    }
	    if (conn != null)
	      try {
	        ps = conn.prepareStatement("TRUNCATE TABLE MWP_Locations");
	        ps.executeUpdate();
	        for (String key : locations.keySet()) {
	          String loc = locations.get(key);
	          String[] locs = loc.split(",");
	          ps = conn.prepareStatement("INSERT INTO MWP_Locations (player, x, y, z, w, world)VALUES (?,?,?,?,?,?)", 1);
	          ps.setString(1, key);
	          ps.setDouble(2, Double.parseDouble(locs[0]));
	          ps.setDouble(3, Double.parseDouble(locs[1]));
	          ps.setDouble(4, Double.parseDouble(locs[2]));
	          ps.setInt(5, MWPRestVoids.TransferWorldToInt(Integer.parseInt(locs[3])));
	          ps.setString(6, locs[4]);
	          ps.executeUpdate();
	        }
	      } catch (SQLException SQLE) {
	        log.severe("[MultiWorldPlugin] Unable to add MultiWorldPlugin_Location into SQL");
	        SQLE.printStackTrace();
	        log.severe("[MultiWorldPlugin] Terminating...");
	        try
	        {
	          if ((ps != null) && (!ps.isClosed())) {
	            ps.close();
	          }
	          if ((conn != null) && (!conn.isClosed()))
	            conn.close();
	        }
	        catch (SQLException SQLE2) {
	          log.warning("[MultiWorldPlugin] Could not close SQL Connection!");
	          SQLE.printStackTrace();
	        }
	      }
	      finally
	      {
	        try
	        {
	          if ((ps != null) && (!ps.isClosed())) {
	            ps.close();
	          }
	          if ((conn != null) && (!conn.isClosed()))
	            conn.close();
	        }
	        catch (SQLException SQLE) {
	          log.warning("[MultiWorldPlugin] Could not close SQL Connection!");
	          SQLE.printStackTrace();
	        }
	      }
	  }

	  @SuppressWarnings("deprecation")
	private Connection getSQLConn() throws SQLException
	  {
	    Connection conn = null;
	    if (filer.useCanaryDb()) {
	      conn = etc.getSQLConnection();
	    }
	    else {
	      conn = DriverManager.getConnection(filer.SqlDb(), filer.SqlUser(), filer.SqlPass());
	    }
	    return conn;
	  }

	  private void CreateSqlTable() {
	    Connection conn = null;
	    Statement st = null;
	    try {
	      conn = getSQLConn();
	    } catch (SQLException SQLE) {
	     log.severe("[MultiWorldPlugin] Could not connect to Database!");
	      SQLE.printStackTrace();
	      log.severe("[MultiWorldPlugin] Terminating...");
	    }
	    if (conn != null)
	      try {
	        st = conn.createStatement();
	        st.executeUpdate("CREATE TABLE IF NOT EXISTS `MWP_Locations` (`player` varchar(32) NOT NULL, `x` double NOT NULL, `y` double NOT NULL,`z` double NOT NULL, `w` int(1) NOT NULL, `world` varchar(32) NOT NULL, PRIMARY KEY (`player`))");
	      } catch (SQLException SQLE) {
	       log.severe("[MultiWorldPlugin] Could not create the table!");
	        SQLE.printStackTrace();
	        log.severe("[MultiWorldPlugin] Terminating...");
	        try
	        {
	          if ((st != null) && (!st.isClosed())) {
	            st.close();
	          }
	          if ((conn != null) && (!conn.isClosed()))
	            conn.close();
	        }
	        catch (SQLException SQLE2) {
               log.warning("[MultiWorldPlugin] Could not close SQL Connection!");
	          SQLE.printStackTrace();
	        }
	      }
	      finally
	      {
	        try
	        {
	          if ((st != null) && (!st.isClosed())) {
	            st.close();
	          }
	          if ((conn != null) && (!conn.isClosed()))
	            conn.close();
	        }
	        catch (SQLException SQLE) {
	          log.warning("[MultiWorldPlugin] Could not close SQL Connection!");
	          SQLE.printStackTrace();
	        }
	      }
	  }
	  
	  public HashMap<String,String> getLocationArray(){
		  return locations;
	  }
	  
	  public void UploadHashMap(HashMap<String,String> map){
		  this.locations = map;
		  this.savedeathlocs();
		  this.loaddeathlocs();
	  }
	 
}
