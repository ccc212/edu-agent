package cn.ccc212.eduagent.judge.codesandbox.impl;

import cn.ccc212.eduagent.judge.codesandbox.CodeSandbox;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱 (调用网上现成的代码沙箱)
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
