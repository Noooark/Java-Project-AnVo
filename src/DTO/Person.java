package DTO;

public class Person {
    private String Name;
    private String Sex;
    private String Birthday;
    private String PhoneNumber;
    public Person(String name, String sex, String phonenumber){}
    public Person(String name, String sex, String birthday, String phonenumber)
    {
        this.Name = name;
        this.Sex = sex;
        this.Birthday = birthday;
        this.PhoneNumber = phonenumber;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
