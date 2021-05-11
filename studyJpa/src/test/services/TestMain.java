package services;


import doc.Arr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    public static void  main(String[] args){
       /* String[] str ={"aa","vv","bbb","cc"};
        for(int i=0;i<str.length;i++){
            if(str[i] == "bb"){

            }
        }*/

       /*List list = new ArrayList();
       for(int i=0;i<6;i++){
           list.add(i);
       }
       System.out.println(list);

       Object[] b = list.toArray();
       for(int i=0;i<6;i++){
           list.remove(0);//0位下标自后往前补
       }
       System.out.println(list);//[]
       System.out.println(list.size());//0*/

        String[] arrs = { "AA" ,"BB" ,"CC" };
        System.out.println(Arrays.toString(arrs));
        arrs[1] = null;
        System.out.println(Arrays.toString(arrs));
        /*Arr arr = new Arr(arrs);
        System.out.println(arr.toString());

        arr.delete(3);
        System.out.println(arr.toString());

        arr.delete(2);
        System.out.println(arr.toString());
        arr.toString2();*/
    }

    public static void test(){
        int[] arrs = { 1 ,2 ,3 ,4 ,5 ,6 };
        Arr a = new Arr(arrs);
        //test2();
    }


    public void test2(){

    }

}
