package BUS;

import DAO.OrderDAO;
import DAO.ProductDAO;
import DTO.Product;
import GUI.OrderGUI;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class OrderBUS {
    OrderDAO dao = new OrderDAO();
    public void Search(DefaultTableModel table, String name, String need) throws Exception {
        dao.Search(table, name, need);
    }
    public void Search1(DefaultTableModel table, String type,String need) throws Exception {
        dao.Search1(table, type, need);
    }
    public void Search2(DefaultTableModel table, String type) throws Exception {
        dao.Search2(table, type);
    }
    public void ConfirmedPurchased(Product prd) throws Exception {
        dao.ConfirmedPurchased(prd);
    }
    public List<Product> SelectAll() throws Exception {
        return dao.SelectAll();
    }
}
