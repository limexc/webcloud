package cn.limexc.util;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * 设置返回前端数据的格式。
 * 在此感谢      https://github.com/josnname/wangpan
 * CSDN项目介绍：https://blog.csdn.net/qq_21187515/article/details/103259335
 * 本项目大多数源码借鉴于此项目。
 */

@Component
public class ResultData {
	private Object data;
	public void setData(Object data) {
		this.data = data;
	}
	

	public void writeToResponse(HttpServletResponse rp) {
		String jsonstr = JSONObject.toJSONString(data);
		byte[] bs;
		try {
			bs = jsonstr.trim().getBytes("utf-8");
			rp.setContentType("application/json;charset=UTF-8");
			rp.getOutputStream().write(bs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
/*
 * {
 *   code:200,
 *   msg:"",
 *   data:{}
 * }
 * 
 * */
	
	/*
	 * {
	 *   code:200,
	 *   msg:"",
	 *   data:[]
	 * }
	 * 
	 * */
}
