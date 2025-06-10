package cn.ccc212.eduagent.pojo.dto.clazz;

import cn.ccc212.eduagent.pojo.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentClassPageDTO extends PageDTO {

    /**
     * 创建时间起始
     */
    @Schema(example = "2022-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(example = "2032-01-01 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    @Override
    public StudentClassPageDTO setPage(int page) {
        super.setPage(page);
        return this;
    }

    @Override
    public StudentClassPageDTO setPageSize(int pageSize) {
        super.setPageSize(pageSize);
        return this;
    }

    @Override
    public StudentClassPageDTO setOrderByColumn(String orderByColumn) {
        super.setOrderByColumn(orderByColumn);
        return this;
    }

    @Override
    public StudentClassPageDTO setSortOrder(String sortOrder) {
        super.setSortOrder(sortOrder);
        return this;
    }
}
