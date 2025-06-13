package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.enums.QuestionSubmitLanguageEnum;
import cn.ccc212.eduagent.judge.codesandbox.CodeSandbox;
import cn.ccc212.eduagent.judge.codesandbox.CodeSandboxFactory;
import cn.ccc212.eduagent.judge.codesandbox.CodeSandboxProxy;
import cn.ccc212.eduagent.pojo.dto.question.JudgeCase;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/judge")
@RequiredArgsConstructor
public class JudgeController {

    @Value("${codesandbox.mode:example}")
    private String codeSandboxType;

    @PostMapping("/test")
    public String test(@RequestParam @NotNull(message = "提交代码不能为空") @Schema(description = "默认输入两参数 1 和 2", example = "public class Main {\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "        int a = Integer.parseInt(args[0]);\n" +
            "        int b = Integer.parseInt(args[1]);\n" +
            "        System.out.println((a + b));\n" +
            "        System.out.println(\"ccc212是谁\");\n" +
            "    }\n" +
            "}\n") String code) {
        // 调用沙箱，获取到执行结果
        CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(codeSandboxType);
        codeSandBox = new CodeSandboxProxy(codeSandBox);

        String language = QuestionSubmitLanguageEnum.JAVA.getValue();

        String judgeCaseStr = "[{\"input\":\"1 2\",\"output\":\"3\"}]";
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);

        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest()
                .setCode(code)
                .setLanguage(language)
                .setInputList(inputList);
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        return executeCodeResponse.toString();
    }

}
