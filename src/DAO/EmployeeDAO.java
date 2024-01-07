package DAO;

import DTO.Employee;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Position;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements InterfaceDAO<Employee>{
    int check;
    ResultSet result;

    @Override
    public boolean Insert(Employee emp) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Insert = "INSERT INTO Employee (Name, Gender, Birthday, Email, Phone, Position, Status) VALUES (?, ?, ?, ? ,?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(Insert);
        //ps.setString(1, emp.getID());
        ps.setString(1, emp.getName());
        ps.setString(2, emp.getGender());
        ps.setString(3, emp.getBirthday());
        ps.setString(4, emp.getMail());
        ps.setString(5, emp.getPhoneNumber());
        ps.setString(6, emp.getPosition());
        ps.setString(7, emp.getStatus());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean Update(Employee emp) throws Exception{
        Connection conn = new DBConnect().getConn();
        String Update = "UPDATE Employee SET ID = ?, Name = ?, Gender = ?, Birthday = ?, Email = ?, Phone = ?, Position =  ?, Status = ? WHERE Employee.ID = ?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setString(1, emp.getID());
        ps.setString(2, emp.getName());
        ps.setString(3, emp.getGender());
        ps.setString(4, emp.getBirthday());
        ps.setString(5, emp.getMail());
        ps.setString(6, emp.getPhoneNumber());
        ps.setString(7, emp.getPosition());
        ps.setString(8, emp.getStatus());
        ps.setString(9, emp.getID());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    /*
    public Boolean Update(Employee emp) throws Exception{
        Connection conn = new DBConnect().getConn();
        String Update = "UPDATE Employee SET Name = ?, Gender = ?, Birthday = ?, Email = ?, Phone = ?, Position =  ?, Status = ? WHERE Employee.ID = ?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setString(1, emp.getName());
        ps.setString(2, emp.getGender());
        ps.setString(3, emp.getBirthday());
        ps.setString(4, emp.getMail());
        ps.setString(5, emp.getPhoneNumber());
        ps.setString(6, emp.getPosition());
        ps.setString(7, emp.getStatus());
        ps.setString(8, emp.getID());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    */
    public Boolean Delete(String s) throws Exception{
        Connection conn = new DBConnect().getConn();
        String delete = "DELETE FROM Employee WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(delete);
        ps.setString(1, s);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public String SelectLast() throws Exception{
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
    @Override
    public List<Employee> SelectAll() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Employee";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Employee> ListEmp= new ArrayList<>();
        while(result.next())
        {
            Employee emp = new Employee(
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Gender"),
                    result.getString("Birthday"),
                    result.getString("Email"),
                    result.getString("Phone"),
                    result.getString("Position"),
                    result.getString("Status")
            );
            ListEmp.add(emp);
        }
        conn.close();
        return ListEmp;
    }

    @Override
    public void Search(DefaultTableModel Table, String Column, String Need) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Employee WHERE " + Column + " LIKE " +  "'" + "%" + Need + "%" + "'";
        /*
        PreparedStatement ps = conn.prepareStatement(Select);
        ps.setString(1, Column);
        ps.setString(2, Need);
         */
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object Employee[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Phone"),
                    result.getString("Position"),
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
        String Query = "ALTER TABLE Employee AUTO_INCREMENT = 1";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.executeUpdate(Query);
        conn.close();
    }
}
