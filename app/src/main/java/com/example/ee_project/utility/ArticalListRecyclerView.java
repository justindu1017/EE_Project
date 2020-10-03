package com.example.ee_project.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ee_project.R;
import com.example.ee_project.utility.Item;

import java.util.ArrayList;
import java.util.List;

public class ArticalListRecyclerView extends RecyclerView.Adapter<ArticalListRecyclerView.viewholder> {


    Context context;
    ArrayList<Item> arrayList;

    public ArticalListRecyclerView(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    //public ArticalListRecyclerView.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.articaladepter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticalListRecyclerView.viewholder holder, int position) {
        // TODO: 9/23/2020 Check to final or not to final 
        final Item item = arrayList.get(position);
        holder.listTitle.setText(item.getArticalTitle());
        holder.listContent.setText(item.getArticalAID());
        holder.listName.setText(item.getArticaluserName());
//        holder.listName.setText(item.getArticaluserName());

        holder.listLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("按下去ㄌ"+item.getArticalTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        LinearLayout listLinearLayout, llistSecondLinearLayout;
        TextView listTitle, listContent, listName;

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
