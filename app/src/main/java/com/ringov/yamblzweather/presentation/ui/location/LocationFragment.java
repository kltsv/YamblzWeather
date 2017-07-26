package com.ringov.yamblzweather.presentation.ui.location;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.ModelViewFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Ivan on 26.07.2017.
 */

public class LocationFragment extends ModelViewFragment<LocationViewModel, List<String>, LocationStateData> implements LocationView {

    public static final String TAG = "LocationFragment";

    private SharedPreferences sharedPrefs;

    @BindView(R.id.atv_location)
    AutoCompleteTextView locationAtv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.location_fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.location_title);
        String currentLocation = sharedPrefs.getString(getString(R.string.prefs_location_key), getString(R.string.prefs_location_default));
        locationAtv.setText(currentLocation);
        locationAtv.requestFocus();

        addDisposable(
                RxTextView
                        .textChanges(locationAtv)
                        .skip(1)
                        .debounce(400, TimeUnit.MILLISECONDS)
                        .map(CharSequence::toString)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(input -> {
                            showLoading();
                            getViewModel().onInputChanges(input);
                        })
        );

        addDisposable(
                RxAutoCompleteTextView
                        .itemClickEvents(locationAtv)
                        .subscribe(adapterViewItemClickEvent -> {
                            TextView textView = (TextView) adapterViewItemClickEvent.clickedView();
                            String chosenValue = textView.getText().toString();

                            sharedPrefs.edit().putString(getString(R.string.prefs_location_key), chosenValue).apply();
                            Log.d(TAG, "User clicked at: " + chosenValue);
                        })
        );
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected Class<LocationViewModel> getViewModelClass() {
        return LocationViewModel.class;
    }

    @Override
    protected void showDataChanges(List<String> strings) {
        hideLoading();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, strings);
        locationAtv.setAdapter(adapter);
    }
}
