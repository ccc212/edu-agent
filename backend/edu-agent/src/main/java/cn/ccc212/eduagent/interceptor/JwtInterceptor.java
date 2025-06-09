package cn.ccc212.eduagent.interceptor;

import cn.ccc212.eduagent.config.properties.JwtProperties;
import cn.ccc212.eduagent.constant.JwtClaimsConstant;
import cn.ccc212.eduagent.context.AuthContext;
import cn.ccc212.eduagent.enums.StatusCodeEnum;
import cn.ccc212.eduagent.exception.BizException;
import cn.ccc212.eduagent.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("当前线程id:{}" ,Thread.currentThread().getId());

        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());
        if (StringUtils.isBlank(token)) {
            throw new BizException(StatusCodeEnum.NO_LOGIN);
        }

        // 2、校验令牌
        try {
            log.info("JWT校验:{}" ,token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户id:{}" ,userId);
            AuthContext.setJwtClaims(claims);
            // 3、通过，放行
            return true;
        } catch (Exception ex) {
            // 4、不通过，响应401状态码
            log.error("JWT token验证失败: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // 请求完成后清除ThreadLocal，防止内存泄漏
        AuthContext.removeJwtClaims();
    }

}
