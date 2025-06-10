package cn.ccc212.eduagent.mapper;

import cn.ccc212.eduagent.pojo.entity.StudentClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生与班级关联表 Mapper 接口
 * </p>
 *
 * @author ccc212
 * @since 2025-06-09
 */
@Mapper
public interface StudentClassMapper extends BaseMapper<StudentClass> {

}
