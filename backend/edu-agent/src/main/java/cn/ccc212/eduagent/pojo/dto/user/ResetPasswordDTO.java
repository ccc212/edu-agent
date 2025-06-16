package cn.ccc212.eduagent.pojo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    @NotBlank(message = "新密码不能为空")
    @Schema(example = "newPassword")
    private String newPassword;

    @NotBlank(message = "邮箱不能为空")
    @Schema(example = "example@example.com")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Schema(example = "验证码")
    private String code;

}
