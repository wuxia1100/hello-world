package study;

import org.cv.sf.dto.entity.File;
import temp.RsPropertiesValueDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IfTest {
    public static void main(String[] args){
        // if 执行体，可以省略{}, 执行体就是紧跟着的";"之前逻辑
        // 好处：结构清晰，代码简洁
        //List<RsPropertiesValueDomain> rsPropertiesValueDomainList = null;
        List<RsPropertiesValueDomain> rsPropertiesValueDomainList = new ArrayList<>();
        rsPropertiesValueDomainList.add(new RsPropertiesValueDomain());
        rsPropertiesValueDomainList.add(new RsPropertiesValueDomain());
        System.out.println("rsPropertiesValueDomainList.isEmpty() :"+rsPropertiesValueDomainList.isEmpty());
        System.out.println("rsPropertiesValueDomainList.contains() :"+rsPropertiesValueDomainList.contains(null));
        if(rsPropertiesValueDomainList != null && !rsPropertiesValueDomainList.contains(null))

            // 1 list 可以存入空对象
            // 2 如果list为空，Collections.sort 是不会进行对象比较操作
            // 3 list 如果有多个空对象，Collections.sort 进行对象比较操作会抛出空指针
            // 4 list 对象为空，Collections.sort 会报错空指针

            Collections.sort(rsPropertiesValueDomainList, new Comparator<RsPropertiesValueDomain>() {
                @Override
                public int compare(RsPropertiesValueDomain o1, RsPropertiesValueDomain o2) {
                    System.out.println("if 1 :"+rsPropertiesValueDomainList.size());
                    if (null != o1.getPropertiesValueType() && null != o2.getPropertiesValueType()) {
                        System.out.println("if 2");
                        return -o1.getPropertiesValueType().compareTo(o2.getPropertiesValueType());
                    }
                    return -1;
                }
            });
        System.out.println("if false");
    }
}
