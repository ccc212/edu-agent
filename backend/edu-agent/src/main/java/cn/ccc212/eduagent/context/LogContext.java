package cn.ccc212.eduagent.context;

import cn.ccc212.eduagent.enums.LogModeEnum;
import cn.ccc212.eduagent.pojo.entity.OperLog;
import cn.ccc212.eduagent.strategy.log.LogStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LogContext {

    @Value("${edu-agent.log.mode}")
    private String logMode;

    private final Map<String, LogStrategy> logStrategyMap;

    private LogStrategy getLogStrategy() {
        String strategyBeanName = LogModeEnum.getStrategy(logMode);
        if (strategyBeanName == null) {
            throw new IllegalStateException("Unknown cache mode: " + logMode);
        }

        LogStrategy strategy = logStrategyMap.get(strategyBeanName);
        if (strategy == null) {
            throw new IllegalStateException("Cache strategy bean not found for mode: " + logMode + ", bean name: " + strategyBeanName);
        }
        return strategy;
    }

    public void log(OperLog operLog) {
        String jsonResult = operLog.getJsonResult();
        String errorMsg = operLog.getErrorMsg();

        // 如果jsonResult为空，则设置为null
        if (StringUtils.isBlank(jsonResult)) {
            operLog.setJsonResult("null");
        }
        // 截取jsonResult前2000个的字符
        else if (jsonResult.length() > 2000) {
            operLog.setJsonResult(jsonResult.substring(0, 2000));
        }

        // 如果errorMsg为空，则设置为null
        if (StringUtils.isBlank(errorMsg)) {
            operLog.setErrorMsg("null");
        }
        // 截取errorMsg前2000个的字符
        else if (errorMsg.length() > 2000) {
            operLog.setErrorMsg(errorMsg.substring(0, 2000));
        }

        // 调用对应的日志策略
        getLogStrategy().log(operLog);
    }
}
