package com.sjq.zmkm.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sjq.zmkm.pojo.Response;
import com.sjq.zmkm.pojo.ResponseSuc;

/**
 * 生成随机验证码
 */
public class ValidateCodeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6659927690774611402L;

	private static final String VALIDATE_CODE = "validateCode";
	
	private ObjectMapper OBJECT_MAPPER = new ObjectMapper()
    	.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) //禁止使用出现未知属性之时，抛出异常
    	.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

	
	private int w = 70;
	private int h = 26;
	
	public ValidateCodeServlet() {
		super();
	}
	
	public void destroy() {
		super.destroy(); 
	}
    public void response(HttpServletResponse response,Response resp) throws IOException{
		response.setContentType("text/html;charset=utf-8");  
		response.setCharacterEncoding("UTF-8");  
        PrintWriter out;
		out = response.getWriter();
		out.println(OBJECT_MAPPER.writeValueAsString(resp));  
        out.flush();  
        out.close();  
	}
	public static boolean validate(HttpServletRequest request, String validateCode){
		String code = (String)request.getSession().getAttribute(VALIDATE_CODE);
		return validateCode.toUpperCase().equals(code); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		createImage(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String validateCode = request.getParameter(VALIDATE_CODE); // AJAX验证，成功返回true
		//response.getOutputStream().print(validate(request, validateCode)?"true":"false");
		Response respobj=new ResponseSuc("验证码验证成功");
		if(!validate(request, validateCode)){
			respobj=new ResponseSuc("验证码验证失败");
		}
		this.response(response, respobj);
	}
	
	private void createImage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		/*
		 * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		 */
//		String width = request.getParameter("width");
//		String height = request.getParameter("height");
//		if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height)) {
//			w = NumberUtils.toInt(width);
//			h = NumberUtils.toInt(height);
//		}
		
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		/*
		 * 生成背景
		 */
		createBackground(g);

		/*
		 * 生成字符
		 */
		String s = createCharacter(g);
		request.getSession().setAttribute(VALIDATE_CODE, s);

		g.dispose();
		OutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.close();

	}
	
	private Color getRandColor(int fc,int bc) { 
		int f = fc;
		int b = bc;
		Random random=new Random();
        if(f>255) {
        	f=255; 
        }
        if(b>255) {
        	b=255; 
        }
        return new Color(f+random.nextInt(b-f),f+random.nextInt(b-f),f+random.nextInt(b-f)); 
	}
	
	private void createBackground(Graphics g) {
		// 填充背景
		g.setColor(getRandColor(220,250)); 
		g.fillRect(0, 0, w, h);
		// 加入干扰线条
		for (int i = 0; i < 8; i++) {
			g.setColor(getRandColor(40,150));
			Random random = new Random();
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int x1 = random.nextInt(w);
			int y1 = random.nextInt(h);
			g.drawLine(x, y, x1, y1);
		}
	}

	private String createCharacter(Graphics g) {
		char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
				'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		String[] fontTypes = {"Arial","Arial Black","AvantGarde Bk BT","Calibri"}; 
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD,26)); 
			g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
//			g.drawString(r, i*w/4, h-5);
			s.append(r);
		}
		return s.toString();
	}
	
}
