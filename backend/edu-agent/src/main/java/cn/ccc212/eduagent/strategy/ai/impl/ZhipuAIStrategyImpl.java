package cn.ccc212.eduagent.strategy.ai.impl;

import cn.ccc212.eduagent.advisor.MyLoggerAdvisor;
import cn.ccc212.eduagent.advisor.ReReadingAdvisor;
import cn.ccc212.eduagent.pojo.dto.ai.ChatDTO;
import cn.ccc212.eduagent.pojo.vo.ai.ChatVO;
import cn.ccc212.eduagent.strategy.ai.AIStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@RequiredArgsConstructor
public class ZhipuAIStrategyImpl implements AIStrategy {

    private final ChatModel zhiPuAiChatModel;
    private final ChatMemory inMemoryChatMemory;

    @Override
    public ChatVO chat(ChatDTO chatDTO, String systemPrompt) {
        ChatClient chatClient = ChatClient.builder(zhiPuAiChatModel)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(inMemoryChatMemory)
                        // 日志 Advisor
//                        , new MyLoggerAdvisor()
                        // 自定义推理增强 Advisor，可按需开启
                        , new ReReadingAdvisor()
                )
                .build();

        String chatId = chatDTO.getChatId();
        if (StringUtils.isBlank(chatId)) {
            chatId = UUID.randomUUID().toString();
        }

        String finalChatId = chatId;
        return new ChatVO().setResponse(chatClient
                        .prompt()
                        .user(chatDTO.getContent())
                        .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, finalChatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 300))
                        .call()
                        .content()
                )
                .setChatId(finalChatId);
    }

}
