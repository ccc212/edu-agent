package cn.ccc212.eduagent.pojo.entity.codesandbox;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 判题信息
 */
@Data
@Accessors(chain = true)
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间（ms）
     */
    private Long time;

    /**
     * 消耗内存（KB）
     */
    private Long memory;
}
