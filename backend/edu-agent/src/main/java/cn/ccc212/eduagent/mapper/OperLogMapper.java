package cn.ccc212.eduagent.mapper;

import cn.ccc212.eduagent.pojo.dto.PageDTO;
import cn.ccc212.eduagent.pojo.entity.OperLog;
import cn.ccc212.eduagent.pojo.vo.OperLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author ccc212
 * @since 2024-10-08
 */
@Mapper
public interface OperLogMapper extends BaseMapper<OperLog> {

    IPage<OperLogVO> pageQuery(Page<OperLog> page, PageDTO pageDTO);

}
