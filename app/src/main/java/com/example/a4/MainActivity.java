package com.example.a4;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    TextView balance, history;
    DataBaseFile DataBase;
    EditText Date, Price, Item;
    Button Add, Sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Date = (EditText) findViewById(R.id.Date);
        Price = (EditText) findViewById(R.id.Price);
        Item = (EditText) findViewById(R.id.Item);
        Add = (Button) findViewById(R.id.Add);
        Sub = (Button) findViewById(R.id.Sub);
        DataBase = new DataBaseFile(this);
        balance = (TextView) findViewById(R.id.balance);
        history = (TextView) findViewById(R.id.history);

        addHistory();
        list_history();
    }

    public void addHistory(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString());
                        boolean result = DataBase.createHistory(Date.getText().toString(), price, Item.getText().toString());
                        if (result)
                            Toast.makeText(MainActivity.this, "Create Success!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Create Fail!", Toast.LENGTH_LONG).show();
                        list_history();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                    }
                }
        );

        Sub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = -1 * Double.parseDouble(Price.getText().toString());
                        boolean result = DataBase.createHistory(Date.getText().toString(), price, Item.getText().toString());
                        if (result)
                            Toast.makeText(MainActivity.this, "Create Success!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Create Fail!", Toast.LENGTH_LONG).show();
                        list_history();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");
                        MainActivity.this.Item.setText("");
                    }
                }
        );
    }

    public void list_history(){
        Cursor result = DataBase.pullData();
        StringBuffer str = new StringBuffer();
        Double balance = 0.0;

        while(result.moveToNext()){
            String priceString = result.getString(3);
            double price = Double.parseDouble(result.getString(3));
            balance += price;

            if (price < 0) {
                str.append("Spent $");
                priceString = priceString.substring(1);
            }
            else {
                str.append("Added $");
            }
            str.append(priceString + " on " + result.getString(2)
                    + " for " + result.getString(1) + "\n");
        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
        MainActivity.this.history.setText(str);
    }
}
