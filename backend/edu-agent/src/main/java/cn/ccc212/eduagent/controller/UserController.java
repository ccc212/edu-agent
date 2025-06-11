package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.annotation.Log;
import cn.ccc212.eduagent.annotation.RequireRoles;
import cn.ccc212.eduagent.constant.MessageConstant;
import cn.ccc212.eduagent.constant.RoleConstant;
import cn.ccc212.eduagent.enums.BusinessTypeEnum;
import cn.ccc212.eduagent.pojo.dto.user.*;
import cn.ccc212.eduagent.pojo.entity.Result;
import cn.ccc212.eduagent.pojo.vo.user.UserInfoVO;
import cn.ccc212.eduagent.pojo.vo.user.UserLoginVO;
import cn.ccc212.eduagent.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author ccc212
 * @since 2025-06-03
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户相关接口")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final IUserService userService;

    /**
     * 用户登录
     *
     * @param userLoginDTO 用户登录数据传输对象，包含用户名和密码
     * @return 包含用户登录信息的Result对象
     */
    @Log(title = "用户登录", businessType = BusinessTypeEnum.LOGIN)
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "登录后会返回token，调用其他接口时需在请求头中携带该token")
    public Result<UserLoginVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return Result.success(userService.login(userLoginDTO));
    }

    /**
     * 用户注册
     *
     * @param userRegisterDTO 用户注册数据传输对象，包含用户名、密码等
     * @return 注册成功信息
     */
    @Log(title = "用户注册", businessType = BusinessTypeEnum.REGISTER)
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户名和邮箱是唯一的，如果碰到重复的用户名，后端会提示用户名已存在，默认角色为游客")
    public Result<?> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return Result.success(MessageConstant.REGISTER_SUCCESS);
    }

    /**
     * 删除用户
     *
     * @param ids 用户ID列表，不能为空
     * @return 删除成功信息
     */
    @Log(title = "删除用户", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/delete")
    @Operation(summary = "删除用户", description = "删除用户，需要携带token，支持用户删除自己账号，也支持管理员删除用户账号")
    public Result<?> delete(@RequestParam(required = false) @NotEmpty(message = "用户id不能为空") List<Long> ids) {
        userService.deleteByIds(ids);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }

    /**
     * 修改用户信息
     *
     * @param updateUserDTO 更新用户数据传输对象
     * @return 修改成功信息
     */
    @Log(title = "修改用户信息", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/update")
    @Operation(summary = "修改用户信息", description = "修改用户信息，需要携带token，支持用户修改自己信息，也支持管理员修改用户信息；能够修改角色，但管理员才能够修改；如果不是管理员，roleCode字段传个空字符串或者不传就行")
    public Result<?> update(@RequestBody @Valid UpdateUserDTO updateUserDTO) {
        userService.updateUser(updateUserDTO);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改成功信息
     */
    @Log(title = "修改密码", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/updatePassword")
    @Operation(summary = "修改密码", description = "修改密码，需要携带token，需要传入原密码进行校验，只能修改自己的密码")
    public Result<?> updatePassword(@RequestParam(required = false) @NotBlank(message = "旧密码不能为空") @Schema(example = "password") String oldPassword,
                                    @RequestParam(required = false) @NotBlank(message = "新密码不能为空") @Schema(example = "123456") String newPassword) {
        userService.updatePassword(oldPassword, newPassword);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 获取用户信息 (分页和条件查询)
     *
     * @param userPageDTO 用户分页查询数据传输对象
     * @return 包含用户信息分页数据的Result对象
     */
    @RequireRoles({RoleConstant.ADMIN})
    @PostMapping("/get")
    @Operation(summary = "获取用户信息", description = "获取用户信息，需要携带token，分页获取，且支持条件查询，无条件即为正常查询；需要管理员权限；页码默认为1，每页大小默认为10")
    public Result<IPage<UserInfoVO>> get(@RequestBody @Valid UserPageDTO userPageDTO) {
        return Result.success(userService.search(userPageDTO));
    }

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 包含UserInfoVO的Result对象
     */
    @GetMapping("/get/{id}")
    @Operation(summary = "根据id获取用户信息", description = "根据id获取用户信息，需要携带token，非管理员只可查询自己的信息")
    public Result<UserInfoVO> getById(@PathVariable Long id) {
        return Result.success(userService.getByUserId(id));
    }

    /**
     * 批量插入用户
     *
     * @param addUserDTOS 待添加的用户数据传输对象列表
     * @return 批量插入成功信息
     */
    @RequireRoles({RoleConstant.ADMIN})
    @PostMapping("/batchInsert")
    @Operation(summary = "批量插入用户", description = "批量插入用户，需要携带token，管理员权限，密码默认123456")
    public Result<?> batchInsert(@RequestBody @Valid List<AddUserDTO> addUserDTOS) {
        userService.batchInsert(addUserDTOS);
        return Result.success(MessageConstant.ADD_SUCCESS);
    }

    /**
     * 批量设置用户角色
     *
     * @param setRoleDTOs 待设置的用户角色数据传输对象列表
     * @return 修改成功信息
     */
    @RequireRoles({RoleConstant.ADMIN})
    @Log(title = "批量设置用户角色", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/setRole")
    @Operation(summary = "批量设置用户角色", description = "设置用户角色，需要携带token，管理员权限；1为管理员，2为教师，3为学生，4为游客")
    public Result<?> setRoles(@RequestBody @Valid List<SetRoleDTO> setRoleDTOs) {
        userService.setRoles(setRoleDTOs);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }

    /**
     * 学生认证
     *
     * @param studentId
     * @return 认证成功信息
     */
    @RequireRoles(allowGuest = true)
    @Log(title = "学生认证", businessType = BusinessTypeEnum.AUTH)
    @PostMapping("/studentAuth")
    @Operation(summary = "学生认证", description = "学生认证，要求角色为游客")
    public Result<?> studentAuth(@RequestParam(required = false) @NotBlank(message = "学号不能为空") @Schema(example = "202200000000") String studentId,
                                 @RequestParam(required = false) @NotBlank(message = "姓名不能为空") @Schema(example = "张三") String name) {
        userService.studentAuth(studentId, name);
        return Result.success();
    }

    // TODO 认证审核

    /**
     * 检查用户名是否存在
     *
     * @param username
     * @return 操作成功信息
     */
    @GetMapping("/checkUsername/{username}")
    @Operation(summary = "检查用户名是否存在", description = "检查用户名是否存在")
    public Result<?> checkUsername(@PathVariable("username") @Schema(example = "admin") String username) {
        userService.checkUsername(username);
        return Result.success();
    }

    /**
     * 检查邮箱是否存在
     *
     * @param email
     * @return 操作成功信息
     */
    @GetMapping("/checkEmail/{email}")
    @Operation(summary = "检查邮箱是否存在", description = "检查邮箱是否存在")
    public Result<?> checkEmail(@PathVariable("email") @Schema(example = "example@example.com") String email) {
        userService.checkEmail(email);
        return Result.success();
    }

    /**
     * 获取邮箱验证码
     */
    @GetMapping(value = "/sendEmail/{email}")
    @Operation(summary = "获取邮箱验证码", description = "获取邮箱验证码")
    public Result<?> sendCode(@PathVariable @Schema(example = "example@example.com") String email) {
        return Result.success(userService.sendCode(email));
    }

}