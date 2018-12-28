package com.example.tuananh.mytravels.dao;

import com.example.tuananh.mytravels.entity.Travel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TravelDao {

    @Insert
    void insert(Travel travel);

    @Update
    void update(Travel travel);

    @Delete
    void delete(Travel... travel);

    @Query("SELECT * FROM travel ORDER BY id DESC")
    LiveData<List<Travel>> getAllTravels();

}
