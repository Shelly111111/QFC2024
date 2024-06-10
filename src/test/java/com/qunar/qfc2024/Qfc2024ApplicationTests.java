package com.qunar.qfc2024;

import com.qunar.qfc2024.loganalysis.pojo.InterfaceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Qfc2024ApplicationTests {

    @Test
    void test() {

        InterfaceInfo interfaceInfo = new InterfaceInfo("POST /user/postDeviceInfo.htm");
        System.out.println(interfaceInfo);


        InterfaceInfo interfaceInfo2 = new InterfaceInfo("GET /twell/public.htm?arg1=var1&arg2=var2");
        System.out.println(interfaceInfo2);
    }

}
