package com.ringov.yamblzweather.data.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import static com.ringov.yamblzweather.data.database.Contract.COLUMN_CITY_ID;
import static com.ringov.yamblzweather.data.database.Contract.COLUMN_CITY_NAME;
import static com.ringov.yamblzweather.data.database.Contract.COLUMN_ENABLED;
import static com.ringov.yamblzweather.data.database.Contract.COLUMN_ID;
import static com.ringov.yamblzweather.data.database.Contract.TABLE_FAVORITE_CITIES;

@Entity(tableName = TABLE_FAVORITE_CITIES)
public class DBFavoriteCity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private long _id;

    @ColumnInfo(name = COLUMN_CITY_NAME)
    private String city_name;

    @ColumnInfo(name = COLUMN_CITY_ID)
    private int city_id;

    @ColumnInfo(name = COLUMN_ENABLED)
    private int enabled;

    public DBFavoriteCity(String city_name, int city_id, int enabled) {
        this.city_name = city_name;
        this.city_id = city_id;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled == 1;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) this.enabled = 1;
        else this.enabled = 0;
    }

    public int getEnabled() {
        return enabled;
    }
}
