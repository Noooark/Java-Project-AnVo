package GUI;

import BUS.StatisticsBUS;
import DTO.Statistics;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class StatisticsGUI extends JPanel implements ActionListener {
    JPanel PChartDate;
    JPanel PNumberInvoice, PIncome, PNumberEmp, PNumberProduct, P1, P2, P3, P4;
    JDatePicker StartDate, EndDate;
    JLabel LStoreRevenue, LStart, LEnd;
    JLabel LNumberInvoice, LIncome, LNumberEmp, LNumberProduct,
            LNumInv, LInc, LNumEmp, LNumCus;
    JButton BUpdate;
    ImageIcon Icon;
    ArrayList<String[]> DataChart = new ArrayList<>();
    List<String> DateOnChard = new ArrayList<>();
    StatisticsBUS stabus = new StatisticsBUS();
    public StatisticsGUI() {
        //----------------------------------------------------//
        this.setLayout(null);
        //----------------------------------------------------//

        //----------------------------------------------------//
        PNumberInvoice = new JPanel();
        PNumberInvoice.setLayout(null);
        PNumberInvoice.setBounds(50, 70, 260, 100);
        PNumberInvoice.setBackground(new Color(255, 255, 255));
        PNumberInvoice.setBorder(BorderFactory.createEtchedBorder(0));
            P1 = new JPanel();
            P1.setLayout(new BorderLayout());
            P1.setBounds(0, 0, 100, 100);
            P1.setBackground(new Color(51, 153, 255));
            Icon = new ImageIcon("src/IMG/Statistics/NumberInvoice.png");
            P1.add(new JLabel(Icon), BorderLayout.CENTER);
            PNumberInvoice.add(P1);

            LNumberInvoice = new JLabel("Number Invoice");
            LNumberInvoice.setFont(new Font("Tahoma", Font.PLAIN, 16));
            LNumberInvoice.setForeground(new Color(0, 0, 0));
            LNumberInvoice.setBounds(110, 10, 150, 50);
            PNumberInvoice.add(LNumberInvoice);

            try
            {
                int NumInv = stabus.GetNumberInvoice();
                LNumInv = new JLabel(String.valueOf(NumInv));
                LNumInv.setFont(new Font("Tahoma", Font.PLAIN, 16));
                LNumInv.setForeground(new Color(0, 0, 0));
                LNumInv.setBounds(110, 50, 150, 50);
                PNumberInvoice.add(LNumInv);
            } catch (Exception e) {
                e.printStackTrace();
            }

        PIncome = new JPanel();
        PIncome.setLayout(null);
        PIncome.setBounds(PNumberInvoice.getX() + 270, PNumberInvoice.getY(), 260, 100);
        PIncome.setBackground(new Color(255, 255, 255));
        PIncome.setBorder(BorderFactory.createEtchedBorder(0));
            P2 = new JPanel();
            P2.setLayout(new BorderLayout());
            P2.setBounds(0, 0, 100, 100);
            P2.setBackground(new Color(51, 153, 255));
            Icon = new ImageIcon("src/IMG/Statistics/Income.png");
            P2.add(new JLabel(Icon), BorderLayout.CENTER);
            PIncome.add(P2);

            LIncome = new JLabel("Total Income");
            LIncome.setFont(new Font("Tahoma", Font.PLAIN, 16));
            LIncome.setForeground(new Color(0, 0, 0));
            LIncome.setBounds(110, 10, 150, 50);
            PIncome.add(LIncome);

            try
            {
                int Inc = stabus.GetTotalIncome();
                LInc = new JLabel(String.valueOf(Inc));
                LInc.setFont(new Font("Tahoma", Font.PLAIN, 16));
                LInc.setForeground(new Color(0, 0, 0));
                LInc.setBounds(110, 50, 150, 50);
                PIncome.add(LInc);
            } catch (Exception e) {
                e.printStackTrace();
            }

        PNumberEmp = new JPanel();
        PNumberEmp.setLayout(null);
        PNumberEmp.setBounds(PIncome.getX() + 270, PIncome.getY(), 260, 100);
        PNumberEmp.setBackground(new Color(255, 255, 255));
        PNumberEmp.setBorder(BorderFactory.createEtchedBorder(0));
            P3 = new JPanel();
            P3.setLayout(new BorderLayout());
            P3.setBounds(0, 0, 100, 100);
            P3.setBackground(new Color(51, 153, 255));
            Icon = new ImageIcon("src/IMG/Statistics/NumberEmp.png");
            P3.add(new JLabel(Icon), BorderLayout.CENTER);
            PNumberEmp.add(P3);

            LNumberEmp = new JLabel("Number Employee");
            LNumberEmp.setFont(new Font("Tahoma", Font.PLAIN, 16));
            LNumberEmp.setForeground(new Color(0, 0, 0));
            LNumberEmp.setBounds(110, 10, 150, 50);
            PNumberEmp.add(LNumberEmp);

            try
            {
                int NumEmp = stabus.GetNumberEmployee();
                LNumEmp = new JLabel(String.valueOf(NumEmp));
                LNumEmp.setFont(new Font("Tahoma", Font.PLAIN, 16));
                LNumEmp.setForeground(new Color(0, 0, 0));
                LNumEmp.setBounds(110, 50, 150, 50);
                PNumberEmp.add(LNumEmp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        PNumberProduct = new JPanel();
        PNumberProduct.setLayout(null);
        PNumberProduct.setBounds(PNumberEmp.getX() + 270, PNumberEmp.getY(), 260, 100);
        PNumberProduct.setBackground(new Color(255, 255, 255));
        PNumberProduct.setBorder(BorderFactory.createEtchedBorder(0));
            P4 = new JPanel();
            P4.setLayout(new BorderLayout());
            P4.setBounds(0, 0, 100, 100);
            P4.setBackground(new Color(51, 153, 255));
            Icon = new ImageIcon("src/IMG/Statistics/NumberProduct.png");
            P4.add(new JLabel(Icon), BorderLayout.CENTER);
            PNumberProduct.add(P4);

            LNumberProduct = new JLabel("Number Product");
            LNumberProduct.setFont(new Font("Tahoma", Font.PLAIN, 16));
            LNumberProduct.setForeground(new Color(0, 0, 0));
            LNumberProduct.setBounds(110, 10, 150, 50);
            PNumberProduct.add(LNumberProduct);

            try
            {
                int NumPrd = stabus.GetNumberProduct();
                LNumCus = new JLabel(String.valueOf(NumPrd));
                LNumCus.setFont(new Font("Tahoma", Font.PLAIN, 16));
                LNumCus.setForeground(new Color(0, 0, 0));
                LNumCus.setBounds(110, 50, 150, 50);
                PNumberProduct.add(LNumCus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //----------------------------------------------------//

        //----------------------------------------------------//
        LStoreRevenue = new JLabel("Store Revenue");
        LStoreRevenue.setFont(new Font("Tahoma", Font.BOLD, 20));
        LStoreRevenue.setForeground(new Color(0, 0, 0));
        LStoreRevenue.setBounds(525, 250, 150, 50);
        //----------------------------------------------------//

        //----------------------------------------------------//
        PChartDate = new JPanel();
        PChartDate.setLayout(null);
        PChartDate.setBounds(50, 772, 1170 - 60,29);
        //PChartDate.setBackground(new Color(0, 0, 0));

        LocalDate CurrDate = LocalDate.now();
        DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("MM-dd");
        String FormatDate = CurrDate.format(Formatter);
        //for(int i = 10; i <= 1080; i+=60)
        for(int i = 17; i >= 0; i--)
        {
            LocalDate Date = LocalDate.now().minusDays(i);
            String DateStr = Date.format(Formatter);
            DateOnChard.add(DateStr);
        }
        //for(int i = 1030; i >= 10; i-=60)
        int j = 1030;
        for(int i = 17; i >= 0; i--)
        {
            JLabel LDate = new JLabel(DateOnChard.get(i));
            LDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
            LDate.setForeground(new Color(0, 0, 0));
            LDate.setBounds(j, 0, 50, 30);
            PChartDate.add(LDate);
            j-=60;
        }
        StartDate = getjDatePicker(PNumberEmp.getX(), PNumberEmp.getY() - 40, 200, 27);
        LStart = new JLabel("Start Date");
        LStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LStart.setForeground(new Color(0, 0, 0));
        LStart.setBounds(PNumberEmp.getX(), PNumberEmp.getY() - 77, 150, 50);

        EndDate = getjDatePicker(PNumberEmp.getX() + 270, PNumberEmp.getY() - 40, 200, 27);
        LEnd = new JLabel("End Date");
        LEnd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        LEnd.setForeground(new Color(0, 0, 0));
        LEnd.setBounds(PNumberEmp.getX() + 270, PNumberEmp.getY() - 77, 150, 50);

        LocalDate Start = LocalDate.parse(EndDate.getModel().getValue().toString()).minusDays(17);
        StartDate.getModel().setDate(Start.getYear(), Start.getMonthValue() - 1, Start.getDayOfMonth());

        EndDate.getModel().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName().equals("value"))
                {
                    LocalDate Start = LocalDate.parse(EndDate.getModel().getValue().toString()).minusDays(17);
                    StartDate.getModel().setDate(Start.getYear(), Start.getMonthValue() - 1, Start.getDayOfMonth());
                }
            }
        });
        try {
            ChartData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------------------------//

        //----------------------------------------------------//
        this.add(PChartDate);
        this.add(PNumberInvoice);
        this.add(PIncome);
        this.add(PNumberEmp);
        this.add(PNumberProduct);
        this.add(LStoreRevenue);
        this.add((Component) StartDate);
        this.add(LStart);
        this.add((Component) EndDate);
        this.add(LEnd);
        //----------------------------------------------------//
    }
    public void ChartData() throws Exception {
        List<Statistics> listSta = stabus.DrawChartData();
        for(Statistics st : listSta)
        {
            String[] PairData = {st.getPaymentDate().substring(5),
                    st.getTotalIncome()};
            DataChart.add(PairData);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = 1080;
        int height = 480;
        int barWidth = 60;

        int startX = 50;
        int startY = 290;

        //------------------ Draw outline ----------------------//
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(startX, startY, width, height + 1);
        //-----------------------------------------------------//
        int j = 0;
        for(int i = 0; i <= 17; i++)
        {
            if(DataChart.get(j)[0].equals(DateOnChard.get(i)))
            {
                int value = Integer.parseInt(DataChart.get(j)[1]);
                int barHeight = (int) ((double) value / getMaxValue() * height);
                g2d.setColor(new Color(51, 153, 255));
                g2d.fillRect(startX + (i) * barWidth, startY + height - barHeight, barWidth, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX + (i) * barWidth, startY + height - barHeight, barWidth, barHeight);
                j++;
            }
            else continue;
            /*
            else
            {
                int value = 0;
                int barHeight = (int) ((double) value / getMaxValue() * height);
                g2d.setColor(new Color(51, 153, 255));
                g2d.fillRect(startX + i * 60, startY + height - barHeight, barWidth, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(startX * i * 60, startY + height - barHeight, barWidth, barHeight);
                startX += barWidth;
            }
             */
        }
    }
    private int getMaxValue() {
        int max = Integer.MIN_VALUE;
        for (String[] pair : DataChart){
            if (Integer.parseInt(pair[1]) > max) {
                max = Integer.parseInt(pair[1]);
            }
        }
        return max;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

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
}
