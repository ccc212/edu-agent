package cn.ccc212.eduagent.utils;

public class UnitConversionUtil {

    /**
     * 根据内存占用动态选择单位
     *
     * @param memorySize 以字节为单位的内存大小
     * @return 转换后的内存大小字符串，带有合适的单位
     */
    public static String formatMemorySize(long memorySize) {
        if (memorySize >= 1024 * 1024 * 1024) {
            return String.format("%.2f GB", memorySize / (1024.0 * 1024.0 * 1024.0));
        } else if (memorySize >= 1024 * 1024) {
            return String.format("%.2f MB", memorySize / (1024.0 * 1024.0));
        } else if (memorySize >= 1024) {
            return String.format("%.2f KB", memorySize / 1024.0);
        } else {
            return memorySize + " B";
        }
    }

    /**
     * 执行时间动态选择单位
     *
     * @param executionTime 以毫秒为单位的执行时间
     * @return 转换后的执行时间字符串，带有合适的单位
     */
    public static String formatExecutionTime(long executionTime) {
        if (executionTime >= 1000 * 60) {
            return String.format("%.2f 分钟", executionTime / (1000.0 * 60.0));
        }
        else if (executionTime >= 1000) {
            return String.format("%.2f 秒", executionTime / 1000.0);
        } else {
            return executionTime + " 毫秒";
        }
    }
}
