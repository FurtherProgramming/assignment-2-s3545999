package main;

public class Booking {

    private String Date;
    private String TableNumber;
    private Boolean AdminAcc;

    Booking(){

    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTableNumber() {
        return TableNumber;
    }

    public void setTableNumber(String tableNumber) {
        TableNumber = tableNumber;
    }

    public Boolean getAdminAcc() {
        return AdminAcc;
    }

    public void setAdminAcc(Boolean adminAcc) {
        AdminAcc = adminAcc;
    }

}
