package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {
    ResultSet result;
    int check;
    public Boolean AddAccount(String usr, String pass, String gmail, String position, String id) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "INSERT INTO Account (Username, Password, Position, Gmail, ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, usr);
        ps.setString(2, pass);
        ps.setString(3, position);
        ps.setString(4, gmail);
        ps.setString(5, id);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean DeleteAccount(String ID) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "DELETE FROM Account WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, ID);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean LoginCheck(String usr, String pass) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Account WHERE Username = ? AND Password = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, usr);
        ps.setString(2, pass);
        result = ps.executeQuery();
        while(result.next())
        {
            if(result.getString("Username").equals(usr) && result.getString("Password").equals(pass))
            {
                return true;
            }
        }
        result.close();
        conn.close();
        return false;
    }
    public String SelectLastAddedID() throws Exception{
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT ID FROM Employee ORDER BY ID DESC LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(Query);
        result = ps.executeQuery();
        result.next();
        String ans = result.getString("ID");
        result.close();
        conn.close();
        return ans;
    }
    public Boolean UpdateAccount(String Gmail, String ID) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "UPDATE Account SET Gmail = ? WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, Gmail);
        ps.setString(2, ID);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public String GetPosition(String usr, String pass) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Account WHERE Username = ? AND Password = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, usr);
        ps.setString(2, pass);
        result = ps.executeQuery();
        while(result.next())
        {
            if(result.getString("Username").equals(usr) && result.getString("Password").equals(pass))
            {
                return result.getString("Position");
            }
        }
        result.close();
        conn.close();
        return null;
    }
    public void ResetPassword(String Gmail, String Newpass) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "UPDATE Account SET Password = ? WHERE Gmail = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, Newpass);
        ps.setString(2, Gmail);
        check = ps.executeUpdate();
        conn.close();
    }
}
