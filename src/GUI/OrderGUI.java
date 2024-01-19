package GUI;

import BUS.InvoiceBUS;
import BUS.OrderBUS;
import BUS.ProductBUS;
import DTO.Invoice;
import DTO.Invoice;
import DTO.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static GUI.SaleGUI.CheckPricePromo;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class OrderGUI extends JPanel implements ActionListener {
    static OrderBUS ordbus = new OrderBUS();
    static ProductBUS prdbus = new ProductBUS();
    static InvoiceBUS invbus = new InvoiceBUS();
    JPanel PLeft, PRight;
    String[] ListType = {"All", "Coffee", "Tea", "Milk", "Iced Drinks"};
    JComboBox CBListType = new JComboBox(ListType);
    JTextField TFSearch;
    ImageIcon Icon;
    static JTable ProductTable;
    static JTable PendingInvoiceTable;
    JTable PaidInvoiceTable;
    JScrollPane ListScroll, PendingListScroll, PaidListScroll;
    static JTabbedPane InvoiceTab;
    JPopupMenu PopMenu;
    JMenuItem MenuAdd;
    JButton BAddInvoice, BRemoveInvoice;
    static JButton BTableStatus;
    JButton BDetail;
    String SelectedType;
    JLabel PendingInvoice, PaidInvoice;
    public static boolean PaymentButton = false;
    static DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    static DefaultTableModel modelpending = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    static DefaultTableModel modelpaid = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public OrderGUI() {
        Font FontLabel = new Font("Tahoma", Font.BOLD, 16);
        //------------------- Left Panel ---------------------//
        PLeft = new JPanel();

        //PLeft.setBackground(new Color(179, 217, 255));
        PLeft.setBackground(new Color(217, 217, 217));
        PLeft.setPreferredSize(new Dimension(600, 750));
        PLeft.setLayout(null);
        PLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //----------------------------------------------------//

        //---------------------- Combo List Action ---------------------------//
        CBListType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SelectedColumn = (String)CBListType.getSelectedItem();
                if(SelectedColumn == "All")
                {
                    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                    model.setRowCount(0);
                    try {
                        ShowProduct();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    try {
                        ordbus.Search2(model, SelectedColumn);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        //-----------------------------------------------------------------------//

        //----------------------------------------------------//
        Font MainFont = new Font("Tahoma", Font.BOLD, 16);
        Icon = new ImageIcon("src/IMG/Search.png");
        TFSearch = new JTextField("      Search");
        TFSearch.setBounds(270, 20, 210, 30);
        TFSearch.setBackground(Color.WHITE);
        //TFSearch.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        TFSearch.setLayout(new BorderLayout());
        TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
        TFSearch.setMargin(new Insets(0, 5, 0, 0));
        TFSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if(CBListType.getSelectedItem().toString().equals("All"))
                {
                    try {
                        ordbus.Search(model, "Name", TFSearch.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    try {
                        ordbus.Search1(model, CBListType.getSelectedItem().toString(), TFSearch.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if(TFSearch.getText().isEmpty())
                {
                    if(Objects.equals(CBListType.getSelectedItem().toString(), "All")) {
                        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                        model.setRowCount(0);
                        try {
                            ShowProduct();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                            throw new RuntimeException(ex);
                        }
                    }
                    else
                    {
                        try {
                            ordbus.Search2(model, CBListType.getSelectedItem().toString());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else
                {
                    if(Objects.equals(CBListType.getSelectedItem().toString(), "All"))
                    {
                        try {
                            ordbus.Search(model, "Name", TFSearch.getText());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else
                    {
                        try {
                            ordbus.Search1(model, CBListType.getSelectedItem().toString(), TFSearch.getText());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

            public void changedUpdate(DocumentEvent e) {
                //System.out.println("Text changed: " + TFCustomerPayment.getText());
            }
        });
        TFSearch.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if(TFSearch.getText().equals("      Search")) {
                    TFSearch.setText("");
                    TFSearch.removeAll();
                    TFSearch.repaint();
                }
            }

            public void focusLost(FocusEvent e) {
                if(TFSearch.getText().isEmpty()){
                    Icon = new ImageIcon("src/IMG/Search.png");
                    TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                    TFSearch.setText("      Search");
                    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                    model.setRowCount(0);
                    try {
                        ShowProduct();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    if(CBListType.getSelectedItem().toString().equals("All"))
                    {
                        TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                        TFSearch.setText("      Search");
                    }
                    else ;
                }
            }
        });
        /*
        TFSearch.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (TFSearch.getText().equals("      Search")) {
                    TFSearch.setText("");
                    TFSearch.removeAll();
                    TFSearch.repaint();
                }
            }

            public void focusLost(FocusEvent e) {
                if (TFSearch.getText().isEmpty()) {
                    TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                    TFSearch.setText("      Search");
                }
            }
        });
        //---------------- Action Listener -------------------//
        TFSearch.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    SelectedType = CBListType.getSelectedItem().toString();
                    if (TFSearch.getText().isEmpty()) {
                        if (Objects.equals(SelectedType, "All")) {
                            DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                            model.setRowCount(0);
                            try {
                                ShowProduct();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error. Please try again.");
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                prdbus.Search(model, "Type", SelectedType);
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        if (Objects.equals(SelectedType, "All")) {
                            try {
                                prdbus.Search(model, "Name", TFSearch.getText());
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                prdbus.Search1(model, SelectedType, TFSearch.getText());
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });
        */
        //----------------------------------------------------//

        //------------------- Combo Boxes --------------------//

        CBListType.setBounds(TFSearch.getX() + 220, 20, 100, 30);
        CBListType.setBackground(Color.WHITE);
        //CBListType.setBackground(Color.WHITE);

        //----------------------------------------------------//


        //----------------------------------------------------//
        ProductTable = new JTable();
        ProductTable.setModel(model);
        //ProductTable.setCellSelectionEnabled(false);
        //ProductTable.setRowSelectionAllowed(true);
        ProductTable.setShowGrid(true);
        ProductTable.getTableHeader().setReorderingAllowed(false);
        ProductTable.getTableHeader().setResizingAllowed(false);

        ProductTable.setFont(MainFont);
        ProductTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
        ProductTable.getTableHeader().setBackground(new Color(179, 204, 255));
        ProductTable.setRowHeight(35);

        JPopupMenu PopupMenu = new JPopupMenu();
        JMenuItem AddToInvoice = new JMenuItem("Add to invoice");
        AddToInvoice.setBackground(Color.WHITE);

        AddToInvoice.addActionListener(event -> {
            System.out.println("UA CAI NAY DE LAM GI");
        });

        PopupMenu.add(AddToInvoice);

        ProductTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }
        });
        ListScroll = new JScrollPane();
        ListScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        ListScroll.setBorder(BorderFactory.createEtchedBorder(1));

        if(model.getRowCount() < 6) {
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Type");
            model.addColumn("Price");
            model.addColumn("Amount");
            model.addColumn("Status");
        }
        ProductTable.getColumnModel().getColumn(0).setPreferredWidth(-1);
        ProductTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        ProductTable.getColumnModel().getColumn(3).setPreferredWidth(32);
        ProductTable.getColumnModel().getColumn(4).setPreferredWidth(32);
        ProductTable.getColumnModel().getColumn(5).setPreferredWidth(50);

        try {
            ShowProduct();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //
        ListScroll.setBounds(0, 65, 599, 350);
        //

        ListScroll.setViewportView(ProductTable);
        //ListScroll.getViewport().setBackground(new Color(179, 217, 255));
        ListScroll.getViewport().setBackground(new Color(217, 217, 217));
        ListScroll.setBorder(BorderFactory.createEmptyBorder());
        ListScroll.setBorder(null);
        //----------------------------------------------------//

        //----------------------------------------------------//
        Icon = new ImageIcon("src/IMG/Order/Table.png");
        BTableStatus = new JButton("Table");
        BTableStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
        BTableStatus.setFocusable(false);
        BTableStatus.setIcon(Icon);
        BTableStatus.setBounds(20, 20, 100, 30);
        BTableStatus.setBackground(Color.WHITE);
        BTableStatus.addActionListener(this);
        //----------------------------------------------------//

        //----------------------------------------------------//
        PLeft.add(TFSearch);
        PLeft.add(CBListType);
        PLeft.add(ListScroll);
        PLeft.add(BTableStatus);
        //----------------------------------------------------//

        //--------------------------------------------------------------------------------------------------------//


        //------------------- Right Panel --------------------//
        PRight = new JPanel();
        PRight.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //PRight.setBackground(new Color(179, 217, 255));
        PRight.setBackground(Color.LIGHT_GRAY);
        PRight.setPreferredSize(new Dimension(570, 800));
        PRight.setLayout(null);
        //---------------------------------------------------//

        //-------------------- Button -----------------------//

        Icon = new ImageIcon("src/IMG/Order/Add.png");
        BAddInvoice = new JButton();
        BAddInvoice.setBackground(Color.WHITE);
        BAddInvoice.setIcon(Icon);
        BAddInvoice.setBounds(450, 5, 25, 20);
        BAddInvoice.addActionListener(this);
        BAddInvoice.setFocusable(false);

        Icon = new ImageIcon("src/IMG/Order/Remove.png");
        BRemoveInvoice = new JButton();
        BRemoveInvoice.setBackground(Color.WHITE);
        BRemoveInvoice.setIcon(Icon);
        BRemoveInvoice.setBounds(BAddInvoice.getX() + 30, 5, 25, 20);
        BRemoveInvoice.addActionListener(this);
        BRemoveInvoice.setFocusable(false);

        //---------------------------------------------------//

        //---------------------------------------------------//
        InvoiceTab = new JTabbedPane();
        InvoiceTab.setBounds(0, 26, 570, 800 - InvoiceTab.getY());
        InvoiceTab.setBackground(new Color(191, 191, 191));
        InvoiceTab.setFont(new Font("Tahoma", Font.BOLD, 12));
        InvoiceTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        //---------------------------------------------------//

        //---------------------------------------------------//
        for (Map.Entry<String, Component> entry : HomePageGUI.CurrState.entrySet()) {
            InvoiceTab.addTab(entry.getKey(), entry.getValue());
        }
        //---------------------------------------------------//

        //---------------------------------------------------//
        PRight.add(BRemoveInvoice);
        PRight.add(BAddInvoice);
        PRight.add(InvoiceTab);
        //---------------------------------------------------//

        //--------------------------------------------------------------------------------------------------------//

        //----------------------------------------------------//
        PendingInvoice = new JLabel("Pending bill:");

        PendingInvoice.setBackground(new Color(179, 204, 255));
        PendingInvoice.setFont(FontLabel);
        PendingInvoice.setBounds(5, 415, 120, 35);
        //----------------------------------------------------//
        PendingInvoiceTable = new JTable(modelpending) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (PendingInvoiceTable.getRowCount() >= 1) {
                    if (comp.getPreferredSize().width > comp.getWidth()) {
                        setToolTipText(getValueAt(row, column).toString());
                    } else {
                        setToolTipText(null);
                    }
                }
                return comp;
            }
        };
        //----------------------------------------------------//
        //PendingInvoiceTable.setCellSelectionEnabled(false);
        //PendingInvoiceTable.setRowSelectionAllowed(true);
        PendingInvoiceTable.setShowGrid(true);
        PendingInvoiceTable.getTableHeader().setReorderingAllowed(false);
        PendingInvoiceTable.getTableHeader().setResizingAllowed(false);

        PendingInvoiceTable.setFont(MainFont);
        PendingInvoiceTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        PendingInvoiceTable.getTableHeader().setBackground(new Color(179, 204, 255));
        PendingInvoiceTable.setRowHeight(35);

        PendingListScroll = new JScrollPane();
        PendingListScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        PendingListScroll.setBorder(BorderFactory.createEtchedBorder(1));

        if (modelpending.getColumnCount() < 6) {
            modelpending.addColumn("Bill ID");
            modelpending.addColumn("Creator");
            modelpending.addColumn("Customer");
            modelpending.addColumn("Time");
            modelpending.addColumn("Status");
            modelpending.addColumn("Note");
        }


        PendingListScroll.setBounds(0, PendingInvoice.getY() + 35, 599, 151);
        PendingListScroll.setViewportView(PendingInvoiceTable);
        PendingListScroll.getViewport().setBackground(Color.WHITE);

        PaidInvoice = new JLabel("Paid bill:");
        PaidInvoice.setBackground(new Color(179, 204, 255));
        PaidInvoice.setFont(FontLabel);
        PaidInvoice.setBounds(5, PendingListScroll.getY() + 150, 120, 35);

        PaidInvoiceTable = new JTable(modelpaid);
        PaidInvoiceTable.setShowGrid(true);
        PaidInvoiceTable.getTableHeader().setReorderingAllowed(false);
        PaidInvoiceTable.getTableHeader().setResizingAllowed(false);

        PaidInvoiceTable.setFont(MainFont);
        PaidInvoiceTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        PaidInvoiceTable.getTableHeader().setBackground(new Color(179, 204, 255));
        PaidInvoiceTable.setRowHeight(35);

        /*
        PaidInvoiceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = PaidInvoiceTable.rowAtPoint(e.getPoint());
                int column = PaidInvoiceTable.columnAtPoint(e.getPoint());

                if (column == 5) {
                    String details = (String) PaidInvoiceTable.getValueAt(row, column);
                    JOptionPane.showMessageDialog(null, "ASD");
                }
            }
        });
         */

        if (modelpaid.getColumnCount() < 6) {
            modelpaid.addColumn("Bill ID");
            modelpaid.addColumn("Creator");
            modelpaid.addColumn("Customer");
            modelpaid.addColumn("Time");
            modelpaid.addColumn("Status");
            modelpaid.addColumn("Note");
        }
        PaidListScroll = new JScrollPane();
        PaidListScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        PaidListScroll.setBorder(BorderFactory.createEtchedBorder(1));
        PaidListScroll.setBounds(0, PaidInvoice.getY() + 35, 599, 161);
        PaidListScroll.setViewportView(PaidInvoiceTable);
        PaidListScroll.getViewport().setBackground(Color.WHITE);

        try {
            ShowPaidInvoice();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BDetail = new JButton("<html><u>Detail</u></html>");
        BDetail.setBounds(PaidInvoice.getX() + 514, PaidInvoice.getY() + 3, 80, 30);
        BDetail.setBackground(Color.WHITE);
        BDetail.setFont(new Font("Tahoma", Font.PLAIN, 13));
        BDetail.setFocusable(false);
        BDetail.addActionListener(this);
        BDetail.setBackground(new Color(217, 217, 217));
        BDetail.setBorder(null);

        //----------------------------------------------------//

        //----------------------------------------------------//
        PLeft.add(PendingInvoice);
        PLeft.add(PendingListScroll);
        PLeft.add(PaidInvoice);
        PLeft.add(PaidListScroll);
        PLeft.add(BDetail);
        //----------------------------------------------------//

        //--------------------------------------------------------------------------------------------------------//

        //----------------------------------------------------//
        this.setLayout(new BorderLayout());
        this.add(PLeft, BorderLayout.WEST);
        this.add(PRight, BorderLayout.EAST);
        //----------------------------------------------------//
    }
    public static void ShowPaidInvoice() throws Exception {
        modelpaid.setRowCount(0);
        List<Invoice> listS = invbus.SelectSimple();
        for (Invoice s : listS) {
            String InvObj[] = {
                    s.getID(),
                    s.getCreator(),
                    s.getCustomerName(),
                    s.getCreateTime(),
                    s.getStatus(),
                    s.getNote()
            };
            modelpaid.addRow(InvObj);
            modelpaid.fireTableDataChanged();
        }
    }
    public static void ShowProduct() throws Exception {
        model.setRowCount(0);
        List<Product> listS = prdbus.SelectAll();
        for (Product s : listS) {
            String ProductObj[] = {
                    s.getID(),
                    s.getName(),
                    s.getType(),
                    CheckPricePromo(s.getStartDate(), s.getEndDate(), s.getNewPrice(), s.getPrice()),
                    s.getQuantity(),
                    s.getStatus()
            };
            if(ProductObj[5].equals("Available") && Integer.parseInt(ProductObj[4]) > 0)
            {
                model.addRow(ProductObj);
                model.fireTableDataChanged();
            }
            else continue;
        }
    }
    public static void ShowProduct1() throws Exception {
        model.setRowCount(0);
        List<Product> listS = ordbus.SelectAll();
        for (Product s : listS) {
            String ProductObj[] = {
                    s.getID(),
                    s.getName(),
                    s.getType(),
                    s.getPrice(),
                    s.getQuantity(),
                    s.getStatus()
            };
            model.addRow(ProductObj);
            model.fireTableDataChanged();
        }
    }
    private void showPopupMenu(MouseEvent e) {
        int row = ProductTable.rowAtPoint(e.getPoint());
        if (row >= 0 && row < ProductTable.getRowCount()) {
            ProductTable.setRowSelectionInterval(row, row);
        }
        JPopupMenu PopupMenu = new JPopupMenu();
        PopupMenu.setBackground(Color.GRAY);
        JMenuItem AddToInvoice = new JMenuItem("Add to invoice");
        AddToInvoice.setBackground(Color.WHITE);
        //----------------------------------------- ADD TO INVOICE HERE ---------------------------------------------//
        AddToInvoice.addActionListener(event -> {
            int IndexTab = InvoiceTab.getSelectedIndex();
            int SelectedRow = ProductTable.getSelectedRow();
            if (IndexTab == -1) {
                JOptionPane.showMessageDialog(null, "Please add a invoice.");
            } else {
                Component tabContent = InvoiceTab.getComponentAt(IndexTab);
                JPanel tabPanel = (JPanel) tabContent;
                Component[] components = tabPanel.getComponents();
                String Data1, Data2, Data3, Data4, Data5, Data6;
                JTable Table;
                DefaultTableModel TableModel = null;
                String Obj[];
                for (Component component : components) {
                    if (component instanceof JScrollPane) {
                        Table = (JTable) ((JScrollPane) component).getViewport().getView();
                        TableModel = (DefaultTableModel) Table.getModel();
                        Data1 = (String) ProductTable.getValueAt(SelectedRow, 0);
                        Data2 = (String) ProductTable.getValueAt(SelectedRow, 1);
                        Data3 = (String) ProductTable.getValueAt(SelectedRow, 2);
                        Data4 = (String) ProductTable.getValueAt(SelectedRow, 3);
                        //Data5 = (String) ProductTable.getValueAt(SelectedRow, 4);
                        Data5 = (String) Data4;
                        Data6 = (String) ProductTable.getValueAt(SelectedRow, 5);
                        Boolean Check = true;
                        Obj = new String[]{Data1, Data2, "1", Data4, Data4, ""};
                        for (int i = 0; i < TableModel.getRowCount(); i++) {
                            Object Value = TableModel.getValueAt(i, 0);
                            Object NewValue = (Integer.parseInt(TableModel.getValueAt(i, 2).toString()) + 1);
                            if (Value.toString() == Obj[0]) {
                                TableModel.setValueAt(NewValue.toString(), i, 2);
                                Check = false;
                                break;
                            }
                        }
                        if (Check) {
                            TableModel.addRow(Obj);
                            TableModel.fireTableDataChanged();
                        } else TableModel.fireTableDataChanged();
                    }

                }
                for (Component component : components) {
                    Integer TotalCost = 0;
                    Integer TotalProduct = 0;
                    if (component instanceof JPanel && component.getName().equals("Right")) {
                        Component[] componentsPanel = ((JPanel) component).getComponents();
                        for (Component component1 : componentsPanel) {
                            //System.out.println(component1);
                            if (component1 instanceof JTextField) {
                                if (component1.getName().equals("Total2")) {
                                    for (Component component2 : components) {
                                        if (component2 instanceof JScrollPane) {
                                            Table = (JTable) ((JScrollPane) component2).getViewport().getView();
                                            TableModel = (DefaultTableModel) Table.getModel();
                                            for (int i = 0; i < TableModel.getRowCount(); i++) {
                                                TotalCost += Integer.parseInt(TableModel.getValueAt(i, 2).toString())
                                                        * Integer.parseInt(TableModel.getValueAt(i, 3).toString());
                                            }
                                            break;
                                        }
                                    }
                                    ((JTextField) component1).setText(TotalCost.toString());
                                    break;
                                }
                            }
                        }
                        for (Component component1 : componentsPanel) {
                            //System.out.println(component1);
                            if (component1 instanceof JTextField) {
                                if (component1.getName().equals("Total1")) {
                                    for (Component component2 : components) {
                                        if (component2 instanceof JScrollPane) {
                                            Table = (JTable) ((JScrollPane) component2).getViewport().getView();
                                            TableModel = (DefaultTableModel) Table.getModel();
                                            for (int i = 0; i < TableModel.getRowCount(); i++) {
                                                TotalProduct += Integer.parseInt(TableModel.getValueAt(i, 2).toString());
                                            }
                                            break;
                                        }
                                    }
                                    ((JTextField) component1).setText(TotalProduct.toString());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });
        //----------------------------------------------------//----------------------------------------------------//
        PopupMenu.add(AddToInvoice);
        PopupMenu.show(ProductTable, e.getX(), e.getY());

    }

    //------------------------------------------- Action Here -----------------------------------------------------//
    public static void DeleteInvoice() {
        int ColumnIndex = -1;
        String DeleteID = "";
        for (int i = 0; i < PendingInvoiceTable.getRowCount(); i++) {
            if (PendingInvoiceTable.getValueAt(i, 0).equals(InvoiceTab.getTitleAt(InvoiceTab.getSelectedIndex()))) {
                modelpending.removeRow(i);
                InvoiceTab.remove(InvoiceTab.getSelectedIndex());
                break;
            }
        }
        modelpending.fireTableStructureChanged();
        PaymentButton = false;

    }

    public static void CheckClickedPayment(int Count) {
        if (PaymentButton == true) {
            if (Count <= 0) {
                JOptionPane.showMessageDialog(null, "An invoice must have at least 1 product");
                PaymentButton = false;
            } else {
                DeleteInvoice();
            }
        } else ;
    }

    public String GenerateID() {
        UUID IDBill = UUID.randomUUID();
        long mostSignificantBits = IDBill.getMostSignificantBits();
        long leastSignificantBits = IDBill.getLeastSignificantBits();
        long id = Math.abs(mostSignificantBits + leastSignificantBits) % 100000000;
        return String.format("%08d", id);
    }

    //----------------------------------------------------////----------------------------------------------------//
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == BAddInvoice) {

            InvoiceGUI NewInvoice = new InvoiceGUI();
            String Title = GenerateID();
            InvoiceTab.addTab(Title, NewInvoice);

            HomePageGUI.CurrState.put(Title, NewInvoice);

            String DateAndTime = GetDateAndTime();

            //----------------------------------------------------////----------------------------------------------------//
            String[] PendingString = {InvoiceTab.getTitleAt(InvoiceTab.getTabCount() - 1), LoginGUI.WelcomeUsername, " ", DateAndTime, "Pending", ""};
            //----------------------------------------------------////----------------------------------------------------//

            modelpending.addRow(PendingString);
        }
        if (e.getSource() == BRemoveInvoice) {
            if (InvoiceTab.getTabCount() >= 1) {
                int Option = JOptionPane.showOptionDialog(null,
                        "Are you sure want to delete this invoice ?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION,
                        1, null, null, null);
                if (Option == 0) {
                    String SelectedTab = (InvoiceTab.getTitleAt(InvoiceTab.getSelectedIndex()));
                    for(Map.Entry<String, Component> entry : HomePageGUI.CurrState.entrySet())
                    {
                        if(entry.getKey() == SelectedTab)
                        {
                            HomePageGUI.CurrState.remove(entry.getKey());
                            break;
                        }
                    }
                    DeleteInvoice();
                }
            }
        }
        if(e.getSource() == BTableStatus)
        {
            TableGUI TableFrame = new TableGUI();
            BTableStatus.setEnabled(false);
        }
        if(e.getSource() == BDetail)
        {
            new DetailInvoiceGUI();
        }
    }

    public static String GetDateAndTime() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        String DateAndTime = hour + ":" + minute + ":" + second + " " + (day + "-" + month + "-" + year);
        return DateAndTime;
    }
}
