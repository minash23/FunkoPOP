package mina.app.funkopop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    EditText numberInput;
    EditText typeInput;
    EditText fandomInput;
    Switch OnInput;
    EditText ultimateInput;
    EditText priceInput;
    Button submitButton;
    Button deleteButton;
    Button nextButton;
    Button queryButton;
    Button prevButton;
    Button updateButton;
    Button searchActButton;
    Cursor mCursor;
    TextView nameTv;
    TextView numberTv;
    TextView typeTv;
    TextView fandomTv;
    TextView onTv;
    TextView ultimateTv;
    TextView priceTv;

    View.OnClickListener searchingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener updateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues mUpdateValues = new ContentValues();

            mUpdateValues.put(funkoProvider.COLUMN_ONE, nameInput.getText().toString().toLowerCase().trim());
            mUpdateValues.put(funkoProvider.COLUMN_TWO, Integer.valueOf(numberInput.getText().toString().toLowerCase().trim()));
            mUpdateValues.put(funkoProvider.COLUMN_THREE, typeInput.getText().toString().toLowerCase().trim());
            mUpdateValues.put(funkoProvider.COLUMN_FOUR, fandomInput.getText().toString().toLowerCase().trim());
            if(OnInput.isChecked()){
                mUpdateValues.put(funkoProvider.COLUMN_FIVE, 1);
            }else{
                mUpdateValues.put(funkoProvider.COLUMN_FIVE, 0);
            }
            mUpdateValues.put(funkoProvider.COLUMN_SIX, ultimateInput.getText().toString().toLowerCase().trim().toLowerCase());
            mUpdateValues.put(funkoProvider.COLUMN_SEVEN, Double.valueOf(priceInput.getText().toString().toLowerCase().trim()));

            String mSelectionClause = funkoProvider.COLUMN_ONE + " = ? " + "AND " + funkoProvider.COLUMN_TWO + " = ? AND " + funkoProvider.COLUMN_THREE + " = ? AND " + funkoProvider.COLUMN_FOUR + " = ? AND " + funkoProvider.COLUMN_FIVE + " = ? AND " + funkoProvider.COLUMN_SIX + " = ? AND " + funkoProvider.COLUMN_SEVEN + " = ?";
            //boolean (On)
            String boo = "1";
            if (onTv.getText().toString().trim().equals("false")) {
                boo = "0";
            }

            String[] mSelectionArgs = {nameTv.getText().toString().toLowerCase().trim(), numberTv.getText().toString().toLowerCase().trim(), typeTv.getText().toString().toLowerCase().trim(), fandomTv.getText().toString().toLowerCase().trim(), boo, ultimateTv.getText().toString().toLowerCase().trim(), priceTv.getText().toString().toLowerCase().trim()};

            int mRowsUpdated = getContentResolver().update(funkoProvider.CONTENT_URI, mUpdateValues, mSelectionClause, mSelectionArgs);

            clear();
        }
    };

    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mSelectionClause = funkoProvider.COLUMN_ONE + " = ? " + "AND " + funkoProvider.COLUMN_TWO + " = ? AND " + funkoProvider.COLUMN_THREE + " = ? AND " + funkoProvider.COLUMN_FOUR + " = ? AND " + funkoProvider.COLUMN_FIVE + " = ? AND " + funkoProvider.COLUMN_SIX + " = ? AND " + funkoProvider.COLUMN_SEVEN + " = ?";
            String boo = "1";
            if(onTv.getText().toString().trim().equals("false")){
                boo = "0";
            }

            String[] mSelectionArgs = { nameTv.getText().toString().toLowerCase().trim(), numberTv.getText().toString().toLowerCase().trim(), typeTv.getText().toString().toLowerCase().trim(), fandomTv.getText().toString().toLowerCase().trim(), boo, ultimateTv.getText().toString().toLowerCase().trim(), priceTv.getText().toString().toLowerCase().trim()};
            //Log.d("name", nameTv.getText().toString().toLowerCase().trim());
            //Log.d("num", numberTv.getText().toString().toLowerCase().trim());
            //Log.d("type", typeTv.getText().toString().toLowerCase().trim());
            //Log.d("fan", fandomTv.getText().toString().toLowerCase().trim());
            //Log.d("on", boo);
            //Log.d("ult", ultimateTv.getText().toString().toLowerCase().trim());
            //Log.d("price", priceTv.getText().toString().toLowerCase().trim());

            int mRowsDeleted = getContentResolver().delete(funkoProvider.CONTENT_URI, mSelectionClause, mSelectionArgs);

            clear();
        }
    };

    View.OnClickListener submitListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ContentValues mUpdateValues = new ContentValues();

            mUpdateValues.put(funkoProvider.COLUMN_ONE, nameInput.getText().toString().toLowerCase().trim());
            mUpdateValues.put(funkoProvider.COLUMN_TWO, Integer.valueOf(numberInput.getText().toString().toLowerCase().trim()));
            mUpdateValues.put(funkoProvider.COLUMN_THREE, typeInput.getText().toString().toLowerCase().trim());
            mUpdateValues.put(funkoProvider.COLUMN_FOUR, fandomInput.getText().toString().toLowerCase().trim());
            if(OnInput.isChecked()){
                mUpdateValues.put(funkoProvider.COLUMN_FIVE, 1);
            }else{
                mUpdateValues.put(funkoProvider.COLUMN_FIVE, 0);
            }
            mUpdateValues.put(funkoProvider.COLUMN_SIX, ultimateInput.getText().toString().toLowerCase().trim().toLowerCase());
            mUpdateValues.put(funkoProvider.COLUMN_SEVEN, Double.valueOf(priceInput.getText().toString().toLowerCase().trim()));

            getContentResolver().insert(funkoProvider.CONTENT_URI, mUpdateValues);

            clear();
        }
    };
    View.OnClickListener queryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCursor = getContentResolver().query(funkoProvider.CONTENT_URI, null, null, null, null);

            if (mCursor != null) {
                if (mCursor.getCount() > 0) {
                    mCursor.moveToNext();
                    setViews();
                }
            }
        }
    };

    View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCursor != null) {
                if (!mCursor.moveToPrevious()) {
                    mCursor.moveToLast();
                }

                setViews();
            }
        }
    };

    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCursor != null) {
                if (!mCursor.moveToNext()) {
                    mCursor.moveToFirst();
                }
                setViews();
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCursor = getContentResolver().query(funkoProvider.CONTENT_URI, null, null, null, null);

        //Finding Views
        nameInput = findViewById(R.id.name_input);
        numberInput = findViewById(R.id.number_input);
        typeInput = findViewById(R.id.type_input);
        fandomInput = findViewById(R.id.fandom_input);
        OnInput = findViewById(R.id.on_switch);
        ultimateInput = findViewById(R.id.ultimate_input);
        priceInput = findViewById(R.id.price_input);
        submitButton = findViewById(R.id.submit_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        deleteButton = findViewById(R.id.delete_button);
        updateButton = findViewById(R.id.update_button);
        queryButton = findViewById(R.id.query_all_button);
        searchActButton = findViewById(R.id.search_act_button);

        //TV
        nameTv = findViewById(R.id.name_tv);
        numberTv = findViewById(R.id.number_tv);
        typeTv = findViewById(R.id.type_tv);
        fandomTv = findViewById(R.id.fandom_tv);
        onTv = findViewById(R.id.on_tv);
        ultimateTv = findViewById(R.id.ultimate_tv);
        priceTv = findViewById(R.id.price_tv);

        //Setting on click listener
        submitButton.setOnClickListener(submitListener);
        deleteButton.setOnClickListener(deleteListener);
        nextButton.setOnClickListener(nextListener);
        prevButton.setOnClickListener(previousListener);
        updateButton.setOnClickListener(updateListener);
        queryButton.setOnClickListener(queryListener);
        searchActButton.setOnClickListener(searchingListener);


    }

    private void setViews(){
        nameTv.setText(mCursor.getString(1).toLowerCase());
        numberTv.setText(""+mCursor.getInt(2));
        typeTv.setText(mCursor.getString(3).toLowerCase());
        fandomTv.setText(mCursor.getString(4).toLowerCase());
        if(mCursor.getInt(5) == 1){
            onTv.setText("true");
        }else{
            onTv.setText("false");
        }
        ultimateTv.setText(mCursor.getString(6).toLowerCase());
        priceTv.setText(""+mCursor.getDouble(7));
    }
    private void clear(){
        nameInput.setText("");
        numberInput.setText("");
        typeInput.setText("");
        fandomInput.setText("");
        OnInput.setChecked(false);
        ultimateInput.setText("");
        priceInput.setText("");
        mCursor = null;

        nameTv.setText("name");
        numberTv.setText("num");
        typeTv.setText("type");
        fandomTv.setText("fandom");
        onTv.setText("On?");
        ultimateTv.setText("ultimate");
        priceTv.setText("price");
    }
}