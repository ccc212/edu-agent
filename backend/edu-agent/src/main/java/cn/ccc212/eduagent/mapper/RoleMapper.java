package cn.ccc212.eduagent.mapper;

import cn.ccc212.eduagent.pojo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author ccc212
 * @since 2025-01-04
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    Integer findExistingRoleCodes(Set<Integer> requestedRoleCodes);
}
