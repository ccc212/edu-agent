## 22. Java NIO / NIO.2 (New I/O)

### 22.1. NIO 核心概念 (NIO Core Concepts)

- **知识点：** NIO 的引入（非阻塞 I/O，高性能）；缓冲区 (Buffer)；通道 (Channel)；选择器 (Selector)；非阻塞模式；与传统 I/O (BIO) 的区别。

1. 题目名称：

    使用 NIO 

   ```
   ByteBuffer
   ```

    读写文件

   题目描述：

   - 创建一个文本文件 `nio_test.txt`，并向其中写入一段字符串（例如 "Hello NIO World!"）。
   - 使用 `FileChannel` 和 `ByteBuffer` 读取该文件中的内容，并打印到控制台。
   - 使用 `ByteBuffer` 和 `FileChannel` 将另一段字符串（例如 "Appending with NIO."）追加写入到同一个文件中。
   - 再次读取文件内容，验证追加是否成功。 **参考代码结构：**

   ```java
   import java.io.IOException;
   import java.nio.ByteBuffer;
   import java.nio.channels.FileChannel;
   import java.nio.file.Paths;
   import java.nio.file.StandardOpenOption;
   import java.nio.file.Files; // For initial file creation
   
   public class NioByteBufferFileOps {
       private static final String FILE_NAME = "nio_test.txt";
   
       public static void main(String[] args) {
           String initialContent = "Hello NIO World!";
           String appendContent = "Appending with NIO.";
   
           // Step 1: Initial write with Files.write for simplicity to create the file
           try {
               Files.write(Paths.get(FILE_NAME), initialContent.getBytes());
               System.out.println("Initial content written to " + FILE_NAME);
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // Step 2: Read file using FileChannel and ByteBuffer
           System.out.println("\n--- Reading file content ---");
           try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_NAME), StandardOpenOption.READ)) {
               ByteBuffer buffer = ByteBuffer.allocate(1024); // Allocate a buffer
               int bytesRead = fileChannel.read(buffer); // Read bytes into buffer
               if (bytesRead != -1) {
                   buffer.flip(); // Prepare buffer for reading
                   byte[] data = new byte[bytesRead];
                   buffer.get(data);
                   System.out.println("Content: " + new String(data));
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // Step 3: Append content using FileChannel and ByteBuffer
           System.out.println("\n--- Appending content ---");
           try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_NAME), StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
               ByteBuffer buffer = ByteBuffer.wrap(appendContent.getBytes()); // Wrap bytes into buffer
               fileChannel.write(buffer); // Write buffer to channel
               System.out.println("Content appended to " + FILE_NAME);
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // Step 4: Read file again to verify
           System.out.println("\n--- Reading file content after append ---");
           try (FileChannel fileChannel = FileChannel.open(Paths.get(FILE_NAME), StandardOpenOption.READ)) {
               ByteBuffer buffer = ByteBuffer.allocate(1024);
               int bytesRead = fileChannel.read(buffer);
               if (bytesRead != -1) {
                   buffer.flip();
                   byte[] data = new byte[bytesRead];
                   buffer.get(data);
                   System.out.println("Content: " + new String(data));
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```

### 22.2. NIO.2 (Path API & Files Class)

- **知识点：** `java.nio.file` 包；`Path` 接口（表示文件或目录路径）；`Paths` 工具类；`Files` 工具类（文件操作，如创建、删除、复制、移动、读取、写入）；`DirectoryStream` (遍历目录)；文件属性。

1. 题目名称：

    使用 Path 和 Files 进行文件操作

   题目描述：

   - 定义一个基本目录路径（例如 `temp_files`）。
   - 在该目录下创建一个新文件 `my_file.txt`。
   - 向 `my_file.txt` 写入一行文本。
   - 读取 `my_file.txt` 的内容并打印。
   - 将 `my_file.txt` 复制为 `my_file_copy.txt`。
   - 重命名 `my_file_copy.txt` 为 `renamed_file.txt`。
   - 列出 `temp_files` 目录下的所有文件和目录名。
   - 最后删除 `renamed_file.txt` 和 `temp_files` 目录（确保目录为空）。
   - 处理可能发生的 `IOException`。 **参考代码结构：**

   ```java
   import java.io.IOException;
   import java.nio.file.*;
   import java.util.List;
   
   public class Nio2FileOperations {
       private static final String BASE_DIR_NAME = "temp_files";
       private static final String FILE_NAME = "my_file.txt";
       private static final String COPY_FILE_NAME = "my_file_copy.txt";
       private static final String RENAMED_FILE_NAME = "renamed_file.txt";
   
       public static void main(String[] args) {
           Path baseDirPath = Paths.get(BASE_DIR_NAME);
           Path filePath = baseDirPath.resolve(FILE_NAME);
           Path copyFilePath = baseDirPath.resolve(COPY_FILE_NAME);
           Path renamedFilePath = baseDirPath.resolve(RENAMED_FILE_NAME);
   
           try {
               // 1. Create base directory if it doesn't exist
               if (!Files.exists(baseDirPath)) {
                   Files.createDirectory(baseDirPath);
                   System.out.println("Directory created: " + baseDirPath);
               }
   
               // 2. Create a new file
               if (!Files.exists(filePath)) {
                   Files.createFile(filePath);
                   System.out.println("File created: " + filePath);
               }
   
               // 3. Write to the file
               String contentToWrite = "This is a test line for NIO.2.";
               Files.write(filePath, contentToWrite.getBytes(), StandardOpenOption.WRITE);
               System.out.println("Content written to: " + filePath);
   
               // 4. Read from the file
               System.out.println("\n--- Reading file content ---");
               List<String> lines = Files.readAllLines(filePath);
               lines.forEach(System.out::println);
   
               // 5. Copy the file
               Files.copy(filePath, copyFilePath, StandardCopyOption.REPLACE_EXISTING);
               System.out.println("File copied to: " + copyFilePath);
   
               // 6. Rename the copied file
               Files.move(copyFilePath, renamedFilePath, StandardCopyOption.REPLACE_EXISTING);
               System.out.println("File renamed to: " + renamedFilePath);
   
               // 7. List all files/directories in the base directory
               System.out.println("\n--- Listing directory contents ---");
               try (DirectoryStream<Path> stream = Files.newDirectoryStream(baseDirPath)) {
                   for (Path entry : stream) {
                       System.out.println(entry.getFileName());
                   }
               }
   
           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               // 8. Clean up: Delete files and directory
               try {
                   System.out.println("\n--- Cleaning up ---");
                   if (Files.exists(renamedFilePath)) {
                       Files.delete(renamedFilePath);
                       System.out.println("Deleted: " + renamedFilePath);
                   }
                   if (Files.exists(baseDirPath)) {
                       // For non-empty directories, use walkFileTree or recursive delete
                       // For this example, assuming only renamedFilePath exists in it now
                       Files.delete(baseDirPath);
                       System.out.println("Deleted directory: " + baseDirPath);
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
   }
   ```
