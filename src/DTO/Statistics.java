package DTO;

public class Statistics {
    private String PaymentDate;
    private String TotalOrders;
    private String TotalIncome;

    public Statistics(String paymentDate, String totalOrders, String totalIncome) {
        PaymentDate = paymentDate;
        TotalOrders = totalOrders;
        TotalIncome = totalIncome;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public String getTotalOrders() {
        return TotalOrders;
    }

    public String getTotalIncome() {
        return TotalIncome;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public void setTotalOrders(String totalOrders) {
        TotalOrders = totalOrders;
    }

    public void setTotalIncome(String totalIncome) {
        TotalIncome = totalIncome;
    }
}
