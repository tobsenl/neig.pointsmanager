package cn.sqhl.neig.pointsmanager.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.sqhl.neig.pointsmanager.service.AdEventService;
import cn.sqhl.neig.pointsmanager.utils.FormatUtils;
import cn.sqhl.neig.pointsmanager.utils.PageCond;
import cn.sqhl.neig.pointsmanager.vo.Eventsinfo;

@Controller
@RequestMapping("/adevent")
public class AdEventController extends ContextInfo{
	@Autowired
	private AdEventService adEventService;
	private Logger logger=LogManager.getLogger(AdEventController.class);
	
	@ResponseBody
	@RequestMapping("/searchad")
	public JSONObject queryAD(HttpServletRequest request,
			HttpServletResponse response,
			@Param(value="type") String type,
			@Param(value="pagesize") String pagesize,
			@Param(value="nowpage") String nowpage) throws IOException{
		JSONObject rsJson = new JSONObject();
		rsJson.put("ver", ver);
		
		InputStream requestjson = request.getInputStream();
		String encoding = request.getCharacterEncoding(); 
		String locationsJSONString=IOUtils.toString(requestjson,encoding);

		JSONObject requestString=JSONObject.parseObject(locationsJSONString);
		logger.log(DEBUG, requestString);
		if(StringUtils.isEmpty(type)){
			if(requestString!=null){
				type=requestString.get("type")+"";
			}
		}
		if(StringUtils.isEmpty(pagesize)){
			if(requestString!=null){
				pagesize=requestString.get("pagesize")+"";
			}else{
				pagesize="15";
			}
		}
		if(StringUtils.isEmpty(nowpage)){
			if(requestString!=null){
				nowpage=requestString.get("nowpage")+"";
			}else{
				nowpage="1";
			}
		}
		List list=null;
		PageCond page=new PageCond(Integer.parseInt(nowpage), Integer.parseInt(pagesize));
		if(!StringUtils.isEmpty(type)){
			Map map=new HashMap();
			map.put("type",type);
			list=adEventService.queryAD(page,map);
		}else{
			result="1";
			message="type 为空请确认无误后再行调用";
			logger.log(INFO, message);
			data="";
		}
		if(list!=null && list.size() > 0 ){
			result="0";
			message="查询成功~";
			logger.log(INFO, message);
			data=list;
		}
		rsJson.put("result", result);
		rsJson.put("message", message);
		rsJson.put("data", data);
		response.setContentType("charset=utf-8");
		return rsJson;
	}
	
	@ResponseBody
	@RequestMapping("/searchevent")
	public JSONObject queryEvent(HttpServletRequest request,
			HttpServletResponse response,
			@Param(value="eventsid") String eventsid,
			@Param(value="pagesize") String pagesize,
			@Param(value="nowpage") String nowpage) throws IOException{
		JSONObject rsJson = new JSONObject();
		rsJson.put("ver", ver);
		
		InputStream requestjson = request.getInputStream();
		String encoding = request.getCharacterEncoding(); 
		String locationsJSONString=IOUtils.toString(requestjson,encoding);

		JSONObject requestString=JSONObject.parseObject(locationsJSONString);
		logger.log(DEBUG, requestString);
		if(StringUtils.isEmpty(eventsid)){
			if(requestString!=null){
				eventsid=requestString.get("eventsid")+"";
			}
		}
		if(StringUtils.isEmpty(pagesize)){
			if(requestString!=null){
				pagesize=requestString.get("pagesize")+"";
			}else{
				pagesize="15";
			}
		}
		if(StringUtils.isEmpty(nowpage)){
			if(requestString!=null){
				nowpage=requestString.get("nowpage")+"";
			}else{
				nowpage="1";
			}
		}
		Eventsinfo event=null;
		PageCond page=new PageCond(Integer.parseInt(nowpage), Integer.parseInt(pagesize));
		if(!StringUtils.isEmpty(eventsid)){
			Map map=new HashMap();
			map.put("eventsid",eventsid);
			event=(Eventsinfo)adEventService.queryEvent(page,map);
		}else{
			result="1";
			message="type 为空请确认无误后再行调用";
			logger.log(INFO, message);
			data="";
		}
		if(event!=null){
			result="0";
			message="查询成功~";
			logger.log(INFO, message);
			data=event;
		}
		rsJson.put("result", result);
		rsJson.put("message", message);
		rsJson.put("data", data);
		response.setContentType("charset=utf-8");
		return rsJson;
	}
}
