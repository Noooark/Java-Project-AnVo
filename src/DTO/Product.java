package DTO;

public class Product {
    private String ID;
    private String Name;
    private String Type;
    private String Price;
    private String Status;
    private String Quantity;
    private String StartDate, EndDate;
    private String NewPrice;
    public Product(){}
    public Product(String Price, String ID)
    {
        this.ID = ID;
        this.Price = Price;
    }
    public Product(String Quantity, String ID, String Name)
    {
        this.ID = ID;
        this.Quantity = Quantity;
    }
    public Product(String Name, String type, String price, String quantity, String status) {
        this.Name = Name;
        this.Type = type;
        this.Price = price;
        this.Quantity = quantity;
        this.Status = status;
    }
    public Product(String ID, String Name, String type, String price, String quantity, String status, String NewPrice,String start, String end) {
        this.ID = ID;
        this.Name = Name;
        this.Type = type;
        this.Price = price;
        this.Quantity = quantity;
        this.Status = status;
        this.StartDate = start;
        this.EndDate = end;
        this.NewPrice = NewPrice;
    }
    public Product(String ID, String Name, String type, String price, String quantity, String status) {
        this.ID = ID;
        this.Name = Name;
        this.Type = type;
        this.Price = price;
        this.Quantity = quantity;
        this.Status = status;
    }
    public Product(String ID, String Name, String type, String price, String quantity, String status, String ID1) {
        this.ID = ID;
        this.Name = Name;
        this.Type = type;
        this.Price = price;
        this.Quantity = quantity;
        this.Status = status;
        this.ID = ID1;
    }
    public String getID() {
        return ID;
    }

    public void setID(String IDProduct) {
        this.ID= IDProduct;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        Price = Price;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
    public String getStartDate() {
        return StartDate;
    }
    public String getEndDate() {
        return EndDate;
    }
    public String getNewPrice() {
        return NewPrice;
    }
    @Override
    public String toString() {
        return "Product{" +
                "IDProduct='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Price='" + Price + '\'' +
                ", Status='" + Status + '\'' +
                ", Quantity='" + Quantity + '\'' +
                '}';
    }
}
