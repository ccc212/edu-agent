## 25. JUnit 单元测试 (JUnit Unit Testing)

### 25.1. JUnit 基础 (JUnit Basics)

- **知识点：** 单元测试的概念与重要性；JUnit 框架的引入；测试类与测试方法；常用注解 (`@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`, `@DisplayName`)；断言 (`Assertions` 类，例如 `assertEquals`, `assertTrue`, `assertNull`, `assertThrows`)。

1. **题目名称：** 对简单计算器类进行单元测试 **题目描述：**

   - 定义一个 `Calculator` 类，包含以下四个静态方法：`add(int a, int b)`、`subtract(int a, int b)`、`multiply(int a, int b)` 和 `divide(int a, int b)`（除法需要考虑除零异常）。
   - 编写一个 JUnit 测试类 `CalculatorTest`，对 `Calculator` 类的方法进行单元测试。
   - 为每个方法编写至少一个 `@Test` 方法。
   - 使用不同的断言来验证方法的正确性（例如，`assertEquals` 验证加减乘结果，`assertThrows` 验证除零异常）。
   - 使用 `@BeforeEach` 或 `@BeforeAll` 模拟测试前的数据准备。 **参考代码结构：**

   **`Calculator.java`**

   ```java
   public class Calculator {
       public static int add(int a, int b) {
           return a + b;
       }
   
       public static int subtract(int a, int b) {
           return a - b;
       }
   
       public static int multiply(int a, int b) {
           return a * b;
       }
   
       public static double divide(int a, int b) {
           if (b == 0) {
               throw new IllegalArgumentException("Cannot divide by zero.");
           }
           return (double) a / b;
       }
   }
   ```

   **`CalculatorTest.java`** (需要 JUnit 5 依赖)

   ```java
   import org.junit.jupiter.api.*; // 导入JUnit 5的注解和断言
   
   import static org.junit.jupiter.api.Assertions.*;
   
   public class CalculatorTest {
   
       @BeforeAll // 在所有测试方法运行前执行一次
       static void setupAll() {
           System.out.println("Starting Calculator tests...");
       }
   
       @AfterAll // 在所有测试方法运行后执行一次
       static void cleanupAll() {
           System.out.println("Finished Calculator tests.");
       }
   
       @BeforeEach // 在每个测试方法运行前执行
       void setup() {
           System.out.println("  Running a new test method...");
       }
   
       @AfterEach // 在每个测试方法运行后执行
       void cleanup() {
           System.out.println("  Test method finished.");
       }
   
       @Test
       @DisplayName("Test addition with positive numbers")
       void testAddPositiveNumbers() {
           // 在这里编写加法测试逻辑
           assertEquals(5, Calculator.add(2, 3), "2 + 3 should be 5");
           assertEquals(0, Calculator.add(-5, 5), "-5 + 5 should be 0");
       }
   
       @Test
       @DisplayName("Test subtraction with various numbers")
       void testSubtract() {
           // 在这里编写减法测试逻辑
           assertEquals(1, Calculator.subtract(5, 4));
           assertEquals(-5, Calculator.subtract(0, 5));
       }
   
       @Test
       @DisplayName("Test multiplication")
       void testMultiply() {
           // 在这里编写乘法测试逻辑
           assertEquals(15, Calculator.multiply(3, 5));
           assertEquals(0, Calculator.multiply(0, 10));
           assertEquals(-20, Calculator.multiply(4, -5));
       }
   
       @Test
       @DisplayName("Test division by non-zero numbers")
       void testDivide() {
           // 在这里编写除法测试逻辑
           assertEquals(2.5, Calculator.divide(5, 2), 0.001); // 浮点数比较需要delta
           assertEquals(10.0, Calculator.divide(100, 10));
       }
   
       @Test
       @DisplayName("Test division by zero should throw IllegalArgumentException")
       void testDivideByZero() {
           // 在这里编写除零异常测试逻辑
           assertThrows(IllegalArgumentException.class, () -> Calculator.divide(10, 0),
                        "Dividing by zero should throw IllegalArgumentException");
       }
   }
   ```
