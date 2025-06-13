package cn.ccc212.eduagent.pojo.entity.codesandbox;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ExecuteCodeRequest {

    private List<String> inputList;

    private String code;

    private String language;
}
