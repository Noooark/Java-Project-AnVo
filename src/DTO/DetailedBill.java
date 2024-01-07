package DTO;

public class DetailedBill {
    private int IDBill;
    private String IDProduct;
    private String NameProduct;
    private int Quantity;
    private double Cost;
    public DetailedBill(String IDProduct, int IDBill)
    {
        this.IDBill = IDBill;
        this.IDProduct = IDProduct;
    }

    public DetailedBill(int IDBill, String IDProduct, String nameProduct, int quantity, double cost) {
        this.IDBill = IDBill;
        this.IDProduct = IDProduct;
        NameProduct = nameProduct;
        Quantity = quantity;
        Cost = cost;
    }

    public int getIDBill() {
        return IDBill;
    }

    public void setIDBill(int IDBill) {
        this.IDBill = IDBill;
    }

    public String getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(String IDProduct) {
        this.IDProduct = IDProduct;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }
}
