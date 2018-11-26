package com.ks.tool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ks.tool.model.Parameters;


@Controller  
@RequestMapping(value="/file")
public class CutPicktureUtil{
	static ImgEditor imgEditor = new ImgEditor();
	public String filePathFinal = "";
	
	/**
	 * fileName:根据源文件生成的临时文件名
	 * fileResult:图片文件字符串，需用ISO-8859-1编码来获取文件流
	 * x:裁剪起点的x坐标
	 * y：裁剪起点的y坐标
	 * w：裁剪的宽度
	 * h:裁剪的高度
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/img/cutandscale.do",method=RequestMethod.POST)
	public @ResponseBody
	static int cutAndscaleimg(HttpServletRequest request,HttpSession session){//@RequestParam("w") int w,@RequestParam("h") int h,@RequestParam("x") int x,@RequestParam("y") int y){
		String fileResult = request.getParameter("fileResult");
		InputStream is = null;
		try {
			//读取文件的编码一般是ISO-8859-1
			is = new ByteArrayInputStream(fileResult.getBytes("ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			//生成临时目录
			File file2 = new File("d:\\temporary\\"+request.getParameter("file"));
			if(!file2.getParentFile().exists()){
                // 目标文件所在目录不存在
                if(!file2.getParentFile().mkdirs()){
                    // 复制文件失败：创建目标文件所在目录失败  
                	throw new RuntimeException("复制文件失败：创建目标文件所在目录失败 ..........");
                }  
            }
			fos = new java.io.FileOutputStream("d:\\temporary\\"+request.getParameter("file"));
			byte[] buffer = new byte[1024];
	        int count = 0;
	        while ((count = is.read(buffer)) != -1){
	        	fos.write(buffer, 0, count);
	        }
	        fos.close();
	        is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
	        try {
				fos.close();
		        is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int x = Integer.parseInt(request.getParameter("x"));
		int y = Integer.parseInt(request.getParameter("y"));
		int w = Integer.parseInt(request.getParameter("w"));
		int h = Integer.parseInt(request.getParameter("h"));
		String newPicktureUrl = "d:\\temporary\\"+session.getAttribute("userId")+"\\"+request.getParameter("fileName");
		fileResult = "d:\\temporary\\"+request.getParameter("file");
		imgEditor.cut(fileResult,newPicktureUrl,x,y,w,h);
		//imgEditor.scale(filePathFinal, filePathFinal, 110, 110, false);
		return 1;
	}
	
	@RequestMapping(value="/img/upload",method=RequestMethod.POST)  
    public @ResponseBody Parameters addImage(@RequestParam("userphoto") MultipartFile file,HttpServletRequest request,
    		HttpServletResponse response,HttpSession session){
		String filePath = "";
		try{
			//上传原图
			filePath = imgEditor.uploadFile(file, request,session);
			filePathFinal = filePath;
			//将图片压缩成指定大小
			imgEditor.zoomImage(filePath,filePath,400,400);
		}catch(IOException e){
			e.printStackTrace();
		}
		Parameters parameter = new Parameters();
        parameter.setFileName(imgEditor.getFileName(file,request,session));
        return parameter;
    }
	
}
