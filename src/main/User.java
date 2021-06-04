package main;



public class User {

    private Integer employeeId;
    private String firstName;
    private String lastName;

    private String userName;

    private String password;
    private Boolean admin;
    private String secretQ;
    private String secretQAns;

    public User(String Fname, String lname, String userName, String password,
                String secretQ, String secretQAns)
    {
        this.firstName = Fname;
        this.lastName = lname;
        this.userName = userName;
        this.password = password;
        this.admin = false;
        this.secretQ = secretQ;
        this.secretQAns = secretQAns;
    }
    public User()
    {

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String Fname)
    {
        this.firstName = Fname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lName) {
        this.lastName = lName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecretQAns(String secretQAns) {
        this.secretQAns = secretQAns;
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
