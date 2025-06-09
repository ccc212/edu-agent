package cn.ccc212.eduagent.service;

import cn.ccc212.eduagent.pojo.dto.PageDTO;
import cn.ccc212.eduagent.pojo.entity.OperLog;
import cn.ccc212.eduagent.pojo.vo.OperLogVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author ccc212
 * @since 2024-10-08
 */
public interface IOperLogService extends IService<OperLog> {

    IPage<OperLogVO> pageQuery(PageDTO pageDTO);

    void clear();
}
