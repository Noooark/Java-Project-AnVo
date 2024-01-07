package DAO;

import DTO.Product;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDAO implements InterfaceDAO<Product>{
    int check;
    ResultSet result;
    public void ConfirmedPurchased(Product prd) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "UPDATE Product SET Quantity = Quantity - ? WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, prd.getQuantity());
        ps.setString(2, prd.getID());
        ps.executeUpdate();
        conn.close();
    }
    public void AfterPurchasedUpdate(Product prd) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Product WHERE Status = ?";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setString(1, prd.getStatus());
        check = ps.executeUpdate();
        conn.close();
    }

    @Override
    public boolean Insert(Product product) throws Exception {
        return false;
    }

    public List<Product> SelectAll() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Product WHERE Status = 'Available'";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Product> ListPrd= new ArrayList<>();
        while(result.next())
        {
            Product prd = new Product(
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Price"),
                    result.getString("Quantity"),
                    result.getString("Status")
            );
            ListPrd.add(prd);
        }
        conn.close();
        return ListPrd;
    }
    @Override
    public void Search(DefaultTableModel Table, String Column, String Need) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Product WHERE " + Column + " LIKE " +  "'" + "%" + Need + "%" + "'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object Employee[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Price"),
                    result.getString("Quantity"),
                    result.getString("Status")
            };
            Table.addRow(Employee);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
    public void Search1(DefaultTableModel Table, String NameType,String Need) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM (SELECT * FROM Product WHERE Type LIKE " +  "'" + NameType + "'" + ") AS S1 WHERE S1.Name LIKE " + "'" + '%' + Need + '%' + "'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object Employee[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Price"),
                    result.getString("Quantity"),
                    result.getString("Status")
            };
            Table.addRow(Employee);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
    public void Search2(DefaultTableModel Table, String Column) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Product WHERE Type = " + "'" + Column + "'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object Employee[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Price"),
                    result.getString("Quantity"),
                    result.getString("Status")
            };
            Table.addRow(Employee);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
}
