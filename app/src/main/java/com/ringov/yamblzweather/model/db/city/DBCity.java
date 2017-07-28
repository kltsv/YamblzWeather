package com.ringov.yamblzweather.model.db.city;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ivan on 28.07.2017.
 */

@Entity(tableName = "cities")
public class DBCity {

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private long _id;

    @ColumnInfo(name = "city_name")
    private String city_name;

    @ColumnInfo(name = "city_id")
    private long city_id;

    public DBCity(long _id, String city_name, long city_id) {
        this._id = _id;
        this.city_name = city_name;
        this.city_id = city_id;
    }

    public long get_id() {
        return _id;
    }

    public String getCity_name() {
        return city_name;
    }

    public long getCity_id() {
        return city_id;
    }
}
