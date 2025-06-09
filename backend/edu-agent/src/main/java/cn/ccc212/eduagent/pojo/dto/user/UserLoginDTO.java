package cn.ccc212.eduagent.pojo.dto.user;

import cn.ccc212.eduagent.pojo.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO extends BaseDTO {

    @NotBlank(message = "账号不能为空")
    @Schema(example = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(example = "password")
    private String password;

}
