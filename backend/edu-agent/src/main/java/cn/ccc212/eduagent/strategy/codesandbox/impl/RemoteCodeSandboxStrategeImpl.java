package cn.ccc212.eduagent.strategy.codesandbox.impl;

import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.ccc212.eduagent.strategy.codesandbox.CodeSandboxStrategy;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * 远程代码沙箱 (实际调用接口的代码沙箱)
 */
@Component
@Slf4j
public class RemoteCodeSandboxStrategeImpl implements CodeSandboxStrategy {

    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = DigestUtils.md5DigestAsHex("edu-agent-api".getBytes());

    @Value("${codesandbox.host:localhost}")
    private String remoteHost;

    @Value("${codesandbox.port:8858}")
    private String remotePort;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = String.format("http://%s:%s/executeCode", remoteHost, remotePort);
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            log.error("executeCode remoteSandbox error, message = " + responseStr);
            throw new BizException(StatusCodeEnum.API_REQUEST_ERROR);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
