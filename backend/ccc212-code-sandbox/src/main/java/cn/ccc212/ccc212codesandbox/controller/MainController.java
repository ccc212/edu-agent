package cn.ccc212.ccc212codesandbox.controller;

import cn.ccc212.ccc212codesandbox.impl.JavaDockerCodeSandbox;
import cn.ccc212.ccc212codesandbox.impl.JavaNativeCodeSandbox;
import cn.ccc212.ccc212codesandbox.entity.ExecuteCodeRequest;
import cn.ccc212.ccc212codesandbox.entity.ExecuteCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("/")
@RequiredArgsConstructor
public class MainController {

    private final JavaNativeCodeSandbox javaNativeCodeSandbox;
    private final JavaDockerCodeSandbox javaDockerCodeSandbox;

    // 鉴权请求头和密钥
    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = DigestUtils.md5DigestAsHex("edu-agent-api".getBytes());

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            return null;
        }

        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        return javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }
}
