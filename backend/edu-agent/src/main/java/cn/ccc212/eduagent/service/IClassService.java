package cn.ccc212.eduagent.service;

import cn.ccc212.eduagent.pojo.dto.clazz.*;
import cn.ccc212.eduagent.pojo.entity.Class;
import cn.ccc212.eduagent.pojo.vo.clazz.ClassListVO;
import cn.ccc212.eduagent.pojo.vo.clazz.StudentClassListVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 班级信息表 服务类
 * </p>
 *
 * @author ccc212
 * @since 2025-06-09
 */
public interface IClassService extends IService<Class> {

    void addClass(AddClassDTO addClassDTO);

    void deleteClass(Long classId);

    void updateClass(UpdateClassDTO updateClassDTO);

    IPage<ClassListVO> listClassByAdmin(ClassPageDTO classPageDTO);

    IPage<ClassListVO> listJoinedClass(ClassPageDTO classPageDTO);

    IPage<ClassListVO> listOwnedClass(OwnedClassPageDTO ownedClassPageDTO);

    void applyJoinClass(Long classId);

    void inviteJoinClass(Long classId, Long studentId);

    void approveJoinClass(Long classId, Long studentId);

    void rejectJoinClass(Long classId, Long studentId);

    void studentApproveJoinClass(Long classId);

    void studentRejectJoinClass(Long classId);

    void exitClass(Long classId);

    IPage<StudentClassListVO> listClassApply(Long classId, StudentClassPageDTO studentClassPageDTO);

    IPage<StudentClassListVO> listClassInvite(StudentClassPageDTO studentClassPageDTO);

    IPage<StudentClassListVO> listStudentClassRelation(ListStudentClassDTO listStudentClassDTO);
}
