package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SettingsGUI extends JPanel implements ActionListener {
    JLabel LName, LID, LCredit;
    public SettingsGUI()
    {
        //----------------------------------------------------//
        this.setLayout(null);
        this.setVisible(true);
        //----------------------------------------------------//

        //----------------------------------------------------//
        LCredit = new JLabel("This project was made by:");
        LCredit.setBounds(50, 20, 600, 50);
        LCredit.setFont(new Font("Tahoma", Font.BOLD, 20));

        LName = new JLabel("Vo Nguyen Hoang An");
        LName.setBounds(50, 50, 600, 50);
        LName.setFont(new Font("Tahoma", Font.PLAIN, 20));

        LID = new JLabel("23IT.EB001");
        LID.setBounds(50, 80, 600, 50);
        LID.setFont(new Font("Tahoma", Font.PLAIN, 20));
        //----------------------------------------------------//

        //----------------------------------------------------//
        this.add(LCredit);
        this.add(LName);
        this.add(LID);
        //----------------------------------------------------//
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
