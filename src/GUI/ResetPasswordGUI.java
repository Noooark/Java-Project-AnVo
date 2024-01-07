package GUI;

import BUS.LoginBUS;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.UUID;

import javax.mail.*;
public class ResetPasswordGUI extends JFrame implements ActionListener{
    JLabel LGmail, LPassword, LConfirmPassword, LResetPwd, LEnterCode, LRegister, LUsername;
    JButton BBack, BContinue, BReset;
    static JButton BSendCode;
    JTextField TFGmail, TFCode, TFUsername;
    JPasswordField TFPassword, TFConfirmPassword;
    ImageIcon Icon;
    static String CodeMatched;
    Timer timer;
    int countdown;
    public ResetPasswordGUI()
    {
        //----------------------------------------------------//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Reset Password");
        this.setLayout(null);
        this.setSize(550, 350);
        this.setResizable(false);
        //----------------------------------------------------//
        LPassword = new JLabel("Password");
        LPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LPassword.setForeground(new Color(0, 0, 0));
        LPassword.setBounds(40, 50, 150, 50);
        TFPassword = new JPasswordField();
        TFPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        TFPassword.setForeground(new Color(0, 0, 0));
        TFPassword.setBackground(new Color(255, 255, 255));
        TFPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 170, 255)));
        TFPassword.setBounds(LPassword.getX(), LPassword.getY() + 45, 260, 30);

        LConfirmPassword = new JLabel("Confirm Password");
        LConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LConfirmPassword.setForeground(new Color(0, 0, 0));
        LConfirmPassword.setBounds(TFPassword.getX(), TFPassword.getY() + 35, 150, 50);
        TFConfirmPassword = new JPasswordField();
        TFConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        TFConfirmPassword.setForeground(new Color(0, 0, 0));
        TFConfirmPassword.setBackground(new Color(255, 255, 255));
        TFConfirmPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 170, 255)));
        TFConfirmPassword.setBounds(LConfirmPassword.getX(), LConfirmPassword.getY() + 45, 260, 30);

        BReset = new JButton("Register");
        BReset.setFont(new Font("Tahoma", Font.BOLD, 12));
        BReset.setForeground(Color.BLACK);
        BReset.setBackground(Color.WHITE);
        BReset.setFocusable(false);
        BReset.setBounds(TFConfirmPassword.getX() + 144, TFConfirmPassword.getY() + 45, 180, 40);
        BReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TFPassword.getText().equals(TFConfirmPassword.getText()))
                {
                    LoginBUS loginBUS = new LoginBUS();
                    try {
                        loginBUS.ResetPassword(TFGmail.getText(), TFPassword.getText());
                        JOptionPane.showMessageDialog(null, "Reset password successfully");
                        new LoginGUI();
                        dispose();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Password and confirm password are not matched");
                }
            }
        });
        //----------------------------------------------------//
        Icon = new ImageIcon("src/IMG/Back.png");
        BBack = new JButton("Back");
        BBack.setIcon(Icon);
        BBack.setFont(new Font("Tahoma", Font.PLAIN, 13));
        BBack.setForeground(Color.BLACK);
        BBack.setBackground(Color.WHITE);
        BBack.setBounds(450, 10, 60, 30);
        BBack.setFocusable(false);
        BBack.setBorder(null);
        BBack.addActionListener(this);

        LResetPwd = new JLabel("Reset Password");
        LResetPwd.setFont(new Font("Tahoma", Font.BOLD, 25));
        LResetPwd.setForeground(new Color(0, 0, 0));
        LResetPwd.setBounds(40, 10, 250, 50);

        LGmail = new JLabel("Gmail");
        LGmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LGmail.setForeground(new Color(0, 0, 0));
        LGmail.setBounds(40, 50, 150, 50);
        TFGmail = new JTextField();
        TFGmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        TFGmail.setForeground(new Color(0, 0, 0));
        TFGmail.setBackground(new Color(255, 255, 255));
        TFGmail.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 170, 255)));
        TFGmail.setBounds(LGmail.getX(), LGmail.getY() + 45, 260, 30);

        BSendCode = new JButton("Send Code");
        BSendCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
        BSendCode.setForeground(Color.BLACK);
        BSendCode.setBackground(Color.WHITE);
        BSendCode.setBounds(TFGmail.getX() + 270, TFGmail.getY(), 110, 30);
        BSendCode.setFocusable(false);
        BSendCode.addActionListener(this);
        BSendCode.setBorder(null);

        LEnterCode = new JLabel("Enter Code");
        LEnterCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LEnterCode.setForeground(new Color(0, 0, 0));
        LEnterCode.setBounds(40, 140, 160, 50);
        TFCode = new JTextField();
        TFCode.setFont(new Font("Tahoma", Font.PLAIN, 14));
        TFCode.setForeground(new Color(0, 0, 0));
        TFCode.setBackground(new Color(255, 255, 255));
        TFCode.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 170, 255)));
        TFCode.setBounds(LEnterCode.getX(), LEnterCode.getY() + 45, 260, 30);

        BContinue = new JButton("Continue");
        BContinue.setFont(new Font("Tahoma", Font.PLAIN, 15));
        BContinue.setForeground(Color.BLACK);
        BContinue.setBackground(Color.WHITE);
        BContinue.setBounds(TFCode.getX() + 270, TFCode.getY(), 110, 30);
        BContinue.setFocusable(false);
        BContinue.setBorder(null);
        BContinue.addActionListener(this);

        //----------------------------------------------------//
        this.add(BBack);
        this.add(LResetPwd);
        this.add(LGmail);
        this.add(TFGmail);
        this.add(BSendCode);
        this.add(LEnterCode);
        this.add(TFCode);
        this.add(BContinue);

        //----------------------------------------------------//

        //----------------------------------------------------//
        this.getContentPane().setBackground(Color.WHITE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //----------------------------------------------------//
    }
    public String GenerateID() {
        UUID IDBill = UUID.randomUUID();
        return IDBill.toString();
    }
    public void SendingCountdown()
    {
        countdown = 10;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;
                if (countdown >= 0) {
                    BSendCode.setText("Countdown: " + countdown);
                } else {
                    BSendCode.setEnabled(true);
                    BSendCode.setText("Start Countdown");
                    timer.stop();
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BBack)
        {
            new LoginGUI();
            this.dispose();
        }
        if(e.getSource() == BSendCode)
        {
            countdown = 10;
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    countdown--;
                    if (countdown >= 0) {
                        BSendCode.setText("Sent (" + countdown + "s)");
                    } else {
                        BSendCode.setEnabled(true);
                        BSendCode.setText("Send Code");
                        timer.stop();
                    }
                }
            });
            BSendCode.setEnabled(false);
            CodeMatched = GenerateID();
            //System.out.println(CodeMatched);
            new SendMail(TFGmail.getText());
            timer.start();
        }
        if(e.getSource() == BContinue)
        {
            if(TFCode.getText().equals(CodeMatched))
            {
                this.remove(BSendCode);
                this.remove(TFGmail);
                this.remove(LGmail);
                this.remove(LEnterCode);
                this.remove(TFCode);
                this.remove(BContinue);
                this.repaint();
                this.add(LPassword);
                this.add(TFPassword);
                this.add(LConfirmPassword);
                this.add(TFConfirmPassword);
                this.add(BReset);
                this.repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Wrong code. Please try again!");
            }
        }
    }
}
