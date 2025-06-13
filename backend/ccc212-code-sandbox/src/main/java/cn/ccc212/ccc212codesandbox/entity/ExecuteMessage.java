package cn.ccc212.ccc212codesandbox.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 进程执行信息
 */
@Data
@Accessors(chain = true)
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;

    private Long memory;
}
