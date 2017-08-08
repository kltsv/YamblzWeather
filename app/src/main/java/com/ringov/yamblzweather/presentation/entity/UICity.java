package com.ringov.yamblzweather.presentation.entity;

public class UICity {

    private int cityId;
    private String cityName;

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public static class Builder {

        private UICity city;

        public Builder() {
            city = new UICity();
        }

        public Builder id(int id) {
            city.cityId = id;
            return this;
        }

        public Builder name(String name) {
            city.cityName = name;
            return this;
        }

        public UICity build() {
            return city;
        }
    }
}
