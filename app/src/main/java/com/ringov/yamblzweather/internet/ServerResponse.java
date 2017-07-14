package com.ringov.yamblzweather.internet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ringov on 14.07.17.
 */

public class ServerResponse {

    @SerializedName("cod")
    @Expose
    int code;

    public int getCode() {
        return code;
    }
}
