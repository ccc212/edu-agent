package cn.ccc212.eduagent.context;

import cn.ccc212.eduagent.enums.CacheModeEnum;
import cn.ccc212.eduagent.enums.CodeSandboxModeEnum;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.ccc212.eduagent.proxy.CodeSandboxProxy;
import cn.ccc212.eduagent.strategy.cache.CacheStrategy;
import cn.ccc212.eduagent.strategy.codesandbox.CodeSandboxStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CodeSandboxContext {

    @Value("${codesandbox.mode:example}")
    private String codeSandboxMode;

    private final Map<String, CodeSandboxStrategy> codeSandboxStrategyMap;

    private CodeSandboxStrategy getCurrentStrategy() {
        String strategyBeanName = CodeSandboxModeEnum.getStrategy(codeSandboxMode);
        if (strategyBeanName == null) {
            throw new IllegalStateException("Unknown cache mode: " + codeSandboxMode);
        }

        CodeSandboxStrategy strategy = codeSandboxStrategyMap.get(strategyBeanName);
        if (strategy == null) {
            throw new IllegalStateException("Cache strategy bean not found for mode: " + codeSandboxMode + ", bean name: " + strategyBeanName);
        }
        return strategy;
    }

    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        CodeSandboxStrategy codeSandboxStrategy = getCurrentStrategy();
        codeSandboxStrategy = new CodeSandboxProxy(codeSandboxStrategy);
        return codeSandboxStrategy.executeCode(executeCodeRequest);
    }

}
