package com.ks.top;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图片处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/getPickture")
public class PicktureHandleController {
	
	/**
	 * 根据客户端的图片请求返回相应的图片文件流
	 * @param req
	 * @param res
	 */
	@ResponseBody
	@RequestMapping("/pickture.do")
	public void getPickture(HttpServletRequest req,HttpServletResponse res){
		String path = req.getParameter("path");
		res.setHeader("Pragma","No-cache");    
		res.setHeader("Cache-Control","no-cache");    
		res.setDateHeader("Expires", 0);
		BufferedInputStream bis = null;  
		OutputStream os = null;  
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(path));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		   
		bis = new BufferedInputStream(fileInputStream);  
		byte[] buffer = new byte[512];  
		res.reset();
		res.setCharacterEncoding("UTF-8");  
		//不同类型的文件对应不同的MIME类型  
		res.setContentType("image/png");  
		//文件以流的方式发送到客户端浏览器
		//response.setHeader("Content-Disposition","attachment; filename=img.jpg");
		//response.setHeader("Content-Disposition", "inline; filename=img.jpg");
		try {
			res.setContentLength(bis.available());  
			os = res.getOutputStream();
			int n;
			while((n = bis.read(buffer)) != -1){
			  os.write(buffer, 0, n);
			}
			bis.close();
			os.flush();
			os.close();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bis.close();
				os.flush();  
				os.close();
			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
