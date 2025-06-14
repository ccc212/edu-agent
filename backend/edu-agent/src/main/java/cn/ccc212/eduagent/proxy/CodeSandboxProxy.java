package cn.ccc212.eduagent.proxy;

import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.ccc212.eduagent.strategy.codesandbox.CodeSandboxStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandboxStrategy {

    private final CodeSandboxStrategy codeSandboxStrategy;

    public CodeSandboxProxy(CodeSandboxStrategy codeSandboxStrategy) {
        this.codeSandboxStrategy = codeSandboxStrategy;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandboxStrategy.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
