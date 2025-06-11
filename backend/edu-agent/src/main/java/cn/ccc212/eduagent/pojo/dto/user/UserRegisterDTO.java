package cn.ccc212.eduagent.pojo.dto.user;

import cn.ccc212.eduagent.pojo.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@NoArgsConstructor
public class UserRegisterDTO extends BaseDTO {

    @NotBlank(message = "用户名不能为空")
    @Schema(example = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(example = "password")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Schema(example = "example@example.com")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Schema(example = "验证码")
    private String code;

}
