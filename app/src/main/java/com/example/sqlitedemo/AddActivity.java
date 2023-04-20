package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner;
    private EditText title,price,date;
    private Button btAdd,btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        spinner = findViewById(R.id.spCategory);
        title = findViewById(R.id.eTitle);
        price = findViewById(R.id.ePrice);
        date = findViewById(R.id.eDate);
        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));
        btAdd.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        date.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.eDate:
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            case R.id.btAdd:
                if(!title.getText().toString().isEmpty() && price.getText().toString().matches("\\d+")){
                    Item item = new Item(title.getText().toString(),spinner.getSelectedItem().toString(),price.getText().toString(),date.getText().toString());
                    SQLiteHelper db = new SQLiteHelper(this);
                    db.addItem(item);
                    finish();
                }
            case R.id.btCancel:
                finish();
        }
    }
}