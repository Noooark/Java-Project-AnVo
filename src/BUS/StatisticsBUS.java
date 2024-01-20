package BUS;

import DAO.StatisticsDAO;
import DTO.Statistics;

import java.util.List;

public class StatisticsBUS {
    StatisticsDAO dao = new StatisticsDAO();
    public int GetNumberInvoice() throws Exception{
        return dao.GetNumberInvoice();
    }
    public int GetTotalIncome() throws Exception{
        return dao.GetTotalIncome();
    }
    public int GetNumberEmployee() throws Exception{
        return dao.GetNumberEmployee();
    }
    public int GetNumberProduct() throws Exception{
        return dao.GetNumberProduct();
    }
    public List<Statistics> DrawChartData() throws Exception{
        return dao.DrawChartData();
    }
}
