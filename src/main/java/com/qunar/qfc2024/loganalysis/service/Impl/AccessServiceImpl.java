package com.qunar.qfc2024.loganalysis.service.Impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.qunar.qfc2024.loganalysis.enumeration.QueryMethod;
import com.qunar.qfc2024.loganalysis.pojo.GroupedURL;
import com.qunar.qfc2024.loganalysis.pojo.InterfaceInfo;
import com.qunar.qfc2024.loganalysis.pojo.InterfaceStat;
import com.qunar.qfc2024.loganalysis.service.AccessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 问题一：日志分析
 *
 * @author zhangge
 * @date 2024/6/10
 */
@Service
@Slf4j
public class AccessServiceImpl implements AccessService {

    @Value("${question.basePath}")
    private String basePath;

    @Value("${question.files.one.path}")
    private String[] filePaths;

    private final ArrayList<InterfaceInfo> interfaceInfos = Lists.newArrayList();

    /**
     * 读取接口文件
     *
     * @author zhangge
     * @date 2024/6/11
     */
    private void readInterfaceFile() {
        //读取测试文件
        Resource resource = new ClassPathResource(Paths.get(basePath, filePaths[0]).toString());
        try {
            //获取流
            InputStream inputStream = resource.getInputStream();
            //扫描每行
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (StringUtils.isBlank(line)) {
                    continue;
                }
                //转化为接口信息类并存储到列表中
                interfaceInfos.add(new InterfaceInfo(line));
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Integer getQueryCount() {
        readInterfaceFile();
        return interfaceInfos.size();
    }

    @Override
    public List<InterfaceStat> getFrequentInterface(Long limitCount) {
        readInterfaceFile();
        //统计各个接口的请求数量
        Map<String, Long> map = interfaceInfos.stream()
                .collect(Collectors.groupingBy(InterfaceInfo::getUrl, Collectors.counting()));
        List<InterfaceStat> list = map.entrySet()
                //按照请求数量进行从大到小排序
                .stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                //截取排序前count个
                .limit(limitCount)
                //转化为类
                .map(a -> {
                    InterfaceStat interfaceStat = new InterfaceStat();
                    interfaceStat.setUrl(a.getKey());
                    interfaceStat.setQueryCount(a.getValue());
                    return interfaceStat;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<InterfaceStat> getQueryMethodCount() {
        readInterfaceFile();
        //统计GET和POST的请求数量
        Map<QueryMethod, Long> map = interfaceInfos.stream()
                //过滤除GET和POST请求外的请求
                .filter(a -> (a.getMethod() == QueryMethod.GET || a.getMethod() == QueryMethod.POST))
                .collect(Collectors.groupingBy(InterfaceInfo::getMethod, Collectors.counting()));
        //格式转化
        List<InterfaceStat> list = map.entrySet().stream()
                .map(a -> {
                    InterfaceStat interfaceStat = new InterfaceStat();
                    interfaceStat.setMethod(a.getKey());
                    interfaceStat.setQueryCount(a.getValue());
                    return interfaceStat;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<GroupedURL> getGroupedURL() {
        readInterfaceFile();
        Map<String, List<InterfaceInfo>> map = interfaceInfos.stream()
                .collect(Collectors.groupingBy(
                        //根据/AAA进行分组
                        a -> Splitter.on('/')
                                .omitEmptyStrings()
                                .splitToList(a.getUrl())
                                .get(0),
                        Collectors.toList()));
        List<GroupedURL> list = map.entrySet().stream()
                //转化为GroupURL
                .map(a -> new GroupedURL('/' + a.getKey(),
                        a.getValue().stream()
                                .map(InterfaceInfo::getUrl)
                                //去重
                                .distinct()
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return list;
    }
}
