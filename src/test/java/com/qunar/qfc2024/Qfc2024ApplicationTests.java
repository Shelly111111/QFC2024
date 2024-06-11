package com.qunar.qfc2024;

import com.qunar.qfc2024.loganalysis.pojo.GroupedURL;
import com.qunar.qfc2024.loganalysis.pojo.InterfaceInfo;
import com.qunar.qfc2024.loganalysis.pojo.InterfaceStat;
import com.qunar.qfc2024.loganalysis.service.AccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class Qfc2024ApplicationTests {

    @Autowired
    private AccessService accessService;

    @Test
    void test() {

        InterfaceInfo interfaceInfo = new InterfaceInfo("POST /user/postDeviceInfo.htm");
        System.out.println(interfaceInfo);


        InterfaceInfo interfaceInfo2 = new InterfaceInfo("GET /twell/public.htm?arg1=var1&arg2=var2");
        System.out.println(interfaceInfo2);
    }

    @Test
    void getQueryCountTest() {
        Integer count = accessService.getQueryCount();
        System.out.println(count);
    }

    @Test
    void getFrequentInterfaceTest() {
        List<InterfaceStat> frequentInterface = accessService.getFrequentInterface(10L);
        System.out.println(frequentInterface);
        System.out.println(frequentInterface.size());
    }

    @Test
    void getQueryMethodCountTest() {
        List<InterfaceStat> list = accessService.getQueryMethodCount();
        System.out.println(list);
    }

    @Test
    void getGroupedURLTest() {
        List<GroupedURL> groupedURL = accessService.getGroupedURL();
        System.out.println(groupedURL);
    }

}
