package study;

import java.math.BigDecimal;

public class MainTest {

    public static void main(String[] args){
        String[] arrayStr = new String[]{"a","b","c"};
        StringBuilder str = new StringBuilder();
        for(String s:arrayStr){
            str.append(s).append(",");
        }
        System.out.println(str.length());
        if(str.length() > 0)
        System.out.println(str.replace(str.length()-1,str.length(),""));
    }
}
