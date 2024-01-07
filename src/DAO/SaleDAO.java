package DAO;

import DTO.Product;
import DTO.Sale;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO implements InterfaceDAO<Sale> {
    int check;
    ResultSet result;
    static public String CheckPricePromo(String StartDate, String EndDate, String NewPrice, String Price)
    {
        if(StartDate == null || EndDate == null || NewPrice == null || Price == null) return Price;
        if(LocalDate.parse(StartDate).compareTo(LocalDate.now()) <= 0)
        {
            if(LocalDate.parse(EndDate).compareTo(LocalDate.now()) >= 0)
            {
                return NewPrice;
            }
        }
        return Price;
    }
    @Override
    public boolean Insert(Sale sale) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Insert = "INSERT INTO Sale (ID, Name, Type, Value, Start, End, Status) VALUES (?, ? ,?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(Insert);
        ps.setString(1, sale.getID());
        ps.setString(2, sale.getName());
        ps.setString(3, sale.getType());
        ps.setString(4, sale.getValue());
        ps.setString(5, sale.getStartDate());
        ps.setString(6, sale.getEndDate());
        ps.setString(7, (LocalDate.now().compareTo(LocalDate.parse(sale.getEndDate()))) <= 0 ? "Active" : "Expired");
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean Delete(String s) throws Exception{
        Connection conn = new DBConnect().getConn();
        String delete = "DELETE FROM Sale WHERE ID = ?";
        PreparedStatement ps = conn.prepareStatement(delete);
        ps.setString(1, s);
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    public Boolean Update(Sale sle) throws Exception{
        Connection conn = new DBConnect().getConn();
        String Update = "UPDATE Sale SET ID = ?, Name = ?, Type = ?, Value = ?, Start = ?, End = ? WHERE Sale.ID = ?";
        PreparedStatement ps = conn.prepareStatement(Update);
        ps.setString(1, sle.getID());
        ps.setString(2, sle.getName());
        ps.setString(3, sle.getType());
        ps.setString(4, sle.getValue());
        ps.setString(5, sle.getStartDate());
        ps.setString(6, sle.getEndDate());
        ps.setString(7, sle.getID());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }
    @Override
    public List<Sale> SelectAll() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Sale";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Sale> ListSle = new ArrayList<>();
        while(result.next())
        {
            Sale sle = new Sale(
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Value"),
                    result.getString("Start"),
                    result.getString("End"),
                    result.getString("Status")
            );
            ListSle.add(sle);
        }
        conn.close();
        return ListSle;
    }

    @Override
    public void Search(DefaultTableModel Table, String Name, String Need) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Sale WHERE Name LIKE '%" + Name + "%'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object Employee[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Value"),
                    result.getString("Start"),
                    result.getString("End"),
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
            Object PrdObj[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    CheckPricePromo(result.getString("Start"),
                            result.getString("End"),
                            result.getString("NewPrice"),
                            result.getString("Price")),
                    "",
                    false
            };
            Table.addRow(PrdObj);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
    public void SearchName(DefaultTableModel Table, String Name) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Product WHERE Name LIKE '%" + Name + "%'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object PrdObj[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    CheckPricePromo(result.getString("Start"),
                            result.getString("End"),
                            result.getString("NewPrice"),
                            result.getString("Price")),
                    "",
                    false
            };
            Table.addRow(PrdObj);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
    public void SearchStatus(DefaultTableModel Table, String Name) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Sale WHERE Status LIKE '%" + Name + "%'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object SaleObj[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Value"),
                    result.getString("Start"),
                    result.getString("End"),
                    result.getString("Status")
            };
            Table.addRow(SaleObj);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
    public void SearchType(DefaultTableModel Table, String Name) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Sale WHERE Type LIKE '%" + Name + "%'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object SaleObj[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    result.getString("Type"),
                    result.getString("Value"),
                    result.getString("Start"),
                    result.getString("End"),
                    result.getString("Status")
            };
            Table.addRow(SaleObj);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
    public void SearchTypeProductCB(DefaultTableModel Table, String Name) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Select = "SELECT * FROM Product WHERE Type LIKE" + "'" + Name + "'";
        Statement sta = conn.createStatement();
        result = sta.executeQuery(Select);
        Table.setRowCount(0);
        while ((result.next())) {
            Object SaleObj[] = {
                    result.getString("ID"),
                    result.getString("Name"),
                    CheckPricePromo(result.getString("Start"),
                            result.getString("End"),
                            result.getString("NewPrice"),
                            result.getString("Price")),
                    result.getString("NewPrice"),
                    false
            };
            Table.addRow(SaleObj);
            Table.fireTableDataChanged();
        }
        result.close();
        conn.close();
    }
}
