package com.example.precious_lord.aedc_cms;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ComplaintRecyclerAdapter extends RecyclerView.Adapter<ComplaintRecyclerAdapter.ComplaintViewHolder> {

    private ArrayList<Complaint> listComplaint;
    public ImageView overflow;
    private Context mContext;
//    private ArrayList<Complaint> nFilteredList;

    public ComplaintRecyclerAdapter(ArrayList<Complaint> listComplaint, Context mContext) {
        this.listComplaint = listComplaint;
        this.mContext = mContext;
//        this.nFilteredList = listComplaint;
    }

    public ComplaintRecyclerAdapter(){

    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView dbEmail;
        public TextView dbLocation;
        public TextView dbCategory;
        public TextView dbSubject;
        public TextView dbMessage;
        public TextView dbReply;
        public ImageView overflow;

        private final Context context;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            dbEmail = itemView.findViewById(R.id.dbemail);
            dbLocation = itemView.findViewById(R.id.dblocation);
            dbCategory = itemView.findViewById(R.id.dbcategory);
            dbSubject = itemView.findViewById(R.id.dbsubject);
            dbMessage = itemView.findViewById(R.id.dbmessage);
            dbReply = itemView.findViewById(R.id.dbReply);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {
//            Snackbar.make(v, "COMPLAINT BY " + dbEmail.getText(), Snackbar.LENGTH_SHORT).show();
            final Intent intent;

//            intent = new Intent(context, EditComplaint.class);
//            context.startActivity(intent);

            intent = EditComplaint.makeIntent(context, dbEmail.getText().toString(), dbLocation.getText().toString(),
                    dbCategory.getText().toString(), dbSubject.getText().toString(), dbMessage.getText().toString(),
                    dbReply.getText().toString());
            context.startActivity(intent);

//            Intent viewIntent = ComplaintHistory.makeIntent(ComplaintActivity.this, conf_email.getText().toString());
//            startActivity(viewIntent);
        }
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_user_complaints, parent, false);
        return new ComplaintViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ComplaintViewHolder holder, int position){
        holder.dbEmail.setText(listComplaint.get(position).getEmail());
        holder.dbLocation.setText(listComplaint.get(position).getLocation());
        holder.dbCategory.setText(listComplaint.get(position).getCategory());
        holder.dbSubject.setText(listComplaint.get(position).getComplaint_nature());
        holder.dbMessage.setText(listComplaint.get(position).getComplaint_detail());
        holder.dbReply.setText(listComplaint.get(position).getComplaint_reply());

    }

    @Override
    public int getItemCount() {
        return listComplaint.size();
    }

    public void setFilter(ArrayList<Complaint> newList){
        listComplaint = new ArrayList<>();
        listComplaint.addAll(newList);
        notifyDataSetChanged();
    }
}
