package com.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.java.utils.FastDFSClient;
import com.java.utils.JsonUtils;

/**
 * 
 * @ClassName: PicUploadController 
 * @Description: 文件上传
 * @author:sangjin
 * @date 2017年10月27日 下午3:31:29
 */
@Controller
@RequestMapping("pic")
public class PicUploadController {

	@Value("${MuliFile_Server_Url}")
	private String muliFileServerUrl;

	@RequestMapping("upload")
	@ResponseBody
	public String PicUpload(MultipartFile uploadFile) {
		Map result = new HashMap<>();
		// 发布图片
		// 获取原来的名称
		String fileName = uploadFile.getOriginalFilename();
		// 获取后缀
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 获取图片地址
		// 加载配置文件
		try {
			FastDFSClient dfsClient = new FastDFSClient("classpath:resource/fileconfig.conf");
			// 返回地址
			String url = dfsClient.uploadFile(uploadFile.getBytes(), extName);
			url = muliFileServerUrl + url;
			result.put("error", 0);
			result.put("url", url);
		} catch (Exception e) {
			result.put("error", 1);
			result.put("message", "文件上传错误" + e.getMessage());
			e.printStackTrace();
		}
		return JsonUtils.objectToJson(result);
	}
}
