package com.example.tuananh.mytravels;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.tuananh.mytravels.base.BaseActivity;
import com.example.tuananh.mytravels.entity.Travel;
import com.example.tuananh.mytravels.utils.MyDate;
import com.example.tuananh.mytravels.utils.MyString;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class EditTravelActivity extends BaseActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private long mStartDt,mEndDt;
    private Place mPlace;
    private EditText mTitleEt,mStartDtEt,mEndDtEt,mPlaceEt;
    private Travel mTravel;
    private boolean mInEditMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_travel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitleEt = findViewById(R.id.title_et);
        mPlaceEt = findViewById(R.id.place_et);
        mStartDtEt = findViewById(R.id.start_dt_et);
        mEndDtEt = findViewById(R.id.end_dt_et);

        findViewById(R.id.place_layout).setOnClickListener(this);
        findViewById(R.id.start_dt_layout).setOnClickListener(this);
        findViewById(R.id.end_dt_layout).setOnClickListener(this);

        if (BaseActivity.REQ_KEY_EDIT_TRAVEL.equals(getIntent().getAction())){
            mInEditMode = true;
            setTitle("Edit Travel");
            mTravel = (Travel) getIntent().getExtras().getSerializable(REQ_KEY_TRAVEL);
            mTitleEt.setText(mTravel.getTitle());
            mStartDt = mTravel.getStartDt();
            mStartDtEt.setText(MyDate.getString(mStartDt));
            mEndDt = mTravel.getEndDt();
            mEndDtEt.setText(MyDate.getString(mEndDt));

            mPlaceEt.setText(mTravel.getPlaceName());

        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Object tag = datePicker.getTag();
        Calendar calendar = Calendar.getInstance();
        if (tag.equals(R.id.start_dt_et)){
            calendar.set(i,i1,i2,0,0,0);
            if (mEndDt>0 && mEndDt < calendar.getTimeInMillis())return;
            mStartDt = calendar.getTimeInMillis();
            mStartDtEt.setText(MyDate.getString(calendar.getTime()));
        }else {
            calendar.set(i,i1,i2,23,59,59);
            if (mStartDt>0 && mStartDt > calendar.getTimeInMillis()) return;
            mEndDt = calendar.getTimeInMillis();
            mEndDtEt.setText(MyDate.getString(calendar.getTime()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_btn :{
                validate();
            }
            break;
            case R.id.start_dt_layout:
            case R.id.start_dt_et:{
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = new DatePickerDialog(this,this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setTag(R.id.start_dt_et);
                if (mEndDt>0){
                    dpd.getDatePicker().setMaxDate(mEndDt);
                }
                dpd.show();
            }
            break;
            case R.id.end_dt_layout:
                case R.id.end_dt_et:{
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog dpd = new DatePickerDialog(this,this,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dpd.getDatePicker().setTag(R.id.end_dt_et);
                    if (mStartDt>0){
                        dpd.getDatePicker().setMinDate(mStartDt);
                    }
                    dpd.show();
                }
                break;
            case R.id.place_layout:
            case R.id.place_et:{
                try {
                    AutocompleteFilter filter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(filter)
                            .build(EditTravelActivity.this);
                    startActivityForResult(intent,REQ_PLACE_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void validate(){
        String title = mTitleEt.getText().toString();
        if (MyString.isEmpty(title)){
            Snackbar.make(mTitleEt,"Travel Title Empty",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!mInEditMode && mPlace==null){
            Snackbar.make(mTitleEt,"City Is Empty",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (mStartDt==0){
            Snackbar.make(mTitleEt,"Start Date Is Empty",Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (mEndDt==0){
            Snackbar.make(mTitleEt,"End Date Is Empty",Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (mTravel==null){
            mTravel = new Travel(title);
        }
        else mTravel.setTitle(title);
        mTravel.setStartDt(mStartDt);
        mTravel.setEndDt(mEndDt);
        if (mPlace!=null){
            mTravel.setPlaceId(mPlace.getId());
            mTravel.setPlaceName((String)mPlace.getName());
            mTravel.setPlaceAddr((String)mPlace.getAddress());
            mTravel.setPlaceLat(mPlace.getLatLng().latitude);
            mTravel.setPlaceLng(mPlace.getLatLng().longitude);

        }
        Intent intent = new Intent();
        intent.putExtra(REQ_KEY_TRAVEL,mTravel);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_PLACE_AUTOCOMPLETE:{
                mPlace = PlaceAutocomplete.getPlace(getBaseContext(),data);
                mPlaceEt.setText(mPlace.getName());
            }
            break;
        }
    }
}
