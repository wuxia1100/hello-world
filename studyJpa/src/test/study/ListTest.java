package study;

import java.util.ArrayList;
import java.util.List;

/**
 * size : 集合里对象数量
 * index: 集合里对象下标,从0开始
 * maxIndex:比size小1， maxIndex = size - 1
 * minIndex:从0开始，minIndex = 0
 */
public class ListTest {
    public static void main(String[] args){
        List<String> strList = new ArrayList<>();
        strList.add("aaa");
        strList.add("aaab");
        strList.add("aaac");
        if(strList.size() == 1)
            System.out.println("== 1:" +strList.get(0));
        if(strList.size() > 1)
            System.out.println("> 1:"+strList.get(strList.size() - 1));
            System.out.println("> 1:"+strList.size());
    }
}
