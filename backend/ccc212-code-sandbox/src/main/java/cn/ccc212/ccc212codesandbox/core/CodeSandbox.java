package cn.ccc212.ccc212codesandbox.core;

import cn.ccc212.ccc212codesandbox.entity.ExecuteCodeRequest;
import cn.ccc212.ccc212codesandbox.entity.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
