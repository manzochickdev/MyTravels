package com.example.tuananh.mytravels.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.tuananh.mytravels.dao.TravelDao;
import com.example.tuananh.mytravels.database.AppDatabase;
import com.example.tuananh.mytravels.entity.Travel;

import java.util.List;


import androidx.lifecycle.LiveData;

public class TravelRepository {
    public static volatile  TravelRepository INSTANCE;
    private final TravelDao mTravelDao;
    private LiveData<List<Travel>> mAllTravels;

    public TravelRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTravelDao = db.travelDao();
    }

    public static  TravelRepository getInstance(final Application application){
        if (INSTANCE==null){
            synchronized (TravelRepository.class){
                if (INSTANCE==null){
                    INSTANCE = new TravelRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Travel>> getAllTravels(){
        if (mAllTravels==null){
            mAllTravels = mTravelDao.getAllTravels();
        }
        return mAllTravels;
    }

    public void insert(Travel travel){
        new insertAsyncTask(mTravelDao).execute(travel);
    }

    private static class insertAsyncTask extends AsyncTask<Travel,Void,Void>{
        private TravelDao mAsyncTaskDao;

        insertAsyncTask(TravelDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            mAsyncTaskDao.insert(travels[0]);
            return null;
        }
    }

    public void update(Travel travel){
        new updateAsyncTask(mTravelDao).execute(travel);
    }

    private static class updateAsyncTask extends AsyncTask<Travel,Void,Void>{
        private TravelDao mAsyncTaskDao;

        updateAsyncTask(TravelDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            mAsyncTaskDao.update(travels[0]);
            return null;
        }
    }

    public void delete(Travel... travels){
        new deleteAsyncTask(mTravelDao).execute(travels);
    }

    private class deleteAsyncTask extends AsyncTask<Travel,Void,Void> {
        private TravelDao mAsyncTaskDao;
        public deleteAsyncTask(TravelDao mTravelDao) {
            this.mAsyncTaskDao = mTravelDao;
        }

        @Override
        protected Void doInBackground(Travel... travels) {
            mAsyncTaskDao.delete(travels);
            return null;
        }
    }
}
