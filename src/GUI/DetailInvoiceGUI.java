package GUI;

import BUS.InvoiceBUS;
import DTO.Invoice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DetailInvoiceGUI extends JFrame implements ActionListener {
    JLabel LDetail;
    JTable DetailInvoiceTable;
    DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
    };
    JScrollPane DetailInvoiceScroll;
    JButton BBack;
    InvoiceBUS invbus = new InvoiceBUS();
    ImageIcon Icon;
    public DetailInvoiceGUI()
    {
        Font MainFont = new Font("Tahoma", Font.BOLD, 16);
        //----------------------------------------------------//
        this.setTitle("Detail Invoice");
        this.setSize(1100, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setVisible(true);
        this.setLayout(null);
        this.getRootPane().registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        //----------------------------------------------------//

        //----------------------------------------------------//
        LDetail = new JLabel("Detail Invoice");
        LDetail.setBounds(10, 10, 155, 30);
        LDetail.setFont(new Font("Tahoma", Font.BOLD, 18));
        //----------------------------------------------------//

        //----------------------------------------------------//
        DetailInvoiceTable = new JTable(model){
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (DetailInvoiceTable.getRowCount() >= 1) {
                    if (comp.getPreferredSize().width > comp.getWidth()) {
                        setToolTipText(getValueAt(row, column).toString());
                    } else {
                        setToolTipText(null);
                    }
                }
                return comp;
            }
        };

        DetailInvoiceTable.setShowGrid(true);
        DetailInvoiceTable.getTableHeader().setReorderingAllowed(false);
        DetailInvoiceTable.getTableHeader().setResizingAllowed(false);

        DetailInvoiceTable.setFont(MainFont);
        DetailInvoiceTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
        DetailInvoiceTable.getTableHeader().setBackground(new Color(179, 204, 255));
        DetailInvoiceTable.setRowHeight(35);

        DetailInvoiceTable.addMouseListener(new MouseAdapter() {
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


        DetailInvoiceScroll = new JScrollPane(DetailInvoiceTable);
        DetailInvoiceScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        DetailInvoiceScroll.setBorder(BorderFactory.createEtchedBorder(1));
        model.addColumn("ID");
        model.addColumn("Creator");
        model.addColumn("Customer");
        model.addColumn("Create");
        model.addColumn("Payment");
        model.addColumn("Amount");
        model.addColumn("Total");
        model.addColumn("Status");
        model.addColumn("Note");
        DetailInvoiceScroll.setBounds(10, 50, 1080, 540);

        DetailInvoiceTable.getColumnModel().getColumn(0).setPreferredWidth(15);
        DetailInvoiceTable.getColumnModel().getColumn(5).setPreferredWidth(23);
        DetailInvoiceTable.getColumnModel().getColumn(6).setPreferredWidth(22);
        DetailInvoiceTable.getColumnModel().getColumn(7).setPreferredWidth(33);
        try {
            ShowDetailInvoice();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Icon = new ImageIcon("src/IMG/Back.png");
        BBack = new JButton("Back");
        BBack.setBounds(980, 10, 100, 30);
        BBack.setIcon(Icon);
        BBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
        BBack.setBackground(new Color(238, 238, 238));
        BBack.setFocusable(false);
        BBack.setBorder(null);
        BBack.addActionListener(this);
        //----------------------------------------------------//

        //----------------------------------------------------//
        this.add(LDetail);
        this.add(DetailInvoiceScroll);
        this.add(BBack);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
        requestFocus();
        //----------------------------------------------------//
    }
    public void ShowDetailInvoice() throws Exception {
        model.setRowCount(0);
        List<Invoice> listS = invbus.SelectAll();
        for(Invoice inv : listS)
        {
            Object InvObj[] = {
                    inv.getID(),
                    inv.getCreator(),
                    inv.getCustomerName(),
                    inv.getCreateTime(),
                    inv.getPaymentTime(),
                    inv.getQuantity(),
                    inv.getTotal(),
                    inv.getStatus(),
                    inv.getNote()
            };
            model.addRow(InvObj);
            model.fireTableDataChanged();
        }
    }
    private void showPopupMenu(MouseEvent e)
    {
        int row = DetailInvoiceTable.rowAtPoint(e.getPoint());
        if (row >= 0 && row < DetailInvoiceTable.getRowCount()) {
            DetailInvoiceTable.setRowSelectionInterval(row, row);
        }
        JPopupMenu PopupMenu = new JPopupMenu();
        PopupMenu.setBackground(Color.GRAY);
        JMenuItem Delete = new JMenuItem("Delete");
        Delete.setBackground(Color.WHITE);

        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int SelectedRow = DetailInvoiceTable.getSelectedRow();
                String ID = DetailInvoiceTable.getModel().getValueAt(SelectedRow, 0).toString();
                try {
                    invbus.DeleteInv(ID);
                    ShowDetailInvoice();
                    OrderGUI.ShowPaidInvoice();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        PopupMenu.add(Delete);
        PopupMenu.show(DetailInvoiceTable, e.getX(), e.getY());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BBack)
        {
            this.dispose();
        }
    }
}
