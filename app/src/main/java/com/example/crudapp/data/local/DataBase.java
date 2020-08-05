package com.example.crudapp.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.crudapp.entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract ContactDao clientDao();

    private static DataBase dataBase;

    public static DataBase getDataBase(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(
                    context.getApplicationContext(), DataBase.class, "clients.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBase;
    }
}
