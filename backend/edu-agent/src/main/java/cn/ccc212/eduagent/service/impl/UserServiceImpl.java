package cn.ccc212.eduagent.service.impl;

import cn.ccc212.eduagent.config.properties.JwtProperties;
import cn.ccc212.eduagent.constant.CommonConstant;
import cn.ccc212.eduagent.constant.JwtClaimsConstant;
import cn.ccc212.eduagent.constant.RoleConstant;
import cn.ccc212.eduagent.context.AuthContext;
import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.mapper.RoleMapper;
import cn.ccc212.eduagent.mapper.UserMapper;
import cn.ccc212.eduagent.pojo.dto.user.*;
import cn.ccc212.eduagent.pojo.entity.Role;
import cn.ccc212.eduagent.pojo.entity.User;
import cn.ccc212.eduagent.pojo.vo.UserInfoVO;
import cn.ccc212.eduagent.pojo.vo.UserLoginVO;
import cn.ccc212.eduagent.service.IUserService;
import cn.ccc212.eduagent.utils.JwtUtil;
import cn.ccc212.eduagent.utils.PageUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ccc212
 * @since 2025-06-03
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public UserLoginVO login(UserLoginDTO userLoginDTO) {

        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        // 获取用户
        User user;
        try {
            user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        } catch (Exception e) {
            throw new BizException(StatusCodeEnum.LOGIN_FAILED);
        }

        // 判断用户是否存在
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        // 判断密码是否正确
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            throw new BizException(StatusCodeEnum.PASSWORD_ERROR);
        }

        // 更新登录时间
        userMapper.update(new LambdaUpdateWrapper<User>()
                .eq(User::getUsername, username)
                .set(User::getLastLoginTime, LocalDateTime.now()));

        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        claims.put(JwtClaimsConstant.NAME, user.getName());
        claims.put(JwtClaimsConstant.USERNAME, user.getUsername());
        claims.put(JwtClaimsConstant.ROLE_CODE, user.getRoleCode());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );
        AuthContext.setJwtClaims(JwtUtil.parseJWT(jwtProperties.getSecretKey(), token));

        return UserLoginVO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .username(user.getUsername())
                .studentId(user.getStudentId() == null ? "" : user.getStudentId())
                .roleCode(user.getRoleCode())
                .token(token)
                .lastLoginTime(user.getLastLoginTime())
                .build();
    }

    @Override
    @Transactional
    public Long register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        User existUser = lambdaQuery().eq(User::getUsername, username).one();

        if (existUser != null) {
            throw new BizException(StatusCodeEnum.USERNAME_EXIST);
        }

        // 默认名字为用户名，角色为普通用户
        User user = new User()
                .setName(username)
                .setUsername(username)
                .setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()))
                .setRoleCode(RoleConstant.GUEST)
                .setLastLoginTime(LocalDateTime.now());

        save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        claims.put(JwtClaimsConstant.NAME, user.getName());
        claims.put(JwtClaimsConstant.USERNAME, user.getUsername());
        claims.put(JwtClaimsConstant.ROLE_CODE, user.getRoleCode());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims
        );
        AuthContext.setJwtClaims(JwtUtil.parseJWT(jwtProperties.getSecretKey(), token));

        return user.getUserId();
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<UserInfoVO> search(UserPageDTO userPageDTO) {
        Page<User> page = new Page<>(userPageDTO.getPage(), userPageDTO.getPageSize());
        PageUtils.setOrderValue(User.class, userPageDTO);
        LocalDateTime createTimeStart = userPageDTO.getCreateTimeStart();
        LocalDateTime createTimeEnd = userPageDTO.getCreateTimeEnd();
        if (createTimeStart != null && createTimeEnd != null && createTimeStart.isAfter(createTimeEnd)) {
            throw new BizException(StatusCodeEnum.CREATE_TIME_START_AFTER_END);
        }
        return userMapper.search(page, userPageDTO);
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserDTO updateUserDTO) {
        Long targetUserId = updateUserDTO.getUserId();
        if (lambdaQuery().eq(User::getUserId, targetUserId).one() == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }

        if (updateUserDTO.getRoleCode() != null) {
            if (!Objects.equals(AuthContext.getJwtClaims().get(JwtClaimsConstant.ROLE_CODE, Integer.class), RoleConstant.ADMIN)) {
                throw new BizException("修改用户角色需管理员权限");
            }

            if (roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleCode, updateUserDTO.getRoleCode())) == null) {
                throw new BizException(StatusCodeEnum.ROLE_NOT_EXISTS);
            }
        }

        User user = BeanUtil.copyProperties(updateUserDTO, User.class);
        updateById(user);
    }

    @Override
    @Transactional
    public void updatePassword(String oldPassword, String newPassword) {
        Long userId = AuthContext.getUserId();
        User user = getById(userId);
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            throw new BizException(StatusCodeEnum.PASSWORD_ERROR);
        }
        update(new LambdaUpdateWrapper<User>()
                .eq(User::getUserId, userId)
                .set(User::getPassword, DigestUtils.md5DigestAsHex(newPassword.getBytes())));
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        Claims claims = AuthContext.getJwtClaims();
        Long currentId = claims.get(JwtClaimsConstant.USER_ID, Long.class);
        Integer roleCode = claims.get(JwtClaimsConstant.ROLE_CODE, Integer.class);

        for (Long id : ids) {
            // 判断用户是否存在
            if (lambdaQuery().eq(User::getUserId, id).one() == null) {
                throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
            }

            // 非管理员不能删除其他用户
            if (!id.equals(currentId) && roleCode != RoleConstant.ADMIN) {
                throw new BizException("删除其他用户需管理员权限");
            }

            userMapper.deleteById(id);
        }
    }

    @Override
    public UserInfoVO getByUserId(Long id) {
        if (!Objects.equals(AuthContext.getUserId(), id) && AuthContext.getJwtClaims().get(JwtClaimsConstant.ROLE_CODE, Integer.class) != RoleConstant.ADMIN) {
            throw new BizException("获取其他用户信息需要管理员权限");
        }
        User user = getById(id);
        if (user == null) {
            throw new BizException(StatusCodeEnum.USER_NOT_FOUND);
        }
        return BeanUtil.copyProperties(user, UserInfoVO.class);
    }

    @Override
    public void batchInsert(List<AddUserDTO> addUserDTOS) {
        List<User> users = BeanUtil.copyToList(addUserDTOS, User.class);
        Set<Integer> requestedRoleCodes = users.stream().map(User::getRoleCode).collect(Collectors.toSet());
        Integer existingRoleCodesCount = roleMapper.findExistingRoleCodes(requestedRoleCodes);
        if (existingRoleCodesCount != requestedRoleCodes.size()) {
            throw new BizException(StatusCodeEnum.ROLE_NOT_EXISTS);
        }
        // 默认密码为123456
        users.forEach(user -> user
                .setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()))
                .setDelFlag(false)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
        );
        userMapper.batchInsert(users);
    }

    @Override
    public void setRoles(List<SetRoleDTO> setRoleDTOs) {
        Set<Integer> requestedRoleCodes = setRoleDTOs.stream().map(SetRoleDTO::getRoleCode).collect(Collectors.toSet());
        Integer existingRoleCodesCount = roleMapper.findExistingRoleCodes(requestedRoleCodes);
        if (existingRoleCodesCount != requestedRoleCodes.size()) {
            throw new BizException(StatusCodeEnum.ROLE_NOT_EXISTS);
        }
        userMapper.setRoles(setRoleDTOs);
    }

}
