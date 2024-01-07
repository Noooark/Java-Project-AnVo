package GUI;

import BUS.LoginBUS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginGUI extends JFrame implements ActionListener{
    JLabel LSignin, LUsername, LPassword;
    JButton BLogin, BForgotPassword;
    JTextField TFUsername;
    JPasswordField TFPassword;
    Font font = new Font("Tahoma", Font.BOLD, 15);
    String inpusername, inppassword;
    LoginBUS logbus = new LoginBUS();
    ImageIcon Icon;
    static String WelcomeUsername;
    static String PositionOfUser;
    public LoginGUI()
    {
        //----------------------------------------------------//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Login");
        this.setLayout(null);
        this.setSize(550, 350);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("src/IMG/Login.png").getImage());
        //----------------------------------------------------//

        LSignin = new JLabel("Sign in");
        LSignin.setFont(new Font("Tahoma", Font.BOLD, 30));
        LSignin.setForeground(new Color(0, 0, 0));
        LSignin.setBounds(50, 20, 150, 50);

        LUsername = new JLabel("Username");
        LUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LUsername.setForeground(new Color(0, 0, 0));
        LUsername.setBounds(50, 80, 150, 50);
        TFUsername = new JTextField();
        TFUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
        TFUsername.setForeground(new Color(0, 0, 0));
        TFUsername.setBackground(new Color(255, 255, 255));
        TFUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 170, 255)));
        TFUsername.setBounds(LUsername.getX(), LUsername.getY() + 45, 260, 30);

        LPassword = new JLabel("Password");
        LPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LPassword.setForeground(new Color(0, 0, 0));
        LPassword.setBounds(TFUsername.getX(), TFUsername.getY() + 35, 150, 50);
        TFPassword = new JPasswordField();
        TFPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        TFPassword.setForeground(new Color(0, 0, 0));
        TFPassword.setBackground(new Color(255, 255, 255));
        TFPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 170, 255)));
        TFPassword.setBounds(LPassword.getX(), LPassword.getY() + 45, 260, 30);

        BLogin = new JButton("Login");
        BLogin.setFont(font);
        BLogin.setForeground(Color.BLACK);
        //BLogin.setBackground(new Color(26, 140, 255));
        BLogin.setBackground(Color.WHITE);
        BLogin.setFocusable(false);
        BLogin.setBounds(TFPassword.getX() + 144, TFPassword.getY() + 55, 180, 40);
        BLogin.addActionListener(this);

        BForgotPassword = new JButton("Forgot password?");
        BForgotPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
        BForgotPassword.setForeground(Color.BLACK);
        //BForgotPassword.setBackground(new Color(26, 140, 255));
        BForgotPassword.setBackground(Color.WHITE);
        BForgotPassword.setFocusable(false);
        BForgotPassword.setBounds(BLogin.getX(), BLogin.getY() + 50, 180, 20);
        BForgotPassword.setBorder(null);
        BForgotPassword.addActionListener(this);
        BForgotPassword.setFocusPainted(false);
        BForgotPassword.setOpaque(false);

        this.add(LSignin);
        this.add(LUsername);
        this.add(LPassword);
        this.add(TFUsername);
        this.add(TFPassword);
        this.add(BLogin);
        this.add(BForgotPassword);

        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setVisible(true);
        this.getRootPane().setDefaultButton(BLogin);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BLogin)
        {
            inpusername = TFUsername.getText();
            inppassword = TFPassword.getText();
            try {
                if(logbus.LoginCheck(inpusername, inppassword))
                {
                    WelcomeUsername = inpusername;
                    PositionOfUser = logbus.GetPosition(inpusername, inppassword);
                    this.dispose();
                    new HomePageGUI();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Login failed");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        else if(e.getSource() == BForgotPassword)
        {
            this.setVisible(false);
            new ResetPasswordGUI();
        }
    }
}
