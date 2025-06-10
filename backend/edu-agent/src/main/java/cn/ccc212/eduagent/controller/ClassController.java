package cn.ccc212.eduagent.controller;


import cn.ccc212.eduagent.annotation.Log;
import cn.ccc212.eduagent.annotation.RequireRoles;
import cn.ccc212.eduagent.constant.MessageConstant;
import cn.ccc212.eduagent.constant.RoleConstant;
import cn.ccc212.eduagent.enums.BusinessTypeEnum;
import cn.ccc212.eduagent.pojo.dto.PageDTO;
import cn.ccc212.eduagent.pojo.dto.clazz.*;
import cn.ccc212.eduagent.pojo.entity.Result;
import cn.ccc212.eduagent.pojo.vo.clazz.ClassListVO;
import cn.ccc212.eduagent.pojo.vo.clazz.StudentClassListVO;
import cn.ccc212.eduagent.service.IClassService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 班级信息表 前端控制器
 * </p>
 *
 * @author ccc212
 * @since 2025-06-09
 */
@RestController
@RequestMapping("/class")
@Tag(name = "班级相关接口")
@RequiredArgsConstructor
public class ClassController {

    private final IClassService classService;

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @Log(title = "添加班级", businessType = BusinessTypeEnum.INSERT)
    @PostMapping("/add")
    @Operation(summary = "添加班级", description = "角色需要管理员或教师")
    public Result<?> addClass(@RequestBody @Valid AddClassDTO addClassDTO) {
        classService.addClass(addClassDTO);
        return Result.success(MessageConstant.ADD_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @Log(title = "删除班级", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping(("/delete"))
    @Operation(summary = "删除班级", description = "非管理员只能删除自己的班级；角色需要管理员或教师")
    public Result<?> deleteClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId) {
        classService.deleteClass(classId);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @Log(title = "修改班级", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/update")
    @Operation(summary = "修改班级", description = "非管理员只能修改自己的班级")
    public Result<?> updateClass(@RequestBody @Valid UpdateClassDTO updateClassDTO) {
        classService.updateClass(updateClassDTO);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @GetMapping("/list")
    @Operation(summary = "查询班级（管理员）", description = "分页、条件查询所有班级；可排序")
    public Result<IPage<ClassListVO>> listClassByAdmin(@Valid ClassPageDTO classPageDTO) {
        return Result.success(classService.listClassByAdmin(classPageDTO));
    }

    @RequireRoles({RoleConstant.STUDENT})
    @GetMapping("/joined")
    @Operation(summary = "查询加入的班级", description = "分页、条件查询自己加入的班级；要求角色为学生；可排序")
    public Result<IPage<ClassListVO>> listJoinedClass(@Valid ClassPageDTO classPageDTO) {
        return Result.success(classService.listJoinedClass(classPageDTO));
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @GetMapping("/owned")
    @Operation(summary = "查询拥有的班级", description = "分页、条件查询自己拥有的班级；要求角色为教师或管理员；可排序")
    public Result<IPage<ClassListVO>> listOwnedClass(@Valid OwnedClassPageDTO ownedClassPageDTO) {
        return Result.success(classService.listOwnedClass(ownedClassPageDTO));
    }

    @RequireRoles({RoleConstant.STUDENT})
    @Log(title = "申请加入班级", businessType = BusinessTypeEnum.APPLY)
    @PostMapping("/apply")
    @Operation(summary = "申请加入班级", description = "学生申请加入班级；要求角色为学生")
    public Result<?> applyJoinClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId) {
        classService.applyJoinClass(classId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @Log(title = "邀请加入班级", businessType = BusinessTypeEnum.INVITE)
    @PostMapping("/invite")
    @Operation(summary = "邀请加入班级", description = "邀请学生加入班级；要求角色为教师或管理员")
    public Result<?> inviteJoinClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId,
                                    @RequestParam @NotNull(message = "学生ID不能为空") @Schema(example = "1") Long studentId) {
        classService.inviteJoinClass(classId, studentId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @Log(title = "同意学生加入班级", businessType = BusinessTypeEnum.APPROVE)
    @PostMapping("/approveApply")
    @Operation(summary = "同意学生加入班级", description = "教师/管理员同意学生加入班级申请；要求角色为教师或管理员")
    public Result<?> approveJoinClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId,
                                      @RequestParam @NotNull(message = "学生ID不能为空") @Schema(example = "1") Long studentId) {
        classService.approveJoinClass(classId, studentId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @Log(title = "拒绝学生加入班级", businessType = BusinessTypeEnum.REJECT)
    @PostMapping("/rejectApply")
    @Operation(summary = "拒绝学生加入班级", description = "教师/管理员拒绝学生加入班级申请；要求角色为教师或管理员")
    public Result<?> rejectJoinClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId,
                                     @RequestParam @NotNull(message = "学生ID不能为空") @Schema(example = "1") Long studentId) {
        classService.rejectJoinClass(classId, studentId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.STUDENT})
    @Log(title = "学生同意班级邀请", businessType = BusinessTypeEnum.APPROVE)
    @PostMapping("/approveInvite")
    @Operation(summary = "学生同意班级邀请", description = "学生同意班级邀请；要求角色为学生")
    public Result<?> studentApproveJoinClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId) {
        classService.studentApproveJoinClass(classId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.STUDENT})
    @Log(title = "学生拒绝班级邀请", businessType = BusinessTypeEnum.REJECT)
    @PostMapping("/rejectInvite")
    @Operation(summary = "学生拒绝班级邀请", description = "学生拒绝班级邀请；要求角色为学生")
    public Result<?> studentRejectJoinClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId) {
        classService.studentRejectJoinClass(classId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.STUDENT})
    @Log(title = "退出班级", businessType = BusinessTypeEnum.EXIT)
    @PostMapping("/exit")
    @Operation(summary = "退出班级", description = "学生退出班级；要求角色为学生")
    public Result<?> exitClass(@RequestParam @NotNull(message = "班级ID不能为空") @Schema(example = "1") Long classId) {
        classService.exitClass(classId);
        return Result.success(MessageConstant.OPERATION_SUCCESS);
    }

    @RequireRoles({RoleConstant.ADMIN, RoleConstant.TEACHER})
    @PostMapping("/apply/list/{classId}")
    @Operation(summary = "查询班级申请", description = "查询班级申请；要求角色为教师或管理员")
    public Result<IPage<StudentClassListVO>> listClassApply(@PathVariable Long classId, @RequestBody StudentClassPageDTO studentClassPageDTO) {
        return Result.success(classService.listClassApply(classId, studentClassPageDTO));
    }

    @RequireRoles({RoleConstant.STUDENT})
    @PostMapping("/invite/list")
    @Operation(summary = "查询班级邀请", description = "查询班级邀请；要求角色为学生")
    public Result<IPage<StudentClassListVO>> listClassInvite(@RequestBody StudentClassPageDTO studentClassPageDTO) {
        return Result.success(classService.listClassInvite(studentClassPageDTO));
    }

    @RequireRoles({RoleConstant.ADMIN})
    @PostMapping("/student/list")
    @Operation(summary = "查询学生与班级的关系", description = "查询学生与班级的关系；要求角色为管理员；状态有：0未加入 1已加入 2申请中 3邀请中；可排序")
    public Result<IPage<StudentClassListVO>> listStudentClassRelation(@RequestBody ListStudentClassDTO listStudentClassDTO) {
        return Result.success(classService.listStudentClassRelation(listStudentClassDTO));
    }

}
