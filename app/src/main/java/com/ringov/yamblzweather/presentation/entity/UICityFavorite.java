package com.ringov.yamblzweather.presentation.entity;

public class UICityFavorite {

    private int cityId;
    private String cityName;
    private boolean enabled;

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static class Builder {

        private UICityFavorite city;

        public Builder() {
            city = new UICityFavorite();
        }

        public Builder id(int id) {
            city.cityId = id;
            return this;
        }

        public Builder name(String name) {
            city.cityName = name;
            return this;
        }

        public Builder enabled(boolean enabled) {
            city.enabled = enabled;
            return this;
        }

        public UICityFavorite build() {
            return city;
        }
    }
}
