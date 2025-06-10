package cn.ccc212.eduagent.pojo.vo.clazz;

import cn.ccc212.eduagent.pojo.entity.Class;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ClassListVO extends Class {

    /**
     * 教师名
     */
    private String teacherName;

}
