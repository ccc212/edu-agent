## 28. Record 类型 (Java 16+)

### 28.1. `Record` 的定义与使用 (`Record` Definition & Usage)

- **知识点：** `record` 关键字的引入与目的（作为不可变数据载体的简洁语法）；自动生成的构造器、`equals()`, `hashCode()`, `toString()`, getter 方法；紧凑构造器 (Compact Constructor)；显式构造器；实例方法与静态方法。
- **注意：** `record` 类型是 Java 16 及更高版本中的特性。

1. 题目名称：

    定义并使用 

   ```
   Point
   ```

    记录

   题目描述：

   - 定义一个 `Point` 记录，包含 `x` (int) 和 `y` (int) 两个组件。

   - 在 

     ```
     main
     ```

      方法中：

     - 创建 `Point` 记录的实例。
     - 访问其组件（使用自动生成的访问器方法）。
     - 打印 `Point` 实例（观察自动生成的 `toString()` 输出）。
     - 创建另一个 `Point` 实例，并使用 `equals()` 比较它们。
     - 尝试定义一个紧凑构造器，在创建实例时进行简单的验证（例如，`x` 和 `y` 不能为负）。 **参考代码结构：**

   ```java
   // Point.java
   // 注意：Record 声明通常直接放在文件中，不需要单独的类文件或 package-info.java
   // 在 Main.java 的同一个文件中声明也可以，但通常建议在单独的文件中
   record Point(int x, int y) {
       // 可以添加紧凑构造器进行验证
       public Point {
           if (x < 0 || y < 0) {
               throw new IllegalArgumentException("Coordinates cannot be negative.");
           }
           System.out.println("Creating Point(" + x + ", " + y + ")");
       }
   
       // 也可以添加自定义方法
       public double distanceToOrigin() {
           return Math.sqrt(x * x + y * y);
       }
   }
   
   // Main.java
   public class RecordDemo {
       public static void main(String[] args) {
           // 1. 创建 Record 实例
           Point p1 = new Point(10, 20);
           Point p2 = new Point(10, 20);
           Point p3 = new Point(30, 40);
   
           // 2. 访问组件 (自动生成的访问器方法)
           System.out.println("P1 x: " + p1.x()); // 或 p1.x
           System.out.println("P1 y: " + p1.y()); // 或 p1.y
   
           // 3. 打印实例 (自动生成的 toString())
           System.out.println("P1: " + p1);
   
           // 4. 比较实例 (自动生成的 equals() 和 hashCode())
           System.out.println("P1 equals P2: " + p1.equals(p2));
           System.out.println("P1 equals P3: " + p1.equals(p3));
           System.out.println("P1 hashcode: " + p1.hashCode());
           System.out.println("P2 hashcode: " + p2.hashCode());
   
           // 5. 调用自定义方法
           System.out.println("Distance of P1 to origin: " + p1.distanceToOrigin());
   
           // 6. 尝试创建无效实例，观察异常 (如果添加了紧凑构造器验证)
           try {
               Point invalidP = new Point(-1, 5);
               System.out.println("Invalid point created: " + invalidP);
           } catch (IllegalArgumentException e) {
               System.out.println("Caught expected exception for invalid point: " + e.getMessage());
           }
       }
   }
   ```
