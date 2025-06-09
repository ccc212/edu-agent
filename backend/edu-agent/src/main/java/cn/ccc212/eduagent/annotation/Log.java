package cn.ccc212.eduagent.annotation;



import cn.ccc212.eduagent.enums.BusinessTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块名称
     */
    String title() default "";

    /**
     * 业务操作类型
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应数据
     */
    boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};
}
