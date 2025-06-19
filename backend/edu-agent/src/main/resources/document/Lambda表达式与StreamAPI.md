## 10. Lambda 表达式与 Stream API (Lambda Expressions & Stream API)

### 10.1. Lambda 表达式 (Lambda Expressions)

- **知识点：** 函数式接口的概念；Lambda 表达式的语法；作为方法参数；作为事件处理器。

1. 题目名称：

    使用 Lambda 表达式排序列表

   题目描述：

   - 创建一个 `List<String>`，包含一些字符串。
   - 使用 Lambda 表达式作为 `Collections.sort()` 方法的比较器，按照字符串长度对列表进行升序排序。
   - 打印排序后的列表。 **输入示例：** `["apple", "banana", "cat", "elephant"]` **输出示例：** `[cat, apple, banana, elephant]` **参考代码结构：**

​    import java.util.ArrayList; import java.util.Arrays; import java.util.Collections; import java.util.List;



````
public class LambdaSortList {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>(Arrays.asList("apple", "banana", "cat", "elephant"));
        System.out.println("Original List: " + words);
        // 在这里使用Lambda表达式进行排序
        System.out.println("Sorted List: " + words);
    }
}
```
````

1. 题目名称：

    Lambda 表达式实现简单计算器

   题目描述：

   - 定义一个函数式接口 `Calculator`，包含一个抽象方法 `calculate(int a, int b)`。
   - 在 `main` 方法中，使用 Lambda 表达式实现加、减、乘、除（考虑除零）四种运算，并分别赋值给 `Calculator` 接口的实例。
   - 调用这些实例执行运算并打印结果。 **参考代码结构：**

   Java

   ```
   // Calculator.java
   @FunctionalInterface
   interface Calculator {
       int calculate(int a, int b);
   }
   
   // Main.java
   public class LambdaCalculator {
       public static void main(String[] args) {
           // 使用Lambda表达式实现加法
           Calculator addition = (a, b) -> {
               // 在这里编写加法逻辑
               return a + b;
           };
   
           // 使用Lambda表达式实现减法
           Calculator subtraction = (a, b) -> {
               // 在这里编写减法逻辑
               return a - b;
           };
   
           // 在这里实现乘法和除法
   
           System.out.println("5 + 3 = " + addition.calculate(5, 3));
           System.out.println("10 - 4 = " + subtraction.calculate(10, 4));
           // 打印乘法和除法结果
       }
   }
   ```

### 10.2. Stream API

- **知识点：** Stream 的概念（数据处理管道）；Stream 的创建；中间操作（`filter`, `map`, `sorted`, `distinct`, `limit`, `skip`）；终止操作（`forEach`, `count`, `sum`, `average`, `reduce`, `collect`）；`Optional` 类。

1. **题目名称：** 筛选偶数并求和 **题目描述：**

   - 创建一个整数列表。
   - 使用 Stream API，筛选出列表中的所有偶数，然后计算这些偶数的和并打印。 **输入示例：** `[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]` **输出示例：** `Sum of even numbers: 30` **参考代码结构：**

   Java

   ```
   import java.util.Arrays;
   import java.util.List;
   import java.util.OptionalInt;
   
   public class StreamFilterSum {
       public static void main(String[] args) {
           List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
           // 在这里使用Stream API进行操作
           System.out.println("Sum of even numbers: " + sumOfEvens);
       }
   }
   ```

2. **题目名称：** 字符串列表转换与去重 **题目描述：**

   - 创建一个字符串列表，其中可能包含重复项和大小写混合的字符串。
   - 使用 Stream API：
     - 将所有字符串转换为小写。
     - 去除重复的字符串。
     - 将结果收集到一个新的 `List` 中并打印。 **输入示例：** `["Apple", "banana", "APPLE", "Orange", "banana"]` **输出示例：** `[apple, banana, orange]` **参考代码结构：**

   Java

   ```
   import java.util.Arrays;
   import java.util.List;
   import java.util.stream.Collectors;
   
   public class StreamStringOps {
       public static void main(String[] args) {
           List<String> words = Arrays.asList("Apple", "banana", "APPLE", "Orange", "banana");
           // 在这里使用Stream API进行操作
           System.out.println("Processed List: " + processedWords);
       }
   }
   ```

3. **题目名称：** 收集到 Map：学生分数映射 **题目描述：**

   - 创建一个 `Student` 类，包含 `name` (String) 和 `score` (int) 属性。
   - 创建一个 `List<Student>`。
   - 使用 Stream API 将学生列表收集到一个 `Map<String, Integer>` 中，其中键是学生姓名，值是学生分数。
   - 打印这个 `Map`。 **参考代码结构：**

   Java

   ```
   import java.util.ArrayList;
   import java.util.List;
   import java.util.Map;
   import java.util.stream.Collectors;
   
   // Student.java
   class Student {
       private String name;
       private int score;
   
       public Student(String name, int score) {
           this.name = name;
           this.score = score;
       }
   
       public String getName() { return name; }
       public int getScore() { return score; }
   }
   
   // Main.java
   public class StreamCollectToMap {
       public static void main(String[] args) {
           List<Student> students = new ArrayList<>();
           students.add(new Student("Alice", 90));
           students.add(new Student("Bob", 85));
           students.add(new Student("Charlie", 90)); // 注意：如果有相同姓名，需要处理冲突
   
           // 在这里使用Stream API进行操作
           System.out.println("Student Scores Map: " + studentScoresMap);
       }
   }
   ```
