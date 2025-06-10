package cn.ccc212.eduagent.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 班级信息表
 * </p>
 *
 * @author ccc212
 * @since 2025-06-09
 */
@Data
@Accessors(chain = true)
@TableName("t_class")
public class Class implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 班级主键
     */
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级所属教师ID
     */
    private Long teacherId;

    /**
     * 班级描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
