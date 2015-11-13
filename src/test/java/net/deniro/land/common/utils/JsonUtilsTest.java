package net.deniro.land.common.utils;

import net.deniro.land.api.entity.Images;
import org.junit.Test;

import java.util.List;

/**
 * @author deniro
 *         2015/11/13
 */
public class JsonUtilsTest {

    @Test
    public void testReadJson() throws Exception {
        String json = "[" +
                "{" +
                "\"imageAddr\":\"谁谁谁沃夫我学hhttp://weoruo/fwdw\",\n" +
                "\"imageType\":\"1\"\n" +
                "}" +
                "]";

        List<Images> list = JsonUtils.readJson(json, List.class, Images.class);
        System.out.println("$$$$$$$$$$$$" + list);
    }
}