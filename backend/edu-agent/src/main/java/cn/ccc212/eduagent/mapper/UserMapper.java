package cn.ccc212.eduagent.mapper;

import cn.ccc212.eduagent.pojo.dto.user.SetRoleDTO;
import cn.ccc212.eduagent.pojo.dto.user.UserPageDTO;
import cn.ccc212.eduagent.pojo.entity.User;
import cn.ccc212.eduagent.pojo.vo.user.UserInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author ccc212
 * @since 2025-06-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<UserInfoVO> search(Page<User> page, @Param("userPageDTO") UserPageDTO userPageDTO);

    void batchInsert(List<User> users);

    void setRoles(List<SetRoleDTO> setRoleDTOs);

    Long getDaylyUsageCount(Integer roleCode);

    Long getWeeklyUsageCount(Integer roleCode);
}
