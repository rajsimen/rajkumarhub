package ccorp.android.bloodfinder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ccorp.android.bloodfinder.model.Donor;

/**
 * Created by rajkumar on 2/28/17.
 */

public class DonarListAdapter extends RecyclerView.Adapter<DonarListAdapter.MyViewHolder> {

private List<Donor> donorList;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView donorname, ageandgroup, phone, donoremail;

        public MyViewHolder(View view){
            super(view);
        }

    }

    public DonarListAdapter(List<Donor> donorList){
        this.donorList=donorList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Donor donor=donorList.get(position);
        holder.donorname.setText(donor.getName());
        holder.ageandgroup.setText(donor.dob+" - "+donor.getBloodGroup());
        holder.donoremail.setText(donor.getEmail());
        holder.phone.setText(donor.getMobileNo());


    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }
}
