package cn.ccc212.eduagent.pojo.dto.clazz;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListStudentClassDTO extends StudentClassPageDTO {

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

}
