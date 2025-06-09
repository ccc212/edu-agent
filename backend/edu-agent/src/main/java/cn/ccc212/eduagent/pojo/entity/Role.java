package cn.ccc212.eduagent.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author ccc212
 * @since 2025-01-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色编号（1为管理员，2为教师，3为学生）
     */
    private Integer roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
