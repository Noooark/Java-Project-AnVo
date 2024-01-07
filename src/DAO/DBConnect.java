package DAO;
import java.sql.*;
public class DBConnect {
    private Connection conn;
    public DBConnect() throws Exception
    {
        String url = "jdbc:mysql://localhost:3306/CoffeeManagement";
        String user = "root";
        String password = "";
        conn = DriverManager.getConnection(url, user, password);
        //System.out.println("Connecting Successfully.");
    }
    public Connection getConn()
    {
        return conn;
    }
    /*
    public ResultSet queryDB(String query) throws Exception
    {
        Statement st = this.getConn().createStatement();
        return st.executeQuery(query);
    }
     */

}
