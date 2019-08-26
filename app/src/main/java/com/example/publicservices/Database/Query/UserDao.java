package com.example.publicservices.Database.Query;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.publicservices.Database.Model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> get();

    @Insert
    void insert(User user);

    @Query("DELETE FROM user")
    void reset();
}
