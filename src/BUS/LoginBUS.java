package BUS;

public class LoginBUS {
    DAO.LoginDAO dao = new DAO.LoginDAO();
    public Boolean AddAccount(String usr, String pass, String gmail, String position) throws Exception {
        return dao.AddAccount(usr, pass, gmail, position);
    }
    public Boolean DeleteAccount(String usr) throws Exception {
        return dao.DeleteAccount(usr);
    }
    public Boolean LoginCheck(String usr, String pass) throws Exception {
        return dao.LoginCheck(usr, pass);
    }
    public String GetPosition(String usr, String pass) throws Exception {
        return dao.GetPosition(usr, pass);
    }
    public void ResetPassword(String usr, String pass) throws Exception {
        dao.ResetPassword(usr, pass);
    }
}
