package com.qunar.qfc2024.loganalysis.service;

import com.qunar.qfc2024.loganalysis.pojo.FrequentInterface;
import com.qunar.qfc2024.loganalysis.pojo.GroupedURL;
import org.springframework.web.servlet.tags.Param;

import java.util.List;

/**
 * @author zhangge
 * @date 2024/6/9
 */
public interface AccessService {

    /**
     * 获取请求总量
     *
     * @return 请求总量
     * @author zhangge
     * @date 2024/6/9
     */
    Long getQueryCount();

    /**
     * 获取请求最频繁的接口列表
     *
     * @param count 接口数
     * @return 最频繁的接口列表
     * @author zhangge
     * @date 2024/6/10
     */
    List<FrequentInterface> getFrequentInterface(Long count);

    /**
     * 获取各个请求方式的请求量
     *
     * @return 各请求方式的请求量
     * @author zhangge
     * @date 2024/6/10
     */
    List<Param> getQueryMethodCount();

    /**
     * URL 格式均为 /AAA/BBB 或者 /AAA/BBB/CCC 格式，按 AAA 分类
     *
     * @return 分类后的url
     * @author zhangge
     * @date 2024/6/10
     */
    List<GroupedURL> getGroupedURL();
}
