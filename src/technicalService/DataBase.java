package technicalService;
import java.sql.*;

public class DataBase {
	
	final private static String USER = "manueliana";
   	final private static String PASS = "ciaosonoricca";
   	final private static String CONNECTION_URL = "jdbc:mariadb://5.196.65.101:5061/comics_reader";
    final private static String DRIVER = "org.mariadb.jdbc.Driver"; 
    private static Connection connection;
    private static Statement statement;

	static public void connect() throws SQLException, ClassNotFoundException{
    	
    	Class.forName(DRIVER);
    	connection= DriverManager.getConnection(CONNECTION_URL, USER, PASS);
    	statement = connection.createStatement(); 	
    }
    
    static public void disconnect() throws SQLException{
    	
    	connection.close();
    }
	
    public static Connection getConnection() {
	
    	return connection;
	}

	public static Statement getStatement() throws SQLException {
		
		statement.close();
		statement = connection.createStatement();
		return statement;
	}
	public static void main(String[] args) {
		try {
			
			DataBase.connect();
			TabellaFumetto f = new TabellaFumetto();
			f.nextFumetto();
			System.out.println(f.getECompleto());
			f.nextFumetto();
			System.out.println(f.getECompleto());


			DataBase.disconnect();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
