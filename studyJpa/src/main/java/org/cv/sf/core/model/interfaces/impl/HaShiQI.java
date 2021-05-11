package org.cv.sf.core.model.interfaces.impl;


import org.cv.sf.core.model.interfaces.DogInterface;

public class HaShiQI implements DogInterface {
    @Override
    public String getString(String value) {
        return value;
    }

    @Override
    public String getString(String value, int counts, String type) {
        return value + counts + type;
    }

    @Override
    public String getString(String value, int counts) {
        return value + counts;
    }
}
