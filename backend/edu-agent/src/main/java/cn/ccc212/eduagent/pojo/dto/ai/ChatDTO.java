package cn.ccc212.eduagent.pojo.dto.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class ChatDTO {

    @Schema(example = "你好")
    @NotBlank(message = "内容不能为空")
    private String content;

    @Schema(example = "1", description = "会话ID，第一次提问没有可以不用传，然后会返回一个会话ID，之后就用这个会话ID来保证处在同一个会话中")
    private String chatId;

}
