package DAO;

import DTO.Product;
import DTO.Table;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableDAO implements InterfaceDAO<Table>{
    int check;
    ResultSet result;
    @Override
    public boolean Insert(Table table) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "INSERT INTO `Table` (Name, Status) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, "Table");
        ps.setString(2, table.getStatus());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public boolean Update(Table table) throws Exception{
        Connection conn = new DBConnect().getConn();
        String Query = "UPDATE `Table` SET Status = ? WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, table.getStatus());
        ps.setString(2, table.getID());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean Delete(String s) throws Exception{
        Connection conn = new DBConnect().getConn();
        String delete = "DELETE FROM `Table` WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(delete);
        ps.setString(1, s);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    @Override
    public List<Table> SelectAll() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM `Table`";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Table> ListTbl = new ArrayList<>();
        while(result.next())
        {
            Table tbl = new Table(
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Status")
            );
            ListTbl.add(tbl);
        }
        conn.close();
        return ListTbl;
    }
    @Override
    public void Search(DefaultTableModel Tabel, String Name, String Need) throws Exception {
    }
}
