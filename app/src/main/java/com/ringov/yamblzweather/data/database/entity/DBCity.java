package com.ringov.yamblzweather.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.ringov.yamblzweather.data.database.DBContract.COLUMN_CITY_ID;
import static com.ringov.yamblzweather.data.database.DBContract.COLUMN_CITY_NAME;
import static com.ringov.yamblzweather.data.database.DBContract.COLUMN_ID;
import static com.ringov.yamblzweather.data.database.DBContract.TABLE_CITIES;

@Entity(tableName = TABLE_CITIES)
public class DBCity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long _id;

    @ColumnInfo(name = COLUMN_CITY_NAME)
    private String city_name;

    @ColumnInfo(name = COLUMN_CITY_ID)
    private int city_id;

    public DBCity(String city_name, int city_id) {
        this.city_name = city_name;
        this.city_id = city_id;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCity_name() {
        return city_name;
    }

    public int getCity_id() {
        return city_id;
    }
}
