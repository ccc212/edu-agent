package cn.ccc212.eduagent.mapper;

import cn.ccc212.eduagent.pojo.dto.clazz.ClassPageDTO;
import cn.ccc212.eduagent.pojo.dto.clazz.ListStudentClassDTO;
import cn.ccc212.eduagent.pojo.dto.clazz.OwnedClassPageDTO;
import cn.ccc212.eduagent.pojo.dto.clazz.StudentClassPageDTO;
import cn.ccc212.eduagent.pojo.entity.Class;
import cn.ccc212.eduagent.pojo.vo.clazz.ClassListVO;
import cn.ccc212.eduagent.pojo.vo.clazz.StudentClassListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 班级信息表 Mapper 接口
 * </p>
 *
 * @author ccc212
 * @since 2025-06-09
 */
@Mapper
public interface ClassMapper extends BaseMapper<Class> {

    IPage<ClassListVO> listClass(Page<Class> page, ClassPageDTO classPageDTO);

    IPage<ClassListVO> listJoinedClass(Page<Class> page, ClassPageDTO classPageDTO, Long userId);

    IPage<ClassListVO> listOwnedClass(Page<Class> page, OwnedClassPageDTO ownedClassPageDTO, Long userId);

    IPage<StudentClassListVO> listClassApply(Page<Class> page, StudentClassPageDTO studentClassPageDTO, Long classId);

    IPage<StudentClassListVO> listClassInvite(Page<Class> page, StudentClassPageDTO studentClassPageDTO, Long userId);

    IPage<StudentClassListVO> listStudentClassRelation(Page<Class> page, ListStudentClassDTO listStudentClassDTO);
}
