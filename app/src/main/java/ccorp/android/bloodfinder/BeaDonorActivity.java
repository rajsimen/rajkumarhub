package ccorp.android.bloodfinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import ccorp.android.bloodfinder.model.BeADonor;

/**
 * Created by rajkumar on 2/20/17.
 */

public class BeaDonorActivity extends Fragment {


    View view;
    private Button register,updateregister;
    private EditText userName,userEmailAdd,dob,bloodGroup,contactNo,address;
    private TextInputLayout userNameTextInputLayout,userEmailAddTextInputLayout,dobTextInputLayout,bloodGroupTextInputLayout,
            contactNoTextInputLayout,addressTextInputLayout;
    private TextView emailId;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private String userId;
    private Context context;
    SharedPreferences sPref;
    String email="proxy@gmail.com";
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private Bundle mSavedInstanceState;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       context = getActivity();
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email=user.getEmail();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("donors");



        // store app title to 'app_title' node
        mFirebaseInstance.getReference("BloodFinder").setValue("Donor Database");

       // System.out.print("ValueFromDataBase::::::::"+mFirebaseDatabase.getParent().toString());


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        mInflater=inflater;
        mContainer=container;
        mSavedInstanceState=savedInstanceState;

        callLayout(inflater,container,R.layout.bedonor,0);

        return view;

    }

    protected void callLayout(LayoutInflater inflater, ViewGroup container,int layout,int position){
        if (position==0){
        view=inflater.inflate(layout,container,false);
        getwidgets1();} else{
            view=inflater.inflate(layout,container,false);
        }
    }

    protected  void getwidgets2(){

        updateregister=(Button)view.findViewById(R.id.updateregister);
        updateregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Functionality Disable",Toast.LENGTH_LONG).show();
            }
        });
    }
    protected void getwidgets1(){

        register=(Button)view.findViewById(R.id.register_button);
        userNameTextInputLayout=(TextInputLayout)view.findViewById(R.id.inputlayoutName);

        dobTextInputLayout=(TextInputLayout)view.findViewById(R.id.inputlayoutdob);
        bloodGroupTextInputLayout=(TextInputLayout)view.findViewById(R.id.inputlayoutbloodgroup);
        contactNoTextInputLayout=(TextInputLayout)view.findViewById(R.id.inputlayoutconatctnumber);
        addressTextInputLayout=(TextInputLayout)view.findViewById(R.id.inputlayoutaddress);

        userName=(EditText)view.findViewById(R.id.username);
        emailId=(TextView) view.findViewById(R.id.emailaddress);
        emailId.setText(email);
        dob=(EditText)view.findViewById(R.id.dob);
        bloodGroup=(EditText)view.findViewById(R.id.bloodgroup);
        contactNo=(EditText)view.findViewById(R.id.contactnumber);
        address=(EditText)view.findViewById(R.id.address);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });


        dob.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    dob.setText(current);
                    dob.setSelection(sel < current.length() ? sel : current.length());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }





    private void validate(){

        boolean isValid=true;

        if (userName.getText().toString().isEmpty()){

            isValid=false;
        }

        if (dob.getText().toString().isEmpty()){
            isValid=false;
        }else {
            isValid = false;
        }



        if (bloodGroup.getText().toString().isEmpty()){
            isValid=false;
        }else{
            isValid=true;
        }

        if (contactNo.getText().toString().isEmpty() && !isValidMobile(contactNo.getText().toString())){
            isValid=false;
        }else{
            isValid= true;
        }

        if (address.getText().toString().isEmpty()){
            isValid=false;
        }else{
            isValid=true;
        }

        if (isValid) {
            //Toast.makeText(getActivity(), "Registered", Toast.LENGTH_LONG).show();
            createUser(userName.getText().toString(), emailId.getText().toString(), dob.getText().toString(),
                    bloodGroup.getText().toString(), contactNo.getText().toString(), address.getText().toString());
        }else{
            Toast.makeText(getActivity(), "All Fields are Mandatory", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email,String dob,String bloodGroup,String contactNo,String address) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        BeADonor beADonor = new BeADonor(name, email,dob,bloodGroup,contactNo,address);

        mFirebaseDatabase.child(userId).setValue(beADonor);

        if(!userId.isEmpty()){
            callLayout(mInflater,mContainer,R.layout.bedonor1,1);}



        //addUserChangeListener();
    }




}
