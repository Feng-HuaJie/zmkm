package com.sjq.zmkm.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GGSDDC {

	private static final String HH="\r\n";
	private static final String TAB="\t";
	private static final String POSE_METHOD="post";
	private static final String PUT_METHOD="put";
	private static final String DELETE_METHOD="delete";
	private static final String GET_METHOD="get";
	Class clazz ;//Nfvo.class
	Field[] fields ;
	String className;///Nfvo
	String serviceName;//NfvoService
	String resourceName;//NfvoResource
	String daoName;//NfvoDao
	String serviceImpName;//NfvoServiceImp
	String daoImpName;//NfvoDaoImp
	String postMethodName;//registerNfvo
	String deleteMethodName;//disregisterNfvo
	String putMethodName;//updateNfvo
	String getMethodName;//retriveNfvo
	String objName;//nfvo
	
	public GGSDDC(Class clazz){
		this.clazz=clazz;
		this.fields=clazz.getDeclaredFields(); 
		this.className=clazz.getSimpleName();
		this.objName=lowerCaseFirstChar(className);
		this.resourceName=className+"Resource";
		this.daoName=className+"Dao";
		this.daoImpName=className+"DapImp";
		this.serviceName=className+"Service";
		this.serviceImpName=className+"ServiceImp";
		this.postMethodName="register"+className;
		this.putMethodName="update"+className;
		this.deleteMethodName="disregister"+className;
		this.getMethodName="retrive"+className;
	}
	//首字母小写
    public static String lowerCaseFirstChar(String name) {
    //  name = name.substring(0, 1).toUpperCase() + name.substring(1);
    //  return  name;
        char[] cs=name.toCharArray();
        cs[0]+=32;
        return String.valueOf(cs);
        
    }
	//首字母大写
    public static String upperCaseFirstChar(String name) {
    //  name = name.substring(0, 1).toUpperCase() + name.substring(1);
    //  return  name;
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
        
    }
  
	public StringBuffer setGet()throws Exception{
		
		//忽略引用类型
		StringBuffer sb=new StringBuffer();
		for(Field f : fields) { 
			//忽略掉序列化id
			if(f.getName().equalsIgnoreCase("serialVersionUID")){
				continue;
			}
			//忽略掉主键
			if(f.getName().equalsIgnoreCase("id")||f.getName().equalsIgnoreCase(className+"Id")){
				continue;
			}
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);  
			Method wM = pd.getWriteMethod(); 
			Method rM = pd.getReadMethod(); 
			Class type = f.getType();
			if (type == String.class) {
				 sb.append(TAB).append(TAB).append("existObj."+wM.getName().toString()+"(newObj."+rM.getName().toString()+"());").append(HH);
			}else if (type == Integer.class||type.getName().equals("int")) {
				sb.append(TAB).append(TAB).append("existObj."+wM.getName().toString()+"(newObj."+rM.getName().toString()+"());").append(HH);
			}else if (type == Long.class||type.getName().equals("long")) {
				sb.append(TAB).append(TAB).append("existObj."+wM.getName().toString()+"(newObj."+rM.getName().toString()+"());").append(HH);
			}else if (type == Double.class||type.getName().equals("double")) {
				sb.append(TAB).append(TAB).append("existObj."+wM.getName().toString()+"(newObj."+rM.getName().toString()+"());").append(HH);
			}else if(type == Boolean.class||type.getName().equals("boolean")){
				sb.append(TAB).append(TAB).append("existObj."+wM.getName().toString()+"(newObj."+rM.getName().toString()+"());").append(HH);
			}else{
				//忽略应用类型
			}
		} 
		return sb;
	}
	public StringBuffer beanToJson()throws Exception{
		//忽略引用类型
		StringBuffer sb=new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();  
		sb.append(TAB).append(TAB).append(TAB).append("for (int i=0; i<list.size(); i++) {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append("JSONObject jsonObj= new JSONObject();").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append(className+" obj = list.get(i);").append(HH);
		for(int i=0;i<fields.length;i++) { 
			Field f=fields[i];
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);  
			Method wM = pd.getWriteMethod(); 
			Method rM = pd.getReadMethod(); 
			Class type = f.getType();
			if (type == String.class) {
				sb.append(TAB).append(TAB).append(TAB).append(TAB).append("jsonObj.put(\""+f.getName()+"\", obj."+rM.getName().toString()+"());").append(HH);
			} else if (type == Integer.class||type.getName().equals("int")) {
				sb.append(TAB).append(TAB).append(TAB).append(TAB).append("jsonObj.put(\""+f.getName()+"\", obj."+rM.getName().toString()+"());").append(HH);	
			} else if (type == Long.class||type.getName().equals("long")) {
				sb.append(TAB).append(TAB).append(TAB).append(TAB).append("jsonObj.put(\""+f.getName()+"\", obj."+rM.getName().toString()+"());").append(HH);
			}else if (type == Double.class||type.getName().equals("double")) {
				sb.append(TAB).append(TAB).append(TAB).append(TAB).append("jsonObj.put(\""+f.getName()+"\", obj."+rM.getName().toString()+"());").append(HH);
			}else if(type == Boolean.class||type.getName().equals("boolean")){
				sb.append(TAB).append(TAB).append(TAB).append(TAB).append("jsonObj.put(\""+f.getName()+"\", obj."+rM.getName().toString()+"());").append(HH);
			}else{
				//忽略掉其他类型 包括自定义的引用类型
			}
		}
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append("jsonArray.add(jsonObj);").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("}").append(HH);
		return sb;
	}
	public StringBuffer getDclareField(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<fields.length;i++) { 
			Field field=fields[i];
			//忽略掉主键
			if(field.getName().equalsIgnoreCase("id")||field.getName().equalsIgnoreCase(className+"Id")){
				continue;
			}
			Class type = field.getType();
			if (type == String.class) {
				sb.append(TAB).append(TAB).append("String ").append(fields[i].getName()).append("=null;").append(HH);	
			} else if (type == Integer.class||type.getName().equals("int")) {
				sb.append(TAB).append(TAB).append("Integer ").append(fields[i].getName()).append("=null;").append(HH);	
			} else if (type == Long.class||type.getName().equals("long")) {
				sb.append(TAB).append(TAB).append("Long ").append(fields[i].getName()).append("=null;").append(HH);	
			}else if (type == Double.class||type.getName().equals("double")) {
				sb.append(TAB).append(TAB).append("Double ").append(fields[i].getName()).append("=null;").append(HH);	
			}else if(type == Boolean.class||type.getName().equals("boolean")){
				sb.append(TAB).append(TAB).append("Boolean ").append(fields[i].getName()).append("=null;").append(HH);	
			}else{
				//忽略掉其他类型 包括自定义的引用类型
			}
		}
		return sb;
	}
	
	public StringBuffer getValidateCode(){
		//目前只支持int 和string两种类型 其他类型全部忽略
		StringBuffer sb=new StringBuffer();
		for(Field f : fields) {  
			//忽略掉主键
			if(f.getName().equalsIgnoreCase("id")||f.getName().equalsIgnoreCase(className+"Id")){
				continue;
			}
			Class type = f.getType();
			if (type == String.class) {
				sb.append(TAB).append(TAB).append(TAB).append(f.getName()+" = this.getStringParameter(reqParam, \""+f.getName()+"\",true);").append(HH);
				sb.append(TAB).append(TAB).append(TAB).append("this.validateStringParameter(\""+f.getName()+"\", "+f.getName()+", 1, DBConstants.MEDIUM_STRING_LEN, null); ").append(HH);
				sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"").append(postMethodName).append(": parameter "+f.getName()+" {} passed validation.\", "+f.getName()+" );").append(HH).append(HH);
			} else if (type == Integer.class||type.getName().equals("int")) {
				sb.append(TAB).append(TAB).append(TAB).append(f.getName()+" = this.getIntegerParameter(reqParam, \""+f.getName()+"\",true);").append(HH);
				sb.append(TAB).append(TAB).append(TAB).append("this.validateIntegerParameter(\""+f.getName()+"\", "+f.getName()+", Integer.MIN_VALUE, Integer.MAX_VALUE); ").append(HH);
				sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"").append(postMethodName).append(": parameter "+f.getName()+" {} passed validation.\", "+f.getName()+" );").append(HH).append(HH);
			} else if (type == Long.class||type.getName().equals("long")) {
				//sb.append(TAB).append("Long ").append(fields[i].getName()).append(";").append(HH);	
			}else if (type == Double.class||type.getName().equals("double")) {
				//sb.append(TAB).append("Double ").append(fields[i].getName()).append(";").append(HH);	
			}else if(type == Boolean.class||type.getName().equals("boolean")){
				//sb.append(TAB).append(TAB).append("Double ").append(fields[i].getName()).append(";").append(HH);	
			}else{
				
			}
		} 
		return sb;
	}
	public StringBuffer getNewObjCode(String methodType) throws IntrospectionException{
		//忽略引用类型
		StringBuffer sb=new StringBuffer();
		sb.append(TAB).append(TAB).append(TAB).append(className).append(" newObj = new ").append(className).append("();").append(HH);
		for(Field f : fields) {  
			//如果是新增 则忽略掉主键
			if(methodType.equals(POSE_METHOD)){
				if(f.getName().equalsIgnoreCase("id")||f.getName().equalsIgnoreCase(className+"Id")){
					continue;
				}
			}
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);  
			Method wM = pd.getWriteMethod(); 
			Class type = f.getType();
			if (type == String.class) {
				 sb.append(TAB).append(TAB).append(TAB).append("newObj."+wM.getName().toString()+"("+f.getName()+");").append(HH);
			} else if (type == Integer.class||type.getName().equals("int")) {
				sb.append(TAB).append(TAB).append(TAB).append("newObj."+wM.getName().toString()+"("+f.getName()+");").append(HH);
			} else if (type == Long.class||type.getName().equals("long")) {
				sb.append(TAB).append(TAB).append(TAB).append("newObj."+wM.getName().toString()+"("+f.getName()+");").append(HH);
			}else if (type == Double.class||type.getName().equals("double")) {
				sb.append(TAB).append(TAB).append(TAB).append("newObj."+wM.getName().toString()+"("+f.getName()+");").append(HH);
			}else if(type == Boolean.class||type.getName().equals("boolean")){
				sb.append(TAB).append(TAB).append(TAB).append("newObj."+wM.getName().toString()+"("+f.getName()+");").append(HH);
			}else if(type == Boolean.class||type.getName().equals("boolean")){
				sb.append(TAB).append(TAB).append(TAB).append("newObj."+wM.getName().toString()+"("+f.getName()+");").append(HH);
			}else{
				
			}
		} 
		return sb;
	}
	
	/*
	 * resourece  class 
	 */
	public StringBuffer getResource() throws Exception{
		StringBuffer resoureClass=new StringBuffer();
		resoureClass.append(this.getHead()).append(this.postMethod())
			.append(this.deleteMethod()).append(this.putMethod())
			.append(this.getMethod()).append(this.getLog()
			.append(this.getTail()));
		return resoureClass;
	}
	public StringBuffer getHead(){
		StringBuffer sb=new StringBuffer();
		sb.append("package com.ericsson.nso.cmcc.resources;").append(HH);
		sb.append("import java.util.HashMap;").append(HH);
		sb.append("import javax.annotation.Resource;").append(HH);
		sb.append("import javax.ws.rs.Consumes;").append(HH);
		sb.append("import javax.ws.rs.DELETE;").append(HH);
		sb.append("import javax.ws.rs.GET;").append(HH);
		sb.append("import javax.ws.rs.POST;").append(HH);
		sb.append("import javax.ws.rs.PUT;").append(HH);
		sb.append("import javax.ws.rs.Path;").append(HH);
		sb.append("import javax.ws.rs.PathParam;").append(HH);
		sb.append("import javax.ws.rs.Produces;").append(HH);
		sb.append("import javax.ws.rs.core.Context;").append(HH);
		sb.append("import javax.ws.rs.core.MediaType;").append(HH);
		sb.append("import javax.ws.rs.core.Response;").append(HH);
		sb.append("import javax.ws.rs.core.Response.Status;").append(HH);
		sb.append("import javax.ws.rs.core.UriInfo;").append(HH);
		sb.append("import org.apache.commons.lang.exception.ExceptionUtils;").append(HH);
		sb.append("import org.apache.http.HttpStatus;").append(HH);
		sb.append("import org.slf4j.Logger;").append(HH);
		sb.append("import org.slf4j.LoggerFactory;").append(HH);
		sb.append("import org.springframework.stereotype.Controller;").append(HH);
		sb.append("import com.alibaba.fastjson.JSONArray;").append(HH);
		sb.append("import com.alibaba.fastjson.JSONObject;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.exceptions.HintMsgMappingConfig;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.exceptions.NsoException;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao.entity."+className+";").append(HH);
		sb.append("import com.ericsson.nso.cmcc.services.wf."+className+"Service;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.util.DBConstants;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.util.SysConstants;").append(HH).append(HH);
		sb.append("@Controller").append(HH);
		sb.append("@Path(\"/nso/config/"+objName+"\")").append(HH);
		sb.append("public class "+className+"Resource  extends GenericResource{").append(HH); 
		sb.append(TAB).append("private static Logger logger = LoggerFactory.getLogger("+resourceName+".class);").append(HH).append(HH);;
		sb.append(TAB).append("@Resource").append(HH);
		sb.append(TAB).append("private "+serviceName+" "+lowerCaseFirstChar(serviceName)+";").append(HH).append(HH);
		return sb;
	}
	public StringBuffer postMethod() throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(TAB).append("@POST").append(HH);
		sb.append(TAB).append("@Consumes(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("@Produces(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("public Response ").append(postMethodName).append("(JSONObject requestParam,@Context UriInfo uri){").append(HH);
		sb.append(TAB).append(TAB).append("final String srvName = \"").append(postMethodName).append("\";").append(HH);
		sb.append(TAB).append(TAB).append("JSONObject msg  = new JSONObject();").append(HH);
		sb.append(getDclareField());
		sb.append(TAB).append(TAB).append("this.logRequest(srvName, requestParam, uri, null);").append(HH);
		sb.append(TAB).append(TAB).append("try {").append(HH);
		sb.append(TAB).append(TAB).append("	JSONObject reqParam = parameterToLowerCase(requestParam);").append(HH);
		sb.append(getValidateCode());
	    sb.append(TAB).append(TAB).append("}catch (NsoException e) {").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return logAndRespond(srvName, HttpStatus.SC_BAD_REQUEST,e.toCnString(), Status.BAD_REQUEST,e.toCnString());");
	    sb.append(TAB).append(TAB).append("}catch (Exception e2){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e2));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.ErrorCode.BAD_REQUEST);  ").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_BAD_REQUEST,msg.toJSONString(), Status.BAD_REQUEST,msg);").append(HH);   
	    sb.append(TAB).append(TAB).append("}").append(HH);
	    sb.append(TAB).append(TAB).append("try {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"").append(postMethodName).append(" to store the ").append(className).append(" to DB\"); ").append(HH);
		sb.append(getNewObjCode(POSE_METHOD));
	    sb.append(TAB).append(TAB).append(TAB).append(lowerCaseFirstChar(serviceName)).append(".").append(postMethodName).append("(newObj);").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.SuccessCode."+className.toUpperCase()+"_ADD_SUCCESS);").append(HH);    		
	    sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"").append(postMethodName).append(": register is successful\"); ").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_OK, msg.toJSONString(), Status.OK, msg);").append(HH);
	    sb.append(TAB).append(TAB).append("} catch (NsoException e){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toCnString(), Status.INTERNAL_SERVER_ERROR,e.toCnString());").append(HH);   
	    sb.append(TAB).append(TAB).append("} catch (Exception e2){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e2));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.ErrorCode.INTERNAL_ERROR); ").append(HH);   		
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, msg.toJSONString(), Status.INTERNAL_SERVER_ERROR,msg);   ").append(HH);
		sb.append(TAB).append(TAB).append("}   ").append(HH); 	
		sb.append(TAB).append("}").append(HH);
		 return sb;
	}
	
	public StringBuffer putMethod() throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(TAB).append("@PUT").append(HH);
		sb.append(TAB).append("@Path(\"/{id}\")").append(HH);
		sb.append(TAB).append("@Consumes(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("@Produces(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("public Response ").append(putMethodName).append("(@PathParam(\"id\") int id,JSONObject requestParam,@Context UriInfo uri){").append(HH);
		sb.append(TAB).append(TAB).append("final String srvName = \"").append(putMethodName).append("\";").append(HH);
		sb.append(TAB).append(TAB).append("JSONObject msg  = new JSONObject();").append(HH);
		sb.append(TAB).append(TAB).append("HashMap<String, String> params = new HashMap<String, String>();").append(HH);
		sb.append(TAB).append(TAB).append("params.put(\"id\", id+\"\");").append(HH);
		sb.append(getDclareField());
		sb.append(TAB).append(TAB).append("this.logRequest(srvName, requestParam, uri, params);").append(HH);
		sb.append(TAB).append(TAB).append("try {").append(HH);
		sb.append(TAB).append(TAB).append("	JSONObject reqParam = parameterToLowerCase(requestParam);").append(HH);
		sb.append(getValidateCode());
	    sb.append(TAB).append(TAB).append("}catch (NsoException e) {").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return logAndRespond(srvName, HttpStatus.SC_BAD_REQUEST,e.toCnString(), Status.BAD_REQUEST,e.toCnString());");
	    sb.append(TAB).append(TAB).append("}catch (Exception e2){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e2));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.ErrorCode.BAD_REQUEST);  ").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_BAD_REQUEST,msg.toJSONString(), Status.BAD_REQUEST,msg);").append(HH);   
	    sb.append(TAB).append(TAB).append("}").append(HH);
	    sb.append(TAB).append(TAB).append("try {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"").append(putMethodName).append(" :update ").append(className).append(" to DB\"); ").append(HH);
		sb.append(getNewObjCode(PUT_METHOD));
	    sb.append(TAB).append(TAB).append(TAB).append(lowerCaseFirstChar(serviceName)).append(".").append(putMethodName).append("(newObj);").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.SuccessCode."+className.toUpperCase()+"_UPDATE_SUCCESS);").append(HH);    		
	    sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"").append(putMethodName).append(":"+className+" update is successful\"); ").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_OK, msg.toJSONString(), Status.OK, msg);").append(HH);
	    sb.append(TAB).append(TAB).append("} catch (NsoException e){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toCnString(), Status.INTERNAL_SERVER_ERROR,e.toCnString());").append(HH);   
	    sb.append(TAB).append(TAB).append("} catch (Exception e2){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e2));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.ErrorCode.INTERNAL_ERROR); ").append(HH);   		
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, msg.toJSONString(), Status.INTERNAL_SERVER_ERROR,msg);   ").append(HH);
		sb.append(TAB).append(TAB).append("}   ").append(HH); 	
		sb.append(TAB).append("}").append(HH);
		 return sb;
	}
	public StringBuffer deleteMethod() throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(TAB).append("@DELETE").append(HH);
		sb.append(TAB).append("@Path(\"/{id}\")").append(HH);
		sb.append(TAB).append("@Consumes(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("@Produces(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("public Response ").append(deleteMethodName).append("(@PathParam(\"id\") int "+objName+"Id,@Context UriInfo uri){").append(HH);
		sb.append(TAB).append(TAB).append("final String srvName = \"").append(deleteMethodName).append("\";").append(HH);
		sb.append(TAB).append(TAB).append("JSONObject msg  = new JSONObject();").append(HH);
		sb.append(TAB).append(TAB).append("HashMap<String, String> params = new HashMap<String, String>();").append(HH);
		sb.append(TAB).append(TAB).append("params.put(\""+objName+"Id\", "+objName+"Id+\"\");").append(HH);
		sb.append(TAB).append(TAB).append("this.logRequest(srvName, null, uri, params);").append(HH);
		sb.append(TAB).append(TAB).append("try {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"deregisterNfvo:Start to delete the nfvo to DB\"); ").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append(lowerCaseFirstChar(serviceName)+"."+deleteMethodName+"("+objName+"Id);").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\"deregisterNfvo: nvfo Deregister is successful\"); ").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.SuccessCode."+className.toUpperCase()+"_DELETE_SUCCESS);").append(HH);		
		sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_OK, msg.toJSONString(), Status.OK, msg);").append(HH);
	    sb.append(TAB).append(TAB).append("} catch (NsoException e){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toCnString(), Status.INTERNAL_SERVER_ERROR,e.toCnString());").append(HH);   
	    sb.append(TAB).append(TAB).append("} catch (Exception e2){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e2));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.ErrorCode.INTERNAL_ERROR); ").append(HH);   		
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, msg.toJSONString(), Status.INTERNAL_SERVER_ERROR,msg);   ").append(HH);
		sb.append(TAB).append(TAB).append("}   ").append(HH); 	
		sb.append(TAB).append("}").append(HH);
		 return sb;
	}
	public StringBuffer getMethod() throws Exception {
		StringBuffer sb=new StringBuffer();
		sb.append(TAB).append("@GET").append(HH);
		sb.append(TAB).append("@Consumes(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("@Produces(MediaType.APPLICATION_JSON + \";charset=UTF-8\")").append(HH);
		sb.append(TAB).append("public Response ").append(getMethodName).append("(@Context UriInfo uri){").append(HH);
		sb.append(TAB).append(TAB).append("final String srvName = \"").append(getMethodName).append("\";").append(HH);
		sb.append(TAB).append(TAB).append("JSONObject msg  = new JSONObject();").append(HH);
		sb.append(TAB).append(TAB).append("this.logRequest(srvName, null, uri, null);").append(HH);
		sb.append(TAB).append(TAB).append("try {").append(HH);
		
		sb.append(TAB).append(TAB).append(TAB).append("JSONArray jsonArray = "+lowerCaseFirstChar(serviceName)+"."+getMethodName+"();").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\""+getMethodName+": "+objName+" retrieve is successful\"); ").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("if (jsonArray.isEmpty()) {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.SuccessCode."+className.toUpperCase()+"_TABLE_EMPTY);").append(HH);   		
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append("logger.debug(msg.toJSONString()); ").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_OK, msg.toJSONString(), Status.OK, msg);").append(HH);		
		sb.append(TAB).append(TAB).append(TAB).append("}else {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_OK, jsonArray.toJSONString(), Status.OK, jsonArray);").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("}").append(HH);
	    sb.append(TAB).append(TAB).append("} catch (NsoException e){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, e.toCnString(), Status.INTERNAL_SERVER_ERROR,e.toCnString());").append(HH);   
	    sb.append(TAB).append(TAB).append("} catch (Exception e2){").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("logger.error(ExceptionUtils.getFullStackTrace(e2));").append(HH);
	    sb.append(TAB).append(TAB).append(TAB).append("msg  = HintMsgMappingConfig.getInstance().getHintJson(SysConstants.ErrorCode.INTERNAL_ERROR); ").append(HH);   		
	    sb.append(TAB).append(TAB).append(TAB).append("return this.logAndRespond(srvName, HttpStatus.SC_INTERNAL_SERVER_ERROR, msg.toJSONString(), Status.INTERNAL_SERVER_ERROR,msg);   ").append(HH);
		sb.append(TAB).append(TAB).append("}   ").append(HH); 	
		sb.append(TAB).append("}").append(HH);
		 return sb;
	}
	public StringBuffer getLog(){
		StringBuffer sb=new StringBuffer();
		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("protected Logger getLogger() {").append(HH);
		sb.append(TAB).append(TAB).append("return logger;").append(HH);
		sb.append(TAB).append("}").append(HH).append(HH);;
		return sb;
	}
	public StringBuffer getTail(){
		StringBuffer sb=new StringBuffer();
		sb.append("}");
		return sb;
	}
	
	/*
	 * service  class 
	 */
	public StringBuffer getService(){
		StringBuffer sb=new StringBuffer();
		sb.append("package com.ericsson.nso.cmcc.services.wf;").append(HH).append(HH);
		
		sb.append("import com.alibaba.fastjson.JSONArray;").append(HH);
		sb.append("import com.alibaba.fastjson.JSONObject;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.exceptions.NsoException;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao.entity."+className+";").append(HH).append(HH);
		
		sb.append("public interface "+serviceName+"{").append(HH).append(HH);
		
		sb.append(TAB).append("public JSONObject "+postMethodName+"("+className+" "+objName+") throws NsoException;").append(HH).append(HH);
		
		sb.append(TAB).append("public boolean "+putMethodName+"("+className+" "+objName+") throws NsoException;").append(HH).append(HH);
		
		sb.append(TAB).append("public boolean "+deleteMethodName+"(Integer "+objName+"Id) throws NsoException;").append(HH).append(HH);
		
		sb.append(TAB).append("public JSONArray "+getMethodName+"() throws NsoException;").append(HH).append(HH);
		
		sb.append("}").append(HH);
		
		return sb;
	}
	/*
	 * dao  class 
	 */
	public StringBuffer  getDao(){
		StringBuffer sb=new StringBuffer();
		sb.append("package com.ericsson.nso.cmcc.query.dao;").append(HH).append(HH);
		
		sb.append("import com.ericsson.nso.cmcc.query.dao.entity."+className+";").append(HH).append(HH);
		
		sb.append("public interface "+daoName+" extends GenericDao<"+className+"> {").append(HH).append(HH);
		
		sb.append(TAB).append("public "+className+" query(Integer id);").append(HH).append(HH);
		
		sb.append("}").append(HH);
		
		return sb;
	}
	/*
	 * daoImp  class 
	 */
	public StringBuffer  getDaoImp(){
		StringBuffer sb=new StringBuffer();
		sb.append("package com.ericsson.nso.cmcc.query.dao.impl;").append(HH).append(HH);
		
		sb.append("import java.util.List;").append(HH);
		sb.append("import org.hibernate.Criteria;").append(HH);
		sb.append("import org.hibernate.criterion.Restrictions;").append(HH);
		sb.append("import org.springframework.stereotype.Repository;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao.BaseDao;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao."+daoName+";").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao.entity."+className+";").append(HH).append(HH);

		sb.append("@Repository").append(HH);
		sb.append("public class "+daoImpName+" extends BaseDao implements "+daoName+" {").append(HH).append(HH);
		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public void save("+className+" bean) {").append(HH);
		sb.append(TAB).append(TAB).append("saveObj(bean);").append(HH);
		sb.append(TAB).append("}").append(HH).append(HH);
		
		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public void update("+className+" bean) {").append(HH);
		sb.append(TAB).append(TAB).append("updateObj(bean);").append(HH);
		sb.append(TAB).append("}").append(HH).append(HH);
		
		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public void delete("+className+" bean) {").append(HH);
		sb.append(TAB).append(TAB).append("getCurrentSession().delete(bean);").append(HH);
		sb.append(TAB).append(TAB).append("getCurrentSession().flush();").append(HH);
		sb.append(TAB).append("}").append(HH).append(HH);

		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public List<"+className+"> getList() {").append(HH);
		sb.append(TAB).append(TAB).append("final Criteria criteria = getCurrentSession().createCriteria("+className+".class);").append(HH);
		sb.append(TAB).append(TAB).append("final List<"+className+"> list = criteria.list();").append(HH);
		sb.append(TAB).append(TAB).append("return list;").append(HH);
		sb.append(TAB).append("}").append(HH).append(HH);

		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public "+className+" query(Integer id) {").append(HH);
		sb.append(TAB).append(TAB).append("return ("+className+") getCurrentSession().get("+className+".class,id);").append(HH);
		sb.append(TAB).append("}").append(HH).append(HH);

		sb.append("}").append(HH);
		return sb;
	}
	/*
	 * serviceImp  class 
	 */
	public StringBuffer  getServiceImp() throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("package com.ericsson.nso.cmcc.services.wf.impl;").append(HH).append(HH);

		sb.append("import java.util.List;").append(HH);
		sb.append("import javax.annotation.Resource;").append(HH);
		sb.append("import org.slf4j.Logger;").append(HH);
		sb.append("import org.slf4j.LoggerFactory;").append(HH);
		sb.append("import org.springframework.stereotype.Service;").append(HH);
		sb.append("import org.springframework.transaction.annotation.Propagation;").append(HH);
		sb.append("import org.springframework.transaction.annotation.Transactional;").append(HH);
		sb.append("import com.alibaba.fastjson.JSONArray;").append(HH);
		sb.append("import com.alibaba.fastjson.JSONObject;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.exceptions.NsoException;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.services.wf."+serviceName+";").append(HH);
		sb.append("import com.ericsson.nso.cmcc.util.CommUtil;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.util.SysConstants;").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao.entity."+className+";").append(HH);
		sb.append("import com.ericsson.nso.cmcc.query.dao."+daoName+";").append(HH).append(HH);

		sb.append("@Service").append(HH);
		sb.append("public class "+serviceImpName+" implements "+serviceName+"{").append(HH).append(HH);
		
		sb.append(TAB).append("private Logger logger = LoggerFactory.getLogger("+serviceImpName+".class);").append(HH).append(HH);
		
		sb.append(TAB).append("@Resource").append(HH);
		sb.append(TAB).append("private "+daoName+" "+lowerCaseFirstChar(daoName)+";").append(HH).append(HH);
			
		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)").append(HH);
		sb.append(TAB).append("public JSONObject "+postMethodName+"("+className+" "+objName+") throws NsoException{").append(HH);
//		sb.append(TAB).append(TAB).append("String name="+objName+".getName();").append(HH);
//		sb.append(TAB).append(TAB).append("List<"+className+"> list = "+lowerCaseFirstChar(daoName)+".queryListByName(name);").append(HH);
//		sb.append(TAB).append(TAB).append("if (list != null && !list.isEmpty()){").append(HH);
//		sb.append(TAB).append(TAB).append(TAB).append("String errMsg = \""+postMethodName+":Table "+className+" already has a record whose name is \"+ name + \" and whose ID is \"" + "list.get(0).getId();").append(HH);
//		sb.append(TAB).append(TAB).append(TAB).append("logger.error(errMsg);").append(HH);
//		sb.append(TAB).append(TAB).append(TAB).append("throw new NsoException(SysConstants.ErrorCode."+className.toUpperCase()+"_ADD_ERR_NAME_EXISTS, errMsg);").append(HH);
//		sb.append(TAB).append(TAB).append("} ").append(HH);
		sb.append(TAB).append(TAB).append(lowerCaseFirstChar(daoName)+".save("+objName+");").append(HH);
		sb.append(TAB).append(TAB).append("logger.debug(\"wfId:\"+"+objName+".getId());").append(HH);
		sb.append(TAB).append(TAB).append("return CommUtil.toJsonObject("+objName+"); ").append(HH);
		sb.append(TAB).append("}").append(HH);

		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)").append(HH);
		sb.append(TAB).append("public boolean update"+className+" ("+className+" newObj) throws NsoException{").append(HH);
		sb.append(TAB).append(TAB).append(className+" existObj = "+lowerCaseFirstChar(daoName)+".query(newObj.getId()); ").append(HH);
		sb.append(TAB).append(TAB).append("if (existObj == null ){").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("String errMsg = \""+putMethodName+":Table "+className+" does not have a record whose Id is \" + newObj.getId();").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.error(errMsg);").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("throw new NsoException(SysConstants.ErrorCode."+className+"_UPDATE_ERR_"+className+"_NOT_EXIST, errMsg);").append(HH);
		sb.append(TAB).append(TAB).append("}").append(HH);
//		sb.append(TAB).append(TAB).append("String name=newObj.getName();").append(HH);
//		sb.append(TAB).append(TAB).append("List<"+className+"> list = "+lowerCaseFirstChar(daoName)+".queryListByName(name);").append(HH);
//		sb.append(TAB).append(TAB).append("if (list != null && !list.isEmpty() && list.get(0).getId() != newObj.getId()){").append(HH);
//		sb.append(TAB).append(TAB).append(TAB).append("String errMsg = \""+putMethodName+":Table "+className+" already has a record whose name is \"+ name + \" and whose ID is \" + list.get(0).getId();").append(HH);
//		sb.append(TAB).append(TAB).append(TAB).append("logger.error(errMsg);").append(HH);
//		sb.append(TAB).append(TAB).append(TAB).append("throw new NsoException(SysConstants.ErrorCode."+className+"_UPDATE_ERR_NAME_EXISTS, errMsg);").append(HH);
//		sb.append(TAB).append(TAB).append("}").append(HH);
		sb.append(TAB).append(TAB).append("logger.debug(\"The existing "+className+"Info {} has been retrieved from DB and pass check. Start to update\",existObj.getId());").append(HH);
		sb.append(setGet());	
		sb.append(TAB).append(TAB).append("return true; ").append(HH);
		sb.append(TAB).append("}").append(HH);

		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public boolean "+deleteMethodName+"(Integer id) throws NsoException{").append(HH);
		sb.append(TAB).append(TAB).append(className+" existObj = "+lowerCaseFirstChar(daoName)+".query(id); ").append(HH);
		sb.append(TAB).append(TAB).append("if (existObj == null ){").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("String errMsg =\""+deleteMethodName+":"+className+" does not have a record whose ID is\"+ id;").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.error(errMsg);").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("throw new NsoException(SysConstants.ErrorCode."+className.toUpperCase()+"_DEL_ERR_"+className.toUpperCase()+"_NOT_EXIST,errMsg);").append(HH);
		sb.append(TAB).append(TAB).append("}").append(HH);
		sb.append(TAB).append(TAB).append("logger.debug(\"Start to delete "+className+" record ...\");").append(HH);
		sb.append(TAB).append(TAB).append(lowerCaseFirstChar(daoName)+".delete(existObj);").append(HH);
		sb.append(TAB).append(TAB).append("return true;").append(HH);
		sb.append(TAB).append("}").append(HH);
	
		sb.append(TAB).append("@Override").append(HH);
		sb.append(TAB).append("public JSONArray "+getMethodName+"() throws NsoException{").append(HH);
		sb.append(TAB).append(TAB).append("JSONArray jsonArray = new JSONArray();").append(HH);
		sb.append(TAB).append(TAB).append("List <"+className+"> list = "+lowerCaseFirstChar(daoName)+".getList(); ").append(HH);
		sb.append(TAB).append(TAB).append("if (list==null || list.isEmpty()){").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\""+getMethodName+":"+className+"s table is empty\");").append(HH);
		sb.append(TAB).append(TAB).append("} else {").append(HH);
		sb.append(TAB).append(TAB).append(TAB).append("logger.debug(\""+getMethodName+":Start to retrieve all "+className+"s at the size of {}\", list.size());").append(HH);
		sb.append(beanToJson());
		sb.append(TAB).append(TAB).append("}").append(HH);
		sb.append(TAB).append(TAB).append("return jsonArray;").append(HH);
		sb.append(TAB).append("}").append(HH);
	
		sb.append("}").append(HH);

		return sb;
	}
	/*
	 * testcase   
	 */ 
	public StringBuffer bean2json(){
	    	
		StringBuffer sb=new StringBuffer();
		Field[] fields = clazz.getDeclaredFields(); 
		sb.append(HH).append(TAB).append(TAB).append("{").append(HH);
		for(int i=0;i<fields.length;i++) {  
			Field f= fields[i];
		    sb.append(TAB).append(TAB).append(TAB).append("\""+f.getName()+"\":\"1\"");
		    if(i==(fields.length-1)){
		    	sb.append(HH);
		    }else{
		    	sb.append(",").append(HH);
		    }
		}  
		sb.append(TAB).append(TAB).append("}");
	    return sb;
	}
	public StringBuffer getTestCase()throws Exception{
		  StringBuffer sb=new StringBuffer();
		  objName="nsm";
		  sb.append("TC  "+getMethodName).append(HH);
		  sb.append(TAB).append("\""+getMethodName+"\": {").append(HH);
		  sb.append(TAB).append(TAB).append("\"method\": \"GET\",").append(HH);
		  sb.append(TAB).append(TAB).append("\"url\": \"http://ip:port/nso/config/"+objName+"\"").append(HH);
		  sb.append(TAB).append("}").append(HH);
		  sb.append(TAB).append("possible respone").append(HH);
		  sb.append(TAB).append("{\"message\":\"异常请求\"}//参数格式校验失败").append(HH);
		  sb.append(TAB).append("操作成功 返回集合").append(HH);
		  sb.append(TAB).append("{\"message\":["+bean2json()+"]}").append(HH);
		  sb.append(TAB).append("{\"message\":\"系统异常\"}//其他不可预见异常").append(HH).append(HH);
		 
		  sb.append("TC  "+postMethodName).append(HH);
		  sb.append(TAB).append("\""+postMethodName+"\": {\"").append(HH);
		  sb.append(TAB).append(TAB).append("\"method\": \"POST\",").append(HH);
		  sb.append(TAB).append(TAB).append("\"url\": \"http://ip:port/nso/config/"+objName+"\",").append(HH);
		  sb.append(TAB).append(TAB).append("\"body\": "+bean2json()).append(HH);
		  sb.append(TAB).append("}").append(HH);
		  sb.append(TAB).append("possible respone").append(HH);
		  sb.append(TAB).append("{\"message\":\"异常请求\"}//参数格式校验失败").append(HH);
		  sb.append(TAB).append("操作成功").append(HH);
		  sb.append(TAB).append("{\"message\":\"新增"+objName+"成功\"}").append(HH);
		  sb.append(TAB).append("{\"message\":\"系统异常\"}//其他不可预见异常").append(HH).append(HH);
		  
		  sb.append("TC  "+putMethodName).append(HH);
		  sb.append(TAB).append("\""+putMethodName+"\": {\"").append(HH);
		  sb.append(TAB).append(TAB).append("\"method\": \"PUT\",").append(HH);
		  sb.append(TAB).append(TAB).append("\"url\": \"http://ip:port/nso/config/"+objName+"/{id}\",").append(HH);
		  sb.append(TAB).append(TAB).append("\"body\": "+bean2json()).append(HH);
		  sb.append(TAB).append(TAB).append("}").append(HH);
		  sb.append(TAB).append("possible respone").append(HH);
		  sb.append(TAB).append("{\"message\":\"异常请求\"}//参数格式校验失败").append(HH);
		  sb.append(TAB).append("操作成功").append(HH);
		  sb.append(TAB).append("{\"message\":\"更新"+objName+"成功\"}").append(HH);
		  sb.append(TAB).append("{\"message\":\"系统异常\"}//其他不可预见异常").append(HH).append(HH);
		  
		  sb.append("TC  "+deleteMethodName).append(HH);
		  sb.append(TAB).append("\""+deleteMethodName+"\": {\"").append(HH);
		  sb.append(TAB).append(TAB).append("\"method\": \"DELETE\",").append(HH);
		  sb.append(TAB).append(TAB).append("\"url\": \"http://ip:port/nso/config/"+objName+"/{id}\"").append(HH);
		  sb.append(TAB).append("}").append(HH);
		  sb.append(TAB).append("possible respone").append(HH);
		  sb.append(TAB).append("{\"message\":\"异常请求\"}//参数格式校验失败").append(HH);
		  sb.append(TAB).append("操作成功").append(HH);
		  sb.append(TAB).append("{\"message\":\"删除"+objName+"成功\"}").append(HH);
		  sb.append(TAB).append("{\"message\":\"系统异常\"}//其他不可预见异常").append(HH).append(HH);
		  return sb;
	}
	public static void main(String[] args) throws Exception  { 
		
//		GGSDDC ggsddc=new GGSDDC(Shop.class);
//		String projectPath=System.getProperty("user.dir");
//		String packeagePath="/src/main/java/com/ericsson/nso/cmcc";
//		String resourecePath=projectPath+packeagePath+"/resources/"+ggsddc.resourceName+".java";
//		String servicePath=projectPath+packeagePath+"/services/wf/"+ggsddc.serviceName+".java";
//		String serviceImpPath=projectPath+packeagePath+"/services/wf/impl/"+ggsddc.serviceImpName+".java";
//		String daoPath=projectPath+packeagePath+"/query/dao/"+ggsddc.daoName+".java";
//		String daoImpPath=projectPath+packeagePath+"/query/dao/impl/"+ggsddc.daoImpName+".java";
//		System.out.println(resourecePath);	
//		System.out.println(servicePath);	
//		System.out.println(serviceImpPath);		
//		System.out.println(daoPath);	
//		System.out.println(daoImpPath);
//		ggsddc.WriterClass(ggsddc.getResource().toString(), resourecePath);
//		ggsddc.WriterClass(ggsddc.getService().toString(), servicePath);
//		ggsddc.WriterClass(ggsddc.getServiceImp().toString(),serviceImpPath);
//		ggsddc.WriterClass(ggsddc.getDao().toString(), daoPath);
//		ggsddc.WriterClass(ggsddc.getDaoImp().toString(), daoImpPath);
//		ggsddc.WriterClass(new GGSDDC(Shop.class).getTestCase().toString(),"C:/abc/"+ggsddc.objName+"TestCase.txt");
		GGSDDC ggsddc=new GGSDDC(Object.class);
		System.out.println(ggsddc.getTestCase().toString());
	} 
    //覆盖模式 写文件
	public void WriterClass(String content,String fileName){
		 try {
	            File f = new File(fileName);
	            if (!f.exists()) {
	                f.createNewFile();
	            }else{
	            	
	            }
	            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f));
	            //覆盖模式 写文件
	            BufferedWriter writer = new BufferedWriter(write);
	            writer.write(content);
	            writer.flush(); 
	            write.close();
	            writer.close();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	}
}
