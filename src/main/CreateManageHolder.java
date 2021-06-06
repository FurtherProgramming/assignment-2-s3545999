package main;

public final class CreateManageHolder {

    private User user;
    private Boolean newAccount;
    private Boolean admin;

    private final static CreateManageHolder INSTANCE = new CreateManageHolder();

    private CreateManageHolder() {}

    public static CreateManageHolder getInstance() {
        return INSTANCE;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Boolean getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(Boolean newAccount) {
        this.newAccount = newAccount;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
