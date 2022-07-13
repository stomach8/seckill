package com.lin.seckill.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>手机号码检验</p>
 *
 * @author : star
 * @date : 2022/7/13 0:04
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile){
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }
}
