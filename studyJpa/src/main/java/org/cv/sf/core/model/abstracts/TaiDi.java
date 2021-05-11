package org.cv.sf.core.model.abstracts;

/**
 * 1 继承只能单继承
 * 2 继承的是抽象方法，必须实现
 */
public class TaiDi extends BaseDog{

    @Override
    String getType(String type) {
        return null;
    }

    @Override
    String eatFood(String name) {
        return null;
    }
}
