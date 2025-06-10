package cn.ccc212.eduagent.utils;

import cn.ccc212.eduagent.pojo.dto.PageDTO;
import cn.ccc212.eduagent.pojo.entity.StudentClass;
import cn.ccc212.eduagent.pojo.entity.User;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageUtils {

    // 存储每个DTO类型（或对应的表名）的可排序字段白名单
    private static final Map<Class<?>, Set<String>> ALLOWED_SORT_COLUMNS_MAP = new HashMap<>();

    static {
        // User
        ALLOWED_SORT_COLUMNS_MAP.put(User.class,
                new HashSet<>(Arrays.asList(
                        "user_id", "name", "username", "role_code", "valid_start_time",
                        "valid_end_time", "del_flag", "create_time", "last_login_time"
                ))
        );

        // Class
        ALLOWED_SORT_COLUMNS_MAP.put(cn.ccc212.eduagent.pojo.entity.Class.class,
                new HashSet<>(Arrays.asList(
                        "class_id", "class_name", "teacher_id", "description", "create_time"
                ))
        );

        // StudentClass
        ALLOWED_SORT_COLUMNS_MAP.put(StudentClass.class,
                new HashSet<>(Arrays.asList(
                        "student_id", "class_id", "status"
                ))
        );

        // 确保Map是不可修改的，防止运行时被篡改
        Collections.unmodifiableMap(ALLOWED_SORT_COLUMNS_MAP);
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void setOrderValue(Class<?> className, PageDTO pageDTO) {
        if (pageDTO.getOrderByColumn() != null) {
            String orderByColumn = PageUtils.humpToLine(pageDTO.getOrderByColumn());
            if (ALLOWED_SORT_COLUMNS_MAP.get(className).contains(orderByColumn)) {
                pageDTO.setOrderByColumn(orderByColumn);
                // 如果排序顺序不是降序(非排序字符)，则默认顺序
                if (!Objects.equals(pageDTO.getSortOrder(), "desc")) {
                    pageDTO.setSortOrder("asc");
                }
            } else {
                pageDTO.setOrderByColumn(null);
            }
        }
    }
}
