## 16. JVM 内存模型 (JVM Memory Model)

### 16.1. 运行时数据区 (Runtime Data Areas)

- **知识点：** JVM 内存模型概览；程序计数器 (Program Counter Register)；Java 虚拟机栈 (Java Virtual Machine Stacks)；本地方法栈 (Native Method Stacks)；Java 堆 (Java Heap)；方法区 (Method Area)；运行时常量池 (Runtime Constant Pool)。
- **注意：** JVM 内存模型相关的题目通常难以通过简单的编程题来直接验证，更多的是概念理解和现象分析。以下题目旨在通过代码行为来间接展示某些内存区域的特性。

1. **题目名称：** 栈内存溢出模拟 **题目描述：** 编写一个递归方法，不设置终止条件，使其无限递归调用自身。运行此程序并观察其抛出的异常类型（`StackOverflowError`），以此理解 Java 虚拟机栈的作用和栈溢出的概念。 **参考代码结构：**

   ```java
   public class StackOverflowDemo {
       public static void infiniteRecursion() {
           // 在这里编写你的递归调用代码
       }
   
       public static void main(String[] args) {
           infiniteRecursion();
       }
   }
   ```

2. **题目名称：** 堆内存溢出模拟 **题目描述：** 编写一个程序，不断地创建大量对象（例如，将字符串添加到 `ArrayList` 中），但不释放它们，直到耗尽堆内存。运行此程序并观察其抛出的异常类型（`OutOfMemoryError: Java heap space`），以此理解 Java 堆的作用。 **参考代码结构：**

   ```java
   import java.util.ArrayList;
   import java.util.List;
   
   public class HeapOOMDemo {
       public static void main(String[] args) {
           List<String> list = new ArrayList<>();
           int count = 0;
           try {
               while (true) {
                   list.add(new String("Memoryhogger" + count++));
                   // 为了更快地消耗内存，可以创建更大的对象，或者不使用String pool
               }
           } catch (OutOfMemoryError e) {
               System.out.println("Caught OutOfMemoryError: " + e.getMessage());
               System.out.println("Objects created before OOM: " + count);
           }
       }
   }
   ```
