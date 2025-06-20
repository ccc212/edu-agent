package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.annotation.Log;
import cn.ccc212.eduagent.constant.PromptConstant;
import cn.ccc212.eduagent.context.AIContext;
import cn.ccc212.eduagent.enums.BusinessTypeEnum;
import cn.ccc212.eduagent.pojo.dto.ai.ChatDTO;
import cn.ccc212.eduagent.pojo.entity.Result;
import cn.ccc212.eduagent.pojo.vo.ai.ChatVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@Tag(name = "AI相关接口")
@RequiredArgsConstructor
public class AIController {

    private final AIContext aiContext;

    @Log(title = "AI聊天", businessType = BusinessTypeEnum.CHAT)
    @PostMapping("/chat")
    @Operation(summary = "AI聊天", description = "需要登录")
    public Result<ChatVO> chat(@RequestBody ChatDTO chatDTO) {
        return Result.success(aiContext.chat(chatDTO, PromptConstant.COMMON_SYSTEM_PROMPT));
    }

    @Log(title = "RAG增强聊天", businessType = BusinessTypeEnum.CHAT)
    @PostMapping("/chatWithRAG")
    @Operation(summary = "RAG增强聊天", description = "需要登录")
    public Result<ChatVO> chatWithRAG(@RequestBody ChatDTO chatDTO) {
        return Result.success(aiContext.chatWithRAG(chatDTO, PromptConstant.TEACHING_CONTENT_DESIGN_SYSTEM_PROMPT));
    }

}
