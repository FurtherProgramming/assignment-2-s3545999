package main;



public class User {
    private int employeeID;
    private String fName;
    private String lName;

    private String userName;

    private String password;
    private Boolean admin;
    private String secretQ;
    private String secretQAns;

    public User(String fname, String lname, String userName, String password,
                String secretQ, String secretQAns)
    {
        this.fName = fname;
        this.lName = lname;
        this.userName = userName;
        this.password = password;
        this.admin = false;
        this.secretQ = secretQ;
//        this.secretQAns = secretQAns;
    }
    public User()
    {

    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getSecretQ() {
        return secretQ;
    }

    public void setSecretQ(String secretQ) {
        this.secretQ = secretQ;
    }

    public String getSecretQAns() {
        return secretQAns;
    }

}
