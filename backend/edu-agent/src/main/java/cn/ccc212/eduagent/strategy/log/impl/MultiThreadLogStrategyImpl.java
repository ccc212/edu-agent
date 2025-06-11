package cn.ccc212.eduagent.strategy.log.impl;

import cn.ccc212.eduagent.pojo.entity.OperLog;
import cn.ccc212.eduagent.service.IOperLogService;
import cn.ccc212.eduagent.strategy.log.LogStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class MultiThreadLogStrategyImpl implements LogStrategy {

    private final IOperLogService operLogService;

    @Override
    public void log(OperLog operLog) {
        CompletableFuture.runAsync(() -> {
            operLogService.save(operLog);
        });
    }
}
