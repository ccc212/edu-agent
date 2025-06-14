package cn.ccc212.eduagent.service;

import cn.ccc212.eduagent.pojo.dto.user.*;
import cn.ccc212.eduagent.pojo.entity.User;
import cn.ccc212.eduagent.pojo.vo.user.UserInfoVO;
import cn.ccc212.eduagent.pojo.vo.user.UserLoginVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author ccc212
 * @since 2025-06-03
 */
public interface IUserService extends IService<User> {
    UserLoginVO login(UserLoginDTO userLoginDTO);

    Long register(UserRegisterDTO userRegisterDTO);

    IPage<UserInfoVO> search(UserPageDTO userPageDTO);

    void updateUser(UpdateUserDTO updateUserDTO);

    void updatePassword(String oldPassword, String newPassword);

    void deleteByIds(List<Long> ids);

    UserInfoVO getByUserId(Long id);

    void batchInsert(List<AddUserDTO> addUserDTOS);

    void setRoles(List<SetRoleDTO> setRoleDTOs);

    void studentAuth(String studentId, String name);

    void checkUsername(String username);

    void checkEmail(String email);

    String sendCode(String email);
}
