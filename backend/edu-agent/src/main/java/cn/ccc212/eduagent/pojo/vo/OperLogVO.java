package cn.ccc212.eduagent.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class OperLogVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    private Long operId;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
//    private String operatorType;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 主机地址
     */
    private String operIp;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（1正常 0异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 执行时长
     */
    private long executeTime;

}
