package com.example.db_recycler_view_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myRecyclerViewAdapter extends RecyclerView.Adapter<myRecyclerViewAdapter.MyViewHolder> {
    List<StudentModel> studentsList;
    private static Context context;

    public myRecyclerViewAdapter(Context context, List<StudentModel> studentsList){
        this.studentsList = studentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.data = studentsList.get(position);
        holder.textViewStudentName.setText(holder.data.getName());
        holder.textViewAge.setText(String.valueOf(holder.data.getAge()));
        holder.textViewActive.setText(holder.data.isActive()? "Active" : "Inactive");
        holder.textViewActive.setTextColor(holder.data.isActive()? Color.parseColor("#FFFFFF") : Color.parseColor("#FF0000"));

        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure to delete?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        removeItem(holder.getAdapterPosition());
                        DbHelper dbHelper = new DbHelper(context);
                        dbHelper.deleteStudent(holder.data.getId());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("EDIT",String.valueOf(holder.data.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewStudentName;
        TextView textViewAge;
        TextView textViewActive;
        ImageView delete;
        ImageView edit;
        StudentModel data;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textViewStudentName = itemView.findViewById(R.id.textViewStudentName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewActive = itemView.findViewById(R.id.textViewActive);
            delete = itemView.findViewById(R.id.imageView);
            edit = itemView.findViewById(R.id.imageView2);
        }
    }

    private void removeItem(int position) {
        studentsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, studentsList.size());
    }

}
