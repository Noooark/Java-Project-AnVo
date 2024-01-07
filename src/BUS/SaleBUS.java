package BUS;

import DAO.OrderDAO;
import DAO.SaleDAO;
import DTO.Product;
import DTO.Sale;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class SaleBUS {
    SaleDAO dao = new SaleDAO();
    OrderDAO dao1 = new OrderDAO();

    public List<Sale> SelectAll() throws Exception {
        return dao.SelectAll();
    }

    public boolean Insert(Sale sale) throws Exception {
        return dao.Insert(sale);
    }

    public boolean Update(Sale sle) throws Exception {
        return dao.Update(sle);
    }

    public boolean Delete(String s) throws Exception {
        return dao.Delete(s);
    }

    public void SelectAllProduct() throws Exception {
        dao1.SelectAll();
    }

    public void Search(DefaultTableModel table, String name, String need) throws Exception {
        dao.Search(table, name, need);
    }
    public void Search1(DefaultTableModel table, String name, String need) throws Exception {
        dao.Search1(table, name, need);
    }
    public void SearchStatus(DefaultTableModel table, String name) throws Exception {
        dao.SearchStatus(table, name);
    }

    public void SearchType(DefaultTableModel table, String name) throws Exception {
        dao.SearchType(table, name);
    }

    public void SearchTypeProductCB(DefaultTableModel Table, String Name) throws Exception {
        dao.SearchTypeProductCB(Table, Name);
    }
    public void SearchName(DefaultTableModel table, String name) throws Exception {
        dao.SearchName(table, name);
    }
}
