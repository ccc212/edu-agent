package cn.ccc212.ccc212codesandbox.impl;

import cn.ccc212.ccc212codesandbox.core.JavaCodeSandboxTemplate;
import cn.ccc212.ccc212codesandbox.entity.ExecuteCodeRequest;
import cn.ccc212.ccc212codesandbox.entity.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Java 原生代码沙箱实现 (直接复用模板方法)
 */
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {
    @Override
    public File saveCodeToFile(String code) {
        return super.saveCodeToFile(code);
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}
