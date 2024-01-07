package DTO;

public class Sale {
    private String ID;
    private String Name;
    private String Type;
    private String Value;
    private String StartDate;
    private String EndDate;
    private String Status;
    public Sale(){}
    public Sale(String ID, String Name, String Type, String Value, String StartDate, String EndDate, String Status)
    {
        this.ID = ID;
        this.Name = Name;
        this.Type = Type;
        this.Value = Value;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Status = Status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
