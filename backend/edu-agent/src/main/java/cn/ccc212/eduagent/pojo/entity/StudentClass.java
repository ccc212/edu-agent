package cn.ccc212.eduagent.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serial;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生与班级关联表
 * </p>
 *
 * @author ccc212
 * @since 2025-06-10
 */
@Data
@Accessors(chain = true)
@TableName("t_student_class")
public class StudentClass implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @TableId(value = "student_id", type = IdType.AUTO)
    private Long studentId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 加入状态（0未加入 1已加入 2申请中 3邀请中）
     */
    private Integer status;

    /**
     * 加入班级的时间
     */
    private LocalDateTime joinedAt;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
