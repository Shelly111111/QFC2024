package com.qunar.qfc2024.codelines.service;

/**
 * 有效代码行数统计
 *
 * @author zhangge
 * @date 2024/6/11
 */
public interface CodeLineService {

    /**
     * 获取有效代码行数
     *
     * @return 有效代码行数
     * @author zhangge
     * @date 2024/6/11
     */
    Long getCodeLineCount();
}
