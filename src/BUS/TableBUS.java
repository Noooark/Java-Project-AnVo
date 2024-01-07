package BUS;

import DAO.TableDAO;
import DTO.Product;
import DTO.Table;

import java.util.List;

public class TableBUS {
    TableDAO dao = new TableDAO();
    public List<Table> SelectAll() throws Exception {
        return dao.SelectAll();
    }
    public boolean Insert(Table table) throws Exception {
        return dao.Insert(table);
    }
    public boolean Update(Table table) throws Exception {
        return dao.Update(table);
    }
    public boolean Delete(String s) throws Exception{
        return dao.Delete(s);
    }
}
