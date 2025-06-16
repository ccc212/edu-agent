package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.context.CodeSandboxContext;
import cn.ccc212.eduagent.enums.QuestionSubmitLanguageEnum;
import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.pojo.dto.judge.TestJavaDTO;
import cn.ccc212.eduagent.pojo.dto.question.JudgeCase;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/judge")
@Tag(name = "判题相关接口")
@RequiredArgsConstructor
public class JudgeController {

    private final CodeSandboxContext codeSandboxContext;

    @PostMapping("/testJava")
    @Operation(summary = "Java代码运行测试", description = "默认输入两参数 1 和 2")
    public String test(@RequestBody @Valid TestJavaDTO testJavaDTO) {
        // 调用沙箱，获取到执行结果
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(testJavaDTO.getLanguage());
        if (languageEnum == null) {
            throw new BizException(StatusCodeEnum.LANGUAGE_NOT_SUPPORT);
        }

        String judgeCaseStr = "[{\"input\":\"1 2\",\"output\":\"3\"}]";
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);

        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest()
                .setCode(testJavaDTO.getCode())
                .setLanguage(Objects.requireNonNull(languageEnum).getValue())
                .setInputList(inputList);
        ExecuteCodeResponse executeCodeResponse = codeSandboxContext.executeCode(executeCodeRequest);
        return executeCodeResponse.toString();
    }

}
