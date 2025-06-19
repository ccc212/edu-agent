## 21. Java 8+ 新特性 (Java 8+ New Features)

### 21.1. Optional 类 (Optional Class)

- **知识点：** `Optional` 的引入与目的（避免 `NullPointerException`）；`Optional.of()`, `Optional.ofNullable()`, `Optional.empty()`；`isPresent()`, `isEmpty()`；`get()`；`orElse()`, `orElseGet()`, `orElseThrow()`；`ifPresent()`, `map()`, `flatMap()`。

1. 题目名称：

    使用 Optional 处理可能为空的值

   题目描述：

   - 编写一个方法 `findStudentNameById(int id)`，该方法模拟从数据库查询学生姓名。如果 ID 为 1，返回 "Alice"；如果 ID 为 2，返回 `null`；其他 ID 返回 "Bob"。

   - 在 

     ```
     main
     ```

      方法中，分别调用该方法并使用 

     ```
     Optional
     ```

      来优雅地处理返回值：

     - 如果学生姓名存在，打印 "Found student: [name]"。
     - 如果学生姓名不存在（`null`），打印 "Student not found, using default name: Guest"，并使用 `orElse()` 提供一个默认值。
     - 尝试使用 `get()` 获取一个可能为 `null` 的值，并观察其行为（应抛出 `NoSuchElementException`）。 **参考代码结构：**

   ```java
   import java.util.Optional;
   import java.util.NoSuchElementException;
   
   public class OptionalDemo {
       // 模拟从数据库查询学生姓名
       public static String findStudentNameById(int id) {
           if (id == 1) {
               return "Alice";
           } else if (id == 2) {
               return null;
           } else {
               return "Bob"; // 其他ID也返回一个名字
           }
       }
   
       public static void main(String[] args) {
           // ID = 1 的情况
           Optional<String> student1 = Optional.ofNullable(findStudentNameById(1));
           // 在这里使用 Optional 处理 student1
           student1.ifPresent(name -> System.out.println("Found student: " + name));
   
           // ID = 2 的情况 (返回 null)
           Optional<String> student2 = Optional.ofNullable(findStudentNameById(2));
           // 在这里使用 Optional 处理 student2，并提供默认值
           String name2 = student2.orElse("Guest");
           System.out.println("Student not found, using default name: " + name2);
   
   
           // 尝试使用 get() 获取可能为 null 的值 (ID = 2)，预期抛出异常
           try {
               // 在这里使用 Optional.get()
               String nameFromGet = student2.get();
               System.out.println("Accessed directly: " + nameFromGet);
           } catch (NoSuchElementException e) {
               System.out.println("Caught expected NoSuchElementException when using get() on empty Optional.");
           }
       }
   }
   ```

### 21.2. 方法引用 (Method References)

- **知识点：** 方法引用的类型（静态方法引用、实例方法引用、特定对象的实例方法引用、构造器引用）；与 Lambda 表达式的互换性。

1. **题目名称：** 使用方法引用打印列表元素 **题目描述：**

   - 创建一个 `List<String>`。
   - 使用 Stream API 的 `forEach()` 方法，并结合方法引用 `System.out::println` 来打印列表中的每个元素。 **参考代码结构：**

   ```java
   import java.util.Arrays;
   import java.util.List;
   
   public class MethodReferenceDemo {
       public static void main(String[] args) {
           List<String> greetings = Arrays.asList("Hello", "World", "Java");
           System.out.println("Printing elements using method reference:");
           // 在这里使用方法引用打印列表元素
       }
   }
   ```

2. **题目名称：** 使用方法引用排序自定义对象 **题目描述：**

   - 定义一个 `Product` 类，包含 `name` (String) 和 `price` (double) 属性。
   - 创建 `List<Product>`。
   - 使用 `Collections.sort()` 方法，并结合方法引用 `Comparator.comparing(Product::getPrice)` 来根据价格对产品列表进行排序。
   - 打印排序后的列表。 **参考代码结构：**

​    import java.util.ArrayList; import java.util.Arrays; import java.util.Collections; import java.util.Comparator; import java.util.List;



````
// Product.java
class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + '}';
    }
}

// Main.java
public class MethodReferenceSort {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(Arrays.asList(
            new Product("Laptop", 1200.0),
            new Product("Mouse", 25.0),
            new Product("Keyboard", 75.0),
            new Product("Monitor", 300.0)
        ));

        System.out.println("Original Products: " + products);
        // 在这里使用方法引用进行排序
        System.out.println("Sorted Products by Price: " + products);
    }
}
```
````