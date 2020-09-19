package com.example.ee_project.utility;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ee_project.R;

public class ArticalListRecyclerView extends RecyclerView.Adapter<ArticalListRecyclerView.viewholder>
{

    @NonNull
    @Override
    //public ArticalListRecyclerView.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticalListRecyclerView.viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends  RecyclerView.ViewHolder{
        LinearLayout listLinearLayout,llistSecondLinearLayout;
        TextView listTitle,listContent,listName;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            listLinearLayout = itemView.findViewById(R.id.listLinearLayout);
            llistSecondLinearLayout = itemView.findViewById(R.id.llistSecondLinearLayout);
            listTitle = itemView.findViewById(R.id.listTitle);
            listContent = itemView.findViewById(R.id.listContent);
            listName = itemView.findViewById(R.id.listName);
        }
    }

}
