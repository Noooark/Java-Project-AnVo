package DAO;

import DTO.Product;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Position;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements InterfaceDAO<Product>{
    int check;
    ResultSet result;


    @Override
    public boolean Insert(Product prd) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Insert = "INSERT INTO Product (Name, Type, Price, Quantity, Status) VALUES (?, ? ,?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(Insert);
        ps.setString(1, prd.getName());
        ps.setString(2, prd.getType());
        ps.setString(3, prd.getPrice());
        ps.setString(4, prd.getQuantity());
        ps.setString(5, prd.getStatus());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean Update(Product prd) throws Exception{
        Connection conn = new DBConnect().getConn();
        String Update = "UPDATE Product SET ID = ?, Name = ?, Type = ?, Price = ?, Quantity = ?, Status = ? WHERE Product.ID = ?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setString(1, prd.getID());
        ps.setString(2, prd.getName());
        ps.setString(3, prd.getType());
        ps.setString(4, prd.getPrice());
        ps.setString(5, prd.getQuantity());
        ps.setString(6, prd.getStatus());
        ps.setString(7, prd.getID());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean UpdatePromo(Product prd, String StartDate, String EndDate) throws Exception{
        Connection conn = new DBConnect().getConn();
        String Update = "UPDATE Product SET NewPrice = ?, Start = " + "'" + StartDate + "'" + ", End = " + "'" + EndDate + "'" + " WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setString(1, prd.getPrice());
        ps.setString(2, prd.getID());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean Delete(String s) throws Exception{
        Connection conn = new DBConnect().getConn();
        String delete = "DELETE FROM Product WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(delete);
        ps.setString(1, s);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public String SelectLast() throws Exception{
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT ID FROM Product ORDER BY ID DESC LIMIT 1";
        PreparedStatement ps = conn.prepareStatement(Query);
        result = ps.executeQuery();
        result.next();
        String ans = result.getString("ID");
        result.close();
        conn.close();
        return ans;
    }
    @Override
    public List<Product> SelectAll() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Product";
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
                    result.getString("Status"),
                    result.getString("NewPrice"),
                    result.getString("Start"),
                    result.getString("End")
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
    public void Search1(DefaultTableModel Table, String NameType, String Need) throws Exception {
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
    public void SearchStatus(DefaultTableModel Table, String Status) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Product WHERE Status = " +  "'" + Status + "'";
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
    public void ResetIncrement() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "ALTER TABLE Product AUTO_INCREMENT = 1";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.executeUpdate(Query);
        conn.close();
    }
}
