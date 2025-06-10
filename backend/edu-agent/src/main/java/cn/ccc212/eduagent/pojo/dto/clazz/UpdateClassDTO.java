package cn.ccc212.eduagent.pojo.dto.clazz;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClassDTO {

    /**
     * 旧班级名称
     */
    @Schema(example = "旧班级名称")
    @NotBlank(message = "旧班级名称不能为空")
    private String oldClassName;

    /**
     * 新班级名称
     */
    @Schema(example = "新班级名称")
    private String newClassName;

    /**
     * 班级所属教师ID
     */
    @Schema(example = "1")
    private Long teacherId;

    /**
     * 班级描述
     */
    @Schema(example = "班级描述")
    private String description;

}
