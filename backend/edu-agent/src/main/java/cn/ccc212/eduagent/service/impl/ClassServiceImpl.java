package cn.ccc212.eduagent.service.impl;

import cn.ccc212.eduagent.constant.ClassJoinStatus;
import cn.ccc212.eduagent.constant.RoleConstant;
import cn.ccc212.eduagent.context.AuthContext;
import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.mapper.ClassMapper;
import cn.ccc212.eduagent.mapper.StudentClassMapper;
import cn.ccc212.eduagent.mapper.UserMapper;
import cn.ccc212.eduagent.pojo.dto.clazz.*;
import cn.ccc212.eduagent.pojo.entity.Class;
import cn.ccc212.eduagent.pojo.entity.StudentClass;
import cn.ccc212.eduagent.pojo.entity.User;
import cn.ccc212.eduagent.pojo.vo.clazz.ClassListVO;
import cn.ccc212.eduagent.pojo.vo.clazz.StudentClassListVO;
import cn.ccc212.eduagent.service.IClassService;
import cn.ccc212.eduagent.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 班级信息表 服务实现类
 * </p>
 *
 * @author ccc212
 * @since 2025-06-09
 */
@Service
@RequiredArgsConstructor
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    private final ClassMapper classMapper;
    private final StudentClassMapper studentClassMapper;
    private final UserMapper userMapper;

    @Override
    public void addClass(AddClassDTO addClassDTO) {
        Class aClass = lambdaQuery().eq(Class::getClassName, addClassDTO.getClassName()).one();
        if (aClass != null) {
            throw new BizException(StatusCodeEnum.CLASS_EXIST);
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, addClassDTO.getTeacherId()));
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        save(BeanUtil.copyProperties(addClassDTO, Class.class));
    }

    @Override
    public void deleteClass(Long classId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        if (AuthContext.getRoleCode() != RoleConstant.ADMIN && !Objects.equals(aClass.getTeacherId(), AuthContext.getUserId())) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_YOURS);
        }
        removeById(classId);
        studentClassMapper.delete(new LambdaUpdateWrapper<StudentClass>().eq(StudentClass::getClassId, classId));
    }

    @Override
    public void updateClass(UpdateClassDTO updateClassDTO) {
        String oldClassName = updateClassDTO.getOldClassName();
        if (lambdaQuery().eq(Class::getClassName, oldClassName).one()
                == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        if (AuthContext.getRoleCode() != RoleConstant.ADMIN && !Objects.equals(updateClassDTO.getTeacherId(), AuthContext.getUserId())) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_YOURS);
        }

        String newClassName = updateClassDTO.getNewClassName();
        if (newClassName != null) {
            Class aClass = lambdaQuery().eq(Class::getClassName, newClassName).one();
            if (aClass != null) {
                throw new BizException(StatusCodeEnum.CLASS_EXIST);
            }
        }

        update(new LambdaUpdateWrapper<Class>()
                .eq(Class::getClassName, oldClassName)
                .set(newClassName != null, Class::getClassName, newClassName)
                .set(updateClassDTO.getTeacherId() != null, Class::getTeacherId, updateClassDTO.getTeacherId())
                .set(updateClassDTO.getDescription() != null, Class::getDescription, updateClassDTO.getDescription()));
    }

    @Override
    public IPage<ClassListVO> listClassByAdmin(ClassPageDTO classPageDTO) {
        Page<Class> page = new Page<>(classPageDTO.getPage(), classPageDTO.getPageSize());
        PageUtils.setOrderValue(Class.class, classPageDTO);
        LocalDateTime createTimeStart = classPageDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = classPageDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return classMapper.listClass(page, classPageDTO);
    }

    @Override
    public IPage<ClassListVO> listJoinedClass(ClassPageDTO classPageDTO) {
        Page<Class> page = new Page<>(classPageDTO.getPage(), classPageDTO.getPageSize());
        PageUtils.setOrderValue(Class.class, classPageDTO);
        LocalDateTime createTimeStart = classPageDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = classPageDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return classMapper.listJoinedClass(page, classPageDTO, AuthContext.getUserId());
    }

    @Override
    public IPage<ClassListVO> listOwnedClass(OwnedClassPageDTO ownedClassPageDTO) {
        Page<Class> page = new Page<>(ownedClassPageDTO.getPage(), ownedClassPageDTO.getPageSize());
        PageUtils.setOrderValue(Class.class, ownedClassPageDTO);
        LocalDateTime createTimeStart = ownedClassPageDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = ownedClassPageDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return classMapper.listOwnedClass(page, ownedClassPageDTO, AuthContext.getUserId());
    }

    @Override
    public void applyJoinClass(Long classId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        studentClassMapper.insert(new StudentClass()
                .setClassId(classId)
                .setStudentId(AuthContext.getUserId())
                .setStatus(ClassJoinStatus.APPLYING));
    }

    @Override
    public void inviteJoinClass(Long classId, Long studentId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, studentId));
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        studentClassMapper.insert(new StudentClass()
                .setClassId(classId)
                .setStudentId(studentId)
                .setStatus(ClassJoinStatus.INVITING));
    }

    @Override
    public void approveJoinClass(Long classId, Long studentId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, studentId));
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        studentClassMapper.update(new LambdaUpdateWrapper<StudentClass>()
                .eq(StudentClass::getClassId, classId)
                .eq(StudentClass::getStudentId, studentId)
                .set(StudentClass::getStatus, ClassJoinStatus.JOINED)
                .set(StudentClass::getJoinedAt, LocalDateTime.now()));
    }

    @Override
    public void rejectJoinClass(Long classId, Long studentId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, studentId));
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        studentClassMapper.update(new LambdaUpdateWrapper<StudentClass>()
                .eq(StudentClass::getClassId, classId)
                .eq(StudentClass::getStudentId, studentId)
                .set(StudentClass::getStatus, ClassJoinStatus.NOT_JOINED));
    }

    @Override
    public void studentApproveJoinClass(Long classId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        studentClassMapper.update(new LambdaUpdateWrapper<StudentClass>()
                .eq(StudentClass::getClassId, classId)
                .eq(StudentClass::getStudentId, AuthContext.getUserId())
                .set(StudentClass::getStatus, ClassJoinStatus.JOINED)
                .set(StudentClass::getJoinedAt, LocalDateTime.now()));
    }

    @Override
    public void studentRejectJoinClass(Long classId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        studentClassMapper.update(new LambdaUpdateWrapper<StudentClass>()
                .eq(StudentClass::getClassId, classId)
                .eq(StudentClass::getStudentId, AuthContext.getUserId())
                .set(StudentClass::getStatus, ClassJoinStatus.NOT_JOINED));
    }

    @Override
    public void exitClass(Long classId) {
        Class aClass = lambdaQuery().eq(Class::getClassId, classId).one();
        if (aClass == null) {
            throw new BizException(StatusCodeEnum.CLASS_NOT_EXISTS);
        }

        studentClassMapper.update(new LambdaUpdateWrapper<StudentClass>()
                .eq(StudentClass::getClassId, classId)
                .eq(StudentClass::getStudentId, AuthContext.getUserId())
                .set(StudentClass::getStatus, ClassJoinStatus.NOT_JOINED));
    }

    @Override
    public IPage<StudentClassListVO> listClassApply(Long classId, StudentClassPageDTO studentClassPageDTO) {
        Page<Class> page = new Page<>(studentClassPageDTO.getPage(), studentClassPageDTO.getPageSize());
        LocalDateTime createTimeStart = studentClassPageDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = studentClassPageDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return classMapper.listClassApply(page, studentClassPageDTO, classId);
    }

    @Override
    public IPage<StudentClassListVO> listClassInvite(StudentClassPageDTO studentClassPageDTO) {
        Page<Class> page = new Page<>(studentClassPageDTO.getPage(), studentClassPageDTO.getPageSize());
        LocalDateTime createTimeStart = studentClassPageDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = studentClassPageDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return classMapper.listClassInvite(page, studentClassPageDTO, AuthContext.getUserId());
    }

    @Override
    public IPage<StudentClassListVO> listStudentClassRelation(ListStudentClassDTO listStudentClassDTO) {
        Page<Class> page = new Page<>(listStudentClassDTO.getPage(), listStudentClassDTO.getPageSize());
        PageUtils.setOrderValue(StudentClass.class, listStudentClassDTO);
        LocalDateTime createTimeStart = listStudentClassDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = listStudentClassDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return classMapper.listStudentClassRelation(page, listStudentClassDTO);
    }
}
