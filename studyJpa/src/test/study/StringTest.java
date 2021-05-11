package study;

public class StringTest {


    public static void main(String[] args){
        String str = "  afa   returnjfoa……&￥#&……（（&jo 23return4908fidda ";
        //indexOf {key}:第一次匹配到key的下标，之后再有不管
        System.out.println("indexOf {return}:"+str.indexOf("return"));

        //trim:可以去掉字符串前后的空格，但是不能去除中间的空格
        String strTrim = str.trim();
        int indexOf = strTrim.indexOf("return");
        System.out.println("{length}:"+str.length());
        System.out.println("trim {str}:"+strTrim+"  {length:}"+strTrim.length());
        System.out.println("indexOf {return}:"+indexOf);

        //subString:截取字符串，从beginIndex开始，结束于endIndex
        String subStr = str.substring(str.indexOf("return")+6,str.indexOf("return")+7);
        System.out.println("subString {subStr}:"+subStr);
        System.out.println("subString {subStr}:"+str.substring(12,19));
        System.out.println("subString {subStr}:"+str.substring(3,6));

    }
}
