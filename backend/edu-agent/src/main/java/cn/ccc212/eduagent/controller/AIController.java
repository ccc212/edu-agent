package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.annotation.Log;
import cn.ccc212.eduagent.constant.MessageConstant;
import cn.ccc212.eduagent.constant.PromptConstant;
import cn.ccc212.eduagent.context.AIContext;
import cn.ccc212.eduagent.enums.BusinessTypeEnum;
import cn.ccc212.eduagent.pojo.dto.ai.ChatDTO;
import cn.ccc212.eduagent.pojo.dto.ai.DeleteDocumentDTO;
import cn.ccc212.eduagent.pojo.entity.Result;
import cn.ccc212.eduagent.pojo.vo.ai.ChatVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ai")
@Tag(name = "AI相关接口")
@RequiredArgsConstructor
public class AIController {

    private final AIContext aiContext;

    @PostMapping("/addDocuments")
    @Operation(summary = "添加文档到向量数据库", description = "需要登录，传文件则将文件存到向量数据库，没有文件则默认存系统文档到向量数据库")
    public Result<?> addDocuments(@Parameter(
            description = "要上传的文件列表",
            array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))
    )
                                   @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        // TODO 解决系统文档重复加载问题
        aiContext.addDocuments(files);
        return Result.success(MessageConstant.ADD_SUCCESS);
    }

    @DeleteMapping("/deleteDocuments")
    @Operation(summary = "删除文档", description = "需要登录，传用户ID和文件名则删除对应的文档，传用户ID则删除该用户的所有文档，都不传则删除系统文档")
    public Result<?> deleteDocuments(@RequestBody DeleteDocumentDTO deleteDocumentDTO) {
        // TODO 删除指定文档
        aiContext.deleteDocuments(deleteDocumentDTO);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }


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
