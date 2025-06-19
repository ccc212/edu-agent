## 12. 日期与时间 API (Date & Time API - Java 8+)

### 12.1. `java.time` 包 (Package `java.time`)

- **知识点：** `LocalDate` (日期)；`LocalTime` (时间)；`LocalDateTime` (日期时间)；`Instant` (时间戳)；`Duration` (持续时间)；`Period` (周期)；`DateTimeFormatter` (格式化与解析)；`ZoneId`, `ZonedDateTime` (时区)。

1. **题目名称：** 计算两个日期之间的天数 **题目描述：**

   - 接收两个日期字符串，格式为 "YYYY-MM-DD"（例如 "2023-01-01", "2023-01-15"）。
   - 将其解析为 `LocalDate` 对象。
   - 计算并打印这两个日期之间的天数（不包含结束日期）。 **输入示例：**

   ```
   2023-01-01
   2023-01-15
   ```

   **输出示例：** `Days between: 14` **参考代码结构：**

   

   Java

   ```
   import java.time.LocalDate;
   import java.time.temporal.ChronoUnit;
   import java.util.Scanner;
   
   public class DateDifference {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           String dateStr1 = scanner.nextLine();
           String dateStr2 = scanner.nextLine();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```

2. **题目名称：** 格式化当前日期和时间 **题目描述：**

   - 获取当前的 `LocalDateTime`。
   - 使用 `DateTimeFormatter` 将其格式化为 "YYYY年MM月DD日 HH:mm:ss" 的字符串格式并打印。 **参考代码结构：**

   Java

   ```
   import java.time.LocalDateTime;
   import java.time.format.DateTimeFormatter;
   
   public class FormatDateTime {
       public static void main(String[] args) {
           // 在这里编写你的代码
       }
   }
   ```

3. **题目名称：** 计算年龄 (基于出生日期) **题目描述：**

   - 接收一个出生日期字符串，格式为 "YYYY-MM-DD"（例如 "1990-05-20"）。
   - 计算从出生日期到当前日期的年龄（年数），并打印。 **输入示例：** `1990-05-20` **输出示例：** `Age: 35 years` (假设当前是2025年6月18日) **参考代码结构：**

   Java

   ```
   import java.time.LocalDate;
   import java.time.Period;
   import java.util.Scanner;
   
   public class CalculateAge {
       public static void main(String[] args) {
           Scanner scanner = new Scanner(System.in);
           String dobStr = scanner.nextLine();
           // 在这里编写你的代码
           scanner.close();
       }
   }
   ```
