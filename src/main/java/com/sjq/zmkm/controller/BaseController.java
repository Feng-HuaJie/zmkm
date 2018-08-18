package com.sjq.zmkm.controller;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sjq.zmkm.dao.entity.User;
import com.sjq.zmkm.exception.BusinessException;
@SuppressWarnings("unchecked")
public class BaseController{
	/**
	 * 你要知道，在继承关系中，不管父类还是子类，这些类里面的this
	 * 都代表了最终new出来时的那个类型的实例对象，
	 * 所以在父类中你可以中this获取到子类的信息！
	 */
	protected  final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected static ThreadLocal<HttpServletRequest> _request = new ThreadLocal<HttpServletRequest>();  
	protected static ThreadLocal<HttpServletResponse> _response = new ThreadLocal<HttpServletResponse>(); 
	protected static ThreadLocal<HttpSession> _session = new ThreadLocal<HttpSession>();  

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){

    	_request.set(request);
    	_response.set(response);
    	_session.set(request.getSession());
    }
	
//    /**
//	 * 获取requestbody 
//	 */
//    public String read(){
//    	try{
//			StringBuffer jsonString = new StringBuffer();  
//	    	BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) _request.get().getInputStream(),"UTF-8"));  
//			String line = null;  
//			while ((line = br.readLine()) != null) {  
//				jsonString.append(line);  
//			} 
//			return jsonString.toString();
//    	}catch (Exception e){
//    		logger.error("服务器异常", e);
//    		//这里将异常转为runtime 在global中统一处理
//    		throw new RuntimeException("服务器异常，读取requestbody异常");
//    	}
//    }
//    /**
//	 * 获取requestbody 转为json
//	 */
//	public JSONObject getRequestJson(){
//		String requestBodyStr=this.read();
//		try{
//			JSONObject jsonobject = JSONObject.fromObject(requestBodyStr);
//			return jsonobject;
//		}catch(Exception e){
//			throw new BusinessException("请求参数格式异常，无法被转换为json");
//		}
//    }
//	/**
//	 * 获取requestbody 转换为java对象
//	 */
//	public <T> T getRequestBody( Class c){
//		JSONObject jsonobject=this.getRequestJson();
//		try{
//			T obj = (T)JSONObject.toBean(jsonobject, c);
//			return obj;
//		}catch(Exception e){
//			throw new BusinessException("请求参数格式异常，json.toBeab异常");
//		}
//    }
//	/**
//	 * 获取requestbody 转换为java对象的集合
//	 */
//	public <T> T getRequestBodyArray(Class c){
//		String requestBodyStr=this.read();
//		JSONArray jsonArray = JSONArray.fromObject(requestBodyStr);
//		T obj = (T)JSONArray.toCollection(jsonArray, c);
//		return obj;
//	}
//	/**
//	 * 获取requestbody 转换为java复合对象
//	 */
//  	public <T> T getRequestBodyMuti(Class c,Map<String,Class> map){
//		
//		String requestBodyStr=this.read();
//		JSONObject jsonobject = JSONObject.fromObject(requestBodyStr);
//		T obj = (T)JSONObject.toBean(jsonobject, c,map);
//		return obj;
//    }
//  	//将制定的html内容片段 导出word
//	public String getRequestBody4Word() throws IOException{
//		  
//		 //String content=(String)request.getAttribute("requestBodyStr");
//		 // content=content.replace("content=", "");
//		 String content=_request.get().getParameter("content");
//		 logger.info("接收到String参数="+URLDecoder.decode(content,"UTF-8"));
//	     String htmlContent= "<!doctype html>"+
//					"<html>"+
//					"<head>"+
//					"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
//					"</head>"+
//					"<body>"+
//						URLDecoder.decode(content,"UTF-8")+
//					"</body>"+
//					"</html>";
//		return htmlContent;
//	}
	
// 	/**
//  	 * 响应json数据
//  	 */
//    public void response(Response responseObj){
//		
//    	_response.get().setContentType("text/html;charset=utf-8");  
//        PrintWriter out=null;
//		try {
//			out = _response.get().getWriter();
//		} catch (IOException e) {
//			logger.error("服务器异常", e);
//			//这里将异常转为runtime 在global中统一处理
//    		throw new RuntimeException("服务器异常，response异常");
//		}
//		out.println(JSONObject.fromObject(responseObj).toString());
//        out.flush();  
//        out.close();  
//	}
    
  	/**
  	 * 获取url后面拼接的参数
  	 */
    public HashMap<String,Object> getRequestParamMap(){
    	
    	HashMap<String,Object> map=new HashMap<String,Object>();
    	Enumeration<String> pNames=_request.get().getParameterNames();
    	while(pNames.hasMoreElements()){
    	    String name=(String)pNames.nextElement();
    	    String cnm=_request.get().getParameter(name);
    	    String value=cnm.toString();
    	    map.put(name, value);
    	}
    	return map;
    }
    
    /**
     * validate 验证用户是否登录
     */
    public User userValidate(int userId){
    	
    	User user=(User)_request.get().getSession().getAttribute("user");
		if(user==null){
			throw new BusinessException("操作异常，用户未登录");
		}
		if(user.getPkid()!=userId){
			 throw new BusinessException("操作异常：非法访问");
		}
		return user;
	}
    /**
     * validate 验证用户是否登录
     */
    public User userValidate(String phone){
    	
    	User user=(User)_request.get().getSession().getAttribute("user");
		if(user==null){
			throw new BusinessException("操作异常，用户未登录");
		}
	    if(user.getPhone()!= phone) {
			throw new BusinessException("操作异常，非法访问");
		}
		return user;
	}
    /**
     * 验证用户是否登录
     */
    public User adminValidate(int adminId){
    	
    	User user=(User)_request.get().getSession().getAttribute("admin");
		if(user==null){
			throw new BusinessException("操作异常，管理员未登录");
		}
		if(user.getPkid()!=adminId){
			 throw new BusinessException("操作异常：非法访问");
		}
		return user;
	}
    /**
     * 验证分页查询接口中pageNum pageSize参数是否正确
     */
    public void pageValidate(HashMap<String,Object> params){
    	if(params==null||params.isEmpty()){
    		throw new BusinessException("请求异常，缺少pageNum,pageSize参数");
    	}
    	String pageNum=(String) params.get("pageNum");
    	if(StringUtils.isBlank(pageNum)){
    		throw new BusinessException("请求异常，缺少pageNum");
    	}
		try {
			int temp=Integer.parseInt(pageNum);
			if(temp<=0){
				throw new BusinessException("请求异常，pageNum参数必须大于0");
			}
		} catch (NumberFormatException e) {
			throw new BusinessException("请求异常，pageNum参数必须为整数");
		}
		String pageSize=(String) params.get("pageSize");
    	if(StringUtils.isBlank(pageSize)){
    		throw new BusinessException("请求异常，缺少pageSize参数");
    	}
		try {
			int temp=Integer.parseInt(pageSize);
			if(temp<=0){
				throw new BusinessException("请求异常，pageSize参数必须大于0");
			}
		} catch (NumberFormatException e) {
			throw new BusinessException("请求异常，pageSize参数必须为整数");
		}
    }
}
