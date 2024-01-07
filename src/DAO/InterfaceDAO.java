package DAO;
import DTO.Employee;

import javax.swing.table.DefaultTableModel;
import java.util.*;
public interface InterfaceDAO<T> {
    boolean Insert(T t) throws Exception;
    List<T> SelectAll() throws Exception;
    void Search(DefaultTableModel Tabel, String Name, String Need) throws Exception;
}
