package cn.ccc212.eduagent.rag;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 向量数据库配置（初始化基于内存的向量数据库 Bean）
 */
@Configuration
@RequiredArgsConstructor
public class EduAgentVectorStoreConfig {

    private final DocumentLoader documentLoader;
    private final HeadingBasedMarkdownTextSplitter headingBasedMarkdownTextSplitter;

    @Bean
    VectorStore eduAgentVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        // 加载文档
        List<Document> documentList = documentLoader.loadMarkdowns();
        // 自主切分文档
        List<Document> splitDocuments = headingBasedMarkdownTextSplitter.splitDocuments(documentList);
        simpleVectorStore.add(splitDocuments);
        return simpleVectorStore;
    }
}
