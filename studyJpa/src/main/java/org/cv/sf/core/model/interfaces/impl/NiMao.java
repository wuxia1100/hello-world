package org.cv.sf.core.model.interfaces.impl;


import org.cv.sf.core.model.interfaces.AnimalInterface;
import org.cv.sf.core.model.interfaces.CatInterface;

/**
 * 1 可以同时实现多个接口
 * 2 多个接口里的所有方法都要实现
 */
public class NiMao implements CatInterface, AnimalInterface {
    @Override
    public String getString(String value, int counts) {
        return null;
    }

    @Override
    public String getString(String value) {
        return null;
    }

    @Override
    public String eatFood(String food) {
        return null;
    }
}
