package study;

import temp.UserConstants;

public class EnumTest {

    public static void main(String[] args){
        String str = "2020072700000001";
        if(UserConstants.TenantEnum.MDK.getTenantCode().equals(str)){
            System.out.println(UserConstants.TenantEnum.MDK.getTenantCode());
            System.out.println("true");
        }
    }
}
