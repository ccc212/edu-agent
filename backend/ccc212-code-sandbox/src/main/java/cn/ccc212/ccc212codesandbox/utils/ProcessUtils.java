package cn.ccc212.ccc212codesandbox.utils;

import cn.ccc212.ccc212codesandbox.entity.ExecuteMessage;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程工具类
 */
public class ProcessUtils {

    /**
     * 执行进程并获取信息
     * @param runProcess
     * @param operationName
     * @return
     */
    @SneakyThrows
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String operationName) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int exitValue = runProcess.waitFor();
        executeMessage.setExitValue(exitValue);
        // 正常退出
        if (exitValue == 0) {
            System.out.println(operationName + "成功");
            // 分批获取进程的输出
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            List<String> outputStrList = new ArrayList<>();
            // 逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                outputStrList.add(compileOutputLine);
            }
            executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));
        } else {
            // 异常退出
            System.out.println(operationName + "失败，错误码：" + exitValue);
            // 分批获取进程的正常输出
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            List<String> outputStrList = new ArrayList<>();
            // 逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                outputStrList.add(compileOutputLine);
            }
            executeMessage.setMessage(StringUtils.join(outputStrList, "\n"));

            // 分批获取进程的异常输出
            BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
            List<String> errorOutputStrList = new ArrayList<>();
            // 逐行读取
            String errorCompileOutputLine;
            while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                errorOutputStrList.add(errorCompileOutputLine);
            }
            executeMessage.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
        }
        stopWatch.stop();
        executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
        return executeMessage;
    }

    /**
     * 执行交互式进程并获取信息
     * @param runProcess
     * @param operationName
     * @param args
     * @return
     */
    @SneakyThrows
    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess, String operationName, String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 向控制台输入程序
        OutputStream outputStream = runProcess.getOutputStream();

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        String[] s = args.split(" ");
        outputStreamWriter.write(StrUtil.join("\n", s) + "\n");
        outputStreamWriter.flush();

        // 分批获取进程的正常输出
        InputStream inputStream = runProcess.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder compileOutputStringBuilder = new StringBuilder();
        // 逐行读取
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
            compileOutputStringBuilder.append(compileOutputLine).append("\n");
        }
        executeMessage.setMessage(compileOutputStringBuilder.toString());

        stopWatch.stop();
        executeMessage.setTime(stopWatch.getLastTaskTimeMillis());

        // 释放资源
        outputStreamWriter.close();
        outputStream.close();
        inputStream.close();
        runProcess.destroy();
        return executeMessage;
    }
}
