package cn.ccc212.eduagent.judge.codesandbox.impl;

import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.judge.codesandbox.CodeSandbox;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeRequest;
import cn.ccc212.eduagent.pojo.entity.codesandbox.ExecuteCodeResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * 远程代码沙箱 (实际调用接口的代码沙箱)
 */
@Slf4j
public class RemoteCodeSandbox implements CodeSandbox {

    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = DigestUtils.md5DigestAsHex("edu-agent-api".getBytes());

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8858/executeCode";
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
