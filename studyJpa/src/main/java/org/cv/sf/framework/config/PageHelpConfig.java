/*
package org.cv.sf.framework.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * java.lang.IllegalStateException: Failed to introspect Class
 * [com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration]
 * from ClassLoader [sun.misc.Launcher$AppClassLoader@18b4aac2]
 *//*

import java.util.Properties;

//@Configuration
public class PageHelpConfig {
    //@Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        //页码和页面大小两个参数进行分页
        p.setProperty("offsetAsPageNum", "true");
        //设置为true时，使用RowBounds分页会进行count查询
        p.setProperty("rowBoundsWithCount", "true");
        //3.5.0版本可用 - 为了支持startPage(Object params
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
*/
