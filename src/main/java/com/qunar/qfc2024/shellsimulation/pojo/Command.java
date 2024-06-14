package com.qunar.qfc2024.shellsimulation.pojo;

import com.google.common.base.Splitter;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Shell命令
 *
 * @author zhangge
 * @date 2024/6/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    @ApiModelProperty("命令名")
    private String name;

    @ApiModelProperty("选项")
    private List<String> opts;

    @ApiModelProperty("参数")
    private List<String> args;

    public Command(String cmd) {
        opts = new ArrayList<>();
        args = new ArrayList<>();
        List<String> list = new ArrayList<>(Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(cmd));

        name = list.remove(0);
        list.forEach(a -> {
            if (a.trim().startsWith("-")) {
                opts.add(a);
            } else {
                args.add(a);
            }
        });
    }
}
