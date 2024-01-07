package GUI;

import DTO.Product;
import BUS.TableBUS;
import DTO.Table;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TableGUI extends JFrame implements ActionListener {
    Boolean EditableRow = false;
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 2) {
                if(EditableRow == true)
                    return true;
                return false;
            }
            return false;
            /*
            if (column == getColumnCount() - 1) {
                return true;
            } else return false;
             */
        }
    };
    TableBUS tblbus = new TableBUS();
    JTable TableList;
    JScrollPane ListScrollTable;
    JButton BAdd, BRemove, BEdit, BConfirm, BCancel;
    ImageIcon Icon;
    JComboBox<String> CBListStatus, CBListTableName;

    public TableGUI() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                OrderGUI.BTableStatus.setEnabled(true);
            }
        });
        //----------------------------------------------------//
        this.setTitle("Table Management");
        this.setSize(650, 500);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        //----------------------------------------------------//

        //----------------------------------------------------//
        TableList = new JTable();
        TableList.setModel(model);
        TableList.getTableHeader().setReorderingAllowed(false);
        TableList.getTableHeader().setResizingAllowed(false);

        TableList.setFont(new Font("Tahoma", Font.BOLD, 14));
        TableList.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        TableList.getTableHeader().setBackground(new Color(179, 204, 255));
        TableList.setRowHeight(30);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Status");

        CBListStatus = new JComboBox<>(new String[]{"Available", "Unavailable"});
        CBListStatus.setBackground(Color.WHITE);
        TableColumn column = TableList.getColumnModel().getColumn(2);

        JTextField TextFieldCol1 = new JTextField();
        TextFieldCol1.setBackground(Color.WHITE);
        JTextField TextFieldCol2 = new JTextField();
        TextFieldCol2.setBackground(Color.WHITE);

        column.setCellEditor(new DefaultCellEditor(CBListStatus));

        TableList.getColumnModel().getColumn(0).setPreferredWidth(1);

        ListScrollTable = new JScrollPane();
        ListScrollTable.setBounds(20, 15, 530, 470);
        ListScrollTable.setViewportView(TableList);
        ListScrollTable.getViewport().getView().setBackground(Color.WHITE);

        try {
            ShowTable();
        } catch (Exception ex) {
            throw new RuntimeException();
        }
        //----------------------------------------------------//

        //----------------------------------------------------//


        Icon = new ImageIcon("src/IMG/Add.png");
        BAdd = new JButton();
        BAdd.setFocusable(false);
        BAdd.setIcon(Icon);
        BAdd.setBackground(Color.WHITE);
        BAdd.setBounds(554, 200, 90, 30);
        BAdd.addActionListener(this);

        Icon = new ImageIcon("src/IMG/Remove.png");
        BRemove = new JButton();
        BRemove.setFocusable(false);
        BRemove.setFocusable(false);
        BRemove.setIcon(Icon);
        BRemove.setBackground(Color.WHITE);
        BRemove.setBounds(554, BAdd.getY() + 35, 90, 30);
        BRemove.addActionListener(this);

        Icon = new ImageIcon("src/IMG/Edit.png");
        BEdit = new JButton();
        BEdit.setFocusable(false);
        BEdit.setFocusable(false);
        BEdit.setIcon(Icon);
        BEdit.setBackground(Color.WHITE);
        BEdit.setBounds(554, BRemove.getY() + 35, 90, 30);
        BEdit.addActionListener(this);

        Icon = new ImageIcon("src/IMG/Order/Confirm.png");
        BConfirm = new JButton();
        BConfirm.setFocusable(false);
        BConfirm.setIcon(Icon);
        BConfirm.setBackground(Color.WHITE);
        BConfirm.setBounds(554, BAdd.getY() - 35, 90, 30);
        BConfirm.addActionListener(this);

        Icon = new ImageIcon("src/IMG/Order/Cancel.png");
        BCancel = new JButton();
        BCancel.setFocusable(false);
        BCancel.setIcon(Icon);
        BCancel.setBackground(Color.WHITE);
        BCancel.setBounds(554, BEdit.getY() + 35, 90, 30);
        BCancel.addActionListener(this);
        //----------------------------------------------------//

        //----------------------------------------------------//
        this.add(BAdd);
        this.add(BRemove);
        this.add(BEdit);
        this.add(ListScrollTable);
        //----------------------------------------------------//


    }

    public void ShowTable() throws Exception {
        model.setRowCount(0);
        List<Table> listS = tblbus.SelectAll();
        for (Table s : listS) {
            String TableObj[] = {
                    s.getID(),
                    s.getName(),
                    s.getStatus()
            };
            model.addRow(TableObj);
            model.fireTableDataChanged();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BAdd) {
            model.addRow(new String[]{"", "", ""});
            model.isCellEditable(1, 0);
            model.fireTableDataChanged();

            EditableRow = false;
            int LastRow = TableList.getRowCount() - 1;
            Table UpdateTableList = new Table(
                    "Table",
                    "Available"
            );
            try {
                tblbus.Insert(UpdateTableList);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            //model.setRowCount(0);
            try {
                ShowTable();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            //EditableRow = true;
            /*
            TableList.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        EditableRow = false;
                    }
                }
            });
             */
        }
        if (e.getSource() == BEdit) {
            this.add(BConfirm);
            this.add(BCancel);
            this.repaint();
            EditableRow = true;
        }
        if (e.getSource() == BConfirm) {
            for(int i = 0; i < TableList.getRowCount(); i++)
            {
                Table UpdateTable = new Table(
                        TableList.getValueAt(i, 0).toString(),
                        TableList.getValueAt(i, 1).toString(),
                        TableList.getValueAt(i, 2).toString()
                );
                try {
                    tblbus.Update(UpdateTable);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                this.remove(BCancel);
                this.remove(BConfirm);
                this.repaint();
            }
        }
        if (e.getSource() == BCancel) {
            //model.removeRow(TableList.getRowCount() - 1);
            this.remove(BCancel);
            this.remove(BConfirm);
            this.repaint();
        }
        if (e.getSource() == BRemove) {
            int SelectedRow = TableList.getSelectedRow();
            int Option = JOptionPane.showOptionDialog(null,
                    "Are you sure want to delete ? ",
                    "Warning",
                    JOptionPane.YES_NO_OPTION,
                    1, null, null, null
            );
            if (Option == 0) {
                try {
                    tblbus.Delete(TableList.getValueAt(SelectedRow, 0).toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    ShowTable();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
