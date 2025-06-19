## 20. 进阶 I/O 流 (Advanced I/O Streams)

### 20.1. 字节流与字符流转换 (Byte Stream & Character Stream Conversion)

- **知识点：** `InputStreamReader` (字节输入流转字符输入流)；`OutputStreamWriter` (字符输出流转字节输出流)；编码 (UTF-8, GBK 等)。

1. 题目名称：

    使用特定编码读写文本文件

   题目描述：

   - 编写一个程序，将一段包含中文的字符串以 UTF-8 编码写入到一个文件中。
   - 然后，再次打开该文件，以 GBK 编码读取文件内容，并打印。观察乱码现象。
   - 最后，再次以 UTF-8 编码读取文件内容，验证是否能正确显示。 **参考代码结构：**

   ```java
   import java.io.*;
   import java.nio.charset.Charset;
   import java.nio.charset.StandardCharsets;
   
   public class CharsetConversionDemo {
       private static final String FILE_NAME = "multibyte_text.txt";
       private static final String CONTENT = "你好，世界！Hello, World!";
   
       public static void main(String[] args) {
           // 1. 以 UTF-8 写入文件
           try (OutputStreamWriter writer = new OutputStreamWriter(
                                              new FileOutputStream(FILE_NAME),
                                              StandardCharsets.UTF_8)) {
               writer.write(CONTENT);
               System.out.println("Content written to " + FILE_NAME + " with UTF-8 encoding.");
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // 2. 以 GBK 读取文件（预期乱码）
           System.out.println("\n--- Reading with GBK encoding (expected garbled) ---");
           try (InputStreamReader reader = new InputStreamReader(
                                            new FileInputStream(FILE_NAME),
                                            Charset.forName("GBK")); // 指定GBK编码
                BufferedReader bufferedReader = new BufferedReader(reader)) {
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   System.out.println(line);
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // 3. 以 UTF-8 读取文件（预期正常）
           System.out.println("\n--- Reading with UTF-8 encoding (expected correct) ---");
           try (InputStreamReader reader = new InputStreamReader(
                                            new FileInputStream(FILE_NAME),
                                            StandardCharsets.UTF_8); // 指定UTF-8编码
                BufferedReader bufferedReader = new BufferedReader(reader)) {
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   System.out.println(line);
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```

### 20.2. 序列化与反序列化 (Serialization & Deserialization)

- **知识点：** 序列化的概念与目的（将对象状态转换为字节流，以便存储或传输）；`Serializable` 接口；`ObjectOutputStream` (对象输出流)；`ObjectInputStream` (对象输入流)；`transient` 关键字；`serialVersionUID`。

1. **题目名称：** 对象的序列化与反序列化 **题目描述：**

   - 定义一个 `Student` 类，包含 `name` (String), `age` (int), `gpa` (double) 属性。让 `Student` 类实现 `Serializable` 接口。

   - 在 

     ```
     main
     ```

      方法中：

     - 创建一个 `Student` 对象。
     - 将其序列化到一个文件中（例如 `student.ser`）。
     - 然后从文件中反序列化该对象。
     - 打印反序列化后的对象属性，验证数据是否一致。 **参考代码结构：**

   ```java
   import java.io.*;
   
   // Student.java
   class Student implements Serializable {
       private static final long serialVersionUID = 1L; // 建议添加
   
       private String name;
       private int age;
       private double gpa; // 平均绩点
   
       public Student(String name, int age, double gpa) {
           this.name = name;
           this.age = age;
           this.gpa = gpa;
       }
   
       @Override
       public String toString() {
           return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + '}';
       }
   }
   
   // Main.java
   public class SerializationDemo {
       private static final String FILE_NAME = "student.ser";
   
       public static void main(String[] args) {
           // 1. 序列化对象
           Student student = new Student("Alice", 20, 3.8);
           try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
               oos.writeObject(student);
               System.out.println("Student object serialized to " + FILE_NAME);
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // 2. 反序列化对象
           Student deserializedStudent = null;
           try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
               deserializedStudent = (Student) ois.readObject();
               System.out.println("Student object deserialized from " + FILE_NAME);
           } catch (IOException | ClassNotFoundException e) {
               e.printStackTrace();
           }
   
           // 3. 验证反序列化结果
           if (deserializedStudent != null) {
               System.out.println("Deserialized Student: " + deserializedStudent);
           }
       }
   }
   ```

2. **题目名称：** `transient` 关键字的使用 **题目描述：**

   - 在上述 `Student` 类中，将 `gpa` 属性标记为 `transient`。
   - 再次执行序列化和反序列化操作。
   - 打印反序列化后的 `Student` 对象，观察 `gpa` 属性的值，理解 `transient` 关键字的作用。 **参考代码结构：**

   ```java
   // Student.java (修改 gpa 属性)
   class Student implements Serializable {
       private static final long serialVersionUID = 1L;
   
       private String name;
       private int age;
       private transient double gpa; // 将gpa标记为transient
   
       public Student(String name, int age, double gpa) {
           this.name = name;
           this.age = age;
           this.gpa = gpa;
       }
   
       @Override
       public String toString() {
           return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + '}';
       }
   }
   
   // Main.java 保持不变，只需运行即可观察gpa的变化
   public class TransientKeywordDemo {
       private static final String FILE_NAME = "student_transient.ser";
   
       public static void main(String[] args) {
           // 1. 序列化对象
           Student student = new Student("Bob", 22, 3.5);
           try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
               oos.writeObject(student);
               System.out.println("Student object serialized to " + FILE_NAME);
           } catch (IOException e) {
               e.printStackTrace();
           }
   
           // 2. 反序列化对象
           Student deserializedStudent = null;
           try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
               deserializedStudent = (Student) ois.readObject();
               System.out.println("Student object deserialized from " + FILE_NAME);
           } catch (IOException | ClassNotFoundException e) {
               e.printStackTrace();
           }
   
           // 3. 验证反序列化结果 (观察gpa是否为默认值)
           if (deserializedStudent != null) {
               System.out.println("Deserialized Student (with transient gpa): " + deserializedStudent);
           }
       }
   }
   ```
