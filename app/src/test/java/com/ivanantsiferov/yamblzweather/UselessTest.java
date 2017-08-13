package com.ivanantsiferov.yamblzweather;

import com.ringov.yamblzweather.presentation.entity.UICity;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class UselessTest {

    @Test
    public void xz() {
        UICity c = new UICity.Builder().id(0).name("XZ").build();
        assertEquals(0, c.getCityId());
        assertEquals("XZ", c.getCityName());
    }

    @Test
    public void xz2() {
        UICityFavorite c = new UICityFavorite.Builder().enabled(false).id(0).name("a").build();
        assertEquals(false, c.isEnabled());
        assertEquals(0, c.getCityId());
        assertEquals("a", c.getCityName());
    }
}
