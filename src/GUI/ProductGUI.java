package GUI;

import BUS.ProductBUS;
import DTO.Product;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.Objects;

import static GUI.SaleGUI.CheckPricePromo;

public class ProductGUI extends JPanel implements ActionListener {
    //----------------------------------------------------//
    Product UpdatePrd, InsertPrd;
    ProductBUS prdbus = new ProductBUS();
    Font MainFont = new Font("Tahoma", Font.BOLD, 16);
    String[] ListPosition = {"All", "ID","Type", "Status"};
    String[] ColumnTable = {"ID", "Name", "Gender", "Birthday", "Email", "Phone", "Position", "Status"};
    String Data1, Data2, Data3, Data4, Data5, Data6, Data7, Data8;
    String SelectedColumn, SelectedStatus;
    //----------------------------------------------------//

    //----------------------------------------------------//
    DefaultTableModel model = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
        }
    };
    //----------------------------------------------------//
    JPanel PAllContent, PHeader, Sidebar, Header, Body;
    JTable ProductTable;
    JScrollPane ListScroll;
    JComboBox CBProduct;
    JComboBox CBStatus = new JComboBox<>(new String[] {"Available", "Unavailable"});
    JComboBox CBSelectedStatus = new JComboBox<>(new String[] {"Available", "Unavailable"});
    JTextField TFSearch, TFID, TFName, TFType, TFPrice, TFQuantity, TFStatus;
    JButton BAdd, BRemove, BEdit, Submit, Cancel, SubmitEdit, CancelEdit;
    JLabel LAdd_Emp, LID_Prd, LName_Prd, LType_Prd, LPrice_Prd, LQuantity_Prd, LStatus_Prd;
    ImageIcon Icon;
    JFrame Main;
    public static Product emp = new Product();
    //----------------------------------------------------//
    static class RoundBorder extends AbstractBorder {
        private int radius;
        public RoundBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape border = new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);
            g2d.draw(border);
            g2d.dispose();
        }
    }
    public ProductGUI() {

        //----------------------------------------------------//
        this.setLayout(null);
        //----------------------------------------------------//

        //------------------- JPanel -------------------------//
        PAllContent = new JPanel();
        PAllContent.setBackground(new Color(153, 194, 255));
        PAllContent.setBounds(0, 0, 1170, 800);
        PAllContent.setLayout(null);

        PHeader = new JPanel();
        PHeader.setBackground(Color.BLACK);
        PHeader.setBounds(0, 0, 200, 100);

        //----------------------------------------------------//

        //------------------ Combo Boxes ---------------------//
        CBProduct = new JComboBox(ListPosition);
        CBProduct.setBounds(1000, 15, 100, 30);
        CBProduct.setBackground(Color.WHITE);
        CBProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectedColumn = (String) CBProduct.getSelectedItem();
            }
        });
        //----------------------------------------------------//

        //--------------------- Buttons -----------------------//
        Icon = new ImageIcon("src/IMG/Add.png");
        BAdd = new JButton("Add");
        BAdd.setFocusable(false);
        BAdd.setIcon(Icon);
        BAdd.setFocusPainted(false);
        BAdd.setBackground(Color.WHITE);
        //BAdd.setBorder(new RoundBorder(30));
        BAdd.setBounds(1057, 348, 105, 35);
        BAdd.addActionListener(this);
        //UIManager.put("Button.select", Color.WHITE);
        BAdd.setHorizontalAlignment(JButton.LEFT);
        BAdd.setVerticalAlignment(JButton.CENTER);

        Icon = new ImageIcon("src/IMG/Remove.png");
        BRemove = new JButton("Remove");
        BRemove.setFocusable(false);
        BRemove.setIcon(Icon);
        BRemove.setFocusPainted(false);
        BRemove.setBackground(Color.WHITE);
        //BRemove.setBorder(new RoundBorder(30));
        BRemove.setBounds(1057, BAdd.getY() + 40, 105, 35);
        BRemove.addActionListener(this);
        BRemove.setHorizontalAlignment(JButton.LEFT);

        Icon = new ImageIcon("src/IMG/Edit.png");
        BEdit = new JButton("Edit");
        BEdit.setFocusable(false);
        BEdit.setIcon(Icon);
        BEdit.setFocusPainted(false);
        BEdit.setBackground(Color.WHITE);
        //BEdit.setBorder(new RoundBorder(30));
        BEdit.setBounds(1057, BRemove.getY() + 40, 105, 35);
        BEdit.addActionListener(this);
        BEdit.setHorizontalAlignment(JButton.LEFT);
        //----------------------------------------------------//

        //---------------------- Combo List Action ---------------------------//
        CBProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SelectedColumn = (String)CBProduct.getSelectedItem();
                if(SelectedColumn == "All")
                {
                    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                    model.setRowCount(0);
                    try {
                        ProductList();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                if(SelectedColumn == "Status")
                {
                    PAllContent.remove(TFSearch);
                    PAllContent.repaint();
                    CBSelectedStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
                    CBSelectedStatus.setBounds(1000 - 5 - 160, 15, 160, 30);
                    CBSelectedStatus.setBackground(Color.WHITE);
                    CBSelectedStatus.setSelectedItem("Available");
                    PAllContent.add(CBSelectedStatus);
                    PAllContent.repaint();
                    PAllContent.revalidate();
                }
                else
                {
                    PAllContent.remove(CBSelectedStatus);
                    PAllContent.repaint();
                    PAllContent.add(TFSearch);
                    PAllContent.repaint();
                    PAllContent.revalidate();
                }
            }
        });
        CBSelectedStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CBSelectedStatus.getSelectedItem().toString() == "Available")
                {
                    try {
                        prdbus.SearchStatus(model, "Available");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(CBSelectedStatus.getSelectedItem().toString() == "Unavailable")
                {
                    try {
                        prdbus.SearchStatus(model, "Unavailable");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        //-----------------------------------------------------------------------//

        //------------------- Text Field ---------------------//
        TFSearch = new JTextField("      Search");
        TFSearch.setBounds(1000 - 5 - 160, 15, 160, 30);
        TFSearch.setOpaque(true);
        Icon = new ImageIcon("src/IMG/Search.png");
        /*
        TFSearch.setBorder(BorderFactory.createCompoundBorder(
                TFSearch.getBorder(),
                BorderFactory.createEmptyBorder(0, 3, 0, 5)));

        */
        TFSearch.setLayout(new BorderLayout());
        TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
        TFSearch.setMargin(new Insets(0, 5, 0, 0));

        TFSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if(CBProduct.getSelectedItem().toString().equals("All"))
                {
                    try {
                        prdbus.Search(model, "Name", TFSearch.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    try {
                        prdbus.Search(model, CBProduct.getSelectedItem().toString(), TFSearch.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if(TFSearch.getText().isEmpty())
                {
                    if(Objects.equals(CBProduct.getSelectedItem().toString(), "All")) {
                        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                        model.setRowCount(0);
                        try {
                            ProductList();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                            throw new RuntimeException(ex);
                        }
                    }
                    else
                    {
                        try {
                            prdbus.Search1(model, CBProduct.getSelectedItem().toString(), TFSearch.getText());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                        model.setRowCount(0);
                        try {
                            ProductList();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                            throw new RuntimeException(ex);
                        }
                    }
                }
                else
                {
                    if(Objects.equals(CBProduct.getSelectedItem().toString(), "All"))
                    {
                        try {
                            prdbus.Search(model, "Name", TFSearch.getText());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else
                    {
                        System.out.println(TFSearch.getText());
                        try {
                            prdbus.Search(model, CBProduct.getSelectedItem().toString(), TFSearch.getText());
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
                    TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                    TFSearch.setText("      Search");
                    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                    model.setRowCount(0);
                    try {
                        ProductList();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    if(CBProduct.getSelectedItem().toString().equals("All"))
                    {
                        TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                        TFSearch.setText("      Search");
                    }
                    else ;

                }
            }
        });

        //----------------------------------------------------//

        //----------------------------------------------------//

        //------------------ Product List --------------------//
        ProductTable = new JTable();
        ProductTable.setModel(model);
        //ProductTable.setCellSelectionEnabled(false);
        //ProductTable.setRowSelectionAllowed(true);
        ProductTable.setShowGrid(false);
        ProductTable.getTableHeader().setReorderingAllowed(false);
        ProductTable.getTableHeader().setResizingAllowed(false);

        ProductTable.setFont(MainFont);
        ProductTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
        ProductTable.getTableHeader().setBackground(new Color(179, 204, 255));
        ProductTable.setRowHeight(50);

        ListScroll = new JScrollPane();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Type");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Status");

        ProductTable.getColumnModel().getColumn(0).setPreferredWidth(1);

        try {
            ProductList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //
        ListScroll.setBounds(0, 65, 1050, 800);
        //

        ListScroll.setViewportView(ProductTable);
        ListScroll.getViewport().setBackground(Color.WHITE);
        //----------------------------------------------------//


        //--------------------- Add comp-----------------------//
        PAllContent.add(ListScroll);
        PAllContent.add(CBProduct);
        PAllContent.add(TFSearch);
        PAllContent.add(BAdd);
        PAllContent.add(BRemove);
        PAllContent.add(BEdit);

        this.add(PAllContent);
        //----------------------------------------------------//
    }
    public void ProductList() throws Exception {
        model.setRowCount(0);
        List<Product> listS = prdbus.SelectAll();
        for (Product s : listS) {
            Object ProductObj[] = {
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
        if(model.getRowCount() == 0)
        {
            prdbus.ResetIncrement();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //----------------- Add Product ----------------------//
        if(e.getSource() == BAdd)
        {
            Main = new JFrame();
            Main.setSize(new Dimension(500, 500));
            Main.setVisible(true);
            Main.setLocationRelativeTo(null);
            Main.setLayout(new BorderLayout());
            Main.setResizable(false);

            //----------------------------------------------------//
            Header = new JPanel();
            //Header.setBackground(Color.RED);
            Header.setPreferredSize(new Dimension(500, 100));
            Header.setLayout(new BorderLayout());

            Body = new JPanel();
            Body.setBackground(Color.WHITE);
            Body.setPreferredSize(new Dimension(500, 510));
            Body.setLayout(null);

            Sidebar = new JPanel();
            Sidebar.setBackground(Color.WHITE);
            Sidebar.setPreferredSize(new Dimension(150, 510));
            Sidebar.setLayout(null);

            //----------------------------------------------------//

            LAdd_Emp = new JLabel("Add Product");
            LAdd_Emp.setFont(new Font("Tahoma", Font.BOLD, 22));
            LAdd_Emp.setHorizontalAlignment(JLabel.CENTER);


            //---------------------- Label ------------------------//
            Font font = new Font("Tahoma", Font.BOLD, 19);

            /*
            LID_Prd = new JLabel("ID:  ");
            LID_Prd.setFont(font);
            LID_Prd.setHorizontalAlignment(JLabel.RIGHT);
            LID_Prd.setBounds(48, 5, 100, 30);
            */

            LName_Prd = new JLabel("Name:  ");
            LName_Prd.setFont(font);
            LName_Prd.setHorizontalAlignment(JLabel.RIGHT);
            LName_Prd.setBounds(48, 30, 100, 30);

            LType_Prd = new JLabel("Type:  ");
            LType_Prd.setFont(font);
            LType_Prd.setHorizontalAlignment(JLabel.RIGHT);
            LType_Prd.setBounds(48, LName_Prd.getY() + 45, 100, 30);

            LPrice_Prd = new JLabel("Price:  ");
            LPrice_Prd.setFont(font);
            LPrice_Prd.setHorizontalAlignment(JLabel.RIGHT);
            LPrice_Prd.setBounds(48, LType_Prd.getY() + 45, 100, 30);

            LQuantity_Prd = new JLabel("Quantity:  ");
            LQuantity_Prd.setFont(font);
            LQuantity_Prd.setHorizontalAlignment(JLabel.RIGHT);
            LQuantity_Prd.setBounds(48, LPrice_Prd.getY() + 45, 100, 30);

            LStatus_Prd = new JLabel("Status:  ");
            LStatus_Prd.setFont(font);
            LStatus_Prd.setHorizontalAlignment(JLabel.RIGHT);
            LStatus_Prd.setBounds(48,LQuantity_Prd.getY() + 45, 100, 30);

            //----------------------------------------------------//

            //-------------------- Text Field ---------------------//
            Font TFfont = new Font("Tahoma", Font.BOLD, 17);
            /*
            TFID = new JTextField();
            TFID.setBounds(0, 5, 250, 30);
            TFID.setFont(TFfont);
             */

            TFName = new JTextField();
            TFName.setBounds(0, 30, 250, 30);
            TFName.setFont(TFfont);

            TFType = new JTextField();
            TFType.setBounds(0, TFName.getY() + 45, 250, 30);
            TFType.setFont(TFfont);

            TFPrice = new JTextField();
            TFPrice.setBounds(0, TFType.getY() + 45, 250, 30);
            TFPrice.setFont(TFfont);

            TFQuantity = new JTextField();
            TFQuantity.setBounds(0, TFPrice.getY() + 45, 250, 30);
            TFQuantity.setFont(TFfont);

            /*
            TFStatus = new JTextField();
            TFStatus.setBounds(0,TFQuantity.getY() + 45, 250, 30);
            TFStatus.setFont(TFfont);
             */
            CBStatus.setBounds(0, TFQuantity.getY() + 45, 250, 30);
            CBStatus.setFont(TFfont);
            CBStatus.setBackground(Color.WHITE);
            CBStatus.setSelectedItem("Available");
            //----------------------------------------------------//

            //----------------------------------------------------//
            Submit = new JButton("Submit");
            Submit.setBounds(0, 320, 100, 30);
            Submit.addActionListener(this);
            Submit.setBackground(Color.WHITE);

            Cancel = new JButton("Cancel");
            Cancel.setBounds(150, 320, 100, 30);
            Cancel.addActionListener(this);
            Cancel.setBackground(Color.WHITE);
            //----------------------------------------------------//


            //----------------------------------------------------//

            Header.add(LAdd_Emp, BorderLayout.CENTER);

            final int H_Gap = 15;
            //Sidebar.add(LID_Prd);
            Sidebar.add(LName_Prd);
            Sidebar.add(LType_Prd);
            Sidebar.add(LPrice_Prd);
            Sidebar.add(LQuantity_Prd);
            Sidebar.add(LStatus_Prd);

            //Body.add(TFID);
            Body.add(TFName);
            Body.add(TFType);
            Body.add(TFPrice);
            Body.add(TFQuantity);
            Body.add(CBStatus);
            Body.add(Submit);
            Body.add(Cancel);

            Main.add(Header, BorderLayout.NORTH);
            Main.add(Sidebar, BorderLayout.WEST);
            Main.add(Body, BorderLayout.CENTER);

            //----------------------------------------------------//
        }
        if(e.getSource() == Submit)
        {
            Product InsertPrd = new Product(
                    TFName.getText(),
                    TFType.getText(),
                    TFPrice.getText(),
                    TFQuantity.getText(),
                    (String) CBStatus.getSelectedItem()
            );
            if(InsertPrd.getName().isEmpty() ||
                    InsertPrd.getType().isEmpty() ||
                    InsertPrd.getPrice().isEmpty() ||
                    InsertPrd.getQuantity().isEmpty() ||
                    InsertPrd.getStatus().isEmpty()
            ){
                JOptionPane.showMessageDialog(null, "Please check your information");
            }
            else {
                try {
                    //System.out.println(InsertPrd.toString());
                    prdbus.Insert(InsertPrd);
                    DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                    model.setRowCount(0);
                    ProductList();
                    Main.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please check your infomation");
                    //throw new RuntimeException(ex);
                }
            }
        }
        if(e.getSource() == Cancel)
        {
            Main.dispose();
        }
        //----------------------------------------------------//

        //---------------- Remove Product -------------------//
        if(e.getSource() == BRemove)
        {
            int SelectedRow = ProductTable.getSelectedRow();
            if(SelectedRow != -1)
            {
                String Data1 = (String) ProductTable.getValueAt(SelectedRow, 0);
                String Data2 = (String) ProductTable.getValueAt(SelectedRow, 1);
                String Data3 = (String) ProductTable.getValueAt(SelectedRow, 2);
                String Data4 = (String) ProductTable.getValueAt(SelectedRow, 3);
                String Data5 = (String) ProductTable.getValueAt(SelectedRow, 4);
                String Data6 = (String) ProductTable.getValueAt(SelectedRow, 5);
                try {
                    int Ans = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if(Ans == 0)
                    {
                        prdbus.Delete(Data1);
                        DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
                        model.setRowCount(0);
                        ProductList();
                        JOptionPane.showMessageDialog(null, "Deletion Successful");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            else JOptionPane.showMessageDialog(null, "Please choose a row in order to delete.");
        }
        //----------------------------------------------------//

        //------------------ Edit Product -------------------//
        if(e.getSource() == BEdit)
        {
            int SelectedRow = ProductTable.getSelectedRow();
            if(SelectedRow != -1)
            {
                //----------------------------------------------------//
                String Data1 = (String) ProductTable.getValueAt(SelectedRow, 0);
                String Data2 = (String) ProductTable.getValueAt(SelectedRow, 1);
                String Data3 = (String) ProductTable.getValueAt(SelectedRow, 2);
                String Data4 = (String) ProductTable.getValueAt(SelectedRow, 3);
                String Data5 = (String) ProductTable.getValueAt(SelectedRow, 4);
                String Data6 = (String) ProductTable.getValueAt(SelectedRow, 5);
                //----------------------------------------------------//

                //----------------------------------------------------//
                Main = new JFrame();
                Main.setSize(new Dimension(500, 530));
                Main.setVisible(true);
                Main.setLocationRelativeTo(null);
                Main.setLayout(new BorderLayout());
                Main.setResizable(false);
                //----------------------------------------------------//

                //----------------------------------------------------//
                Header = new JPanel();
                //Header.setBackground(Color.RED);
                Header.setPreferredSize(new Dimension(500, 100));
                Header.setLayout(new BorderLayout());

                Body = new JPanel();
                Body.setBackground(Color.WHITE);
                Body.setPreferredSize(new Dimension(500, 600));
                Body.setLayout(null);

                Sidebar = new JPanel();
                Sidebar.setBackground(Color.WHITE);
                Sidebar.setPreferredSize(new Dimension(150, 510));
                Sidebar.setLayout(null);

                //----------------------------------------------------//

                LAdd_Emp = new JLabel("Edit");
                LAdd_Emp.setFont(new Font("Tahoma", Font.BOLD, 22));
                LAdd_Emp.setHorizontalAlignment(JLabel.CENTER);


                //---------------------- Label ------------------------//
                Font font = new Font("Tahoma", Font.BOLD, 19);

                LID_Prd = new JLabel("ID:  ");
                LID_Prd.setFont(font);
                LID_Prd.setHorizontalAlignment(JLabel.RIGHT);
                LID_Prd.setBounds(48, 30, 100, 30);

                LName_Prd = new JLabel("Name:  ");
                LName_Prd.setFont(font);
                LName_Prd.setHorizontalAlignment(JLabel.RIGHT);
                LName_Prd.setBounds(48, LID_Prd.getY() + 45, 100, 30);

                LType_Prd = new JLabel("Type:  ");
                LType_Prd.setFont(font);
                LType_Prd.setHorizontalAlignment(JLabel.RIGHT);
                LType_Prd.setBounds(48, LName_Prd.getY() + 45, 100, 30);

                LPrice_Prd = new JLabel("Price:  ");
                LPrice_Prd.setFont(font);
                LPrice_Prd.setHorizontalAlignment(JLabel.RIGHT);
                LPrice_Prd.setBounds(48, LType_Prd.getY() + 45, 100, 30);

                LQuantity_Prd = new JLabel("Quantity:  ");
                LQuantity_Prd.setFont(font);
                LQuantity_Prd.setHorizontalAlignment(JLabel.RIGHT);
                LQuantity_Prd.setBounds(48, LPrice_Prd.getY() + 45, 100, 30);

                LStatus_Prd = new JLabel("Status:  ");
                LStatus_Prd.setFont(font);
                LStatus_Prd.setHorizontalAlignment(JLabel.RIGHT);
                LStatus_Prd.setBounds(48,LQuantity_Prd.getY() + 45, 100, 30);

                //----------------------------------------------------//

                //-------------------- Text Field ---------------------//
                Font TFfont = new Font("Tahoma", Font.BOLD, 17);
                TFID = new JTextField(Data1);
                TFID.setBounds(0, 30, 250, 30);
                TFID.setFont(TFfont);
                TFID.setEnabled(false);

                TFName = new JTextField(Data2);
                TFName.setBounds(0, TFID.getY() + 45, 250, 30);
                TFName.setFont(TFfont);

                TFType = new JTextField(Data3);
                TFType.setBounds(0, TFName.getY() + 45, 250, 30);
                TFType.setFont(TFfont);

                TFPrice = new JTextField(Data4);
                TFPrice.setBounds(0, TFType.getY() + 45, 250, 30);
                TFPrice.setFont(TFfont);

                TFQuantity = new JTextField(Data5);
                TFQuantity.setBounds(0, TFPrice.getY() + 45, 250, 30);
                TFQuantity.setFont(TFfont);

                /*
                TFStatus = new JTextField(Data6);
                TFStatus.setBounds(0, TFQuantity.getY() + 45, 250, 30);
                TFStatus.setFont(TFfont);
                 */
                CBStatus.setBounds(0, TFQuantity.getY() + 45, 250, 30);
                CBStatus.setFont(TFfont);
                CBStatus.setBackground(Color.WHITE);
                CBStatus.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        SelectedStatus = (String) CBProduct.getSelectedItem();
                    }
                });
                CBStatus.setSelectedItem(Data6);

                //----------------------------------------------------//

                //----------------------------------------------------//
                SubmitEdit = new JButton("Submit");
                SubmitEdit.setBounds(0, 390, 100, 30);
                SubmitEdit.addActionListener(this);
                SubmitEdit.setBackground(Color.WHITE);

                CancelEdit = new JButton("Cancel");
                CancelEdit.setBounds(150, 390, 100, 30);
                CancelEdit.addActionListener(this);
                CancelEdit.setBackground(Color.WHITE);
                //----------------------------------------------------//


                //----------------------------------------------------//

                Header.add(LAdd_Emp, BorderLayout.CENTER);

                Sidebar.add(LID_Prd);
                Sidebar.add(LName_Prd);
                Sidebar.add(LType_Prd);
                Sidebar.add(LPrice_Prd);
                Sidebar.add(LQuantity_Prd);
                Sidebar.add(LStatus_Prd);

                Body.add(TFID);
                Body.add(TFName);
                Body.add(TFType);
                Body.add(TFPrice);
                Body.add(TFQuantity);
                Body.add(CBStatus);
                Body.add(SubmitEdit);
                Body.add(CancelEdit);

                Main.add(Header, BorderLayout.NORTH);
                Main.add(Sidebar, BorderLayout.WEST);
                Main.add(Body, BorderLayout.CENTER);
            }
            else
                JOptionPane.showMessageDialog(null, "Please choose a row to edit.");
        }
        if(e.getSource() == SubmitEdit)
        {
            Product UpdatePrd = new Product(
                    TFID.getText(),
                    TFName.getText(),
                    TFType.getText(),
                    TFPrice.getText(),
                    TFQuantity.getText(),
                    (String) CBStatus.getSelectedItem(),
                    TFID.getText()
            );
            try {
                prdbus.Update(UpdatePrd);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            DefaultTableModel model = (DefaultTableModel) ProductTable.getModel();
            model.setRowCount(0);
            try {
                ProductList();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            Main.dispose();
        }
        if(e.getSource() == CancelEdit) Main.dispose();
        //----------------------------------------------------//

    }
}
