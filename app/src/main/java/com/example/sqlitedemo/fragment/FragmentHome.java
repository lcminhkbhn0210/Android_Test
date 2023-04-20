package com.example.sqlitedemo.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitedemo.R;
import com.example.sqlitedemo.UpdateDeleteActivity;
import com.example.sqlitedemo.adpter.RecycleViewAdapter;
import com.example.sqlitedemo.dal.SQLiteHelper;
import com.example.sqlitedemo.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private RecycleViewAdapter adapter;
    private TextView tvTong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new SQLiteHelper(getContext());
        tvTong = view.findViewById(R.id.tvTong);
        adapter = new RecycleViewAdapter();
        Date d = new Date();
        SimpleDateFormat l = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getListItemByDate(l.format(d));
        adapter.setmList(list);
        tvTong.setText(String.valueOf(tong(list)));
        adapter.setItemListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }
    private int tong(List<Item> list){
        int tong = 0;
        for(Item item:list){
            tong = tong + Integer.parseInt(item.getPrice());
        }
        return tong;
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item",item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat l = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getListItemByDate(l.format(d));
        adapter.setmList(list);
        tvTong.setText(String.valueOf(tong(list)));
        adapter.setmList(list);
    }
}
