package BUS;
import DAO.ProductDAO;
import DTO.Product;

import javax.swing.table.DefaultTableModel;
import java.util.*;
public class ProductBUS {
    ProductDAO dao = new ProductDAO();
    public boolean Insert(Product prd) throws Exception{
        return dao.Insert(prd);
    }
    public boolean Update(Product prd) throws Exception {
        return dao.Update(prd);
    }
    public boolean UpdatePromo(Product prd, String StartDate, String EndDate) throws Exception {
        return dao.UpdatePromo(prd, StartDate, EndDate);
    }
    public boolean Delete(String s) throws Exception {
        return dao.Delete(s);
    }
    public List<Product> SelectAll() throws Exception {
        return dao.SelectAll();
    }
    public void Search(DefaultTableModel table, String name, String need) throws Exception {
        dao.Search(table, name, need);
    }
    public void Search1(DefaultTableModel table, String type,String need) throws Exception {
        dao.Search1(table, type, need);
    }
    public void Search2(DefaultTableModel table, String type) throws Exception {
        dao.Search2(table, type);
    }
    public void SearchStatus(DefaultTableModel table, String type) throws Exception {
        dao.SearchStatus(table, type);
    }
    public void ResetIncrement() throws Exception
    {
        dao.ResetIncrement();
    }
}

