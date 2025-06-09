package cn.ccc212.eduagent.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class PageDTO extends BaseDTO{

    /**
     * 页码
     */
    @Min(value = 1, message = "页码最小为1")
    @Schema(example = "1")
    private int page = 1;

    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页数量最小为1")
    @Max(value = 100, message = "每页数量最大为100")
    @Schema(example = "10")
    private int pageSize = 10;

    /**
     * 排序字段
     */
    @Schema(example = "createTime")
    private String orderByColumn = "createTime";

    /**
     * 排序方式(升序asc、降序desc)
     */
    @Schema(example = "asc")
    private String sortOrder = "desc";

}
