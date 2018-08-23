package kaizone.songmaya.smartns.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtil {

    public static void save(String base64, String target) throws Exception {
        if (StringUtils.isEmpty(base64) || StringUtils.isEmpty(target)) {
            return;
        }
        byte[] b = Base64Utils.decode(base64.getBytes());
        OutputStream out = new FileOutputStream(target);
        out.write(b);
        out.flush();
        out.close();
    }

}
