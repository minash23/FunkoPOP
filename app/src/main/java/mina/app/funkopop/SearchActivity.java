package mina.app.funkopop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Button searchButton;
    Button homeButton;
    ListView funkoListView;
    EditText nameSearchInput;
    EditText numberSearchInput;
    ArrayAdapter<String> adapter;
    Cursor mCursor;

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            query();
        }
    };

    View.OnClickListener homeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    public void query() {
        ArrayList<String> funkos = new ArrayList<>();
        String mSelectionClause = funkoProvider.COLUMN_ONE + " = ? AND " + funkoProvider.COLUMN_TWO + " = ? ";
        String[] mSelectionArgs = {nameSearchInput.getText().toString().trim(), numberSearchInput.getText().toString().trim()};

        mCursor = getContentResolver().query(funkoProvider.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
        String attributes = "";
        if (!mCursor.moveToNext()) {
            mCursor.moveToFirst();
        }

        while (!mCursor.isLast()) {
            String Name = mCursor.getString(1);
            String Number = mCursor.getString(2);
            String Type = mCursor.getString(3);
            String Fandom = mCursor.getString(4);
            String Onn = mCursor.getString(5);
            String Ultimate = mCursor.getString(6);
            String Price = mCursor.getString(7);

            attributes = "Pokemon Name: " + Name + "\n" + "\n";
            attributes += "Attributes(Separated By Commas in Order of Input): " + "\n" + Number + ", " + Type + ", " + Fandom + ", " + Onn + ", " + Ultimate + ", " + Price;
            funkos.add(attributes);

            mCursor.moveToNext();
            attributes = "";
        }
        String Name = mCursor.getString(1);
        String Number = mCursor.getString(2);
        String Type = mCursor.getString(3);
        String Fandom = mCursor.getString(4);
        String Onn = mCursor.getString(5);
        String Ultimate = mCursor.getString(6);
        String Price = mCursor.getString(7);

        attributes = "Pokemon Name: " + Name + "\n" + "\n";
        attributes += "Attributes(Separated By Commas in Order of Input): " + "\n" + Number + ", " + Type + ", " + Fandom + ", " + Onn + ", " + Ultimate + ", " + Price;
        funkos.add(attributes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, funkos);
        funkoListView.setAdapter(adapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = findViewById(R.id.search_button);
        nameSearchInput = findViewById(R.id.name_search_input);
        numberSearchInput = findViewById(R.id.number_search_input);
        funkoListView = findViewById(R.id.funko_listview);
        homeButton = findViewById(R.id.home_button);

        mCursor = getContentResolver().query(funkoProvider.CONTENT_URI, null, null, null, null);

        searchButton.setOnClickListener(searchListener);
        homeButton.setOnClickListener(homeListener);

    }
}