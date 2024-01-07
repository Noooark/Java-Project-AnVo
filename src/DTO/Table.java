package DTO;
public class Table {
    String ID, Name, Status;

    public Table(){}
    public Table(String ID, String name, String status) {
        this.ID = ID;
        this.Name = name;
        this.Status = status;
    }
    public Table(String Name, String Status){
        this.Name = Name;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Table{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
