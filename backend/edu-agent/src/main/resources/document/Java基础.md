## 1. Java 基础 (Java Basics)

### 1.1. 变量、常量与数据类型 (Variables, Constants & Data Types)

- **知识点：** 变量的声明与初始化；基本数据类型（整型：byte, short, int, long；浮点型：float, double；字符型：char；布尔型：boolean）；类型转换；常量的定义（`final` 关键字）。

1. **题目名称：** 整数与浮点数求和 **题目描述：** 声明一个整型变量 `a` 并赋值为 15，声明一个双精度浮点型变量 `b` 并赋值为 7.5。计算它们的和，并将结果打印到控制台。 **参考代码结构：**

   ```java
   public class SumNumbers {
       public static void main(String[] args) {
           // 在这里编写你的代码
       }
   }
   ```

2. **题目名称：** 字符变量与 ASCII 值 **题目描述：** 声明一个字符变量 `ch` 并赋值为 'A'。将其转换为对应的 ASCII 整数值并打印。 **参考代码结构：**

   ```java
   public class CharToAscii {
       public static void main(String[] args) {
           // 在这里编写你的代码
       }
   }
   ```

3. **题目名称：** 声明与使用常量 **题目描述：** 定义一个名为 `MAX_VALUE` 的整型常量，值为 100。在一个方法中尝试修改 `MAX_VALUE` 的值，观察编译器的行为。然后正确地使用这个常量进行一些计算（例如，判断一个数是否小于 `MAX_VALUE`）。 **参考代码结构：**

   ```java
   public class UseConstant {
       public static void main(String[] args) {
           // 在这里编写你的代码
       }
   }
   ```

### 1.2. 运算符 (Operators)

- **知识点：** 算术运算符（`+`, `-`, `*`, `/`, `%`）；关系运算符（`==`, `!=`, `<`, `>`, `<=`, `>=`）；逻辑运算符（`&&`, `||`, `!`）；赋值运算符（`=`, `+=`, `-=` 等）；自增自减运算符（`++`, `--`）。

1. **题目名称：** 模运算与判断 **题目描述：** 接收两个整数 `x` 和 `y`。判断 `x` 是否能被 `y` 整除。如果能，打印 "x is divisible by y"，否则打印 "x is not divisible by y"。 **输入示例：** `10 5` **输出示例：** `x is divisible by y` **参考代码结构：**

   ```java
   import java.util.Scanner;
   public class DivisibilityCheck {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           int x = scanner.nextInt();
           int y = scanner.nextInt();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```

2. **题目名称：** 逻辑门模拟 **题目描述：** 声明两个布尔变量 `inputA` 和 `inputB`。模拟逻辑与（AND）、逻辑或（OR）、逻辑非（NOT）操作，并打印每种操作的结果。例如，如果 `inputA` 为 `true`，`inputB` 为 `false`。 **参考代码结构：**

   ```java
   public class LogicGateSimulator {
       public static void main(String[] args) {
           boolean inputA = true;
           boolean inputB = false;
           // 在这里编写你的代码
       }
   }
   ```

3. **题目名称：** 自增与自减 **题目描述：** 声明一个整数 `count` 并初始化为 5。

   - 首先，使用前缀自增 (`++count`) 运算，打印其结果。
   - 然后，使用后缀自减 (`count--`) 运算，打印其操作后的 `count` 值。 **参考代码结构：**

   ```java
   public class IncrementDecrement {
       public static void main(String[] args) {
           int count = 5;
           // 在这里编写你的代码
       }
   }
   ```

### 1.3. 控制流 (Control Flow)

- **知识点：** `if-else if-else` 条件语句；`switch` 多分支选择语句；`for` 循环；`while` 循环；`do-while` 循环；`break` 语句；`continue` 语句。

1. **题目名称：** 红绿灯模拟 **题目描述：** 接收一个表示灯颜色的字符串（"red", "yellow", "green"）。使用 `switch` 语句判断当前灯的颜色，并打印相应的行为提示（"Stop", "Prepare to stop", "Go"）。如果输入非法，打印 "Invalid color"。 **输入示例：** `green` **输出示例：** `Go` **参考代码结构：**

   ```java
   import java.util.Scanner;
   public class TrafficLight {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           String color = scanner.nextLine();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```

2. **题目名称：** 计算阶乘 **题目描述：** 接收一个非负整数 `n`，计算 `n` 的阶乘（`n!`）并打印结果。使用 `for` 循环实现。 **输入示例：** `5` **输出示例：** `120` **参考代码结构：**

   ```java
   import java.util.Scanner;
   public class Factorial {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           int n = scanner.nextInt();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```

3. **题目名称：** 猜数字游戏 **题目描述：** 编写一个简单的猜数字游戏。程序随机生成一个 1 到 100 之间的整数，玩家通过键盘输入猜测。程序根据猜测给出提示（"Too high", "Too low", "Correct!"）。直到玩家猜中为止，并统计猜测次数。使用 `while` 循环。 **参考代码结构：**

   ```java
   import java.util.Random;
   import java.util.Scanner;
   public class GuessNumber {
       public static void main(String[] args) {
           Random random = new Random();
           int targetNumber = random.nextInt(100) + 1; // 1 to 100
           Scanner scanner = new Scanner(System.in);
           int guessCount = 0;
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```

4. **题目名称：** 过滤偶数并跳过 **题目描述：** 使用 `for` 循环遍历从 1 到 20 的所有整数。如果当前数字是偶数，使用 `continue` 语句跳过本次循环；如果当前数字是 15，使用 `break` 语句结束循环。打印所有被处理的奇数。 **参考代码结构：**

   ```java
   public class LoopControl {
       public static void main(String[] args) {
           // 在这里编写你的代码
       }
   }
   ```