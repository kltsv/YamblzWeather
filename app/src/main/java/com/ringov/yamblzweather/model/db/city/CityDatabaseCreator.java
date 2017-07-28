package com.ringov.yamblzweather.model.db.city;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

import static com.ringov.yamblzweather.model.db.city.CityDatabase.DATABASE_NAME;

/**
 * Created by Ivan on 28.07.2017.
 */

public class CityDatabaseCreator {

    private static CityDatabaseCreator instance;

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    private CityDatabase mDb;

    private final AtomicBoolean initializing = new AtomicBoolean(true);

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public synchronized static CityDatabaseCreator getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new CityDatabaseCreator();
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
    public CityDatabase getDatabase() {
        return mDb;
    }

    /**
     * Creates or returns a previously-created database.
     * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
     */
    public void createDb(Context context) {
        Timber.d("Creating DB from " + Thread.currentThread().getName());

        if (!initializing.compareAndSet(true, false)) {
            return; // Already initializing
        }

        isDatabaseCreated.setValue(false); // Trigger an update to show a loading screen.

        new AsyncTask<Context, Void, Void>() {

            @Override
            protected Void doInBackground(Context... params) {
                Timber.d("Starting bg job " + Thread.currentThread().getName());

                Context context = params[0].getApplicationContext();

                // Build the database
                CityDatabase db = Room
                        .databaseBuilder(context.getApplicationContext(), CityDatabase.class, DATABASE_NAME)
                        .build();

                Timber.d("DB was populated in thread " + Thread.currentThread().getName());

                mDb = db;

                readDataFromRaw();

                return null;
            }

            @Override
            protected void onPostExecute(Void ignored) {
                // Now on the main thread, notify observers that the mDb is created and ready.
                isDatabaseCreated.setValue(true);
            }

        }.execute(context.getApplicationContext());
    }

    private void readDataFromRaw() {
        ArrayList<DBCity> cities = new ArrayList<>();

        // Read two string from text files (City names and city ids)
        String namesStr = fromRawToString(R.raw.name);
        String idsStr = fromRawToString(R.raw.id);

        // Convert string to array
        String[] nameArray = namesStr.split(",");
        String[] idsArray = idsStr.split(",");

        // Compose two arrays into DBCity objects
        for (int i = 0; i < nameArray.length; i++)
            cities.add(new DBCity(i, nameArray[i], Long.valueOf(idsArray[i])));

        // Write to room database
        mDb.cityDAO().insertAll(cities);

        Timber.d("Database filled");
    }

    // Helper method that reads text file from Raw resources and returns it content as a String
    private static String fromRawToString(@RawRes int resId) {
        String output = "";

        try {
            InputStream inputStream = App.getContext().getResources().openRawResource(resId);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
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
