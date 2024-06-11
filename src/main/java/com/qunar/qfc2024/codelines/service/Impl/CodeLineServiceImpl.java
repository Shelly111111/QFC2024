package com.qunar.qfc2024.codelines.service.Impl;

import com.qunar.qfc2024.codelines.service.CodeLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * 有效代码行数统计
 *
 * @author zhangge
 * @date 2024/6/11
 */
@Service
@Slf4j
public class CodeLineServiceImpl implements CodeLineService {

    @Value("${attachments.basePath}")
    private String basePath;

    @Value("${attachments.questions.two.folder}")
    private String folder;

    @Value("${attachments.questions.two.file}")
    private String file;

    @Value("${attachments.questions.two.output}")
    private String output;

    @Override
    public Long getCodeLineCount() {
        Long count = 0L;
        //读取测试文件
        Resource resource = new ClassPathResource(Paths.get(basePath, folder, file).toString());
        String rootPath = System.getProperty("user.dir");
        try {
            //获取流
            InputStream inputStream = resource.getInputStream();
            //扫描每行
            Scanner scanner = new Scanner(inputStream);
            //获取输出文件
            File file = new File(Paths.get(rootPath, "out", output).toString());
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                System.out.println(newFile);
            }
            //获取输出流
            OutputStream outputStream = new FileOutputStream(file);

            boolean canAdd = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String s = line.trim();
                //如果是空白行
                if (StringUtils.isBlank(s)) {
                    continue;
                }

                //处理"/* xxxxx */"类注释
                if (!canAdd) {
                    if (s.endsWith("*/")) {
                        canAdd = true;
                    }
                    continue;
                }
                if (s.startsWith("/*")) {
                    if (!s.endsWith("*/")) {
                        canAdd = false;
                    }
                    continue;
                }

                //处理"//"类注释
                if (s.startsWith("//")) {
                    continue;
                }
//                outputStream.write(line.getBytes());
//                outputStream.write('\n');
                count++;
            }

            //写入文件
            outputStream.write(count.toString().getBytes());
            outputStream.flush();

            //关闭scanner
            scanner.close();
            //关闭流
            inputStream.close();
            //关闭输出流
            outputStream.close();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return count;
    }
}
