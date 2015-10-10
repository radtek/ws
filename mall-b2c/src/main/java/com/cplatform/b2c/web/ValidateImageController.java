package com.cplatform.b2c.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cplatform.b2c.model.SessionUser;
import com.cplatform.b2c.util.Constants;

/**
 * 输出验证码图像并记录验证码到session
 * <p>
 * Copyright: Copyright (c) 2013-6-19 下午12:18:44
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author jisn@c-platform.com
 * @version 1.0.0
 */
@Controller
@RequestMapping("/image")
public class ValidateImageController {

	static final Log logger = LogFactory.getLog(ValidateImageController.class);

	/** 随机字符范围 */
	protected static String RANGE = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789";

	/**
	 * 生成绑定邮箱图片验证码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "bindEmail/code", method = RequestMethod.GET)
	@ResponseBody
	public void bindEmailCode(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		genCode(res, session, Constants.SESSION_CENTER_USER_BIND_EMAIL_KEY);
	}

	/**
	 * 生成绑定手机图片验证码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "bindMoblie/code", method = RequestMethod.GET)
	@ResponseBody
	public void bindMoblieCode(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		genCode(res, session, Constants.SESSION_CENTER_USER_BIND_MOBLIE_KEY);
	}

	/**
	 * 生成修改手机_步骤1图片验证码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "editMoblie/step1/code", method = RequestMethod.GET)
	@ResponseBody
	public void editMoblieStep1Code(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		genCode(res, session, Constants.SESSION_CENTER_USER_EDIT_MOBLIE_STEP1_KEY);
	}

	/**
	 * 生成修改手机_步骤2图片验证码
	 * 
	 * @param model
	 * @param req
	 * @param res
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "editMoblie/step2/code", method = RequestMethod.GET)
	@ResponseBody
	public void editMoblieStep2Code(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		genCode(res, session, Constants.SESSION_CENTER_USER_EDIT_MOBLIE_STEP2_KEY);
	}

	/**
	 * 生成验证码
	 * 
	 * @param res
	 * @param session
	 * @throws IOException
	 */
	private void genCode(HttpServletResponse res, HttpSession session, String key) throws IOException {
		// ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputStream os = res.getOutputStream();
		try {
			SessionUser userinfo = SessionUser.getSessionUser(res);
			String rand = getValidateImage(RANGE, 100, 30, 4, os);
			session.setAttribute(key + userinfo.getId(), rand);
			res.setContentType("image/jpeg");
		}
		finally {
			if (os != null)
				os.close();
		}
	}

	/**
	 * 根据给定的字符范围及数量生成随机验证码图片并写入输出流
	 * 
	 * @param str
	 *            给定的字符范围，例如"0123456789"表示在0-9中挑选随机字进行生成
	 * @param width
	 *            生成图像的宽
	 * @param height
	 *            生成图像的高
	 * @param show
	 *            验证图片中包含字符的个数
	 * @param output
	 *            输出流对象
	 * @return 生成的随机字符
	 */
	private static String getValidateImage(String str, int width, int height, int show, OutputStream output) {
		Random random = new Random();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		Font font = new Font("Arial", Font.PLAIN, height - 1);
		int distance = 18;
		Graphics2D d = (Graphics2D) image.getGraphics();
		d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		d.setColor(Color.WHITE);
		d.fillRect(0, 0, image.getWidth(), image.getHeight());
		d.setColor(new Color(random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100));
		for (int i = 0; i < 10; i++) {
			d.drawLine(random.nextInt(image.getWidth()), random.nextInt(image.getHeight()), random.nextInt(image.getWidth()),
			           random.nextInt(image.getHeight()));
		}
		d.setColor(Color.BLACK);
		d.setFont(font);
		String checkCode = "";
		char tmp;
		int x = -distance;
		for (int i = 0; i < show; i++) {
			tmp = str.charAt(random.nextInt(str.length()));
			checkCode = checkCode + tmp;
			x = x + distance;
			d.setColor(new Color(random.nextInt(100) + 50, random.nextInt(100) + 50, random.nextInt(100) + 50));
			d.drawString(tmp + "", x, random.nextInt(image.getHeight() - (font.getSize())) + (font.getSize()));
		}
		d.dispose();
		try {
			ImageIO.write(image, "jpg", output);
		}
		catch (IOException e) {
			logger.warn("生成验证码错误.", e);
		}
		return checkCode;
	}
}