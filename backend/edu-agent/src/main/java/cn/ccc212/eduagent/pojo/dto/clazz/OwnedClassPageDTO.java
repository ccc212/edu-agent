package cn.ccc212.eduagent.pojo.dto.clazz;

import cn.ccc212.eduagent.pojo.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class OwnedClassPageDTO extends PageDTO {

    /**
     * 班级主键
     */
    @Schema(example = "1")
    private Long classId;

    /**
     * 班级名称
     */
    @Schema(example = "班级名称")
    private String className;

    /**
     * 班级描述
     */
    @Schema(example = "班级描述")
    private String description;

    /**
     * 创建时间起始
     */
    @Schema(example = "2022-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(example = "2032-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    @Override
    public OwnedClassPageDTO setPage(int page) {
        super.setPage(page);
        return this;
    }

    @Override
    public OwnedClassPageDTO setPageSize(int pageSize) {
        super.setPageSize(pageSize);
        return this;
    }

    @Override
    public OwnedClassPageDTO setOrderByColumn(String orderByColumn) {
        super.setOrderByColumn(orderByColumn);
        return this;
    }

    @Override
    public OwnedClassPageDTO setSortOrder(String sortOrder) {
        super.setSortOrder(sortOrder);
        return this;
    }
}
