package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.Calendar;

public class HomePageGUI extends JFrame implements ActionListener {
    JPanel PSidebar1, PSidebar2, PContent;
    static JButton BAdmin, BSettings, BStatistics, BProductMana, BOrderMana, BEmployeeMana, BLogout, BSaleMana;
    JLabel LWelcome;
    ImageIcon icon;
    static boolean Display = true;
    public static int width_sidebar = 230, height_sidebar = 230;
    public static final int width = 1400, height = 800;
    public static Map<String, Component> CurrState = new TreeMap<>();
    public HomePageGUI() {
        Font font = new Font("Tahoma", Font.BOLD, 16);

        //----------------------- GUI -------------------------//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setBackground(Color.RED);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(new Dimension(width, height));
        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("Home");
        this.setIconImage(new ImageIcon("src/IMG/Home.png").getImage());
        //----------------------------------------------------//


        //---------------------  Label -----------------------//
        LWelcome = new JLabel("");
        if(LoginGUI.WelcomeUsername.equals("admin")) {
            LWelcome.setText("Admin");
            LWelcome.setBounds(50, 20, 150, 50);
            LWelcome.setFont(new Font("Serif", Font.BOLD, 20));
        }
        else {
            LWelcome.setText(String.format("<html>Welcome, <br> %s</html>", LoginGUI.WelcomeUsername));
            LWelcome.setBounds(30, 16, 250, 50);
            LWelcome.setFont(new Font("Serif", Font.BOLD, 17));
        }
        icon = new ImageIcon("src/IMG/Admin.png");
        LWelcome.setIcon(icon);
        //----------------------------------------------------//

        //---------------------  Panel -----------------------//
        PSidebar1 = new JPanel();
        PSidebar1.setBounds(0, 0, 230, 80);
        PSidebar1.setBackground(new Color(51, 173, 255));
        PSidebar1.setLayout(null);

        PSidebar2 = new JPanel();
        PSidebar2.setBounds(0, 80, 230, height - 80);
        PSidebar2.setBackground(new Color(217, 217, 217));
        PSidebar2.setLayout(new BoxLayout(PSidebar2, BoxLayout.Y_AXIS));

                    //---------------Get Width & Height-------------------//
                    width_sidebar = PSidebar2.getWidth();
                    height_sidebar = PSidebar2.getHeight();
                    //----------------------------------------------------//

        PContent = new JPanel();
        PContent.setBackground(new Color(153, 235, 255));
        PContent.setBounds(width_sidebar, 0, width - width_sidebar, height);
        PContent.setLayout(new CardLayout());
        //----------------------------------------------------//


        //--------------------  Button ----------------------//
        int BHeight = 70;

        BAdmin = new JButton();
        BAdmin.setBounds(0, 0, 230, 80);
        BAdmin.setBackground(null);
        BAdmin.setFocusable(false);
        BAdmin.setOpaque(false);
        BAdmin.setContentAreaFilled(false);
        BAdmin.setBorderPainted(false);
        BAdmin.addActionListener(this);


        BLogout = new JButton("Logout");
        BLogout.setFont(new Font("Tahoma", Font.BOLD, 13));
        BLogout.setFocusable(false);
        int gap = 70;
        BLogout.setBounds(gap, height - 25, width_sidebar - gap * 2, 20);
        BLogout.setFocusable(false);
        BLogout.setBackground(new Color(217, 217, 217));
        BLogout.setBorder(null);
        icon = new ImageIcon("src/IMG/Logout.png");
        BLogout.setIcon(icon);
        BLogout.setHorizontalAlignment(JButton.LEFT);
        BLogout.addActionListener(this);

        Color Bcolor = Color.WHITE;
        BEmployeeMana = new JButton("Employee Management");
        BEmployeeMana.setFont(font);
        BEmployeeMana.setFocusable(false);
        BEmployeeMana.setMaximumSize(new Dimension(width_sidebar, BHeight));
        BEmployeeMana.setBackground(new Color(230, 230, 230));
        BEmployeeMana.setBorder(BorderFactory.createEtchedBorder());
        icon = new ImageIcon("src/IMG/Employee.png");
        BEmployeeMana.setIcon(icon);
        BEmployeeMana.setHorizontalAlignment(JButton.LEFT);
        BEmployeeMana.addActionListener(this);

        BProductMana = new JButton("Product Management");
        BProductMana.setFont(font);
        BProductMana.setFocusable(false);
        BProductMana.setMaximumSize(new Dimension(width_sidebar,BHeight));
        BProductMana.setBackground(new Color(230, 230, 230));
        BProductMana.setBorder(BorderFactory.createEtchedBorder());
        icon = new ImageIcon("src/IMG/Product.png");
        BProductMana.setIcon(icon);
        BProductMana.setHorizontalAlignment(JButton.LEFT);
        BProductMana.addActionListener(this);

        BOrderMana = new JButton("Order Management");
        BOrderMana.setFont(font);
        BOrderMana.setFocusable(false);
        BOrderMana.setMaximumSize(new Dimension(width_sidebar, BHeight));
        BOrderMana.setBackground(new Color(230, 230, 230));
        BOrderMana.setBorder(BorderFactory.createEtchedBorder());
        icon = new ImageIcon("src/IMG/Order.png");
        BOrderMana.setIcon(icon);
        BOrderMana.setHorizontalAlignment(JButton.LEFT);
        BOrderMana.addActionListener(this);

        BSaleMana = new JButton("Sale Management");
        BSaleMana.setFont(font);
        BSaleMana.setFocusable(false);
        BSaleMana.setMaximumSize(new Dimension(width_sidebar, BHeight));
        BSaleMana.setBackground(new Color(230, 230, 230));
        BSaleMana.setBorder(BorderFactory.createEtchedBorder());
        icon = new ImageIcon("src/IMG/Sale.png");
        BSaleMana.setIcon(icon);
        BSaleMana.setHorizontalAlignment(JButton.LEFT);
        BSaleMana.addActionListener(this);

        BStatistics = new JButton("Statistics");
        BStatistics.setFont(font);
        BStatistics.setFocusable(false);
        BStatistics.setMaximumSize(new Dimension(width_sidebar, BHeight));
        BStatistics.setBackground(new Color(230, 230, 230));
        BStatistics.setBorder(BorderFactory.createEtchedBorder());
        icon = new ImageIcon("src/IMG/Statistics.png");
        BStatistics.setIcon(icon);
        BStatistics.setHorizontalAlignment(JButton.LEFT);
        BStatistics.addActionListener(this);

        BSettings = new JButton("Settings");
        BSettings.setFont(font);
        BSettings.setFocusable(false);
        BSettings.setMaximumSize(new Dimension(width_sidebar, BHeight));
        BSettings.setBackground(new Color(230, 230, 230));
        BSettings.setBorder(BorderFactory.createEtchedBorder());
        icon = new ImageIcon("src/IMG/Settings.png");
        BSettings.setIcon(icon);
        BSettings.setHorizontalAlignment(JButton.LEFT);
        BSettings.addActionListener(this);
        //----------------------------------------------------//

        //----------------------------------------------------//
        //----------------------------------------------------//

        //--------------------Add component-------------------//
        PSidebar1.add(LWelcome);
        PSidebar1.add(BLogout);
        PSidebar1.add(BAdmin);

        if(LoginGUI.PositionOfUser.equals("Employee"))
        {
            PSidebar2.add(BOrderMana);
            PSidebar2.add(BSaleMana);
            PSidebar2.add(BStatistics);
        }
        if(LoginGUI.PositionOfUser.equals("Manager"))
        {
            PSidebar2.add(BEmployeeMana);
            PSidebar2.add(BProductMana);
            PSidebar2.add(BOrderMana);
            PSidebar2.add(BSaleMana);
            PSidebar2.add(BStatistics);
            PSidebar2.add(BSettings);
        }

        this.add(BLogout);
        this.add(PSidebar2);
        this.add(PSidebar1);
        this.add(PContent);
        this.setLocationRelativeTo(null);
        //----------------------------------------------------//
        this.setVisible(true);
        //System.out.println("Width: " + this.getWidth() + " | Height: " + this.getHeight());
    }


