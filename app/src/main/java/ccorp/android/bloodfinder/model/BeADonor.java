package ccorp.android.bloodfinder.model;

/**
 * Created by rajkumar on 2/23/17.
 */

public class BeADonor {

    public String name;
    public String email;
    public String dob;
    public String mobileNo;
    public String bloodGroup;
    public String address;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public BeADonor() {
    }

    public BeADonor(String name, String email, String dob, String bloodGroup,String mobileNo, String address) {
        this.name = name;
        this.email = email;
        this.dob=dob;
        this.mobileNo=mobileNo;
        this.bloodGroup=bloodGroup;
        this.address=address;
    }
}
