package cn.ccc212.eduagent.pojo.dto.user;

import cn.ccc212.eduagent.pojo.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDTO extends BaseDTO {

    @NotNull(message = "用户id不能为空")
    @Schema(example = "1")
    private Long userId;

    @Schema(example = "新姓名")
    private String name;

    @Schema(example = "新用户名")
    private String username;

    @Schema(example = "新学号")
    private String studentId;

    @Schema(example = "新邮箱")
    private String email;

    @Schema(example = "2")
    private Integer roleCode;

}
