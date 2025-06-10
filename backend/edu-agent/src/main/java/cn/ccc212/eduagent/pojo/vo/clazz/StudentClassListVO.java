package cn.ccc212.eduagent.pojo.vo.clazz;

import cn.ccc212.eduagent.pojo.entity.StudentClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentClassListVO extends StudentClass {

    /**
     * 学生名
     */
    private String studentName;

    /**
     * 班级名
     */
    private String className;

}
