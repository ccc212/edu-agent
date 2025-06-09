package cn.ccc212.eduagent.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * IP工具类
 */
@SuppressWarnings("unused")
public class IpUtil {
    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    private static final String LOCALHOST_IPV6_FULL = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_IPV6_SHORT = "::1";

    /**
     * 获取ip
     * @param request 请求
     * @return {@link String }
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = getHeaderIp(request);
        if (ipAddress == null || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = getLocalHostIp(request);
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    private static String getHeaderIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        return ipAddress;
    }

    private static String getLocalHostIp(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6_FULL.equals(ipAddress) || LOCALHOST_IPV6_SHORT.equals(ipAddress)) {
            try {
                InetAddress inet = InetAddress.getLocalHost();
                ipAddress = inet.getHostAddress();
            } catch (UnknownHostException e) {
                // 记录日志
                System.err.println("Failed to get local host IP address: " + e.getMessage());
                ipAddress = "unknown";
            }
        }
        return ipAddress;
    }

    // 验证 IP 地址是否有效
    private static boolean isValidIp(String ip) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            return !inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().equals(ip);
        } catch (UnknownHostException e) {
            return false;
        }
    }

    /**
     * 获取网关ip
     * @param request 请求
     * @return {@link String }
     */
    public static String getGatwayIpAddress(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }
        return ip;
    }
}
