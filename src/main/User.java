package main;

public class User {

    private int employeeId;
    private String firstName;
    private String lastName;

    private String userName;
    private String role;
    private String password;
    private Boolean admin;
    private String secretQ;
    private String secretQAns;
    private Boolean active;

    private Boolean deleteLastBooking;

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

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

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

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public Boolean getDeleteLastBooking() { return deleteLastBooking; }

    public void setDeleteLastBooking(Boolean deleteLastBooking) { this.deleteLastBooking = deleteLastBooking; }
}
