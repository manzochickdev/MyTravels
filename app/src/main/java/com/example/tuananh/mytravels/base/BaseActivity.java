package com.example.tuananh.mytravels.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    public static final int REQ_PLACE_AUTOCOMPLETE = 1000;
    public static final int REQ_TRAVEL_ADD = 2000;
    public static final int REQ_TRAVEL_EDIT = 2001;

    public static final String REQ_KEY_TRAVEL = "REQ_KEY_TRAVEL";
    public static final String REQ_KEY_EDIT_TRAVEL = "REQ_KEY_EDIT_TRAVEL";
    public static final String REQ_KEY_ADD_TRAVEL = "REQ_KEY_ADD_TRAVEL";
    public static final String REQ_KEY_DEL_TRAVEL = "REQ_KEY_DEL_TRAVEL";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }
}
