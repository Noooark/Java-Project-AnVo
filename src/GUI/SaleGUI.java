package GUI;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import BUS.OrderBUS;
import BUS.ProductBUS;
import BUS.SaleBUS;
import DTO.Product;
import DTO.Sale;
import com.mysql.cj.protocol.AuthenticationPlugin;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.*;

public class SaleGUI extends JPanel implements ActionListener {
    SaleBUS slebus = new SaleBUS();
    ProductBUS prdbus = new ProductBUS();
    OrderBUS ordbus = new OrderBUS();
    JPanel PRight, PLeft;
    JLabel LPromotionCode, LPromotionName, LPromotionType, LPromotionValue,LPromotionStartDate, LPromotionEndDate,
                LPromotionStatus,
            LSearch, LFindPromotionType, LFindPromotionStatus,
            LSaleInfo;
    JLabel LFindProduct, LFindProductType;
    JTextField TFSearchProduct;
    JCheckBox CheckBoxAllProduct;
    JComboBox CBFindProductType = new JComboBox(new String[]{"All", "Coffee", "Tea", "Milk", "Iced Drinks"});
    JTextField TFPromotionCode, TFPromotionName, TFPromotionValue, TFPromotionStartDate, TFPromotionEndDate;
    JComboBox CBPromotionType = new JComboBox<>(new String[]{"Discount", "Gift"}),
            CBPromotionStatus = new JComboBox<>(new String[]{"Active | Expired"}),
            CBFindPromotionStatus = new JComboBox<>(new String[]{"All", "Active", "Expired"}),
            CBFindPromotionType = new JComboBox<>(new String[]{"All", "Discount", "Gift"});
    JDatePicker StartDate, EndDate;
    JButton BAdd, BCancel, BUpdate, BApplyPromotion;
    JTable PromotionListTable, AppliedPromotionTable;
    JScrollPane PromotionListScrollPane, AppliedPromotionScrollPanel;
    JTextField TFSearch;
    ImageIcon Icon;
    DefaultTableModel model = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    DefaultTableModel model1 = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 4) {
                return Boolean.class; // Set the column "Selected" data type to Boolean
            }
            return super.getColumnClass(columnIndex);
        }
    };
    public SaleGUI()
    {
        //--------------------------------------------------------------------------------------------------------//
        PRight = new JPanel();
        PRight.setPreferredSize(new Dimension(450, 800));
        PRight.setBackground(new Color(217, 217, 217));
        PRight.setLayout(null);
        PRight.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Font font = new Font("Tahoma", Font.BOLD, 16);
        Font fonttext = new Font("Tahoma", Font.PLAIN, 15);

        LSaleInfo = new JLabel("Promotion Information");
        LSaleInfo.setFont(new Font("Tahoma", Font.BOLD, 22));
        LSaleInfo.setBounds(100, 55, 300, 30);

        LPromotionCode = new JLabel("Promotion Code:");
        LPromotionCode.setFont(font);
        LPromotionCode.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionCode.setBounds(20, 140, 200, 30);
        TFPromotionCode = new JTextField();
        TFPromotionCode.setFont(fonttext);
        TFPromotionCode.setHorizontalAlignment(SwingConstants.LEFT);
        TFPromotionCode.setBounds(180, LPromotionCode.getY(), 250, 30);
        TFPromotionCode.setEnabled(false);

        LPromotionName = new JLabel("Promotion Name:");
        LPromotionName.setFont(font);
        LPromotionName.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionName.setBounds(20, LPromotionCode.getY() + 60, 200, 30);
        TFPromotionName = new JTextField();
        TFPromotionName.setFont(fonttext);
        TFPromotionName.setHorizontalAlignment(SwingConstants.LEFT);
        TFPromotionName.setBounds(180, LPromotionName.getY(), 250, 30);

        LPromotionType = new JLabel("Promotion Type:");
        LPromotionType.setFont(font);
        LPromotionType.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionType.setBounds(20, LPromotionName.getY() + 60, 200, 30);
        CBPromotionType.setFont(fonttext);
        CBPromotionType.setBounds(180, LPromotionType.getY(), 250, 30);
        CBPromotionType.setBackground(Color.WHITE);

        LPromotionValue = new JLabel("Value:");
        LPromotionValue.setFont(font);
        LPromotionValue.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionValue.setBounds(20, LPromotionType.getY() + 60, 200, 30);
        TFPromotionValue = new JTextField();
        TFPromotionValue.setFont(fonttext);
        TFPromotionValue.setHorizontalAlignment(SwingConstants.LEFT);
        TFPromotionValue.setBounds(180, LPromotionValue.getY(), 250, 30);

        LPromotionStartDate = new JLabel("Start Date:");
        LPromotionStartDate.setFont(font);
        LPromotionStartDate.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionStartDate.setBounds(20, LPromotionValue.getY() + 60, 200, 30);
        StartDate = getjDatePicker(180, LPromotionStartDate.getY(), 250, 27);

        LPromotionEndDate = new JLabel("End Date:");
        LPromotionEndDate.setFont(font);
        LPromotionEndDate.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionEndDate.setBounds(20, LPromotionStartDate.getY() + 60, 200, 30);
        EndDate = getjDatePicker(180, LPromotionEndDate.getY(), 250, 27);

        LPromotionStatus = new JLabel("Status:");
        LPromotionStatus.setFont(font);
        LPromotionStatus.setHorizontalAlignment(SwingConstants.LEFT);
        LPromotionStatus.setBounds(20, LPromotionEndDate.getY() + 60, 200, 30);
        CBPromotionStatus.setFont(fonttext);
        CBPromotionStatus.setBounds(180, LPromotionStatus.getY(), 250, 30);
        CBPromotionStatus.setBackground(Color.WHITE);
        CBPromotionStatus.setEnabled(false);

        BAdd = new JButton("Add");
        BAdd.setFont(font);
        BAdd.setBounds(CBPromotionStatus.getX() + 75, LPromotionStatus.getY() + 60, 100, 30);
        BAdd.setBackground(Color.WHITE);
        BAdd.addActionListener(this);
        BAdd.setFocusable(false);

        /*
        BCancel = new JButton("Cancel");
        BCancel.setFont(font);
        BCancel.setBounds(BAdd.getX() + 150, LPromotionStatus.getY() + 60, 100, 30);
        BCancel.setBackground(Color.WHITE);
        BCancel.addActionListener(this);
        BCancel.setFocusable(false);
        */
        //----------------------------------------------------//
        PRight.add(LSaleInfo);

        PRight.add(LPromotionCode);
        PRight.add(TFPromotionCode);

        PRight.add(LPromotionName);
        PRight.add(TFPromotionName);

        PRight.add(LPromotionType);
        PRight.add(CBPromotionType);

        PRight.add(LPromotionValue);
        PRight.add(TFPromotionValue);

        PRight.add(LPromotionStartDate);
        PRight.add((Component) StartDate);

        PRight.add(LPromotionEndDate);
        PRight.add((Component) EndDate);

        PRight.add(LPromotionStatus);
        PRight.add(CBPromotionStatus);

        PRight.add(BAdd);
        //--------------------------------------------------------------------------------------------------------//


        //--------------------------------------------------------------------------------------------------------//

        //----------------------------------------------------//

        /*
        JPopupMenu PopupMenu = new JPopupMenu();
        JMenuItem EditSale = new JMenuItem("Edit");
        EditSale.setBackground(Color.WHITE);

        EditSale.addActionListener(event -> {
            System.out.println("ASDASD");
        });
        PopupMenu.add(EditSale);
         */

        //----------------------------------------------------//

        //----------------------------------------------------//
        PLeft = new JPanel();
        PLeft.setBackground(new Color(217, 217, 217));
        PLeft.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        PLeft.setPreferredSize(new Dimension(1170 - PRight.getWidth(), 800));
        PLeft.setLayout(null);

        //----------------------------------------------------///----------------------------------------------------//
        TFSearch = new JTextField("      Search");
        TFSearch.setBounds(20, 35, 250, 30);
        TFSearch.setOpaque(true);
        Icon = new ImageIcon("src/IMG/Search.png");
        TFSearch.setLayout(new BorderLayout());
        TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
        TFSearch.setMargin(new Insets(0, 5, 0, 0));

        CBFindPromotionStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CBFindPromotionStatus.getSelectedItem().equals("All"))
                {
                    try {
                        ShowListPromo();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    try {
                        slebus.SearchStatus(model, CBFindPromotionStatus.getSelectedItem().toString());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        CBFindPromotionType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CBFindPromotionType.getSelectedItem().equals("All"))
                {
                    try {
                        ShowListPromo();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    try {
                        slebus.SearchType(model, CBFindPromotionType.getSelectedItem().toString());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        TFSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
               if(CBFindPromotionType.getSelectedItem().equals("All") && CBFindPromotionStatus.getSelectedItem().equals("All"))
               {
                   try {
                       slebus.Search(model, TFSearch.getText(), "");
                   } catch (Exception ex) {
                       throw new RuntimeException(ex);
                   }
               }
               else
               {

               }
            }
            public void removeUpdate(DocumentEvent e) {
                if(CBFindPromotionType.getSelectedItem().equals("All") && CBFindPromotionStatus.getSelectedItem().equals("All"))
                {
                    try {
                        slebus.Search(model, TFSearch.getText(), "");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {

                }
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        TFSearch.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                TFSearch.setText("");
                TFSearch.removeAll();
                TFSearch.repaint();
            }

            public void focusLost(FocusEvent e) {
                if(TFSearch.getText().isEmpty()) {
                    Icon = new ImageIcon("src/IMG/Search.png");
                    TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                    TFSearch.setText("      Search");
                    DefaultTableModel model = (DefaultTableModel) PromotionListTable.getModel();
                    model.setRowCount(0);
                    try {
                        ShowListPromo();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {

                }
            }
        });

        //----------------------------------------------------///----------------------------------------------------//
        CBFindPromotionType.setFont(fonttext);
        CBFindPromotionType.setBounds(TFSearch.getX() + 475, TFSearch.getY(), 100, 30);
        CBFindPromotionType.setBackground(Color.WHITE);
        CBFindPromotionType.setFocusable(false);
        LFindPromotionType = new JLabel("Type");
        LFindPromotionType.setFont(new Font("Tahoma", Font.BOLD, 14));
        LFindPromotionType.setBounds(CBFindPromotionType.getX(), CBFindPromotionType.getY() - 30, 100, 30);

        CBFindPromotionStatus.setFont(fonttext);
        CBFindPromotionStatus.setBounds(CBFindPromotionType.getX() + 110, TFSearch.getY(), 100, 30);
        CBFindPromotionStatus.setBackground(Color.WHITE);
        CBFindPromotionStatus.setFocusable(false);
        LFindPromotionStatus = new JLabel("Status");
        LFindPromotionStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
        LFindPromotionStatus.setBounds(CBFindPromotionStatus.getX(), CBFindPromotionStatus.getY() - 30, 100, 30);

        PromotionListTable = new JTable();
        PromotionListTable.setModel(model);

        model.addColumn("Promo");
        model.addColumn("Name");
        model.addColumn("Type");
        model.addColumn("Value");
        model.addColumn("Start Date");
        model.addColumn("End Date");
        model.addColumn("Status");

        PromotionListTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        PromotionListTable.getTableHeader().setOpaque(false);
        PromotionListTable.getTableHeader().setBackground(new Color(179, 204, 255));
        //PromotionListTable.getTableHeader().setForeground(new Color(255,255,255));
        PromotionListTable.getTableHeader().setReorderingAllowed(false);
        PromotionListTable.getTableHeader().setResizingAllowed(false);
        PromotionListTable.setRowHeight(25);
        PromotionListTable.setFont(new Font("Tahoma", Font.BOLD, 16));
        PromotionListTable.setGridColor(Color.BLACK);
        PromotionListTable.setRowHeight(35);

        PromotionListTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        PromotionListTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        PromotionListTable.getColumnModel().getColumn(3).setPreferredWidth(10);

        //--------------------------------------------------------------------------------------------------------//

        //--------------------------------------------------------------------------------------------------------//
        PromotionListScrollPane = new JScrollPane(PromotionListTable);
        PromotionListScrollPane.setBounds(3, TFSearch.getY() + 45, 715, 300);
        PromotionListScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        PromotionListScrollPane.setViewportView(PromotionListTable);

        try {
            ShowListPromo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //----------------------------------------------------//
        PromotionListTable.addMouseListener(new MouseAdapter() {
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
        //----------------------------------------------------//
        TFSearchProduct = new JTextField("      Search");
        TFSearchProduct.setBounds(20, PromotionListScrollPane.getY() + 330, 250, 30);
        TFSearchProduct.setOpaque(true);
        Icon = new ImageIcon("src/IMG/Search.png");
        TFSearchProduct.setLayout(new BorderLayout());
        TFSearchProduct.add(new JLabel(Icon), BorderLayout.WEST);
        TFSearchProduct.setMargin(new Insets(0, 5, 0, 0));
        //----------------------------------------------------////----------------------------------------------------//
        CBFindProductType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(CBFindProductType.getSelectedItem().equals("All"))
                {
                    DefaultTableModel model1 = (DefaultTableModel) AppliedPromotionTable.getModel();
                    model1.setRowCount(0);
                    try {
                        ShowListProduct();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    DefaultTableModel model1 = (DefaultTableModel) AppliedPromotionTable.getModel();
                    model1.setRowCount(0);
                    try {
                        slebus.SearchTypeProductCB(model1, CBFindProductType.getSelectedItem().toString());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        TFSearchProduct.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if(CBFindProductType.getSelectedItem().equals("All"))
                {
                    try {
                        slebus.SearchName(model1, TFSearchProduct.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    String SelectedColumn = CBFindProductType.getSelectedItem().toString();
                    try {
                        slebus.Search1(model1, SelectedColumn, TFSearchProduct.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            public void removeUpdate(DocumentEvent e) {
                if(CBFindProductType.getSelectedItem().equals("All"))
                {
                    try {
                        slebus.SearchName(model1, TFSearchProduct.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    String SelectedColumn = CBFindProductType.getSelectedItem().toString();
                    try {
                        slebus.Search1(model1, SelectedColumn, TFSearchProduct.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        TFSearchProduct.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                TFSearchProduct.setText("");
                TFSearchProduct.removeAll();
                TFSearchProduct.repaint();
            }

            public void focusLost(FocusEvent e) {
                if(TFSearchProduct.getText().isEmpty()) {
                    Icon = new ImageIcon("src/IMG/Search.png");
                    TFSearchProduct.add(new JLabel(Icon), BorderLayout.WEST);
                    TFSearchProduct.setText("      Search");
                    DefaultTableModel model1 = (DefaultTableModel) AppliedPromotionTable.getModel();
                    model1.setRowCount(0);
                    try {
                        ShowListProduct();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {

                }
            }
        });
        //----------------------------------------------------////----------------------------------------------------//
        LFindProductType = new JLabel("Product Type");
        LFindProductType.setFont(new Font("Tahoma", Font.BOLD, 14));
        LFindProductType.setBounds(TFSearchProduct.getX() + 475, TFSearchProduct.getY() - 30, 100, 30);

        CBFindProductType.setFont(fonttext);
        CBFindProductType.setBounds(TFSearchProduct.getX() + 475, TFSearchProduct.getY(), 100, 30);
        CBFindProductType.setBackground(Color.WHITE);
        CBFindProductType.setFocusable(false);

        CheckBoxAllProduct = new JCheckBox("All");
        UIManager.put("CheckBox.icon", new ImageIcon("src/IMG/Check.png"));
        CheckBoxAllProduct.setFont(new Font("Tahoma", Font.BOLD, 15));
        CheckBoxAllProduct.setBounds(CBFindProductType.getX() + 119, TFSearchProduct.getY(), 100, 30);
        CheckBoxAllProduct.setBackground(new Color(217, 217, 217));
        CheckBoxAllProduct.setFocusable(false);
        CheckBoxAllProduct.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(CheckBoxAllProduct.isSelected())
                {
                    for(int i = 0; i < AppliedPromotionTable.getRowCount(); i++) {
                        AppliedPromotionTable.setValueAt(true, i, 4);
                    }
                }
                else
                {
                    for(int i = 0; i < AppliedPromotionTable.getRowCount(); i++) {
                        AppliedPromotionTable.setValueAt(false, i, 4);
                    }
                }
            }
        });


        AppliedPromotionTable = new JTable(model1);
        model1.addColumn("ID");
        model1.addColumn("Name");
        model1.addColumn("Old Price");
        model1.addColumn("New Price");
        model1.addColumn("");
        AppliedPromotionTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        AppliedPromotionTable.getTableHeader().setOpaque(false);
        AppliedPromotionTable.getTableHeader().setBackground(new Color(179, 204, 255));
        //AppliedPromotionTable.getTableHeader().setForeground(new Color(255,255,255));
        AppliedPromotionTable.getTableHeader().setReorderingAllowed(false);
        AppliedPromotionTable.getTableHeader().setResizingAllowed(false);
        AppliedPromotionTable.setFont(new Font("Tahoma", Font.BOLD, 16));
        AppliedPromotionTable.setGridColor(Color.BLACK);
        AppliedPromotionTable.setRowHeight(35);
        AppliedPromotionTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        AppliedPromotionTable.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((Boolean) value);
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                checkBox.setBackground(Color.WHITE);
                return checkBox;
            }
        });

        AppliedPromotionScrollPanel = new JScrollPane(AppliedPromotionTable);
        AppliedPromotionScrollPanel.setBounds(3, TFSearchProduct.getY() + 45, 715, 300);
        AppliedPromotionScrollPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        AppliedPromotionScrollPanel.setViewportView(AppliedPromotionTable);


        BApplyPromotion = new JButton("Apply");
        BApplyPromotion.setFont(new Font("Tahoma", Font.BOLD, 16));
        BApplyPromotion.setBounds(AppliedPromotionScrollPanel.getX() + 575, AppliedPromotionScrollPanel.getY() + 307, 100, 30);
        BApplyPromotion.setBackground(Color.WHITE);
        BApplyPromotion.addActionListener(this);
        BApplyPromotion.setFocusable(false);

        try {
            ShowListProduct();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------------------------////----------------------------------------------------//
        PromotionListTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2)
                {
                    if(PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 2).equals("Discount")
                        && PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 6).equals("Active")
                    )
                    {
                        int DiscountValue = Integer.parseInt(PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 3).toString());
                        for(int i = 0; i < AppliedPromotionTable.getRowCount(); i++)
                        {
                            if(AppliedPromotionTable.getValueAt(i, 3) == "")
                            {
                                int Change = (Integer.parseInt(AppliedPromotionTable.getValueAt(i, 2).toString()) * (100 - DiscountValue) / 100);
                                AppliedPromotionTable.setValueAt(Change, i, 3);

                            }
                            // 06-01-2024 01:53
                            else
                            {
                                int Change = (Integer.parseInt(AppliedPromotionTable.getValueAt(i, 2).toString()) * (100 - DiscountValue) / 100);
                                if(Change == Integer.parseInt(AppliedPromotionTable.getValueAt(i, 3).toString()))
                                {
                                    JOptionPane.showMessageDialog(null, "This promotion is already applied");
                                    break;
                                }
                                else
                                {
                                    AppliedPromotionTable.setValueAt(Change, i, 3);
                                }

                            }
                        }
                    }

                }
            }
        });
        //----------------------------------------------------////----------------------------------------------------//

        //----------------------------------------------------//
        PLeft.add(TFSearch);
        PLeft.add(CBFindPromotionType);
        PLeft.add(LFindPromotionType);
        PLeft.add(CBFindPromotionStatus);
        PLeft.add(LFindPromotionStatus);
        PLeft.add(PromotionListScrollPane);
        PLeft.add(TFSearchProduct);
        PLeft.add(CBFindProductType);
        PLeft.add(LFindProductType);
        PLeft.add(CheckBoxAllProduct);
        PLeft.add(AppliedPromotionScrollPanel);
        PLeft.add(BApplyPromotion);
        PLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PromotionListTable.clearSelection();
            }
        });
        //----------------------------------------------------//

        //--------------------------------------------------------------------------------------------------------//
        this.setLayout(new BorderLayout());
        this.add(PRight, BorderLayout.EAST);
        this.add(PLeft, BorderLayout.WEST);
        //----------------------------------------------------//
    }
    public void ShowListPromo() throws Exception {
        model.setRowCount(0);
        List<Sale> listS = slebus.SelectAll();
        for (Sale s : listS) {
            LocalDate EndedDate = LocalDate.parse(s.getEndDate());
            LocalDate CurrentDate = LocalDate.now();
            String ProductObj[] = {
                    s.getID(),
                    s.getName(),
                    s.getType(),
                    s.getValue(),
                    s.getStartDate(),
                    s.getEndDate(),
                    s.getStatus()
            };
            model.addRow(ProductObj);
            model.fireTableDataChanged();
        }
    }
    static public String CheckPricePromo(String StartDate, String EndDate, String NewPrice, String Price)
    {
        if(StartDate == null || EndDate == null || NewPrice == null || Price == null) return Price;
        if(LocalDate.parse(StartDate).compareTo(LocalDate.now()) <= 0)
        {
            if(LocalDate.parse(EndDate).compareTo(LocalDate.now()) >= 0)
            {
                return NewPrice;
            }
        }
        return Price;
    }
    public void ShowListProduct() throws Exception {
        model1.setRowCount(0);
        List<Product> listS = prdbus.SelectAll();
        for (Product s : listS) {
            Object ProductObj[] = {
                    s.getID(),
                    s.getName(),
                    CheckPricePromo(s.getStartDate(), s.getEndDate(), s.getNewPrice(), s.getPrice()),
                    "",
                    false
            };
            model1.addRow(ProductObj);
            model1.fireTableDataChanged();
        }
    }
    private JDatePickerImpl getjDatePicker(int x, int y, int width, int height) {
        SqlDateModel model = new SqlDateModel();
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.day", "Day");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter(){
            @Override
            public Object stringToValue(String text) throws ParseException {
                return null;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if(value != null){
                    Calendar cal = (Calendar) value;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = format.format(cal.getTime());
                    return strDate;
                }
                return "";
            }
        });
        datePicker.setBounds(x, y, width, height);
        Component comp = datePicker.getComponent(0);
        if(comp instanceof JFormattedTextField){
            ((JFormattedTextField) comp).setHorizontalAlignment(SwingConstants.LEFT);
            ((JFormattedTextField) comp).setFont(new Font("Tahoma", Font.PLAIN, 15));
        }
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("JFormattedTextField.foreground", Color.WHITE);
        UIManager.put("JCalendar.dayforeground", Color.WHITE);;


        datePicker.getComponent(0).setBackground(Color.WHITE);
        datePicker.getComponent(1).setBackground(Color.WHITE);
        datePicker.getComponent(1).setFont(new Font("Tahoma", Font.BOLD, 15));
        datePicker.getComponent(1).setFocusable(false);
        datePicker.setBackground(Color.WHITE);
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ;
            }
        });
        return datePicker;
    }
    public String GenerateID() {
        UUID IDBill = UUID.randomUUID();
        long mostSignificantBits = IDBill.getMostSignificantBits();
        long leastSignificantBits = IDBill.getLeastSignificantBits();
        long id = Math.abs(mostSignificantBits + leastSignificantBits) % 1000000;
        return String.format("%06d", id);
    }
    private void showPopupMenu(MouseEvent e) {
        int row = PromotionListTable.rowAtPoint(e.getPoint());
        if (row >= 0 && row < PromotionListTable.getRowCount()) {
            PromotionListTable.setRowSelectionInterval(row, row);
        }
        JPopupMenu PopupMenu = new JPopupMenu();
        PopupMenu.setBackground(Color.GRAY);
        JMenuItem EditSale = new JMenuItem("Edit");
        JMenuItem DeleteSale = new JMenuItem("Delete");
        DeleteSale.setBackground(Color.WHITE);
        EditSale.setBackground(Color.WHITE);
        //-------------------------------------------------- Edit --------------------------------------------------//
        EditSale.addActionListener(event -> {
            String ID = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 0).toString();
            String Name = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 1).toString();
            String Type = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 2).toString();
            String Value = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 3).toString();
            String Start = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 4).toString();
            String End = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 5).toString();
            String Status = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 6).toString();
            TFPromotionCode.setText(ID);
            TFPromotionName.setText(Name);
            CBPromotionType.setSelectedItem(Type);
            TFPromotionValue.setText(Value);
            LocalDate LocalDateStart = LocalDate.parse(Start);
            LocalDate LocalDateEnd = LocalDate.parse(End);
            StartDate.getModel().setDate(LocalDateStart.getYear(), LocalDateStart.getMonthValue() - 1, LocalDateStart.getDayOfMonth());
            StartDate.getModel().setSelected(true);
            EndDate.getModel().setDate(LocalDateEnd.getYear(), LocalDateEnd.getMonthValue() - 1, LocalDateEnd.getDayOfMonth());
            EndDate.getModel().setSelected(true);
            CBPromotionStatus.setSelectedItem(Status);
            //----------------------------------------------------//
            BUpdate = new JButton("Update");
            BUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
            BUpdate.setBounds(CBPromotionStatus.getX(), LPromotionStatus.getY() + 60, 100, 30);
            BUpdate.addActionListener(this);
            BCancel = new JButton("Cancel");
            BCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
            BCancel.setVisible(true);
            BCancel.setBounds(BUpdate.getX() + 150, LPromotionStatus.getY() + 60, 100, 30);
            BCancel.addActionListener(this);
            PRight.add(BCancel);
            PRight.add(BUpdate);
            PRight.remove(BAdd);
            PRight.repaint();
            //----------------------------------------------------//
        });
        //----------------------------------------------------//----------------------------------------------------//

        //-------------------------------------------------- Delete -------------------------------------------------//
        DeleteSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure to delete this promotion?",
                        "Delete Promotion", JOptionPane.YES_NO_OPTION);
                if(Choice == 0)
                {
                    try {
                        if(slebus.Delete(PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 0).toString()))
                        {
                            //JOptionPane.showMessageDialog(null, "Delete Promotion Successfully");
                            ShowListPromo();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Delete Promotion Failed");
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                if(Choice == 1)
                {
                    return;
                }
            }
        });
        //----------------------------------------------------//----------------------------------------------------//
        PopupMenu.add(EditSale);
        PopupMenu.add(DeleteSale);
        PopupMenu.show(PromotionListTable, e.getX(), e.getY());

    }
    public void RemoveRemainder()
    {
        PRight.remove(BUpdate);
        PRight.remove(BCancel);
        PRight.add(BAdd);
        PRight.repaint();

        JTextField[] TextField = new JTextField[]{ TFPromotionCode, TFPromotionName, TFPromotionValue, TFPromotionStartDate, TFPromotionEndDate};
        for(JTextField TF : TextField)
        {
            if(TF != null) TF.setText("");
        }
        Date Today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(Today);
        StartDate.getModel().setDate(Integer.parseInt(strDate.substring(0, 4)),
                Integer.parseInt(strDate.substring(5, 7)) - 1,
                Integer.parseInt(strDate.substring(8, 10)));
        StartDate.getModel().setSelected(true);
        EndDate.getModel().setDate(Integer.parseInt(strDate.substring(0, 4)),
                Integer.parseInt(strDate.substring(5, 7)) - 1,
                Integer.parseInt(strDate.substring(8, 10)));
        EndDate.getModel().setSelected(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BAdd)
        {
            //System.out.println(StartDate.getModel().getValue());
            TFPromotionCode.setText(GenerateID());
            Sale InsertSaleInfo = new Sale(
                    "P" + TFPromotionCode.getText(),
                    TFPromotionName.getText(),
                    CBPromotionType.getSelectedItem().toString(),
                    TFPromotionValue.getText(),
                    StartDate.getModel().getValue().toString(),
                    EndDate.getModel().getValue().toString(),
                    CBPromotionStatus.getSelectedItem().toString()
            );
            LocalDate LocalDateEnd = LocalDate.parse(EndDate.getModel().getValue().toString());
            if(LocalDateEnd.isBefore(LocalDate.now()))
            {
                JOptionPane.showMessageDialog(null, "End Date must be after today");
                return;
            }
            try {
                if(slebus.Insert(InsertSaleInfo))
                {
                    JOptionPane.showMessageDialog(null, "Add Promotion Successfully");
                    ShowListPromo();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Add Promotion Failed");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if(e.getSource() == BCancel)
        {
            //BAdd.setText("Add");
            //BAdd.setBounds(CBPromotionStatus.getX() + 75, LPromotionStatus.getY() + 60, 100, 30);
            RemoveRemainder();
        }
        if(e.getSource() == BUpdate)
        {
            Sale UpdateSale = new Sale(
                    TFPromotionCode.getText(),
                    TFPromotionName.getText(),
                    CBPromotionType.getSelectedItem().toString(),
                    TFPromotionValue.getText(),
                    StartDate.getModel().getValue().toString(),
                    EndDate.getModel().getValue().toString(),
                    ""
            );
            try {
                if(slebus.Update(UpdateSale))
                {
                    ShowListPromo();
                    RemoveRemainder();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() == BApplyPromotion)
        {
            int Choice = JOptionPane.showConfirmDialog(null,
                    "Are you sure to apply this promotion?",
                    "Apply Promotion", JOptionPane.YES_NO_OPTION);
            if(Choice == 0)
            {
                for(int i = 0; i < AppliedPromotionTable.getRowCount(); i++)
                {
                    if(AppliedPromotionTable.getValueAt(i, 4).equals(true))
                    {
                        String ID = AppliedPromotionTable.getValueAt(i, 0).toString();
                        String NewPrice = AppliedPromotionTable.getValueAt(i, 3).toString();
                        String StartDate = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 4).toString();
                        String EndDate = PromotionListTable.getValueAt(PromotionListTable.getSelectedRow(), 5).toString();
                        Product UpdateProduct = new Product(
                                NewPrice,
                                ID
                        );
                        try {
                            if(prdbus.UpdatePromo(UpdateProduct, StartDate, EndDate))
                            {
                                JOptionPane.showMessageDialog(null, "Apply Promotion Successfully");
                                ShowListProduct();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Apply Promotion Failed");
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
            if(Choice == 1) {
                return;
            }
        }
    }
}
