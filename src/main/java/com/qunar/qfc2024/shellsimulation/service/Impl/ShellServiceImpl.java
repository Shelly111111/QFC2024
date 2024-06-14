package com.qunar.qfc2024.shellsimulation.service.Impl;

import com.google.common.base.Splitter;
import com.qunar.qfc2024.shellsimulation.common.Shell;
import com.qunar.qfc2024.shellsimulation.pojo.Command;
import com.qunar.qfc2024.shellsimulation.pojo.Result;
import com.qunar.qfc2024.shellsimulation.service.ShellService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Shell模拟器
 * <p>
 * 目前考虑到方法不多，暂时只将shell命令划分到方法一层，
 * 对于未来加入其他命令的拓展应对每个shell命令设置为不同
 * 类，将相应opt选项设置为类中方法
 *
 * @author zhangge
 * @date 2024/6/14
 */
@Service
@Slf4j
public class ShellServiceImpl implements ShellService {

    @Override
    public void run(String command) {

        //创建Shell命令处理器
        Shell shell = new Shell();

        //分解command
        List<Command> cmds = Splitter.on("|")
                .trimResults().omitEmptyStrings()
                .splitToList(command)
                //转化为Command
                .stream().map(Command::new)
                .collect(Collectors.toList());


        //文件名为第一个命令的最后一个参数
        String filename = cmds.get(0).getArgs().remove(cmds.get(0).getArgs().size() - 1);
        //获取文件
        File file = new File(filename);

        try {
            Result context = new Result<>();
            //循环读取每一行，这里不考虑一行数据量过大的超大型文件
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                context = new Result<>(scanner.nextLine(), false);
                //根据命令处理改行
                for (Command cmd : cmds) {
                    //根据命令名获取相关方法
                    Method method = Shell.class.getMethod(cmd.getName(), Result.class, List.class, List.class);
                    //方法调用
                    context = (Result) method.invoke(shell, context, cmd.getOpts(), cmd.getArgs());
                }
                //判断结果并输出
                if (Objects.isNull(context)) {
                    continue;
                }
                if (!context.isPrint()) {
                    System.out.println(context.getData());
                }
            }
            if (Objects.nonNull(context) && context.isPrint()) {
                System.out.println(context.getData());
            }
            //关闭流
            scanner.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
