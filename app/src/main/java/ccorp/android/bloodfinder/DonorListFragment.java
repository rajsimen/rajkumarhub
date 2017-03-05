package ccorp.android.bloodfinder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ccorp.android.bloodfinder.model.Donor;

/**
 * Created by rajkumar on 3/1/17.
 */

public class DonorListFragment  extends Fragment{

    private View view;
    private RecyclerView donorListView;
    private DonarListAdapter donorListAdapter;
    List<Donor> donorList=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //System.out.print("Values from Fragment"+"Passed1");
        Log.v("Values from Fragment","Passed1");


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Values from Fragment","Passed1");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.search_screen,container,false);

        //getwidget();
        return view;
    }


    protected void getData(){
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("donors");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Donor donor=dataSnapshot1.getValue(Donor.class);
                    donorList.add(donor);
                    System.out.print("Values from FDatabase"+donor.getMobileNo());
                    Log.v("Values from FDatabase",donor.getMobileNo());
                    Log.v("Size of List",String.valueOf(donorList.size()));
                }
                //donorListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error",databaseError.getMessage());
                Log.v("Values from Fragment","failed");

            }
        });
    }

    protected void getwidget(){

        getData();
        Log.v("Size of List:!",String.valueOf(donorList.size()));
       donorListView=(RecyclerView)view.findViewById(R.id.my_recycler_view);
        donorListAdapter=new DonarListAdapter(donorList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        donorListView.setLayoutManager(mLayoutManager);
        donorListView.setItemAnimator(new DefaultItemAnimator());
        donorListView.setAdapter(donorListAdapter);



    }


}
