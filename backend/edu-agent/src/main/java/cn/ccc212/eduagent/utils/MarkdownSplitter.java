package cn.ccc212.eduagent.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownSplitter {

    public static void main(String[] args) {
        String inputFilePath = "E:\\gitProject\\edu-agent\\backend\\edu-agent\\src\\main\\resources\\document\\1.md";
        String outputDirectoryPath = "E:\\gitProject\\edu-agent\\backend\\edu-agent\\src\\main\\resources\\document";

        try {
            splitMarkdownByChapters(inputFilePath, outputDirectoryPath);
            System.out.println("Markdown file split successfully!");
        } catch (IOException e) {
            System.err.println("Error splitting Markdown file: " + e.getMessage());
        }
    }

    public static void splitMarkdownByChapters(String inputFilePath, String outputDirectoryPath) throws IOException {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IOException("Input file does not exist or is not a file: " + inputFilePath);
        }

        File outputDir = new File(outputDirectoryPath);
        if (!outputDir.exists()) {
            Files.createDirectories(Paths.get(outputDirectoryPath));
        } else if (!outputDir.isDirectory()) {
            throw new IOException("Output path is not a directory: " + outputDirectoryPath);
        }

        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }
        }

        Pattern titlePattern = Pattern.compile("^(##\\s)(?:\\d+\\.\\s*)?([^\\n\\(\uff08]+?)(?:\\s*[(\uff08].*?[)\uff09])?$");

        int currentStartIndex = 0;
        String currentChapterTitle = null;

        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            Matcher matcher = titlePattern.matcher(line);

            if (matcher.matches()) {
                // 找到了一个 ## 标题
                if (currentChapterTitle != null) {
                    // 如果已经有正在处理的章节，保存它
                    String contentToSave = String.join(System.lineSeparator(), allLines.subList(currentStartIndex, i));
                    saveContentToFile(outputDirectoryPath, currentChapterTitle, contentToSave);
                }

                // 开始新的章节
                currentStartIndex = i;
                currentChapterTitle = matcher.group(2).trim();
            }
        }

        // 处理最后一个章节或整个文件（如果没有 ## 标题）
        if (currentStartIndex < allLines.size()) {
            String contentToSave = String.join(System.lineSeparator(), allLines.subList(currentStartIndex, allLines.size()));
            String fileName = (currentChapterTitle != null) ? currentChapterTitle : "untitled_content";
            saveContentToFile(outputDirectoryPath, fileName, contentToSave);
        }
    }

    private static void saveContentToFile(String outputDirectoryPath, String baseFileName, String content) throws IOException {
        // 清理文件名，确保只包含合法字符，避免路径问题
        String cleanedFileName = baseFileName.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5_\\-]", ""); // 只保留中文、英文、数字、下划线、中划线
        if (cleanedFileName.isEmpty()) {
            cleanedFileName = "untitled_section_" + System.currentTimeMillis(); // 如果清理后为空，使用时间戳
        }

        String fileName = cleanedFileName + ".md";
        File outputFile = new File(outputDirectoryPath, fileName);

        // 确保父目录存在
        Files.createDirectories(outputFile.getParentFile().toPath());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(content);
        }
        System.out.println("Saved: " + outputFile.getAbsolutePath());
    }
}