package com.qunar.qfc2024.loganalysis.pojo;

import com.qunar.qfc2024.loganalysis.enumeration.QueryMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 频繁请求接口
 * @author zhangge
 * @date 2024/6/10
 */
@Data
@AllArgsConstructor
@Validated
public class FrequentInterface {

    @ApiModelProperty("请求方式")
    @NotNull(message = "请求方式不能为空")
    private QueryMethod method;

    @ApiModelProperty("请求地址")
    @NotBlank(message = "请求地址不能为空")
    private String url;

    @ApiModelProperty("请求数量")
    @Min(value = 0, message = "请求数量应大于等于0")
    private Long queryCount;
}
