package DTO;

public class Bill {
    private int IDBill;
    private String IDStaff;
    private String TotalMoney;
    private String Status;
    //card, cash
    private String PaymentMethod;
    private String BillDate;
    public Bill(){};

    public Bill(int IDBill, String IDStaff, String totalMoney, String paymentMethod, String billDate) {
        this.IDBill = IDBill;
        this.IDStaff = IDStaff;
        this.TotalMoney = totalMoney;
        this.PaymentMethod = paymentMethod;
        this.BillDate = billDate;
    }
    public Bill(String status, String totalMoney, String paymentMethod, String billDate, int IDBill)
    {
        this.Status = status;
        this.TotalMoney = totalMoney;
        this.PaymentMethod = paymentMethod;
        this.BillDate = billDate;
        this.IDBill = IDBill;
    }

    public int getIDBill() {
        return IDBill;
    }

    public void setIDBill(int IDBill) {
        this.IDBill = IDBill;
    }

    public String getIDStaff() {
        return IDStaff;
    }

    public void setIDStaff(String IDStaff) {
        this.IDStaff = IDStaff;
    }

    public String getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        TotalMoney = totalMoney;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }
}
