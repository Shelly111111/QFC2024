package com.qunar.qfc2024.loganalysis.service.Impl;

import com.qunar.qfc2024.loganalysis.pojo.FrequentInterface;
import com.qunar.qfc2024.loganalysis.pojo.GroupedURL;
import com.qunar.qfc2024.loganalysis.service.AccessService;
import org.springframework.web.servlet.tags.Param;

import java.util.List;

/**
 * @author zhangge
 * @date 2024/6/10
 */
public class AccessServiceImpl implements AccessService {
    @Override
    public Long getQueryCount() {
        return null;
    }

    @Override
    public List<FrequentInterface> getFrequentInterface(Long count) {
        return null;
    }

    @Override
    public List<Param> getQueryMethodCount() {
        return null;
    }

    @Override
    public List<GroupedURL> getGroupedURL() {
        return null;
    }
}
