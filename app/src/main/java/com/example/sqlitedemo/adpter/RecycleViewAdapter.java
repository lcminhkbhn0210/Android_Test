package com.example.sqlitedemo.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {

    private List<Item> mList;
    private ItemListener itemListener;

    public void setmList(List<Item> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener){
        this.itemListener = itemListener;
    }

    public Item getItem(int position){
        return mList.get(position);
    }
    public RecycleViewAdapter(){
        mList = new ArrayList<>();
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
            if(mList==null)
                return;
            Item item = mList.get(position);
            holder.title.setText(item.getTitle());
            holder.price.setText(item.getPrice());
            holder.catogory.setText(item.getCategory());
            holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,catogory, price,date;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            catogory = itemView.findViewById(R.id.tvCategory);
            price = itemView.findViewById(R.id.tvPrice);
            date  = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null)
                itemListener.onItemClick(view,getAdapterPosition());
        }
    }
    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
