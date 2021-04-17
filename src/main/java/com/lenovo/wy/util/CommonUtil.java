package com.lenovo.wy.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lenovo.wy.util.ann.MapToEntity;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class CommonUtil {

	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 把json转成map
	 * @param json
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> json2map(String json) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.readValue(json, Map.class);
	}
	
	/**
	 * 把json转成object
	 * @param json
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T json2Object(String json,Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper=new ObjectMapper();
		return mapper.readValue(json, clazz);
	}
	
	/**
	 * 把object转成json
	 * @param obj
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String obj2json(Object obj) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	/**
	 * map转bean
	 * @param map
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws java.text.ParseException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T transMap2Bean(Map<String, ?> map, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			IntrospectionException, IllegalArgumentException,
			InvocationTargetException, NoSuchFieldException, SecurityException, ParseException, java.text.ParseException {
		
		Object obj = clazz.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();

		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			log.info("property: "+key);
			if (map.containsKey(key)) {
				Object value = map.get(key);
				String typeName = property.getPropertyType().getName();
				
				//如果是日期类型，并且value不为空
				if("java.util.Date".equals(typeName) && value!=null && !CommonUtil.isNull(value.toString())){
					String pattern = "yyyy-MM-dd HH:mm:ss";
					JsonFormat jsonFormat = clazz.getDeclaredField(key).getAnnotation(JsonFormat.class);
					if(jsonFormat!=null && !CommonUtil.isNull(jsonFormat.pattern())){
						pattern = jsonFormat.pattern();
					}
					value = DateUtil.string2Date(value.toString(), pattern);
				}else if("java.lang.Integer".equals(typeName)  && value!=null ){
					if("".equals(value.toString().trim())){
						value = null;
					}else{
						value = Integer.valueOf(value.toString());
					}
				}
				
				//如果字段上使用了MapToEntity注解，并且要忽略
				if(clazz.getDeclaredField(key).getAnnotation(MapToEntity.class)!=null){
					MapToEntity mapToEntity = clazz.getDeclaredField(key).getAnnotation(MapToEntity.class);
					if(mapToEntity.ignore()==true){
						continue;
					}
				}
				
				// 得到property对应的setter方法
				Method setter = property.getWriteMethod();
				setter.invoke(obj, value);
			}

		}

		return (T) obj;

	}

	public static boolean isNull(String str) {
		if (str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算页数
	 * 
	 * @param pageSize
	 *            页面大小
	 * @param dataNum
	 *            数据数量
	 * @return
	 */
	public static int computPageNum(int pageSize, int dataNum) {
		if (dataNum % pageSize == 0) {
			return dataNum / pageSize;
		} else {
			return (dataNum / pageSize) + 1;
		}
	}

	/**
	 * 把一个entity转成map以便自动转成json
	 * 
	 * @param entity
	 * @param clazz
	 * @param entityCols
	 * @param cache
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertEntity2Map(Object entity,
			Class<?> clazz, String[] entityCols, Map<String, Method> cache)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Map<String, Object> entityMap = new HashMap<String, Object>();
		// 所有成员变量
		Field[] fields = clazz.getDeclaredFields();
		// 特殊属性集合
		Map<String, Field> entitySpecialFields = new HashMap<String, Field>();
		// 普通属性
		HashSet<Field> entityNormalFields = new HashSet<Field>();
		Object[] emptyParam = new Object[0];

		for (Field field : fields) {
			Class<?> fieldType = field.getType();
			if (isNormalField(fieldType)) {// 普通属性
				entityNormalFields.add(field);
			} else {// 其它关联对象
				entitySpecialFields.put(field.getName(), field);
			}
		}

		// 特殊属性（逐级找到目录值）
		if (entityCols != null) {
			for (String showCol : entityCols) {
				String cols[] = showCol.replace('.', ',').split(",");
				Object tmp = entity;
				Map<String, Object> tmpMap = null;

				int len = cols.length;

				// 构建结构
				for (int i = 0; i < len - 1; i++) {
					String col = cols[i];
					if (i == 0) {
						if (!entityMap.containsKey(col)) {
							tmpMap = new HashMap<String, Object>();
							entityMap.put(col, tmpMap);
						} else {
							tmpMap = (Map<String, Object>) entityMap.get(col);
						}
					} else {
						tmpMap.put(col, new HashMap<String, Object>());
						tmpMap = (HashMap<String, Object>) tmpMap.get(col);
					}
				}

				// tmpMap赋值
				for (int i = 0; i < len; i++) {
					String col = cols[i];
					try {
						Method m = getGetMethod(col, tmp.getClass(), cache);
						if (m == null) {
							break;
						}
						tmp = m.invoke(tmp, emptyParam);
						if (tmp == null) {
							break;
						} else {
							if (i == (len - 1)) {
								tmpMap.put(col, tmp.toString());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		// 普通属性
		for (Field field : entityNormalFields) {
			String col = field.getName();
			Method m = getGetMethod(col, entity.getClass(), cache);
			if (m == null) {
				continue;
			}
			Object val = m.invoke(entity, emptyParam);
			if (val == null) {
				entityMap.put(col, "");
				continue;
			} else {
				entityMap.put(col, val);
			}
			// field.setAccessible(true);
			// entityMap.put(field.getName(), field.get(entity));
		}

		return entityMap;
	}

	/**
	 * 是否为普通属性
	 * 
	 * @param fieldType
	 * @return
	 */
	private static boolean isNormalField(Class<?> fieldType) {
		if (fieldType.getAnnotation(Entity.class) == null
				&& !fieldType.toString().contains("java.util.Set")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据成员变量的名称，获取对应的get反射方法
	 * 
	 * @param filedName
	 *            成员变量的名称
	 * @param clazz
	 *            反射类
	 * @param cache
	 * @return
	 */
	private static Method getGetMethod(String filedName, Class<?> clazz,
			Map<String, Method> cache) {
		String clazzName = clazz.getName();
		String methodName1 = ("get" + filedName).toLowerCase();
		String methodName2 = ("is" + filedName).toLowerCase();

		if (cache != null) {
			if (cache.containsKey(clazzName + "_" + methodName1)) {
				return cache.get(clazzName + "_" + methodName1);
			}

			if (cache.containsKey(clazzName + "_" + methodName2)) {
				return cache.get(clazzName + "_" + methodName2);
			}
		}

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			String methodName = method.getName().toLowerCase();
			if (methodName.equals(methodName1)
					|| methodName.equals(methodName2)) {
				if (cache != null) {
					cache.put(clazzName + "_" + methodName, method);
				}
				return method;
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void typeConvert(Map<String,List> filter,Class c){
		if(filter==null){
			return ;
		}
		for(String key : filter.keySet()){
			try {
				String typeName = c.getDeclaredField(key).getType().getName();
				if("java.lang.Integer".equals(typeName) 
						&& filter.get(key)!=null
						&& filter.get(key).get(0)!=null){
					filter.get(key).set(0,Integer.valueOf(filter.get(key).get(0).toString()));
				}else if("java.lang.Long".equals(typeName) 
						&& filter.get(key)!=null
						&& filter.get(key).get(0)!=null){
					filter.get(key).set(0,Long.valueOf(filter.get(key).get(0).toString()));
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}
}
