package cn.ccc212.eduagent.pojo.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetRoleDTO {

    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotNull(message = "角色编号不能为空")
    private Integer roleCode;

}
