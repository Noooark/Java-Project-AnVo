package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {
    ResultSet result;
    int check;
    public Boolean AddAccount(String usr, String pass, String gmail, String position) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "INSERT INTO Account (Username, Password, Position, Gmail) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, usr);
        ps.setString(2, pass);
        ps.setString(3, position);
        ps.setString(4, gmail);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean DeleteAccount(String gmail) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "DELETE FROM Account WHERE Gmail = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, gmail);
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
