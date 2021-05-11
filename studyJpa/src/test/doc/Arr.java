package doc;

import java.util.Arrays;

public class Arr {
    private int[] arr;
    private int len;//实际内容长度

    public Arr(int[] arr){
        this.arr = arr;
        this.len = this.arr.length;
    }

    public void delete(int index){
        if(index > arr.length){
            return;
        }

        for(int i = index; i < arr.length - 1; i ++){
            arr[i] = arr[i + 1];
        }

        len --;

        arr[arr.length - 1] = 0;
    }

    public void toString2(){
        for(int i = 0; i < len; i ++){
            if(i + 1 != len){
                System.out.print(arr[i] + ",");
            } else{
                System.out.println(arr[i]);
            }
        }
    }

    @Override
    public String toString(){
        // TODO Auto-generated method stub
        return Arrays.toString(arr);
    }
}
