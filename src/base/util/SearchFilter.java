package base.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 查询过滤条件转换工具类，（基础）
 * @author Administrator
 *
 */
public class SearchFilter {
    public static final String  SPLIT_CHAR="_";
	//LIKEIGNORE 模糊查找不区分大小写
	//LIKE 模糊查找区分大小写
	public enum Operator {
		EQ, IN, ISNULL, LIKE, GT, LT, GTE, LTE, NE,LIKEIGNORE,FINDINSET,
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static Map<String, SearchFilter> parse(Map<String, Object> filterParams) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();

		for (Entry<String, Object> entry : filterParams.entrySet()) {
			//String[] names = StringUtils.split(entry.getKey(), "_");
			String[] names = entry.getKey().split("_");
			Object value = entry.getValue();
			if (names.length < 2) {
				throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			}
			if(names.length == 3){
				if("SHORTDATE".equals(names[2])){
					String fieldValue = (String)value;
					if(XaUtil.isNotEmpty(fieldValue)){
						value = fieldValue.replace("T", "").replace(":", "").replace("-", "").subSequence(0, 8);
					}
				}
				if("DATE".equals(names[2])){
					String fieldValue = (String)value;
					if(XaUtil.isNotEmpty(fieldValue)){
					//	fieldValue = fieldValue.replace("T", "").replace(":", "").replace("-", "");
						if("LTE".equals(names[0])){ 
						 	try {
						 	 Date d=DateUtils.parseDate(value.toString(), new String[]{"yyyy-MM-dd"});
						 	 value=DateProcessUtil.YYYYMMDD.format(DateUtils.addDays(d, 1));
						 	} catch (ParseException e) {
								e.printStackTrace();
							} 
						}else{
							value = fieldValue+ "000000";
						}
					}
				}
			}
			SearchFilter filter = new SearchFilter(names[1], Operator.valueOf(names[0]), value);
			if(XaUtil.isNotEmpty(entry.getValue())){
				filters.put(filter.fieldName + Identities.uuid2(), filter);
			}
		}
		return filters;
	}
}
