package cn.limexc.util;

import org.junit.Test;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-22 14:49
 * @since jdk1.8.0
 */
public class DiskSizeTest {

    @Test
    public void getTotalTest() {
        new DiskSize().getTotal("G:\\SoftWare");
    }
}