package BUS;

import DTO.Invoice;

import java.util.List;

public class InvoiceBUS {
    DAO.InvoiceDAO dao = new DAO.InvoiceDAO();
    public List<Invoice> SelectSimple() throws Exception {
        return dao.SelectAll1();
    }
    public List<Invoice> SelectAll() throws Exception {
        return dao.SelectAll();
    }
    public boolean Insert(Invoice Invoice) throws Exception {
        return dao.Insert(Invoice);
    }
    public void DeleteInv(String ID) throws Exception {
        dao.DeleteInv(ID);
    }
}
