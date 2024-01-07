package BUS;
import DAO.EmployeeDAO;
import DTO.Employee;

import javax.swing.table.DefaultTableModel;
import java.util.*;
public class EmployeeBUS {
    EmployeeDAO dao = new EmployeeDAO();
    public boolean Insert(Employee emp) throws Exception{
        /*
        String getID = dao.SelectLast();
        String sliceID = dao.SelectLast();
        String ID = String.valueOf(Integer.valueOf(sliceID) + 1);
        emp.setID(ID);
         */
        return dao.Insert(emp);
    }
    public boolean Update(Employee emp) throws Exception {
        return dao.Update(emp);
    }
    public boolean Delete(String s) throws Exception {
        return dao.Delete(s);
    }
    public List<Employee> SelectAll() throws Exception {
        return dao.SelectAll();
    }
    public void Search(DefaultTableModel table, String name, String need) throws Exception {
        dao.Search(table, name, need);
    }
    public void ResetIncrement() throws Exception
    {
        dao.ResetIncrement();
    }
}

