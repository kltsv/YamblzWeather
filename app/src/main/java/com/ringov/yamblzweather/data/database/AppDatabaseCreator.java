package com.ringov.yamblzweather.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import com.ringov.yamblzweather.Prefs;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AppDatabaseCreator {

    private static final int DEFAULT_CITY_ID = 524901;

    private static AppDatabaseCreator instance;

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    private AppDatabase mDb;

    private final AtomicBoolean initializing = new AtomicBoolean(true);

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public synchronized static AppDatabaseCreator getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new AppDatabaseCreator();
                }
            }
        }

        return instance;
    }

    /**
     * Used to observe when the database initialization is done
     */
    public LiveData<Boolean> isDatabaseCreated() {
        return isDatabaseCreated;
    }

    @Nullable
    public AppDatabase getDatabase() {
        return mDb;
    }

    /**
     * Creates or returns a previously-created database.
     * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
     */
    public void createDb(Context context) {
        Timber.d("Creating DB from " + Thread.currentThread().getName() + " thread");

        if (!initializing.compareAndSet(true, false)) {
            return; // Already initializing
        }

        isDatabaseCreated.setValue(false); // Trigger an update to show a loading screen.

        Completable.fromCallable(() -> {
            // Build the database
            mDb = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBContract.DATABASE_NAME)
                    .build();

            checkIsFirstLaunch(context);

            return true;
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> Timber.d("Room db created"))
                .subscribe(() -> isDatabaseCreated.setValue(true));
    }

    /**
     * Check if app is launched for first time and populate DB with data if so
     */
    private void checkIsFirstLaunch(Context context) {
        boolean firstLaunch = Prefs.getBoolean(context,
                R.string.prefs_first_launch_key, R.bool.prefs_first_launch_default);

        if (firstLaunch) {
            // Populate Cities table with pre filled data
            readDataFromRaw(context);
            // Select default city
            DBCity dbCity = mDb.cityDAO().getById(DEFAULT_CITY_ID);
            DBFavoriteCity dbFavoriteCity =
                    new DBFavoriteCity(dbCity.getCity_name(), dbCity.getCity_id(), 1);
            mDb.favoriteCityDAO().insert(dbFavoriteCity);
            // Set first launch to false
            Prefs.putBoolean(context, R.string.prefs_first_launch_key, false);
        }
    }

    private void readDataFromRaw(Context context) {
        ArrayList<DBCity> cities = new ArrayList<>();

        // Read two string from text files (City names and city ids)
        String namesStr = fromRawToString(context, R.raw.name);
        String idsStr = fromRawToString(context, R.raw.id);

        // Convert string to array
        String[] nameArray = namesStr.split(",");
        String[] idsArray = idsStr.split(",");

        // Compose two arrays into DBCity objects
        for (int i = 0; i < nameArray.length; i++)
            cities.add(new DBCity(nameArray[i], Integer.valueOf(idsArray[i])));

        // Write to room database
        mDb.cityDAO().insertAll(cities);

        Timber.d("Database filled with initial data");
    }

    // Helper method that reads text file from Raw resources and returns its content as a String
    private static String fromRawToString(Context context, @RawRes int resId) {
        String output = "";

        try {
            InputStream inputStream = context.getResources().openRawResource(resId);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                output = stringBuilder.toString();
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return output;
    }
}
