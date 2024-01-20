package DTO;
public class Invoice{
    private String ID, Creator, CustomerName, CreateTime, PaymentTime, Quantity, Total, Status, Note, PaymentDate;
    public Invoice(String ID, String Creator, String CustomerName, String CreateTime, String Status, String Note)
    {
        this.ID = ID;
        this.Creator = Creator;
        this.CustomerName = CustomerName;
        this.CreateTime = CreateTime;
        this.Status = Status;
        this.Note = Note;
    }
    public Invoice(String ID, String Creator, String CustomerName, String CreateTime, String PaymentTime, String Quantity, String Total, String Status, String Note, String PaymentDate)
    {
        this.ID = ID;
        this.Creator = Creator;
        this.CustomerName = CustomerName;
        this.CreateTime = CreateTime;
        this.PaymentTime = PaymentTime;
        this.Quantity = Quantity;
        this.Total = Total;
        this.Status = Status;
        this.Note = Note;
        this.PaymentDate = PaymentDate;
    }
    public Invoice(String ID, String Creator, String CustomerName, String CreateTime, String PaymentTime, String Quantity, String Total, String Status, String Note)
    {
        this.ID = ID;
        this.Creator = Creator;
        this.CustomerName = CustomerName;
        this.CreateTime = CreateTime;
        this.PaymentTime = PaymentTime;
        this.Quantity = Quantity;
        this.Total = Total;
        this.Status = Status;
        this.Note = Note;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getPaymentTime() {
        return PaymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        PaymentTime = paymentTime;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        Total = Total;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }
    public void setPaymentDate(String PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "ID='" + ID + '\'' +
                ", Creator='" + Creator + '\'' +
                ", CustomerName='" + CustomerName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", PaymentTime='" + PaymentTime + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", Total='" + Total + '\'' +
                ", Status='" + Status + '\'' +
                ", Note='" + Note + '\'' +
                '}';
    }
}
