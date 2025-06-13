package cn.ccc212.eduagent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    // 通用状态码
    SUCCESS(0, "操作成功"),
    FAIL(-10001, "操作失败"),
    SERVER_ERROR(-10002, "服务器内部错误"),
    PARAM_ERROR(-10003, "参数校验失败"),
    NOT_FOUND(-10004, "未找到"),
    NO_PERMISSION(-10005, "权限不足"),

    // 登录相关
    NO_LOGIN(-10101, "用户未登录"),
    PASSWORD_ERROR(-10102, "密码错误"), 
    USERNAME_EXIST(-10103, "用户名已存在"),
    TOKEN_EXPIRED(-10104, "Token已过期"),
    TOKEN_INVALID(-10105, "Token验证失败"),
    ACCOUNT_NOT_FOUND(-10106, "账号不存在"),
    LOGIN_FAILED(-10107, "登录失败"),
    USER_NOT_FOUND(-10108, "用户不存在"),
    CREATE_TIME_START_AFTER_END(-10109, "创建时间起始不能在创建时间结束之后"),
    EMAIL_EXIST(-10110, "邮箱已存在"),
    EMAIL_CODE_ERROR(-10111, "邮箱验证码错误"),
    EMAIL_CODE_NOT_EXIST(-10112, "未发送验证码"),

    // 角色相关
    ROLE_NOT_EXISTS(-10201, "角色不存在"),
    NEED_ADMIN(-10202, "需管理员权限"),

    // 班级相关
    CLASS_EXIST(-10301, "班级已存在"),
    CLASS_NOT_YOURS(-10302, "班级不属于当前用户"),
    CLASS_NOT_EXISTS(-10303, "班级不存在"),
    STUDENT_EXIST(-10304, "学生已存在"),

    // 判题相关
    API_REQUEST_ERROR(-10401, "API请求错误"),

    // 其他
    PORT_OCCUPIED(-90001, "端口被占用"),
    TOO_MANY_REQUESTS(-90002, "访问过于频繁");


    private final Integer code;

    private final String desc;
}
