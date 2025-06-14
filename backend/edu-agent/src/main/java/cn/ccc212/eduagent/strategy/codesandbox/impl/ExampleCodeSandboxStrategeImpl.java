package cn.ccc212.eduagent.strategy.codesandbox.impl;

import cn.ccc212.eduagent.enums.JudgeInfoMessageEnum;
import cn.ccc212.eduagent.enums.QuestionSubmitStatusEnum;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.ccc212.eduagent.pojo.entity.codesandbox.JudgeInfo;
import cn.ccc212.eduagent.strategy.codesandbox.CodeSandboxStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 示例代码沙箱 (仅为了跑通业务流程)
 */
@Component
public class ExampleCodeSandboxStrategeImpl implements CodeSandboxStrategy {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        JudgeInfo judgeInfo = new JudgeInfo()
                .setMessage(JudgeInfoMessageEnum.ACCEPTED.getText())
                .setMemory(100L)
                .setTime(100L);

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse()
                .setOutputList(inputList)
                .setMessage("测试执行成功")
                .setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue())
                .setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
