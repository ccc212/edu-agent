package cn.ccc212.eduagent.context;

import cn.ccc212.eduagent.enums.AIModeEnum;
import cn.ccc212.eduagent.enums.LogModeEnum;
import cn.ccc212.eduagent.pojo.dto.ai.ChatDTO;
import cn.ccc212.eduagent.pojo.vo.ai.ChatVO;
import cn.ccc212.eduagent.strategy.ai.AIStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AIContext {

    @Value("${ai.mode}")
    private String aiMode;

    private final Map<String, AIStrategy> aiStrategyMap;

    private AIStrategy getAIStrategy() {
        String strategyBeanName = AIModeEnum.getStrategy(aiMode);
        if (strategyBeanName == null) {
            throw new IllegalStateException("Unknown cache mode: " + aiMode);
        }

        AIStrategy strategy = aiStrategyMap.get(strategyBeanName);
        if (strategy == null) {
            throw new IllegalStateException("Cache strategy bean not found for mode: " + aiMode + ", bean name: " + strategyBeanName);
        }
        return strategy;
    }

    public ChatVO chat(ChatDTO chatDTO, String systemPrompt) {
        return getAIStrategy().chat(chatDTO, systemPrompt);
    }


}
