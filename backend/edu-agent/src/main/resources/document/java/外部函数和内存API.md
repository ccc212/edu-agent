## 34. 外部函数和内存 API (Foreign Function & Memory API - FFM API) (Java 22+)

### 34.1. FFM API 基础 (FFM API Basics)

- **知识点：** FFM API 的引入与目的（安全、高效地与 JVM 外部代码（如 C/C++ 库）和内存进行互操作）；`MemorySegment` (内存段)；`SegmentAllocator` (内存分配器)；`Linker` (链接器)；`FunctionDescriptor` (函数描述符)；`MethodHandle` (方法句柄)；结构体和联合体布局。
- **注意：** FFM API 是 Java 19 的孵化特性，并在 Java 22 中成为标准特性。这类题目通常需要一个本地库（如 C 库）作为交互目标，对于纯 Java 环境的题目生成可能较复杂。这里提供一个纯 Java 模拟或简化交互的思路。

1. **题目名称：** 模拟 FFM API 访问原生内存（简化版） **题目描述：**

   - **概念模拟：** FFM API 的核心在于安全地分配和操作堆外内存。本题通过模拟 `MemorySegment` 的行为，让学生理解“分配一段连续内存”和“读写这段内存”的概念，尽管不直接涉及真实 Native 调用，但模拟其核心交互模式。
   - 定义一个类 `SimulatedMemorySegment`，包含一个 `byte[]` 内部数组来模拟堆外内存，并提供 `getByte(long offset)` 和 `setByte(long offset, byte value)` 方法。
   - 在 `main` 方法中，创建 `SimulatedMemorySegment` 实例，向其写入一些字节数据，然后读取并打印。
   - （选做，高级）模拟一个 `SimulatedNativeFunction`，接受 `SimulatedMemorySegment` 和一个长度，模拟处理这段内存。 **参考代码结构：**

   **`SimulatedMemorySegment.java`**

   ```java
   import java.util.Arrays;
   
   class SimulatedMemorySegment {
       private final byte[] memory; // 模拟堆外内存的内部数组
       private final long size;
   
       public SimulatedMemorySegment(long size) {
           if (size < 0 || size > Integer.MAX_VALUE) { // 简化，实际MemorySegment支持long
               throw new IllegalArgumentException("Simulated memory size out of bounds for byte array.");
           }
           this.size = size;
           this.memory = new byte[(int) size];
           System.out.println("SimulatedMemorySegment created with size: " + size + " bytes.");
       }
   
       // 模拟 FFM API 的 getByte
       public byte getByte(long offset) {
           if (offset < 0 || offset >= size) {
               throw new IndexOutOfBoundsException("Offset " + offset + " out of bounds for size " + size);
           }
           return memory[(int) offset];
       }
   
       // 模拟 FFM API 的 setByte
       public void setByte(long offset, byte value) {
           if (offset < 0 || offset >= size) {
               throw new IndexOutOfBoundsException("Offset " + offset + " out of bounds for size " + size);
           }
           memory[(int) offset] = value;
       }
   
       // 辅助方法，用于验证
       public byte[] toByteArray() {
           return Arrays.copyOf(memory, memory.length);
       }
   
       public long size() {
           return size;
       }
   }
   
   // Main.java
   public class FfmApiSimulatedDemo {
       public static void main(String[] args) {
           // 1. 创建一个模拟的内存段 (例如，16字节)
           SimulatedMemorySegment segment = new SimulatedMemorySegment(16);
   
           // 2. 向内存段写入数据
           System.out.println("\n--- Writing data to simulated memory ---");
           for (int i = 0; i < 10; i++) {
               segment.setByte(i, (byte) ('A' + i)); // 写入 ASCII 字符
           }
           segment.setByte(10, (byte) 0xDE); // 写入十六进制值
           segment.setByte(11, (byte) 0xAD);
   
           // 3. 从内存段读取数据并打印
           System.out.println("\n--- Reading data from simulated memory ---");
           StringBuilder sb = new StringBuilder();
           for (int i = 0; i < segment.size(); i++) {
               byte b = segment.getByte(i);
               sb.append(String.format("%02X ", b)); // 打印十六进制表示
           }
           System.out.println("Memory content (hex): " + sb.toString().trim());
   
           System.out.println("Byte at offset 0: " + (char) segment.getByte(0)); // 应该输出 'A'
           System.out.println("Byte at offset 5: " + (char) segment.getByte(5)); // 应该输出 'F'
           System.out.println("Byte at offset 10 (hex): 0x" + String.format("%02X", segment.getByte(10))); // 应该输出 0xDE
   
           // 4. 尝试访问越界内存 (预期抛出异常)
           System.out.println("\n--- Attempting out-of-bounds access ---");
           try {
               segment.getByte(20);
           } catch (IndexOutOfBoundsException e) {
               System.out.println("Caught expected exception: " + e.getMessage());
           }
   
           // (可选) 模拟原生函数调用，处理这个内存段
           // 例如，一个C函数可能会接收一个指针和长度
           // 在这里可以设计一个SimulatedNativeFunction类来接收SimulatedMemorySegment
           // 但这超出了纯Java的模拟范畴，需要真实FFM API来完成。
       }
   }
   ```
