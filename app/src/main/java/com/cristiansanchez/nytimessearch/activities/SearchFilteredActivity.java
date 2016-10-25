package com.cristiansanchez.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cristiansanchez.nytimessearch.R;
import com.cristiansanchez.nytimessearch.fragments.DatePickerFragment;
import com.cristiansanchez.nytimessearch.models.NewsDesk;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFilteredActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "SearchFilteredActivity";
    @BindView(R.id.etBeginDate) EditText etBeginDate;
    @BindView(R.id.spSortOrder) Spinner spSortOrder;
    @BindView(R.id.cbArt) CheckBox cbArt;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbStyleSport) CheckBox cbStyleSport;

    private Calendar calendar;
    private Date date;
    private int year;
    private int month;
    private int day;
    private NewsDesk newsDesk=null;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filtered);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        query = getIntent().getStringExtra("query");
        newsDesk = new NewsDesk();
        newsDesk.clean();

        //Set up Listeners
        setListeners();

        Calendar today = Calendar.getInstance();
        date = today.getTime();
        String date = new com.cristiansanchez.nytimessearch.utils.Utils().getFormatDate(today,true);
        etBeginDate.setText(date);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.sort_order,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortOrder.setAdapter(adapter);
    }

    public void setListeners(){
        etBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }

    public void showDatePickerDialog(View v){
        DialogFragment dialogFragment;
        if(calendar!=null){
            dialogFragment = DatePickerFragment.newInstance(String.valueOf(day),String.valueOf(month),
                    String.valueOf(year));
        }
        else{
            dialogFragment = new DatePickerFragment();
        }

        dialogFragment.show(getFragmentManager(), "datePicker");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        date = calendar.getTime();
        this.year=year;
        month++;
        this.month=month;
        this.day=day;
        date = calendar.getTime();
        etBeginDate.setText(""+month+"/"+day+"/"+year);

        Toast.makeText(this, "Date: "+date.toString(), Toast.LENGTH_LONG).show();
    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {

            case R.id.cbArt:
                newsDesk.setArts((newsDesk.isArts())?false:true);
                break;

            case R.id.cbFashion:
                newsDesk.setFashion((newsDesk.isFashion())?false:true);
                break;

            case R.id.cbStyleSport:
                newsDesk.setStyleSports((newsDesk.isStyleSports())?false:true);
                break;
        }

        Log.v(TAG, "News Desk: " + newsDesk.isArts() + ", " + newsDesk.isFashion() + ", " + newsDesk.isStyleSports());
    }

    public void onSearchFiltered(View v){
        String sort = spSortOrder.getSelectedItem().toString().toLowerCase();
        Intent intent = new Intent();
        intent.putExtra("date",date.toString());
        intent.putExtra("sort",sort);
        intent.putExtra("news", Parcels.wrap(newsDesk));
        intent.putExtra("query",query);
        intent.putExtra("code",90);
        setResult(RESULT_OK,intent);
        finish();

    }

}
