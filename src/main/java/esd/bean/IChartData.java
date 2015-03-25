package esd.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台数据统计显示类--提供给ichart.js控件使用--已废弃！！！--by yufu 2015-03-25
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-3-17
 */
public class IChartData {

	private String name; // 柱子名称
	private String value; // 柱子值
	private String color; // 柱子颜色

	
	@Override
	public String toString() {
		return "IChartData [name=" + name + ", value=" + value + ", color="
				+ color + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	public static void main(String[] args) {
		IChartData d1 = new IChartData();
		IChartData d2 = new IChartData();
		List<IChartData> list = new ArrayList<IChartData>();
		list.add(d1);
		list.add(d2);
		System.out.println(d1);
		System.out.println(list);
		Map<String,Object> m1 = new HashMap<String,Object>();
		m1.put("name1", d1.getName());
		m1.put("value1", d1.getValue());
		m1.put("color1", d1.getColor());
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		list2.add(m1);
		System.out.println(list2);
		
	}
}
