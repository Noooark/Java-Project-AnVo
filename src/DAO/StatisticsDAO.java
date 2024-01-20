package DAO;

import DTO.Sale;
import DTO.Statistics;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDAO {
    int check;
    ResultSet result;
    public int GetNumberInvoice() throws Exception{
        Connection conn = new DBConnect().getConn();
        String GetNumberInvoice = "SELECT COUNT(*) FROM Invoice";
        Statement st = conn.createStatement();
        result = st.executeQuery(GetNumberInvoice);
        while(result.next())
        {
            check = result.getInt(1);
        }
        conn.close();
        return check;
    }
    public int GetTotalIncome() throws Exception{
        Connection conn = new DBConnect().getConn();
        String GetTotalIncome = "SELECT SUM(Total) FROM Invoice";
        Statement st = conn.createStatement();
        result = st.executeQuery(GetTotalIncome);
        while(result.next())
        {
            check = result.getInt(1);
        }
        conn.close();
        return check;
    }
    public int GetNumberEmployee() throws Exception{
        Connection conn = new DBConnect().getConn();
        String GetNumberEmployee = "SELECT COUNT(*) FROM Employee";
        Statement st = conn.createStatement();
        result = st.executeQuery(GetNumberEmployee);
        while(result.next())
        {
            check = result.getInt(1);
        }
        conn.close();
        return check;
    }
    public int GetNumberProduct() throws Exception{
        Connection conn = new DBConnect().getConn();
        String GetNumberProduct = "SELECT COUNT(*) FROM Product";
        Statement st = conn.createStatement();
        result = st.executeQuery(GetNumberProduct);
        while(result.next())
        {
            check = result.getInt(1);
        }
        conn.close();
        return check;
    }
    public List<Statistics> DrawChartData() throws Exception{
        Connection conn = new DBConnect().getConn();
        String Query = "Select PaymentDate, COUNT(*) AS TotalOrders, SUM(Total) AS TotalIncome FROM Invoice GROUP BY PaymentDate";
        PreparedStatement ps = conn.prepareStatement(Query);
        ResultSet result = ps.executeQuery(Query);
        List<Statistics> ListSta = new ArrayList<>();
        while(result.next())
        {
            Statistics sta = new Statistics(
                    result.getString("PaymentDate"),
                    result.getString("TotalOrders"),
                    result.getString("TotalIncome")
            );
            ListSta.add(sta);
        }
        conn.close();
        return ListSta;
    }
}
