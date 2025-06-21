package cn.ccc212.eduagent.context;

import cn.ccc212.eduagent.enums.AIModeEnum;
import cn.ccc212.eduagent.pojo.dto.ai.ChatDTO;
import cn.ccc212.eduagent.pojo.dto.ai.DeleteDocumentDTO;
import cn.ccc212.eduagent.pojo.vo.ai.ChatVO;
import cn.ccc212.eduagent.rag.DocumentLoader;
import cn.ccc212.eduagent.strategy.ai.AIStrategy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AIContext {

    @Value("${ai.mode}")
    private String aiMode;

    private final Map<String, AIStrategy> aiStrategyMap;
    private final VectorStore pgVectorStore;
    private final DocumentLoader documentLoader;

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

    public ChatVO chatWithRAG(ChatDTO chatDTO, String systemPrompt) {
        return getAIStrategy().chatWithRAG(chatDTO, systemPrompt);
    }

    public void addDocuments(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            // 加载系统文档
            pgVectorStore.add(documentLoader.loadDefaultMarkdowns());
        } else {
            // TODO 加载用户文档到向量数据库
        }
    }

    public void deleteDocuments(DeleteDocumentDTO deleteDocumentDTO) {
        String userId = deleteDocumentDTO.getUserId();
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        if (StringUtils.isNotBlank(userId)) {
            String fileName = deleteDocumentDTO.getFileName();
            if (StringUtils.isNotBlank(fileName)) {
                pgVectorStore.delete(builder
                        .and(builder.eq("filename", fileName), builder.eq("user_id", userId))
                        .build());
            } else {
                pgVectorStore.delete(builder
                        .eq("user_id", userId)
                        .build());
            }
        } else {
            // 删除系统文档
            pgVectorStore.delete(builder
                    .eq("user_id", "0")
                    .build());
        }
    }
}
