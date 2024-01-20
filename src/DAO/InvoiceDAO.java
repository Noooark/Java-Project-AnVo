package DAO;

import DTO.Invoice;
import DTO.Invoice;

import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO implements InterfaceDAO<Invoice>{
    int check;
    ResultSet result;
    @Override
    public boolean Insert(Invoice Invoice) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Insert = "INSERT INTO Invoice VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(Insert);
        ps.setString(1, Invoice.getID());
        ps.setString(2, Invoice.getCreator());
        ps.setString(3, Invoice.getCustomerName());
        ps.setString(4, Invoice.getCreateTime());
        ps.setString(5, Invoice.getPaymentTime());
        ps.setString(6, Invoice.getQuantity());
        ps.setString(7, Invoice.getTotal());
        ps.setString(8, Invoice.getStatus());
        ps.setString(9, Invoice.getNote());
        ps.setString(10, Invoice.getPaymentDate());
        check = ps.executeUpdate();
        conn.close();
        return check > 0;
    }

    @Override
    public List<Invoice> SelectAll() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Invoice";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Invoice> ListInvoice = new ArrayList<>();
        while(result.next())
        {
            Invoice dtlb = new Invoice(
                    result.getString("IID"),
                    result.getString("Creator"),
                    result.getString("Customer"),
                    result.getString("CreateTime"),
                    result.getString("PaymentTime"),
                    result.getString("Quantity"),
                    result.getString("Total"),
                    result.getString("Status"),
                    result.getString("Note")
            );
            ListInvoice.add(dtlb);
        }
        conn.close();
        return ListInvoice;
    }
    public List<Invoice> SelectAll1() throws Exception {
        Connection conn = new DBConnect().getConn();
        String Query = "SELECT * FROM Invoice";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Invoice> ListInvoice = new ArrayList<>();
        while(result.next())
        {
            Invoice dtlb = new Invoice(
                    result.getString("IID"),
                    result.getString("Creator"),
                    result.getString("Customer"),
                    result.getString("CreateTime"),
                    result.getString("Status"),
                    result.getString("Note")
            );
            ListInvoice.add(dtlb);
        }
        conn.close();
        return ListInvoice;
    }
    public void DeleteInv(String ID) throws Exception {
        Connection conn = new DBConnect().getConn();
        String Delete = "DELETE FROM Invoice WHERE IID = ?";
        PreparedStatement ps = conn.prepareStatement(Delete);
        ps.setString(1, ID);
        ps.executeUpdate();
        conn.close();
    }
    @Override
    public void Search(DefaultTableModel Tabel, String Name, String Need) throws Exception {

    }
}
