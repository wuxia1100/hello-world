import java.math.BigDecimal;

public class BigDecimalTest {

    public static void main(String[] args){
        BigDecimal a = BigDecimal.ONE;
        //BigDecimal 对象为null,操作时会报空指针
        //augend.intCompact != -9223372036854775808L
        BigDecimal b = null;
        if(a.add(b).compareTo(BigDecimal.ZERO) == 0){
            System.out.println("11");
        }
        System.out.println("22");
    }
}

