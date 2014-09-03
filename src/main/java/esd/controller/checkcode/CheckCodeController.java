package esd.controller.checkcode;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octo.captcha.service.CaptchaServiceException;

/**
 * 验证 码
 * 
 * @author zhangjianzong
 * 
 */
@Controller
@RequestMapping(value = "/checkcode")
public class CheckCodeController {
    private static Logger logger = Logger.getLogger(CheckCodeController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String testPage() {
        return "checkcode";
    }

    @RequestMapping(value = "/create")
    public void createCheckCode(HttpServletRequest request, HttpServletResponse response) {
        genernateCaptchaImage(request, response);
    }

    /**
     * 校验验证码
     * 
     * @param request
     * @param codeStr
     * @return
     */
    public Boolean validateCode(HttpServletRequest request, String codeStr) {
        String captchaId = request.getSession().getId();
        try {
            Boolean isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, codeStr);
            return isResponseCorrect;
        } catch (CaptchaServiceException e) {
            logger.error("error in validateCode", e);
        }
        return false;
    }

    @RequestMapping(value = "/check")
    @ResponseBody
    public Boolean check(@RequestParam(value = "checkcode") String codeStr, HttpServletRequest request,
            HttpServletResponse response, HttpSession session) {

        String captchaId = request.getSession().getId();
        try {
            Boolean isResponseCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, codeStr);
            if (isResponseCorrect) {
                return true;
            } else {
                return false;

            }
        } catch (CaptchaServiceException e) {
            logger.error("error in check", e);
        }

        return false;

    }

    private void genernateCaptchaImage(final HttpServletRequest request, final HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try (ServletOutputStream out = response.getOutputStream()) {
            String captchaId = request.getSession().getId();
            BufferedImage challenge = (BufferedImage)
                    CaptchaServiceSingleton.getInstance().getChallengeForID(captchaId, request.getLocale());
            ImageIO.write(challenge, "jpg", out);
            out.flush();
        } catch (CaptchaServiceException | IOException e) {
            logger.error("error in genernateCaptchaImage", e);
        }
    }
}
