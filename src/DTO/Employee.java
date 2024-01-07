package DTO;

public class Employee {
    private String ID, Name, Gender, Birthday, Email, Phone, Position, Status;
    public Employee()
    {

    }
    public Employee(String Name, String Gender, String Birthday, String Email, String Phone, String Position, String Status)
    {
        this.Name = Name;
        this.Gender = Gender;
        this.Birthday = Birthday;
        this.Email = Email;
        this.Phone = Phone;
        this.Position = Position;
        this.Status = Status;
    }
    public Employee(String ID, String Name, String Gender, String Birthday, String Email, String Phone, String Position, String Status, String ID1)
    {
        this.ID = ID;
        this.Name = Name;
        this.Gender = Gender;
        this.Birthday = Birthday;
        this.Email = Email;
        this.Phone = Phone;
        this.Position = Position;
        this.Status = Status;
        this.ID = ID1;
    }
    public Employee(String ID, String Name, String Gender, String Birthday, String Email, String Phone, String Position, String Status)
    {
        this.ID = ID;
        this.Name = Name;
        this.Gender = Gender;
        this.Birthday = Birthday;
        this.Email = Email;
        this.Phone = Phone;
        this.Position = Position;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        Gender = Gender;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getMail() {
        return Email;
    }

    public void setMail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Birthday='" + Birthday + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Position='" + Position + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
