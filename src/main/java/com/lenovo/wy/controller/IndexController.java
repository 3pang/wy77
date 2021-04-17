package com.lenovo.wy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lenovo.wy.entity.Operator;
import com.lenovo.wy.service.IndexService;
import com.lenovo.wy.service.impl.IndexServiceImpl;
import com.lenovo.wy.util.CommonUtil;
@RequestMapping("/wy77")
@RestController
public class IndexController {
	private static final int String = 0;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	IndexService indexService;

	@RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
	public String index(@PathVariable String id,String name ,String age) {
		Map<String,String> m = new HashMap<String,String>();
		m.put("id",id);
		m.put("name",name);
		m.put("age",age);
		String s = JSONObject.toJSONString(m);
		return s;
	}
	
	@RequestMapping(value = "/returnString",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public String returnString(@RequestBody String msg,@RequestHeader Map<String, String> headers) {
		Map<String,String> m = new HashMap<String,String>();
		m.put("method","****returnString****");
		String s = JSONObject.toJSONString(m);
		return s;
	}
	
	@RequestMapping(value = "/returnJson",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public JSONObject returnJson(@RequestBody String msg,@RequestHeader Map<String, String> headers) {
		System.out.println(headers.get("hh"));
		Map<String,String> m = new HashMap<String,String>();
		m.put("method","****returnJson****");
		String s = JSONObject.toJSONString(m);
		JSONObject rmsg = JSONObject.parseObject(s);
		return rmsg;
	}
	@RequestMapping(value = "/returnHead",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public ResponseEntity<String> returnHead(@RequestBody String msg,@RequestHeader Map<String, String> headers) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("wy77", "daniel");
		Map<String,String> m = new HashMap<String,String>();
		m.put("method","****returnHead****");
		String s = JSONObject.toJSONString(m);
		return new ResponseEntity<String>(s, responseHeaders, HttpStatus.CREATED);
	}
	@RequestMapping(value = "/getById",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public Map<?,?> getById(@RequestBody String msg) {
		return null;
	}
	
	@RequestMapping(value = "/add",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public Map<?,?> add(@RequestBody String msg) {
		logger.info("#### info /add recive message: " + msg);
		Map<String,Object> result = new ModelMap();
		try {
			Map<String,Object> reqParam = new ObjectMapper().readValue(msg, Map.class);
			Operator operator = CommonUtil.transMap2Bean(reqParam,Operator.class);
			indexService.saveOrUpdateUser(operator);
			result.put("isSuccess", true);
			result.put("message", "操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("isSuccess", false);
			result.put("message", "操作失败！"+e.getMessage());
		}
		return result; 
	}
	
	@RequestMapping(value = "/del",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public Map<?,?> del(@RequestBody String msg) {
		logger.info("#### info /del recive message: " + msg);
		Map<String,Object> result = new ModelMap();
		try {
			Map<String,Object> reqParam = new ObjectMapper().readValue(msg, Map.class);
			Operator operator = CommonUtil.transMap2Bean(reqParam,Operator.class);
			indexService.delUser(operator.getOper_id());
			result.put("isSuccess", true);
			result.put("message", "操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("isSuccess", false);
			result.put("message", "操作失败！"+e.getMessage());
		}
		return result; 
	}
	
	@RequestMapping(value = "/update",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public Map<?,?> update(@RequestBody String msg) {
		logger.info("#### info /update recive message: " + msg);
		Map<String,Object> result = new ModelMap();
		try {
			Map<String,Object> reqParam = new ObjectMapper().readValue(msg, Map.class);
			Operator operator = CommonUtil.transMap2Bean(reqParam,Operator.class);
			indexService.saveOrUpdateUser(operator);
			result.put("isSuccess", true);
			result.put("message", "操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("isSuccess", false);
			result.put("message", "操作失败！"+e.getMessage());
		}
		return result; 
	}
	
	@RequestMapping(value = "/find",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public Map<?,?> find(@RequestBody String msg) {
		logger.info("#### info /find recive message: " + msg);
		Map<String,Object> result = new ModelMap();
		try {
			Map<String,Object> reqParam = new ObjectMapper().readValue(msg, Map.class);
			Operator operator = CommonUtil.transMap2Bean(reqParam,Operator.class);
			List<Operator> operators = indexService.queryUserList();
			result.put("operatorList", operators);
			result.put("isSuccess", true);
			result.put("message", "操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("isSuccess", false);
			result.put("message", "操作失败！"+e.getMessage());
		}
		return result; 
	}
	
	@RequestMapping(value = "/findById",produces = "application/json; charset=UTF-8",method = RequestMethod.POST)
	public Map<?,?> findById(@RequestBody String msg) {
		logger.info("#### info /findById recive message: " + msg);
		Map<String,Object> result = new ModelMap();
		try {
			Map<String,Object> reqParam = new ObjectMapper().readValue(msg, Map.class);
			Operator respOperators = indexService.queryUserById((int)reqParam.get("oper_id"));
			result.put("operatorList", respOperators);
			result.put("isSuccess", true);
			result.put("message", "操作成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("isSuccess", false);
			result.put("message", "操作失败！"+e.getMessage());
		}
		return result; 
	}
}
