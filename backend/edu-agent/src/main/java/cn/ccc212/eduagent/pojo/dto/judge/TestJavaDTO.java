package cn.ccc212.eduagent.pojo.dto.judge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestJavaDTO {

    @NotBlank(message = "语言不能为空")
    @Schema(example = "java")
    private String language;

    @NotBlank(message = "代码不能为空")
    @Schema(example = "public class Main {\n" +
            "\n" +
            "    public static void main(String[] args) {\n" +
            "        int a = Integer.parseInt(args[0]);\n" +
            "        int b = Integer.parseInt(args[1]);\n" +
            "        System.out.println((a + b));\n" +
            "        System.out.println(\"ccc212是谁\");\n" +
            "    }\n" +
            "}\n")
    private String code;

}
