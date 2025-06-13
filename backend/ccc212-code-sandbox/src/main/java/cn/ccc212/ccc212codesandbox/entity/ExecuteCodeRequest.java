package cn.ccc212.ccc212codesandbox.entity;

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
