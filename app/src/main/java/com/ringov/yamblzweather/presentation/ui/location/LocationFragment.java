package com.ringov.yamblzweather.presentation.ui.location;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Ivan on 26.07.2017.
 */

public class LocationFragment extends BaseFragment<LocationViewModel> {

    public static final String TAG = "LocationFragment";

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.location_fragment;
    }

    @Override
    protected Class<LocationViewModel> getViewModelClass() {
        return LocationViewModel.class;
    }

    @BindView(R.id.atv_location)
    AutoCompleteTextView locationAtv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private SharedPreferences sharedPrefs;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.location_title);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentLocation = sharedPrefs.getString(getString(R.string.prefs_location_key), getString(R.string.prefs_location_default));
        locationAtv.setText(currentLocation);
        locationAtv.requestFocus();
    }

    @Override
    protected void attachInputListeners() {
        getViewModel().observe(this, this::showLoading, this::showSuggestions, this::showError);

        disposables.add(
                RxTextView
                        .textChanges(locationAtv)
                        .skipInitialValue()
                        .filter(charSequence -> charSequence.length() >= 2)
                        .debounce(400, TimeUnit.MILLISECONDS)
                        .map(CharSequence::toString)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(input -> getViewModel().onInputChanges(input))
        );

        disposables.add(
                RxAutoCompleteTextView
                        .itemClickEvents(locationAtv)
                        .subscribe(adapterViewItemClickEvent -> {
                            TextView textView = (TextView) adapterViewItemClickEvent.clickedView();
                            String chosenValue = textView.getText().toString();

                            sharedPrefs.edit().putString(getString(R.string.prefs_location_key), chosenValue).apply();
                        })
        );
    }

    private void showLoading(boolean isLoading) {
        if (isLoading)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    private void showSuggestions(List<String> strings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, strings);
        locationAtv.setAdapter(adapter);
    }

    private void showError(Throwable error) {
        error.printStackTrace();
    }
}
