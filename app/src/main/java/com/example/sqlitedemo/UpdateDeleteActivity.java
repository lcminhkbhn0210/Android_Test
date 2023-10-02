package com.example.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sqlitedemo.dal.SQLiteHelper;
import com.example.sqlitedemo.model.Item;

import java.util.Calendar;
import java.sql.*;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private EditText title,price,date;
    private Button btUpdate,btDelete,btBack;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        spinner = findViewById(R.id.spCategory);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));
        title = findViewById(R.id.eTitle);
        price = findViewById(R.id.ePrice);
        date = findViewById(R.id.eDate);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);
        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        btBack.setOnClickListener(this);
        date.setOnClickListener(this);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        title.setText(item.getTitle());
        price.setText(item.getPrice());
        date.setText(item.getDate());
        int p = 0;
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(item.getCategory())) {
                p=i;
                break;
            }
        }
        spinner.setSelection(p);
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        switch (view.getId()){
            case R.id.eDate:
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1 = "";
                        if(m>8){
                            date1 = d+"/"+(m+1)+"/"+y;
                        }else {
                            date1 = d+"/0"+(m+1)+"/"+y;
                        }
                        date.setText(date1);
                    }
                },year,month,day);
                dialog.show();
                break;
            case R.id.btDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong Bao xoa");
                builder.setMessage("Ban co chac muon xoa "+ item.getTitle()+" nay khong?");
                builder.setIcon(R.drawable.ic_remove);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(item.getId());
                        finish();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();
                break;
            case R.id.btBack:
                finish();
            case R.id.btUpdate:
                if(!title.getText().toString().isEmpty() && price.getText().toString().matches("\\d+")){
                    Item i = new Item();
                    i.setId(item.getId());
                    i.setTitle(title.getText().toString());
                    i.setCategory(spinner.getSelectedItem().toString());
                    i.setPrice(price.getText().toString());
                    i.setDate(date.getText().toString());
                    db.update(i);
                }
                finish();
        }

    }
}