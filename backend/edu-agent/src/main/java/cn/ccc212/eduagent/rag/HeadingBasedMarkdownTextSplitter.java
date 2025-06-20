package cn.ccc212.eduagent.rag;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义基于 Token 的切词器。
 * 主要按三级标题（###）进行粗粒度切分，空行不再作为切分依据。
 * 对每个Document，先提取代码块，再按标题切分，最后用TokenTextSplitter细粒度切分。
 */
@Component
class HeadingBasedMarkdownTextSplitter {

    // 正则表达式匹配 Java 代码块
    private static final Pattern JAVA_CODE_BLOCK_PATTERN = Pattern.compile("```java\\n([\\s\\S]*?)\\n```");

    // 正则表达式匹配三级标题 (###)，作为主要切分点
    private static final Pattern H3_HEADING_PATTERN = Pattern.compile("^(###\\s.*)$", Pattern.MULTILINE);

    /**
     * 将输入的文档列表进行多阶段切分。
     * 对于每个Document，会提取代码块，按三级标题初步切分，然后进行Token级别的细粒度切分。
     *
     * @param documents 待切分的原始 Document 列表
     * @return 切分后的 Document 列表
     */
    public List<Document> splitDocuments(List<Document> documents) {
        List<Document> finalChunks = new ArrayList<>();

        // 初始化 HeadingBasedMarkdownTextSplitter
        org.springframework.ai.transformer.splitter.TokenTextSplitter tokenSplitter =
                new org.springframework.ai.transformer.splitter.TokenTextSplitter(
                        3000,   // chunkSize
                        350,    // minChunkSizeChars
                        5,      // minChunkLengthToEmbed
                        10000,  // maxNumChunks
                        true    // keepSeparator
                );

        // 遍历输入的每个原始 Document
        for (Document originalDoc : documents) {
            String fullMarkdownContent = originalDoc.getText();
            Map<String, Object> originalMetadata = originalDoc.getMetadata();


            // 第一步：提取代码块，并用占位符替换
            List<String> codeBlocks = new ArrayList<>();
            StringBuilder contentWithoutCode = new StringBuilder();
            Matcher codeMatcher = JAVA_CODE_BLOCK_PATTERN.matcher(fullMarkdownContent);
            int codeBlockIndex = 0;
            while (codeMatcher.find()) {
                codeBlocks.add(codeMatcher.group(1).trim()); // 提取代码内容
                codeMatcher.appendReplacement(contentWithoutCode, "CODE_BLOCK_PLACEHOLDER_" + codeBlockIndex + "\n"); // 替换为占位符
                codeBlockIndex++;
            }
            codeMatcher.appendTail(contentWithoutCode);
            String processedContent = contentWithoutCode.toString();

            // 第二步：基于三级标题进行粗粒度切分
            List<String> coarseGrainedChunks = new ArrayList<>();
            String[] lines = processedContent.split("\\r?\\n"); // 按行分割

            StringBuilder currentChunkBuilder = new StringBuilder();
            String currentSectionHeading = ""; // 用于跟踪当前块所属的最高级别标题（例如，三级标题）

            for (String line : lines) {
                // 检查是否是三级标题
                Matcher h3Matcher = H3_HEADING_PATTERN.matcher(line);

                if (h3Matcher.matches()) { // 如果是三级标题行
                    // 如果当前块不为空，先保存它
                    if (!currentChunkBuilder.isEmpty()) {
                        coarseGrainedChunks.add(currentChunkBuilder.toString().trim());
                        currentChunkBuilder.setLength(0); // 重置
                    }
                    // 更新当前章节标题
                    currentSectionHeading = h3Matcher.group(1).trim();
                    currentChunkBuilder.append(line).append("\n"); // 标题行也添加到当前块中

                } else { // 不是三级标题，也不是空行，就添加到当前块
                    currentChunkBuilder.append(line).append("\n");
                }
            }
            // 添加最后一个粗粒度块
            if (!currentChunkBuilder.isEmpty()) {
                coarseGrainedChunks.add(currentChunkBuilder.toString().trim());
            }

            // 第三步：对每个粗粒度切分块进行处理
            for (String coarseChunkText : coarseGrainedChunks) {
                // 处理代码块占位符，并将其替换回实际代码
                boolean containsPlaceholder = false;
                String finalCoarseChunkText = coarseChunkText; // 使用一个final变量以便lambda表达式或内联使用
                for (int i = 0; i < codeBlocks.size(); i++) {
                    String placeholder = "CODE_BLOCK_PLACEHOLDER_" + i;
                    if (finalCoarseChunkText.contains(placeholder)) {
                        // 将代码块作为单独的文档
                        Document codeDoc = new Document("```java\n" + codeBlocks.get(i) + "\n```", originalMetadata);
                        codeDoc.getMetadata().put("type", "code");
                        codeDoc.getMetadata().put("original_filename", originalMetadata.get("filename"));
                        codeDoc.getMetadata().put("code_block_index", i);
                        // 为代码块添加其所属的标题
                        if (!currentSectionHeading.isEmpty()) {
                            codeDoc.getMetadata().put("parent_heading", currentSectionHeading);
                        }
                        finalChunks.add(codeDoc);
                        finalCoarseChunkText = finalCoarseChunkText.replace(placeholder, ""); // 从文本中移除占位符
                        containsPlaceholder = true;
                    }
                }

                // 对剩余文本（不含代码和已处理的占位符）进行 HeadingBasedMarkdownTextSplitter 细粒度切分
                if (!finalCoarseChunkText.trim().isEmpty()) {
                    Document docForTokenSplit = new Document(finalCoarseChunkText, originalMetadata);
                    // 添加所属的标题元数据
                    if (!currentSectionHeading.isEmpty()) {
                        docForTokenSplit.getMetadata().put("parent_heading", currentSectionHeading);
                    }
                    if (containsPlaceholder) {
                        docForTokenSplit.getMetadata().put("contains_removed_code_placeholder", true);
                    }

                    List<Document> subDocs = tokenSplitter.apply(List.of(docForTokenSplit));
                    finalChunks.addAll(subDocs);
                }
            }
        }
        return finalChunks;
    }
}