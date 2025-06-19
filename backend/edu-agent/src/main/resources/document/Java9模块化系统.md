## 27. Java 9+ 模块化系统 (JPMS - Java Platform Module System)

### 27.1. 模块的定义与使用 (Module Definition & Usage)

- **知识点：** 模块化系统的目的（解决 JAR 包地狱，增强封装性，提高可维护性）；`module-info.java` 文件；`requires` (依赖其他模块)；`exports` (导出包)；`opens` (开放包给反射)；`uses` (使用服务)；`provides ... with` (提供服务)。
- **注意：** 模块化系统需要 Java 9 及以上版本，并且需要通过命令行或 IDE 正确配置模块路径来编译和运行。

1. **题目名称：** 创建两个简单模块并互相依赖 **题目描述：**

   - 创建两个 Java 模块：`com.example.greeter` 和 `com.example.app`。
   - `com.example.greeter` 模块应包含一个 `Greeter` 类，其中有一个公共方法 `greet(String name)` 返回问候语。该模块需要将包含 `Greeter` 类的包导出。
   - `com.example.app` 模块应包含一个 `Main` 类，其中有 `main` 方法。该模块需要依赖 `com.example.greeter` 模块，并使用 `Greeter` 类来打印问候语。
   - 编写 `module-info.java` 文件来定义这两个模块的结构和依赖关系。
   - 操作步骤：
     1. 创建两个目录作为模块根：`greeter-module` 和 `app-module`。
     2. 在每个模块根下创建 `src/main/java` 目录。
     3. 在 `src/main/java` 下创建 `module-info.java` 和对应的包及类文件。
     4. 编译和运行这些模块。 **参考代码结构：**

   **模块 1: `com.example.greeter`**

   - 目录结构:

     ```
     greeter-module/
     └── src/main/java/
         ├── module-info.java
         └── com/example/greeter/
             └── Greeter.java
     ```

   - `greeter-module/src/main/java/module-info.java`

     Java

     ```
     module com.example.greeter {
         exports com.example.greeter; // 导出包含 Greeter 类的包
     }
     ```

   - `greeter-module/src/main/java/com/example/greeter/Greeter.java`

     Java

     ```
     package com.example.greeter;
     
     public class Greeter {
         public String greet(String name) {
             return "Hello, " + name + " from Greeter module!";
         }
     }
     ```

   **模块 2: `com.example.app`**

   - 目录结构:

     ```
     app-module/
     └── src/main/java/
         ├── module-info.java
         └── com/example/app/
             └── Main.java
     ```

   - `app-module/src/main/java/module-info.java`

     Java

     ```
     module com.example.app {
         requires com.example.greeter; // 声明依赖 greeter 模块
     }
     ```

   - `app-module/src/main/java/com/example/app/Main.java`

     Java

     ```
     package com.example.app;
     
     import com.example.greeter.Greeter; // 导入 Greeter 类
     
     public class Main {
         public static void main(String[] args) {
             Greeter greeter = new Greeter();
             System.out.println(greeter.greet("Modular Java"));
         }
     }
     ```

   - 编译和运行步骤示例 (命令行):

     Bash

     ```
     # 1. 创建输出目录
     mkdir mods
     mkdir out
     
     # 2. 编译 greeter-module
     javac -d out/greeter-module --module-source-path greeter-module src/main/java/module-info.java \
         greeter-module/src/main/java/com/example/greeter/Greeter.java
     # 或者更简洁地编译整个模块源
     # javac --module-source-path greeter-module -d out/greeter-module $(find greeter-module -name "*.java")
     # 更标准的做法是：
     # javac -d out/greeter-module greeter-module/src/main/java/module-info.java \
     #     greeter-module/src/main/java/com/example/greeter/Greeter.java
     
     # 3. 编译 app-module (需要指定 module-path 找到 greeter-module)
     javac -d out/app-module --module-path out/greeter-module --module-source-path app-module src/main/java/module-info.java \
         app-module/src/main/java/com/example/app/Main.java
     # 或者更简洁地编译整个模块源
     # javac --module-source-path app-module --module-path out/greeter-module -d out/app-module $(find app-module -name "*.java")
     
     # 4. 运行 app-module
     java --module-path out/greeter-module:out/app-module -m com.example.app/com.example.app.Main
     # 注意：Windows 上路径分隔符是分号 ;
     # java --module-path out/greeter-module;out/app-module -m com.example.app/com.example.app.Main
     ```

   **预期输出：** `Hello, Modular Java from Greeter module!`
