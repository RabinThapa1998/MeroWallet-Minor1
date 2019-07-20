package com.example.merowalletv11;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.ExampleViewHolder> {
    private ArrayList<activity_recordsview> mRecordsList;
    private OnItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView string_cat;
        public TextView string_acc;
        public TextView string_price;
        public TextView string_date;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.catIcon);
            string_cat = itemView.findViewById(R.id.category);
            string_acc = itemView.findViewById(R.id.account);
            string_price = itemView.findViewById(R.id.price);
            string_date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }

            });
        }
    }

    public RecordsAdapter(ArrayList<activity_recordsview> recordsList){
        mRecordsList = recordsList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recordsview,parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return  evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        activity_recordsview currentItem = mRecordsList.get(position);

        holder.icon.setImageResource(currentItem.getCategIcon());
        holder.string_cat.setText(currentItem.getCategory());
        holder.string_acc.setText(currentItem.getAccount());
        holder.string_price.setText(currentItem.getPrice());
        holder.string_date.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }
}