package cn.ccc212.eduagent.controller;

import cn.ccc212.eduagent.annotation.Log;
import cn.ccc212.eduagent.annotation.RequireRoles;
import cn.ccc212.eduagent.constant.MessageConstant;
import cn.ccc212.eduagent.constant.RoleConstant;
import cn.ccc212.eduagent.enums.BusinessTypeEnum;
import cn.ccc212.eduagent.pojo.dto.PageDTO;
import cn.ccc212.eduagent.pojo.entity.Result;
import cn.ccc212.eduagent.pojo.vo.OperLogVO;
import cn.ccc212.eduagent.service.IOperLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author 
 * @since 2024-10-13
 */
@RestController
@RequestMapping("/operLog")
@Tag(name = "操作日志相关接口")
@RequiredArgsConstructor
public class OperLogController {

    private final IOperLogService operLogService;

    @RequireRoles({ RoleConstant.ADMIN })
    @GetMapping("/page")
    @Operation(summary = "分页查询", description = "倒序展示操作日志，即最新的在最前面，需要管理员权限")
    public Result<IPage<OperLogVO>> page(@Valid PageDTO pageDTO) {
        return Result.success(operLogService.pageQuery(pageDTO));
    }

    @RequireRoles({ RoleConstant.ADMIN })
    @Log(title = "清除日志", businessType = BusinessTypeEnum.DELETE)
    @DeleteMapping("/clear")
    @Operation(summary = "清除日志", description = "清除所有日志，需要管理员权限")
    public Result<?> clear() {
        operLogService.clear();
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }
}
