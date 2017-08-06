package com.ringov.yamblzweather.presentation.ui.main.location;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.dagger.Injectable;
import com.ringov.yamblzweather.presentation.base.BaseMvvmFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LocationFragment extends BaseMvvmFragment<LocationViewModel> implements Injectable {

    public static final String TAG = "LocationFragment";

    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected int getLayout() {
        return R.layout.fragment_location;
    }

    @Override
    protected Class<LocationViewModel> getViewModelClass() {
        return LocationViewModel.class;
    }

    @BindView(R.id.atv_location)
    AutoCompleteTextView locationAtv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_location);
    }

    @Override
    public void onStop() {
        super.onStop();

        closeSoftKeyboard();
    }

    @Override
    protected void onViewModelAttach() {
        viewModel =  ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
    }

    @Override
    protected void attachInputListeners() {
        getViewModel().observe(
                this, this::showLoading, this::showSuggestions, this::showError, this::showCity);

        // Listen for user input to provide suggestions
        disposables.add(
                RxTextView
                        .textChanges(locationAtv)
                        .skipInitialValue() // First emitted value is equals to current chosen city
                        .filter(charSequence -> charSequence.length() >= 3)
                        .debounce(400, TimeUnit.MILLISECONDS)
                        .map(CharSequence::toString)
                        .observeOn(AndroidSchedulers.mainThread()) // Because debounce runs on Computation thread
                        .subscribe(input -> getViewModel().onInputChanges(input))
        );

        // Listen for clicks on city suggestions
        disposables.add(
                RxAutoCompleteTextView
                        .itemClickEvents(locationAtv)
                        .subscribe(adapterViewItemClickEvent -> {
                            TextView textView = (TextView) adapterViewItemClickEvent.clickedView();
                            String chosenValue = textView.getText().toString();
                            getViewModel().onCitySelected(chosenValue);
                            Toast.makeText(getContext(), R.string.location_changed, Toast.LENGTH_SHORT).show();
                            closeSoftKeyboard();
                        })
        );
    }

    private void showCity(String city) {
        locationAtv.setText(city);
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
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void closeSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(locationAtv.getWindowToken(), 0);
    }
}
