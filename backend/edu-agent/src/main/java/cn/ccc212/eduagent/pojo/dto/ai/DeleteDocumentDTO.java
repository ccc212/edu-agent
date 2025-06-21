package cn.ccc212.eduagent.pojo.dto.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteDocumentDTO {

    @Schema(example = "1")
    private String userId;

    @Schema(example = "文件名")
    private String fileName;

}
