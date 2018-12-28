package com.example.tuananh.mytravels;

import android.content.Intent;
import android.os.Bundle;

import com.example.tuananh.mytravels.base.BaseActivity;
import com.example.tuananh.mytravels.entity.Travel;
import com.example.tuananh.mytravels.main.TravelViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.InvalidationTracker;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.Observer;
import java.util.Set;

public class MainActivity extends BaseActivity implements TravelListAdapter.ListItemClickListener {
    private TravelViewModel mTravelViewModel;
    private TravelListAdapter mTravelListAdapter;
    private final androidx.lifecycle.Observer<List<Travel>> mTravelObserver = new androidx.lifecycle.Observer<List<Travel>>() {
        @Override
        public void onChanged(List<Travel> travels) {
            Log.d("OK", "onChanged: travels.size()"+travels.size());
            mTravelListAdapter.setList(travels);
        }
    };

    @Override
    public void onListItemClick(View v, int position, Travel travel) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,EditTravelActivity.class);

                startActivityForResult(intent,REQ_TRAVEL_ADD);
            }
        });

        mTravelListAdapter = new TravelListAdapter(this);
        mTravelListAdapter.setmListItemClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        recyclerView.setAdapter(mTravelListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTravelViewModel = ViewModelProviders.of(this).get(TravelViewModel.class);
        mTravelViewModel.getAllTravels().observe(this,mTravelObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= RESULT_OK)return;
        switch (requestCode){
            case REQ_TRAVEL_ADD:{
                Travel travel = (Travel) data.getExtras().getSerializable(REQ_KEY_TRAVEL);
                Log.d("OK", "onActivityResult: "+travel);
                mTravelViewModel.insert(travel);
            }break;
            case REQ_TRAVEL_EDIT:{
                Travel travel = (Travel) data.getExtras().getSerializable(REQ_KEY_TRAVEL);
                if (REQ_KEY_DEL_TRAVEL.equals(data.getAction())){
                    mTravelViewModel.delete(travel);
                }
                else mTravelViewModel.update(travel);

            }break;
        }
    }

}
