package net.deniro.land.common.captcha;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.TransparentBackgroundProducer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义验证码生成规则
 *
 * @author deniro
 *         2015/10/9
 */
public class CustomCaptchaServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(CustomCaptchaServlet.class);

    private static final long serialVersionUID = 1L;
    private static int _width = 200;
    private static int _height = 50;

    /**
     * 颜色库
     */
    private static final java.util.List<Color> COLORS = new ArrayList(2);

    /**
     * 字体库
     */
    private static final java.util.List<Font> FONTS = new ArrayList(3);

    static {
        COLORS.add(new Color(24, 78, 190));
//        COLORS.add(new Color(28,93,128));
//        COLORS.add(new Color(64,76,29));
//        COLORS.add(new Color(172,91,65));

        FONTS.add(new Font("Geneva", 2, 48));
//        FONTS.add(new Font("Courier", 1, 48));
//        FONTS.add(new Font("Arial", 1, 48));
    }

    public CustomCaptchaServlet() {
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if (this.getInitParameter("captcha-height") != null) {
            _height = Integer.valueOf(this.getInitParameter("captcha-height")).intValue();
        }

        if (this.getInitParameter("captcha-width") != null) {
            _width = Integer.valueOf(this.getInitParameter("captcha-width")).intValue();
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DefaultWordRenderer wordRenderer = new DefaultWordRenderer(COLORS, FONTS);
            Captcha captcha = (new Captcha.Builder(_width, _height)).addText(new
                    CustomTextProducer(), wordRenderer)
                    .addBackground(new TransparentBackgroundProducer()).addNoise(new
                            CurvedLineNoiseProducer(COLORS.get(0), 3.0F)).gimp
                            (new
                                    RippleGimpyRenderer())
                    .build();
            CaptchaServletUtil.writeImage(resp, captcha.getImage());
            req.getSession().setAttribute("simpleCaptcha", captcha);
        } catch (Exception e) {
            logger.error("自定义验证码生成规则", e);
        }
    }
}
