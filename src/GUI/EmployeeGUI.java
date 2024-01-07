package GUI;

import BUS.EmployeeBUS;
import BUS.LoginBUS;
import DTO.DetailedBill;
import DTO.Employee;
import com.sun.source.tree.DefaultCaseLabelTree;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class EmployeeGUI extends JPanel implements ActionListener {
    //----------------------------------------------------//
    Employee UpdateEmp, InsertEmp;
    EmployeeBUS empbus = new EmployeeBUS();
    LoginBUS logbus = new LoginBUS();
    Font MainFont = new Font("Tahoma", Font.BOLD, 16);
    String[] ListPosition = {"All", "ID", "Name", "Position"};
    String[] ColumnTable = {"ID", "Name", "Gender", "Birthday", "Email", "Phone", "Position", "Status"};
    String[] PositionList = {"Manager", "Employee"};
    String[] StatusList = {"Active", "Inactive"};
    String[] GenderList = {"Male", "Female"};
    String ID, Name, Gender, Birthday, Mail, Phone, Position, Status, IDTemp;
    String Data1, Data2, Data3, Data4, Data5, Data6, Data7, Data8;
    String SelectedColumn;
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
    JTable EmployeeTable;
    JScrollPane ListScroll;
    JDatePickerImpl ChooseBirthday;
    JComboBox CBPosition, CBChoosePosition, CBChooseStatus, CBChooseGender;
    JTextField TFSearch, TFID, TFName, TFBirthday, TFEmail, TFPhone, TFPosition, TFStatus;
    JButton BAdd, BRemove, BEdit, Submit, Cancel, SubmitEdit, CancelEdit;
    JLabel LAdd_Emp, LID_Emp, LName_Emp, LGender_Emp, LBirthday_Emp, LEmail_Emp, LPhone_Emp, LPosition_Emp, LStatus_Emp;
    ImageIcon Icon;
    JFrame Main;
    public static Employee emp = new Employee();
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
    public EmployeeGUI() {

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
        CBPosition = new JComboBox(ListPosition);
        CBPosition.setBounds(1000, 15, 100, 30);
        CBPosition.setBackground(Color.WHITE);
        CBPosition.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectedColumn = (String) CBPosition.getSelectedItem();
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
                if(CBPosition.getSelectedItem().toString().equals("All"))
                {
                    ;
                }
                else
                {
                    try {
                        empbus.Search(model, CBPosition.getSelectedItem().toString(), TFSearch.getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if(TFSearch.getText().isEmpty())
                {
                    DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                    model.setRowCount(0);
                    try {
                        EmployeeList();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    if(CBPosition.getSelectedItem().toString().equals("All"))
                    {
                        ;
                    }
                    else
                    {
                        try {
                            empbus.Search(model, CBPosition.getSelectedItem().toString(), TFSearch.getText());
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
                    DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                    model.setRowCount(0);
                    try {
                        EmployeeList();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    if (CBPosition.getSelectedItem().toString().equals("All")) {
                        TFSearch.add(new JLabel(Icon), BorderLayout.WEST);
                        TFSearch.setText("      Search");
                    } else ;
                }
            }
        });
        //---------------- Action Listener -------------------//
        /*
            TFSearch.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    SelectedColumn = CBPosition.getSelectedItem().toString();
                    if(TFSearch.getText().isEmpty())
                    {
                        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                        model.setRowCount(0);
                        try {
                            EmployeeList();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                            throw new RuntimeException(ex);
                        }
                    }
                    else
                    {
                        if(Objects.equals(SelectedColumn, "All"))
                        {
                            ;
                        }
                        else
                        {
                            try {
                                empbus.Search(model, SelectedColumn, TFSearch.getText());
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

        //------------------ Employee List --------------------//
        EmployeeTable = new JTable();
        EmployeeTable.setModel(model);
            //EmployeeTable.setCellSelectionEnabled(false);
            //EmployeeTable.setRowSelectionAllowed(true);
        EmployeeTable.setShowGrid(false);
        EmployeeTable.getTableHeader().setReorderingAllowed(false);
        EmployeeTable.getTableHeader().setResizingAllowed(false);

        EmployeeTable.setFont(MainFont);
        EmployeeTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 18));
        EmployeeTable.getTableHeader().setBackground(new Color(179, 204, 255));
        EmployeeTable.setRowHeight(50);

        ListScroll = new JScrollPane();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Gender");
        model.addColumn("Birthday");
        model.addColumn("Email");
        model.addColumn("Phone");
        model.addColumn("Position");
        model.addColumn("Status");

        EmployeeTable.getColumnModel().getColumn(0).setPreferredWidth(1);

        try {
            EmployeeList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //
        ListScroll.setBounds(0, 65, 1050, 800);
        //

        ListScroll.setViewportView(EmployeeTable);
        ListScroll.getViewport().setBackground(Color.WHITE);
        //----------------------------------------------------//


        //--------------------- Add comp-----------------------//
        PAllContent.add(ListScroll);
        PAllContent.add(CBPosition);
        PAllContent.add(TFSearch);
        PAllContent.add(BAdd);
        PAllContent.add(BRemove);
        PAllContent.add(BEdit);

        this.add(PAllContent);
        //----------------------------------------------------//
    }
    public String GenerateUser(String Name, String Birthday) throws Exception {
        String[] NameSplit = Name.split(" ");
        String[] BirthdaySplit = Birthday.split("-");
        String Username;
        if(NameSplit.length > 1)
        {
            Username = NameSplit[NameSplit.length - 1] + NameSplit[0].substring(0, NameSplit[0].length()) + BirthdaySplit[0];
            Username = Username.toLowerCase();
            return Username;
        }
        else
        {
            Username = NameSplit[0] + BirthdaySplit[0];
            Username = Username.toLowerCase();
            return Username;
        }
    }
    public void EmployeeList() throws Exception {
        model.setRowCount(0);
        List<Employee> listS = empbus.SelectAll();
        for (Employee s : listS) {
            String EmployeeObj[] = {
                    s.getID(),
                    s.getName(),
                    s.getGender(),
                    s.getBirthday(),
                    s.getMail(),
                    s.getPhoneNumber(),
                    s.getPosition(),
                    s.getStatus()
            };
            model.addRow(EmployeeObj);
            model.fireTableDataChanged();
        }
        if(model.getRowCount() == 0)
        {
            empbus.ResetIncrement();
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
    @Override
    public void actionPerformed(ActionEvent e) {
        //----------------- Add Employee ----------------------//
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

            LAdd_Emp = new JLabel("Add Employee");
            LAdd_Emp.setFont(new Font("Tahoma", Font.BOLD, 22));
            LAdd_Emp.setHorizontalAlignment(JLabel.CENTER);


            //---------------------- Label ------------------------//
            Font font = new Font("Tahoma", Font.BOLD, 19);

            /*
            LID_Emp = new JLabel("ID:  ");
            LID_Emp.setFont(font);
            LID_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LID_Emp.setBounds(48, 5, 100, 30);
            */

            LName_Emp = new JLabel("Name:  ");
            LName_Emp.setFont(font);
            LName_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LName_Emp.setBounds(48, 5, 100, 30);

            LGender_Emp = new JLabel("Gender:  ");
            LGender_Emp.setFont(font);
            LGender_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LGender_Emp.setBounds(48, LName_Emp.getY() + 45, 100, 30);

            LBirthday_Emp = new JLabel("Birthday:  ");
            LBirthday_Emp.setFont(font);
            LBirthday_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LBirthday_Emp.setBounds(48, LGender_Emp.getY() + 45, 100, 30);

            LEmail_Emp = new JLabel("Email:  ");
            LEmail_Emp.setFont(font);
            LEmail_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LEmail_Emp.setBounds(48, LBirthday_Emp.getY() + 45, 100, 30);

            LPhone_Emp = new JLabel("Phone:  ");
            LPhone_Emp.setFont(font);
            LPhone_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LPhone_Emp.setBounds(48,LEmail_Emp.getY() + 45, 100, 30);

            LPosition_Emp = new JLabel("Position:  ");
            LPosition_Emp.setFont(font);
            LPosition_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LPosition_Emp.setBounds(48,LPhone_Emp.getY() + 45, 100, 30);

            LStatus_Emp = new JLabel("Status:  ");
            LStatus_Emp.setFont(font);
            LStatus_Emp.setHorizontalAlignment(JLabel.RIGHT);
            LStatus_Emp.setBounds(48,LPosition_Emp.getY() + 45, 100, 30);
            //----------------------------------------------------//

            //-------------------- Text Field ---------------------//
            Font TFfont = new Font("Tahoma", Font.BOLD, 17);
            /*
            TFID = new JTextField();
            TFID.setBounds(0, 5, 250, 30);
            TFID.setFont(TFfont);
             */

            TFName = new JTextField();
            TFName.setBounds(0, 5, 250, 30);
            TFName.setFont(TFfont);

            CBChooseGender = new JComboBox<>(GenderList);
            CBChooseGender.setBounds(0, TFName.getY() + 45, 250, 30);
            CBChooseGender.setFont(TFfont);
            CBChooseGender.setBackground(Color.WHITE);
            CBChooseGender.setFocusable(false);

            ChooseBirthday = getjDatePicker(0, CBChooseGender.getY() + 50, 250, 27);
            ChooseBirthday.setFont(TFfont);
            ChooseBirthday.getJFormattedTextField().setEditable(true);
            LocalDate specificDate = LocalDate.of(2005, 1, 1);
            ChooseBirthday.getModel().setDate(specificDate.getYear(), specificDate.getMonthValue() - 1, specificDate.getDayOfMonth());

            TFEmail = new JTextField();
            TFEmail.setBounds(0, ChooseBirthday.getY() + 40, 250, 30);
            TFEmail.setFont(TFfont);

            TFPhone = new JTextField();
            TFPhone.setBounds(0, TFEmail.getY() + 45, 250, 30);
            TFPhone.setFont(TFfont);

            CBChoosePosition = new JComboBox<>(PositionList);
            CBChoosePosition.setBounds(0,TFPhone.getY() + 45, 250, 30);
            CBChoosePosition.setFont(TFfont);
            CBChoosePosition.setFocusable(false);
            CBChoosePosition.setBackground(Color.WHITE);

            CBChooseStatus = new JComboBox<>(StatusList);
            CBChooseStatus.setBounds(0, CBChoosePosition.getY() + 45, 250, 30);
            CBChooseStatus.setFont(TFfont);
            CBChooseStatus.setFocusable(false);
            CBChooseStatus.setBackground(Color.WHITE);
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
            //Sidebar.add(LID_Emp);
            Sidebar.add(LName_Emp);
            Sidebar.add(LGender_Emp);
            Sidebar.add(LBirthday_Emp);
            Sidebar.add(LEmail_Emp);
            Sidebar.add(LPhone_Emp);
            Sidebar.add(LPosition_Emp);
            Sidebar.add(LStatus_Emp);

            //Body.add(TFID);
            Body.add(TFName);
            Body.add(CBChooseGender);
            Body.add(ChooseBirthday);
            Body.add(TFEmail);
            Body.add(TFPhone);
            Body.add(CBChoosePosition);
            Body.add(CBChooseStatus);
            Body.add(Submit);
            Body.add(Cancel);

            Main.add(Header, BorderLayout.NORTH);
            Main.add(Sidebar, BorderLayout.WEST);
            Main.add(Body, BorderLayout.CENTER);

            //----------------------------------------------------//
        }
        if(e.getSource() == Submit)
        {
            Employee InsertEmp = new Employee(
                    TFName.getText(),
                    CBChooseGender.getSelectedItem().toString(),
                    ChooseBirthday.getModel().getValue().toString(),
                    TFEmail.getText(),
                    TFPhone.getText(),
                    CBChoosePosition.getSelectedItem().toString(),
                    CBChooseStatus.getSelectedItem().toString()
            );
            if(InsertEmp.getName().isEmpty() ||
                    InsertEmp.getGender().isEmpty() ||
                    InsertEmp.getBirthday().isEmpty() ||
                    InsertEmp.getMail().isEmpty() ||
                    InsertEmp.getPhoneNumber().isEmpty() ||
                    InsertEmp.getPosition().isEmpty() ||
                    InsertEmp.getStatus().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please check your information");
            }
            else
            {
                try {
                    if(empbus.Insert(InsertEmp))
                    {
                        DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                        model.setRowCount(0);
                        try {
                            EmployeeList();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                            throw new RuntimeException(ex);
                        }
                        try {
                            String NewUserName = GenerateUser(InsertEmp.getName(), InsertEmp.getBirthday());

                            JOptionPane.showMessageDialog(null, "Username: " + NewUserName + " Password: 123456");
                            logbus.AddAccount(NewUserName, "123456", InsertEmp.getMail(),InsertEmp.getPosition(), logbus.SelectLastAddedID());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                            Main.dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Please check your information");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                /*
                try {
                    //System.out.println(InsertEmp.toString());
                    empbus.Insert(InsertEmp);
                    DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                    model.setRowCount(0);
                    EmployeeList();
                    //logbus.AddAccount()
                    Main.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Please check your infomation");
                    //throw new RuntimeException(ex);
                }
                */
            }

        }
        if(e.getSource() == Cancel)
        {
            Main.dispose();
        }
        //----------------------------------------------------//

        //---------------- Remove Employee -------------------//
        if(e.getSource() == BRemove)
        {
            int SelectedRow = EmployeeTable.getSelectedRow();
            if(SelectedRow != -1)
            {
                String Data1 = (String) EmployeeTable.getValueAt(SelectedRow, 0);
                String Data2 = (String) EmployeeTable.getValueAt(SelectedRow, 1);
                String Data3 = (String) EmployeeTable.getValueAt(SelectedRow, 2);
                String Data4 = (String) EmployeeTable.getValueAt(SelectedRow, 3);
                String Data5 = (String) EmployeeTable.getValueAt(SelectedRow, 4);
                String Data6 = (String) EmployeeTable.getValueAt(SelectedRow, 5);
                String Data7 = (String) EmployeeTable.getValueAt(SelectedRow, 6);
                String Data8 = (String) EmployeeTable.getValueAt(SelectedRow, 7);
                try {
                    int Ans = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if(Ans == 0)
                    {
                        if(logbus.DeleteAccount(Data1))
                        {
                            empbus.Delete(Data1);
                            DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
                            model.setRowCount(0);
                            EmployeeList();
                            JOptionPane.showMessageDialog(null, "Deletion Successful");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Deletion Failed");
                        }
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            else JOptionPane.showMessageDialog(null, "Please choose a row in order to delete.");
        }
        //----------------------------------------------------//

        //------------------ Edit Employee -------------------//
        if(e.getSource() == BEdit)
        {
            int SelectedRow = EmployeeTable.getSelectedRow();
            if(SelectedRow != -1)
            {
                //----------------------------------------------------//
                String Data1 = (String) EmployeeTable.getValueAt(SelectedRow, 0);
                String Data2 = (String) EmployeeTable.getValueAt(SelectedRow, 1);
                String Data3 = (String) EmployeeTable.getValueAt(SelectedRow, 2);
                String Data4 = (String) EmployeeTable.getValueAt(SelectedRow, 3);
                String Data5 = (String) EmployeeTable.getValueAt(SelectedRow, 4);
                String Data6 = (String) EmployeeTable.getValueAt(SelectedRow, 5);
                String Data7 = (String) EmployeeTable.getValueAt(SelectedRow, 6);
                String Data8 = (String) EmployeeTable.getValueAt(SelectedRow, 7);
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

                LID_Emp = new JLabel("ID:  ");
                LID_Emp.setFont(font);
                LID_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LID_Emp.setBounds(48, 5, 100, 30);

                LName_Emp = new JLabel("Name:  ");
                LName_Emp.setFont(font);
                LName_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LName_Emp.setBounds(48, LID_Emp.getY() + 45, 100, 30);

                LGender_Emp = new JLabel("Gender:  ");
                LGender_Emp.setFont(font);
                LGender_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LGender_Emp.setBounds(48, LName_Emp.getY() + 45, 100, 30);

                LBirthday_Emp = new JLabel("Birthday:  ");
                LBirthday_Emp.setFont(font);
                LBirthday_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LBirthday_Emp.setBounds(48, LGender_Emp.getY() + 45, 100, 30);

                LEmail_Emp = new JLabel("Email:  ");
                LEmail_Emp.setFont(font);
                LEmail_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LEmail_Emp.setBounds(48, LBirthday_Emp.getY() + 45, 100, 30);

                LPhone_Emp = new JLabel("Phone:  ");
                LPhone_Emp.setFont(font);
                LPhone_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LPhone_Emp.setBounds(48,LEmail_Emp.getY() + 45, 100, 30);

                LPosition_Emp = new JLabel("Position:  ");
                LPosition_Emp.setFont(font);
                LPosition_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LPosition_Emp.setBounds(48,LPhone_Emp.getY() + 45, 100, 30);

                LStatus_Emp = new JLabel("Status:  ");
                LStatus_Emp.setFont(font);
                LStatus_Emp.setHorizontalAlignment(JLabel.RIGHT);
                LStatus_Emp.setBounds(48,LPosition_Emp.getY() + 45, 100, 30);
                //----------------------------------------------------//

                //-------------------- Text Field ---------------------//
                Font TFfont = new Font("Tahoma", Font.BOLD, 17);
                TFID = new JTextField(Data1);
                TFID.setBounds(0, 5, 250, 30);
                TFID.setFont(TFfont);
                TFID.setEnabled(false);

                TFName = new JTextField(Data2);
                TFName.setBounds(0, TFID.getY() + 45, 250, 30);
                TFName.setFont(TFfont);

                CBChooseGender = new JComboBox<>(GenderList);
                CBChooseGender.setBounds(0, TFName.getY() + 45, 250, 30);
                CBChooseGender.setFont(TFfont);
                CBChooseGender.setBackground(Color.WHITE);
                CBChooseGender.setFocusable(false);

                ChooseBirthday = getjDatePicker(0, CBChooseGender.getY() + 50, 250, 27);
                ChooseBirthday.setFont(TFfont);
                ChooseBirthday.getJFormattedTextField().setEditable(true);
                LocalDate Data4_1 = LocalDate.parse(Data4);
                ChooseBirthday.getModel().setDate(Data4_1.getYear(), Data4_1.getMonthValue() - 1, Data4_1.getDayOfMonth());

                TFEmail = new JTextField(Data5);
                TFEmail.setBounds(0, ChooseBirthday.getY() + 45, 250, 30);
                TFEmail.setFont(TFfont);

                TFPhone = new JTextField(Data6);
                TFPhone.setBounds(0, TFEmail.getY() + 45, 250, 30);
                TFPhone.setFont(TFfont);

                CBChoosePosition = new JComboBox<>(PositionList);
                CBChoosePosition.setBounds(0,TFPhone.getY() + 45, 250, 30);
                CBChoosePosition.setFont(TFfont);
                CBChoosePosition.setFocusable(false);
                CBChoosePosition.setBackground(Color.WHITE);

                CBChooseStatus = new JComboBox<>(StatusList);
                CBChooseStatus.setBounds(0, CBChoosePosition.getY() + 45, 250, 30);
                CBChooseStatus.setFont(TFfont);
                CBChooseStatus.setFocusable(false);
                CBChooseStatus.setBackground(Color.WHITE);
                CBChooseStatus.setSelectedItem(Data8);
                CBChooseStatus.setEnabled(false);
                CBChooseStatus.setBackground(Color.WHITE);
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

                Sidebar.add(LID_Emp);
                Sidebar.add(LName_Emp);
                Sidebar.add(LGender_Emp);
                Sidebar.add(LBirthday_Emp);
                Sidebar.add(LEmail_Emp);
                Sidebar.add(LPhone_Emp);
                Sidebar.add(LPosition_Emp);
                Sidebar.add(LStatus_Emp);

                Body.add(TFID);
                Body.add(TFName);
                Body.add(CBChooseGender);
                Body.add(ChooseBirthday);
                Body.add(TFEmail);
                Body.add(TFPhone);
                Body.add(CBChoosePosition);
                Body.add(CBChooseStatus);
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
            Employee UpdateEmp = new Employee(
                    TFID.getText(),
                    TFName.getText(),
                    CBChooseGender.getSelectedItem().toString(),
                    ChooseBirthday.getModel().getValue().toString(),
                    TFEmail.getText(),
                    TFPhone.getText(),
                    CBChoosePosition.getSelectedItem().toString(),
                    CBChooseStatus.getSelectedItem().toString(),
                    TFID.getText()
            );
            try {
                empbus.Update(UpdateEmp);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            try {
                logbus.UpdateAccount(UpdateEmp.getMail(), UpdateEmp.getID());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            DefaultTableModel model = (DefaultTableModel) EmployeeTable.getModel();
            model.setRowCount(0);
            try {
                EmployeeList();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            Main.dispose();
        }
        if(e.getSource() == CancelEdit)
        {
            Main.dispose();
        }
        //----------------------------------------------------//
    }
}
