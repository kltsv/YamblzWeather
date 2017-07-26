package com.ringov.yamblzweather.presentation.ui.location;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 26.07.2017.
 */

public class LocationViewModel extends BaseViewModel<List<String>, LocationStateData> {

    public LocationViewModel() {
        App.getComponent().inject(this);

        ArrayList<String> list = new ArrayList<>();
        list.add("Moscow");
        list.add("London");
        list.add("Paris");
        updateValue(list);
    }

    @Override
    protected LocationStateData getInitialState() {
        return new LocationStateData();
    }
}
