package com.example.sqlitedemo.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitedemo.AddActivity;
import com.example.sqlitedemo.R;
import com.example.sqlitedemo.adpter.RecycleViewAdapter;
import com.example.sqlitedemo.dal.SQLiteHelper;
import com.example.sqlitedemo.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private Spinner spinner;
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btSearch;
    private SearchView searchView;
    private EditText eFrom,eTo;
    private SQLiteHelper db;
    private RecycleViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinner_category);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTong = view.findViewById(R.id.tvTong);
        btSearch = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        db = new SQLiteHelper(getContext());
        adapter = new RecycleViewAdapter();
        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for(int i=0;i<arr.length;i++){
            arr1[i+1] = arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.item_spinner,arr1));
        List<Item> list = db.getAll();
        adapter.setmList(list);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        tvTong.setText("Tong tien la: "+tong(list)+"K");
        btSearch.setOnClickListener(this);
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = (String) spinner.getItemAtPosition(position);
                if(cate.equals("All")){
                    List<Item> list1 = db.getAll();
                    adapter.setmList(list1);
                }else {
                    List<Item> list1 = db.getListItemByCategory(cate);
                    adapter.setmList(list1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> listitem = db.getListItemBytitle(s);
                tvTong.setText("Tong tien la: "+tong(listitem)+"K");
                adapter.setmList(listitem);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        switch (view.getId()){
            case R.id.btSearch:
                String from = eFrom.getText().toString();
                String to = eTo.getText().toString();
                if(!from.isEmpty() && !to.isEmpty()){
                    List<Item> itemList = db.getListItemByDateFromTo(from,to);
                    tvTong.setText("Tong tien la: "+tong(itemList)+"K");
                    adapter.setmList(itemList);
                }
                break;
            case R.id.eFrom:
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1 = "";
                        if(m>8){
                            date1 = d+"/"+(m+1)+"/"+y;
                        }else {
                            date1 = d+"/0"+(m+1)+"/"+y;
                        }
                        eFrom.setText(date1);
                    }
                },year,month,day);
                dialog.show();
                break;
            case R.id.eTo:
                DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1 = "";
                        if(m>8){
                            date1 = d+"/"+(m+1)+"/"+y;
                        }else {
                            date1 = d+"/0"+(m+1)+"/"+y;
                        }
                        eTo.setText(date1);
                    }
                },year,month,day);
                dialog1.show();
                break;
        }
    }

    private int tong(List<Item> list){
        int tong = 0;
        for(Item item:list){
            tong = tong + Integer.parseInt(item.getPrice());
        }
        return tong;
    }

}
