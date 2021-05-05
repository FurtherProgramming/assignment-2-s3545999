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
                Boolean admin, String secretQ, String secretQAns)
    {
        this.fName = fname;
        this.lName = lname;
        this.userName = userName;
        this.password = password;
        this.admin = admin;
        this.secretQ = secretQ;
        this.secretQAns = secretQAns;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public String getSecretQ() {
        return secretQ;
    }

    public String getSecretQAns() {
        return secretQAns;
    }
}
