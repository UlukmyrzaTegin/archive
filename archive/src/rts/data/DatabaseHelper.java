package rts.data;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

@SuppressWarnings("serial")
public class DatabaseHelper implements Serializable{
    private JDBCConnectionPool connectionPool = null;
    private static ResourceBundle bundle = ResourceBundle.getBundle("rts.data.databaseHelper");
    
    public DatabaseHelper() {
  	  initConnectionPool();
    }

	private void initConnectionPool() {
      	try {
          	connectionPool = new SimpleJDBCConnectionPool(
          			"org.postgresql.Driver",bundle.getString("connectionUri"),bundle.getString("userName"), bundle.getString("password"), 2, 2);
      	} catch (SQLException e) {
        	  e.printStackTrace();
        }
    }

    public JDBCConnectionPool getConnectionPool() {
		return connectionPool;
	}

}
