## 18. JDBC 数据库连接 (JDBC Database Connectivity)

### 18.1. JDBC 基本操作 (Basic JDBC Operations)

- **知识点：** JDBC 驱动程序；`DriverManager`；`Connection` (连接数据库)；`Statement` (执行 SQL)；`PreparedStatement` (预编译 SQL，防止 SQL 注入)；`ResultSet` (处理查询结果)；事务管理（`commit`, `rollback`）；资源关闭。
- **前提条件：** 需要一个可用的数据库（如 MySQL, PostgreSQL, H2 等），并在项目中引入对应的 JDBC 驱动。以下示例假设使用 H2 内存数据库，因为它无需外部安装，便于快速测试。

1. **题目名称：** 创建表并插入数据 **题目描述：**

   - 使用 JDBC 连接到数据库（例如 H2 内存数据库）。
   - 创建一个名为 `students` 的表，包含 `id` (INT PRIMARY KEY AUTO_INCREMENT), `name` (VARCHAR(50)), `age` (INT) 字段。
   - 向表中插入至少三条学生记录。
   - 最后查询所有学生数据并打印。
   - 确保关闭所有 JDBC 资源（`ResultSet`, `Statement`, `Connection`）。 **参考代码结构：**

   ```java
   import java.sql.*;
   
   public class JdbcInsertQuery {
       private static final String DB_URL = "jdbc:h2:mem:testdb"; // H2内存数据库
       private static final String USER = "sa";
       private static final String PASS = "";
   
       public static void main(String[] args) {
           Connection conn = null;
           Statement stmt = null;
           ResultSet rs = null;
   
           try {
               // 1. 注册JDBC驱动 (对于新版JDBC通常不需要显式注册)
               // Class.forName("org.h2.Driver"); // 如果是H2
   
               // 2. 打开连接
               System.out.println("Connecting to database...");
               conn = DriverManager.getConnection(DB_URL, USER, PASS);
   
               // 3. 执行SQL操作
               stmt = conn.createStatement();
   
               // 创建表
               String createTableSql = "CREATE TABLE IF NOT EXISTS students (" +
                                       "id INT AUTO_INCREMENT PRIMARY KEY," +
                                       "name VARCHAR(50) NOT NULL," +
                                       "age INT NOT NULL)";
               stmt.executeUpdate(createTableSql);
               System.out.println("Table 'students' created or already exists.");
   
               // 插入数据 (使用Statement)
               String insertSql1 = "INSERT INTO students (name, age) VALUES ('Alice', 20)";
               String insertSql2 = "INSERT INTO students (name, age) VALUES ('Bob', 22)";
               stmt.executeUpdate(insertSql1);
               stmt.executeUpdate(insertSql2);
               System.out.println("Data inserted using Statement.");
   
               // 使用PreparedStatement插入数据
               String preparedInsertSql = "INSERT INTO students (name, age) VALUES (?, ?)";
               try (PreparedStatement pstmt = conn.prepareStatement(preparedInsertSql)) {
                   pstmt.setString(1, "Charlie");
                   pstmt.setInt(2, 21);
                   pstmt.executeUpdate();
                   System.out.println("Data inserted using PreparedStatement.");
               }
   
               // 查询数据
               String selectSql = "SELECT id, name, age FROM students";
               rs = stmt.executeQuery(selectSql);
   
               System.out.println("\n--- Student Data ---");
               while (rs.next()) {
                   int id = rs.getInt("id");
                   String name = rs.getString("name");
                   int age = rs.getInt("age");
                   System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
               }
   
           } catch (SQLException se) {
               se.printStackTrace();
           } finally {
               // 4. 关闭资源
               try {
                   if (rs != null) rs.close();
               } catch (SQLException se2) { /* ignore */ }
               try {
                   if (stmt != null) stmt.close();
               } catch (SQLException se2) { /* ignore */ }
               try {
                   if (conn != null) conn.close();
               } catch (SQLException se) {
                   se.printStackTrace();
               }
           }
           System.out.println("Database operations finished.");
       }
   }
   ```

2. **题目名称：** 更新与删除数据 **题目描述：**

   - 在上述 `students` 表的基础上，更新一个学生的年龄。
   - 然后删除某个学生记录。
   - 最后再次查询所有数据，验证更新和删除操作是否成功。
   - 使用 `PreparedStatement` 进行更新和删除操作。 **参考代码结构：**

   ```java
   import java.sql.*;
   
   public class JdbcUpdateDelete {
       private static final String DB_URL = "jdbc:h2:mem:testdb"; // H2内存数据库
       private static final String USER = "sa";
       private static final String PASS = "";
   
       public static void main(String[] args) {
           Connection conn = null;
           // 假设表和初始数据已由上一个程序创建
           // 在实际应用中，你可能需要确保数据库存在或重新创建
   
           try {
               conn = DriverManager.getConnection(DB_URL, USER, PASS);
   
               // 更新数据
               String updateSql = "UPDATE students SET age = ? WHERE name = ?";
               try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                   pstmt.setInt(1, 25);
                   pstmt.setString(2, "Alice");
                   int rowsAffected = pstmt.executeUpdate();
                   System.out.println("Rows updated (Alice): " + rowsAffected);
               }
   
               // 删除数据
               String deleteSql = "DELETE FROM students WHERE name = ?";
               try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                   pstmt.setString(1, "Bob");
                   int rowsAffected = pstmt.executeUpdate();
                   System.out.println("Rows deleted (Bob): " + rowsAffected);
               }
   
               // 再次查询所有数据
               System.out.println("\n--- Student Data After Update/Delete ---");
               try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT id, name, age FROM students")) {
                   while (rs.next()) {
                       int id = rs.getInt("id");
                       String name = rs.getString("name");
                       int age = rs.getInt("age");
                       System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
                   }
               }
   
           } catch (SQLException se) {
               se.printStackTrace();
           } finally {
               try {
                   if (conn != null) conn.close();
               } catch (SQLException se) {
                   se.printStackTrace();
               }
           }
       }
   }
   ```