    //PContent.setBackground(new Color(179, 179, 179));
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton[] Buttons = {BEmployeeMana, BProductMana, BOrderMana, BSaleMana,BStatistics, BSettings};
        if(e.getSource() == BAdmin) {
            PContent.removeAll();
            PContent.revalidate();
            PContent.repaint();
            for(var x : Buttons)
            {
                x.setBackground(new Color(230, 230, 230));
            }
        }
        if (e.getSource() == BLogout) {
            try {
                int k = JOptionPane.showOptionDialog(null,
                        "Are you sure ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, null, null);
                if (k == 0) {
                    new LoginGUI();
                    //this.setVisible(false);
                    this.dispose();
                }

            } catch (Exception ex) {
                throw new RuntimeException();
            }
        }
        if (e.getSource() == BEmployeeMana) {
            PContent.removeAll();
            this.setTitle("Employee");
            EmployeeGUI emp = new EmployeeGUI();
            PContent.setLayout(new BorderLayout());
            PContent.add(emp);
            BEmployeeMana.setBackground(Color.WHITE);
            for(var x : Buttons)
            {
                if(x != BEmployeeMana) x.setBackground(new Color(230, 230, 230));
            }
            PContent.revalidate();
            PContent.repaint();
        }
        if (e.getSource() == BProductMana) {
            PContent.removeAll();
            this.setTitle("Product Management");
            ProductGUI prd = new ProductGUI();
            PContent.setLayout(new BorderLayout());
            PContent.add(prd);
            BProductMana.setBackground(Color.WHITE);
            for(var x : Buttons)
            {
                if(x != BProductMana) x.setBackground(new Color(230, 230, 230));
            }
            PContent.revalidate();
            PContent.repaint();
        }
        if (e.getSource() == BOrderMana) {
            PContent.removeAll();
            this.setTitle("Order Management");
            OrderGUI ord = new OrderGUI();
            PContent.setLayout(new BorderLayout());
            PContent.add(ord);
            BOrderMana.setBackground(Color.WHITE);
            for(var x : Buttons)
            {
                if(x != BOrderMana) x.setBackground(new Color(230, 230, 230));
            }
            PContent.revalidate();
            PContent.repaint();
        }
        if (e.getSource() == BSaleMana) {
            PContent.removeAll();
            this.setTitle("Sale Management");
            SaleGUI sal = new SaleGUI();
            PContent.setLayout(new BorderLayout());
            PContent.add(sal);
            BSaleMana.setBackground(Color.WHITE);
            for(var x : Buttons)
            {
                if(x != BSaleMana) x.setBackground(new Color(230, 230, 230));
            }
            PContent.revalidate();
            PContent.repaint();
        }
        if (e.getSource() == BStatistics) {
            PContent.removeAll();
            this.setTitle("Statistics");
            StatisticsGUI stat = new StatisticsGUI();
            PContent.setLayout(new BorderLayout());
            PContent.add(stat);
            BStatistics.setBackground(Color.WHITE);
            for(var x : Buttons)
            {
                if(x != BStatistics) x.setBackground(new Color(230, 230, 230));
            }
            PContent.revalidate();
            PContent.repaint();
        }
        if (e.getSource() == BSettings) {
            PContent.removeAll();
            this.setTitle("Settings");
            SettingsGUI set = new SettingsGUI();
            PContent.setLayout(new BorderLayout());
            PContent.add(set);
            BSettings.setBackground(Color.WHITE);
            for(var x : Buttons)
            {
                if(x != BSettings) x.setBackground(new Color(230, 230, 230));
            }
            PContent.revalidate();
            PContent.repaint();
        }
    }
}
