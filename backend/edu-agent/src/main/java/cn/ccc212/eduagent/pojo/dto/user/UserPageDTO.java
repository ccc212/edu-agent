package cn.ccc212.eduagent.pojo.dto.user;

import cn.ccc212.eduagent.pojo.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class UserPageDTO extends PageDTO {

    // 用户id
    @Schema(example = "1")
    private Long userId;

    // 名字
    @Schema(example = "名字")
    private String name;

    // 用户名
    @Schema(example = "用户名")
    private String username;

    // 学号
    @Schema(example = "202200000000")
    private String studentId;

    // 邮箱
    @Schema(example = "example@example.com")
    private String email;

    // 角色编号
    @Schema(example = "1")
    private Integer roleCode;

    // 是否删除(1是,0否)
    @Schema(example = "0")
    private Integer delFlag;

    // 创建时间起始
    @Schema(example = "2022-01-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    // 创建时间结束
    @Schema(example = "2032-01-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

    @Override
    public UserPageDTO setPage(int page) {
        super.setPage(page);
        return this;
    }

    @Override
    public UserPageDTO setPageSize(int pageSize) {
        super.setPageSize(pageSize);
        return this;
    }

    @Override
    public UserPageDTO setOrderByColumn(String orderByColumn) {
        super.setOrderByColumn(orderByColumn);
        return this;
    }

    @Override
    public UserPageDTO setSortOrder(String sortOrder) {
        super.setSortOrder(sortOrder);
        return this;
    }
}
