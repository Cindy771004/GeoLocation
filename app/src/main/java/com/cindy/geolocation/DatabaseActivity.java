package com.cindy.geolocation;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.cindy.geolocation.database.Item;
import com.cindy.geolocation.database.ItemDAO;

import java.util.List;

public class DatabaseActivity extends ListActivity {
    private static String TAG="DatabaseActivity";

    private Context mContext;
    private ItemDAO mDatabaseSource;
    private ArrayAdapter<Item> adapter;
    private Button mAddBtn;
    private Button mDeleteBtn;
    private EditText nameEditText;
    private EditText ageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mContext=getApplicationContext();

        mAddBtn= (Button) findViewById(R.id.addBtn);
        mAddBtn.setOnClickListener(addBthClicked);

        mDeleteBtn = (Button) findViewById(R.id.deleteBtn);
        mDeleteBtn.setOnClickListener(deleteBthClicked);

        nameEditText= (EditText) findViewById(R.id.nameEditText);
        ageEditText=(EditText) findViewById(R.id.ageEditText);

        mDatabaseSource= new ItemDAO(mContext);
        mDatabaseSource.open();

        // Use the SimpleCursorAdapter to show the elements in a ListView
        List<Item> allData= mDatabaseSource.getAllData();
        adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, allData);
        setListAdapter(adapter);

    }
    private View.OnClickListener addBthClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "addBthClicked");

            Log.d(TAG, "name: "+ nameEditText.getText().toString());
            Log.d(TAG, "age: "+ageEditText.getText().toString());
            String name=  nameEditText.getText().toString();
            int age=  Integer.parseInt(ageEditText.getText().toString());

            // Save the new Item to the database
            Item item = mDatabaseSource.inserItem(name,age);

            adapter.add(item);
            adapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener deleteBthClicked= new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Log.d(TAG, "deleteBthClicked");

            if(getListAdapter().getCount()>0){
                Item item =(Item) getListAdapter().getItem(0);

                //delete item from database
                mDatabaseSource.deleteItem(item);
                adapter.remove(item);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseSource.open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseSource.close();
    }
}
