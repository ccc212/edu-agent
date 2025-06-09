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
public class AddUserDTO extends BaseDTO {

    @NotNull(message = "姓名不能为空")
    @Schema(example = "姓名")
    private String name;

    @NotNull(message = "账号不能为空")
    @Schema(example = "账号")
    private String username;

    @NotNull(message = "密码不能为空")
    @Schema(example = "2")
    private Integer roleCode;

}
