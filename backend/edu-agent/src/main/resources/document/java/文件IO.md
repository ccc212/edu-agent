## 7. 文件 I/O (File I/O)

### 7.1. 文本文件读写 (Text File I/O)

- **知识点：** `File` 类（文件操作）；`FileReader` 和 `BufferedReader`（字符流读取）；`FileWriter` 和 `BufferedWriter`（字符流写入）；`Scanner` 读取文件；`try-with-resources` 语句。

1. **题目名称：** 读取并打印文件内容 **题目描述：** 接收一个文件路径。读取该文本文件的所有内容，并逐行打印到控制台。如果文件不存在或读取失败，捕获并打印相应的 `IOException` 信息。 **输入示例：** `./input.txt` (假设 `input.txt` 存在并包含多行文本) **输出示例：** (文件内容) **参考代码结构：**

   ```java
   import java.io.BufferedReader;
   import java.io.FileReader;
   import java.io.IOException;
   import java.util.Scanner;
   public class ReadFile {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           String filePath = scanner.nextLine();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```

2. **题目名称：** 写入文本到文件 **题目描述：** 接收一个文件路径和一个字符串 `content`。将 `content` 写入到指定文件中。如果文件不存在则创建，如果存在则覆盖其内容。如果写入失败，捕获并打印 `IOException` 信息。 **输入示例：**

   ```
   ./output.txt
   Hello Java! This is a test.
   ```

   **输出示例：** (无控制台输出，但 `output.txt` 文件将被创建或更新) **参考代码结构：**

   ```java
   import java.io.BufferedWriter;
   import java.io.FileWriter;
   import java.io.IOException;
   import java.util.Scanner;
   public class WriteFile {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           String filePath = scanner.nextLine();
           String content = scanner.nextLine();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```