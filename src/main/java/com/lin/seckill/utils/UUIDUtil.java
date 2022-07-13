package com.lin.seckill.utils;

import java.util.UUID;

/**
 * <p>UUID工具类</p>
 *
 * @author : star
 * @date : 2022/7/13 23:00
 */
public class UUIDUtil {

   public static String uuid() {
      return UUID.randomUUID().toString().replace("-", "");
   }

}