package cn.ccc212.eduagent.strategy.codesandbox.impl;

import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.ccc212.eduagent.strategy.codesandbox.CodeSandboxStrategy;
import org.springframework.stereotype.Component;

/**
 * 第三方代码沙箱 (调用网上现成的代码沙箱)
 */
@Component
public class ThirdPartyCodeSandboxStrategeImpl implements CodeSandboxStrategy {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
