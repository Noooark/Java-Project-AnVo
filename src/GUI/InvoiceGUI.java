package GUI;

import BUS.InvoiceBUS;
import BUS.OrderBUS;
import DTO.Invoice;
import DTO.Product;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class InvoiceGUI extends JPanel implements ActionListener {
    /*
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
     */
    DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Price", "Total", "Note"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 2 || column == 5;
        }
    };

    JTable InvoiceTable;
    JScrollPane ListScroll;
    JPanel LeftPayment, RightPayment;
    JTextField TFCustomerPhoneNumber, TFCustomerName, TFCustomerPoint, TFOtherAmount, TFNote,
            TFTotalProduct, TFTotalPrice, TFCustomerPayment, TFCustomerChange, TFPaymentMethod, TFAddress;
    JLabel LCustomerPhone, LCustomerName, LCustomerPoint, LNote, LOtherAmount,
            LTotalProduct, LTotalPrice, LCustomerPayment, LCustomerChange, LPaymentMethod, LAddress;
    JTextArea TANote, TAAddress;
    JComboBox CBPaymentMethod;
    JButton BPayment;
    static int ProductCount = 0;
    public static String[] Obj;
    OrderBUS ordbus = new OrderBUS();
    InvoiceBUS invbus = new InvoiceBUS();
    public InvoiceGUI()
    {
        Font FontLabel = new Font("Tahoma", Font.BOLD, 15);
        //----------------------------------------------------//
        this.setLayout(null);
        //----------------------------------------------------//

        //----------------------------------------------------//
        InvoiceTable = new JTable();
        InvoiceTable.setModel(model);
        InvoiceTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        InvoiceTable.setFont(new Font("Tahoma", Font.BOLD, 15));
        InvoiceTable.getTableHeader().setReorderingAllowed(false);
        InvoiceTable.getTableHeader().setResizingAllowed(false);
        InvoiceTable.getTableHeader().setBackground(new Color(179, 204, 255));

        ListScroll = new JScrollPane();
        ListScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        ListScroll.setBorder(BorderFactory.createEtchedBorder(1));

        /*
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Total");
        model.addColumn("Note");

         */
        InvoiceTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 2 || e.getColumn() == 3) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object value1 = InvoiceTable.getValueAt(row, 2);
                    Object value2 = InvoiceTable.getValueAt(row, 3);
                    if (value1 != null && value2 != null) {
                        int newValue = Integer.parseInt(value1.toString()) * Integer.parseInt(value2.toString());
                        InvoiceTable.setValueAt(newValue, row, 4);
                    }
                }
            }
        });
        ListScroll.setBounds(2, 5, 565, 300);
        ListScroll.setViewportView(InvoiceTable);

        InvoiceTable.getColumnModel().getColumn(0).setPreferredWidth(-1);
        InvoiceTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        InvoiceTable.getColumnModel().getColumn(5).setPreferredWidth(140);

        InvoiceTable.setRowHeight(30);
        //----------------------------------------------------//

        //--------------------- Payment ----------------------//
        LeftPayment = new JPanel();
        LeftPayment.setName("Left");
        LeftPayment.setBounds(2, 310, 282,  435);
        LeftPayment.setBackground(new Color(230, 230, 230));
        LeftPayment.setBorder(BorderFactory.createEtchedBorder(1));
        LeftPayment.setLayout(null);
            //----------------------------------------------------//
            LCustomerPhone= new JLabel("<html><body><b>Phone:</b></body></html>");
            LCustomerPhone.setBounds(10, 2, 70, 35);
            LCustomerPhone.setFont(FontLabel);
            TFCustomerPhoneNumber = new JTextField();
            TFCustomerPhoneNumber.setBounds(10, LCustomerPhone.getY() + 35, 200, 25);
            TFCustomerPhoneNumber.setFont(FontLabel);
            TFCustomerPhoneNumber.setBorder(BorderFactory.createEtchedBorder(0));

            LCustomerName = new JLabel("Name:");
            LCustomerName.setBounds(10, TFCustomerPhoneNumber.getY() + 26,  70, 35);
            LCustomerName.setFont(FontLabel);
            TFCustomerName = new JTextField();
            TFCustomerName.setBounds(10, LCustomerName.getY() + 35, 262, 25);
            TFCustomerName.setFont(FontLabel);
            TFCustomerName.setBorder(BorderFactory.createEtchedBorder(0));

            LCustomerPoint = new JLabel("Point:");
            LCustomerPoint.setBounds(10, TFCustomerName.getY() + 26,  70, 35);
            LCustomerPoint.setFont(FontLabel);
            TFCustomerPoint = new JTextField();
            TFCustomerPoint.setBounds(10,LCustomerPoint.getY() + 35, 200, 25);
            TFCustomerPoint.setFont(FontLabel);
            TFCustomerPoint.setBorder(BorderFactory.createEtchedBorder(0));

            LOtherAmount = new JLabel("Other Amount:");
            LOtherAmount.setBounds(10, TFCustomerPoint.getY() + 26,  150, 35);
            LOtherAmount.setFont(FontLabel);
            TFOtherAmount = new JTextField();
            TFOtherAmount.setBounds(10,LOtherAmount.getY() + 35, 200, 25);
            TFOtherAmount.setFont(FontLabel);
            TFOtherAmount.setBorder(BorderFactory.createEtchedBorder(0));

            /*
            LNote = new JLabel("Note:");
            LNote.setBounds(10, TFOtherAmount.getY() + 26, 80, 35);
            LNote.setFont(FontLabel);
            TANote = new JTextArea();
            TANote.setBounds(10,LNote.getY() + 35, 262, 85);
            TANote.setFont(FontLabel);
            TANote.setBorder(BorderFactory.createEtchedBorder(0));
            TANote.setLineWrap(true);
            TANote.setWrapStyleWord(true);
            TANote.setAlignmentX(Component.LEFT_ALIGNMENT);
            TANote.setAlignmentY(Component.TOP_ALIGNMENT);
             */
            LAddress = new JLabel("Address:");
            LAddress.setBounds(10, TFOtherAmount.getY() + 26, 80, 35);
            LAddress.setFont(FontLabel);
            TAAddress = new JTextArea();
            TAAddress.setBounds(10,LAddress.getY() + 35, 262, 85);
            TAAddress.setFont(FontLabel);
            TAAddress.setBorder(BorderFactory.createEtchedBorder(0));
            TAAddress.setLineWrap(true);
            TAAddress.setWrapStyleWord(true);
            TAAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
            TAAddress.setAlignmentY(Component.TOP_ALIGNMENT);

            //----------------------------------------------------//
        LeftPayment.add(LCustomerPhone);
        LeftPayment.add(TFCustomerPhoneNumber);
        LeftPayment.add(LCustomerName);
        LeftPayment.add(TFCustomerName);
        LeftPayment.add(LCustomerPoint);
        LeftPayment.add(TFCustomerPoint);
        LeftPayment.add(LOtherAmount);
        LeftPayment.add(TFOtherAmount);
        LeftPayment.add(LAddress);
        LeftPayment.add(TAAddress);

        RightPayment = new JPanel();
        RightPayment.setName("Right");
        RightPayment.setBounds(286, 310, 282, 435);
        RightPayment.setBackground(new Color(230, 230, 230));
        RightPayment.setBorder(BorderFactory.createEtchedBorder(1));
        RightPayment.setLayout(null);
            //----------------------------------------------------//
            LTotalProduct= new JLabel("Total Product Amount:");
            LTotalProduct.setBounds(10, 2, 200, 35);
            LTotalProduct.setFont(FontLabel);
            TFTotalProduct = new JTextField();
            TFTotalProduct.setName("Total1");
            TFTotalProduct.setBounds(10, LTotalProduct.getY() + 35, 200, 25);
            TFTotalProduct.setFont(FontLabel);
            TFTotalProduct.setBorder(BorderFactory.createEtchedBorder(0));

            LTotalPrice = new JLabel("Total Price:");
            LTotalPrice.setBounds(10, TFTotalProduct.getY() + 26,  100, 35);
            LTotalPrice.setFont(FontLabel);
            TFTotalPrice = new JTextField();
            TFTotalPrice.setName("Total2");
            TFTotalPrice.setBounds(10, LTotalPrice.getY() + 35, 262, 25);
            TFTotalPrice.setFont(FontLabel);
            TFTotalPrice.setBorder(BorderFactory.createEtchedBorder(0));

            LCustomerPayment = new JLabel("Given:");
            LCustomerPayment.setBounds(10, TFTotalPrice.getY() + 26,  150, 35);
            LCustomerPayment.setFont(FontLabel);
            TFCustomerPayment = new JTextField();
            TFCustomerPayment.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    if(Integer.parseInt(TFCustomerPayment.getText()) >= Integer.parseInt(TFTotalPrice.getText())) {
                        TFCustomerChange.setText(String.valueOf(Integer.parseInt(TFCustomerPayment.getText())
                                - Integer.parseInt(TFTotalPrice.getText())));
                    }
                }

                public void removeUpdate(DocumentEvent e) {
                    if(TFCustomerPayment.getText().isEmpty()) {
                        TFCustomerChange.setText("");
                    }
                    else {
                        if (Integer.parseInt(TFCustomerPayment.getText()) < Integer.parseInt(TFTotalPrice.getText())) {
                            TFCustomerChange.setText("");
                        } else {
                            TFCustomerChange.setText(String.valueOf(Integer.parseInt(TFCustomerPayment.getText())
                                    - Integer.parseInt(TFTotalPrice.getText())));
                        }
                    }
                }

                public void changedUpdate(DocumentEvent e) {
                    //System.out.println("Text changed: " + TFCustomerPayment.getText());
                }
            });
            TFCustomerPayment.setBounds(10,LCustomerPayment.getY() + 35, 130, 25);
            TFCustomerPayment.setFont(FontLabel);
            TFCustomerPayment.setBorder(BorderFactory.createEtchedBorder(0));

            LCustomerChange = new JLabel("Change:");
            LCustomerChange.setBounds(10 + LCustomerPayment.getX() + 125, LCustomerPayment.getY(),  139, 35);
            LCustomerChange.setFont(FontLabel);
            TFCustomerChange = new JTextField();
            TFCustomerChange.setBounds(LCustomerChange.getX(),LCustomerChange.getY() + 35, 130, 25);
            TFCustomerChange.setFont(FontLabel);
            TFCustomerChange.setBorder(BorderFactory.createEtchedBorder(0));
                if(TFCustomerPayment.equals("")) TFCustomerChange.setText("");

            LPaymentMethod = new JLabel("Payment Method:");
            LPaymentMethod.setBounds(10, TFCustomerPayment.getY() + 26,  200, 35);
            LPaymentMethod.setFont(FontLabel);
            JComboBox CBPaymentMethod = new JComboBox<>(new String[]{"Cash", "Card"});
            CBPaymentMethod.setBounds(10,LPaymentMethod.getY() + 35, 130, 25);
            CBPaymentMethod.setFont(FontLabel);
            CBPaymentMethod.setBorder(BorderFactory.createEtchedBorder(0));
            CBPaymentMethod.setBackground(Color.WHITE);
            CBPaymentMethod.setFocusable(false);
            /*
            LAddress = new JLabel("Address:");
            LAddress.setBounds(10, CBPaymentMethod.getY() + 26, 80, 35);
            LAddress.setFont(FontLabel);
            TAAddress = new JTextArea();
            TAAddress.setBounds(10,LAddress.getY() + 35, 262, 85);
            TAAddress.setFont(FontLabel);
            TAAddress.setBorder(BorderFactory.createEtchedBorder(0));
            TAAddress.setLineWrap(true);
            TAAddress.setWrapStyleWord(true);
            TAAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
            TAAddress.setAlignmentY(Component.TOP_ALIGNMENT);
             */
            LNote = new JLabel("Note:");
            LNote.setBounds(10, CBPaymentMethod.getY() + 26, 80, 35);
            LNote.setFont(FontLabel);
            TANote = new JTextArea();
            TANote.setBounds(10,LNote.getY() + 35, 262, 85);
            TANote.setFont(FontLabel);
            TANote.setBorder(BorderFactory.createEtchedBorder(0));
            TANote.setLineWrap(true);
            TANote.setWrapStyleWord(true);
            TANote.setAlignmentX(Component.LEFT_ALIGNMENT);
            TANote.setAlignmentY(Component.TOP_ALIGNMENT);

            BPayment = new JButton("Payment");
            BPayment.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(InvoiceTable.getRowCount() == 0)
                        {
                            JOptionPane.showMessageDialog(null,
                                    "Please add product to invoice!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            if(TFCustomerPayment.getText().isEmpty())
                            {
                                JOptionPane.showMessageDialog(null,
                                        "Please enter the amount of money to pay!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                if(TFCustomerChange.getText().isEmpty() || Integer.parseInt(TFCustomerChange.getText()) < 0)
                                {
                                    JOptionPane.showMessageDialog(null,
                                            "The amount of money to pay is not enough!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else {

                                    OrderGUI.PaymentButton = true;
                                    String TitleTabSelected = OrderGUI.InvoiceTab.getTitleAt(OrderGUI.InvoiceTab.getSelectedIndex());
                                    ProductCount = InvoiceTable.getRowCount();
                                    String StartTime = "";
                                    for(int i = 0; i < OrderGUI.PendingInvoiceTable.getRowCount(); i++)
                                    {
                                        if(OrderGUI.PendingInvoiceTable.getValueAt(i, 0).toString().equals(TitleTabSelected))
                                        {
                                            StartTime = OrderGUI.PendingInvoiceTable.getValueAt(i, 3).toString();
                                            break;
                                        }
                                    }
                                    OrderGUI.CheckClickedPayment(ProductCount); // Delete the pending invoice in the table
                                    Invoice inv = new Invoice(
                                            TitleTabSelected,
                                            LoginGUI.WelcomeUsername,
                                            TFCustomerName.getText(),
                                            StartTime,
                                            OrderGUI.GetDateAndTime(),
                                            TFTotalProduct.getText(),
                                            TFTotalPrice.getText(),
                                            "Confirmed",
                                            TANote.getText()
                                    );
                                    try {
                                        invbus.Insert(inv);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    try {
                                        OrderGUI.ShowPaidInvoice();
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    for (int i = 0; i < InvoiceTable.getRowCount(); i++) {
                                        Product Update = new Product(
                                                InvoiceTable.getValueAt(i, 2).toString(),
                                                InvoiceTable.getValueAt(i, 0).toString(),
                                                InvoiceTable.getValueAt(i, 1).toString()
                                        );
                                        try {
                                            ordbus.ConfirmedPurchased(Update);
                                        } catch (Exception ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                    for (Map.Entry<String, Component> entry : HomePageGUI.CurrState.entrySet()) {
                                        if (entry.getKey() == TitleTabSelected) {
                                            HomePageGUI.CurrState.remove(entry.getKey());
                                            break;
                                        }
                                    }

                                    try {
                                        DefaultTableModel model1 = (DefaultTableModel) OrderGUI.ProductTable.getModel();
                                        model1.setRowCount(0);
                                        OrderGUI.ShowProduct();
                                    } catch (Exception ex) {
                                        throw new RuntimeException();
                                    }

                                }
                            }
                        }
                }
            });
            BPayment.setName("PaymentButton");
            BPayment.setBounds(10, TAAddress.getY() + 95, 262, 50);
            BPayment.setFont(new Font("Arial", Font.BOLD, 20));
            BPayment.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            BPayment.setBackground(Color.WHITE);
            BPayment.setFocusable(false);
            //BPayment.addActionListener(this);
            //----------------------------------------------------//
        RightPayment.add(LTotalProduct);
        RightPayment.add(TFTotalProduct);
        RightPayment.add(LTotalPrice);
        RightPayment.add(TFTotalPrice);
        RightPayment.add(LCustomerPayment);
        RightPayment.add(TFCustomerPayment);
        RightPayment.add(LCustomerChange);
        RightPayment.add(TFCustomerChange);
        RightPayment.add(LPaymentMethod);
        RightPayment.add(CBPaymentMethod);
        RightPayment.add(LNote);
        RightPayment.add(TANote);
        RightPayment.add(BPayment);
        //----------------------------------------------------//

        //----------------------------------------------------//
        //----------------------------------------------------//

        //----------------------------------------------------//
        this.add(ListScroll);
        this.add(LeftPayment);
        this.add(RightPayment);
        //----------------------------------------------------//
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
