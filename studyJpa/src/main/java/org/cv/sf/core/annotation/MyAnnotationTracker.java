package org.cv.sf.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MyAnnotationTracker {

    public void tracker(Class<?> c){
        for(Method m : c.getMethods()){
            MyAnnotation myAnnotation = m.getAnnotation(MyAnnotation.class);
            if(myAnnotation == null){
                System.out.println("处理结束");
                return;
            }
            switch (myAnnotation.value()){
                case save:System.out.println("SAVE");break;
                case delete:System.out.println("de");break;
                default:System.out.println("D");
            }
        }
    }
}
