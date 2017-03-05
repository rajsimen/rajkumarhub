package ccorp.android.bloodfinder.model;

/**
 * Created by rajkumar on 2/28/17.
 */

public class Donor {

    public String name;
    public String email;
    public String dob;
    public String mobileNo;
    public String bloodGroup;
    public String address;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Donor() {
    }

    /*public Donor(String name, String email, String dob, String bloodGroup,String mobileNo, String address) {
        this.name = name;
        this.email = email;
        this.dob=dob;
        this.mobileNo=mobileNo;
        this.bloodGroup=bloodGroup;
        this.address=address;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

