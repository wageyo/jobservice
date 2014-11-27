package esd.controller;

import org.apache.log4j.Logger;

/**
 * 常用工具常量类
 * 
 * @author Administrator
 * 
 */
public class Constants {
	private static Logger log = Logger.getLogger(Constants.class);
	public static final int START = 0;
	public static final int SIZE = 20;
	// public static final int PAGE_SIZE = 20;

	public static final String NOTICE = "notice"; // 提示文字key
	public static final String BENEFIT = "benefit"; // 其他福利
	public static final String CHECKSTATUS = "checkStatus"; // 审核状态
	public static final String COMPANYNATURE = "companyNature"; // 单位/企业性质
	public static final String DISABILITYCATEGORY = "disabilityCategory"; // 残疾类别
	public static final String DISABILITYLEVEL = "disabilityLevel"; // 残疾等级
	public static final String DISABILITYPART = "disabilityPart"; // 残疾类别
	public static final String ECONOMYTYPE = "economyType"; // 单位/企业经济类型
	public static final String EDUCATION = "education"; // 学历
	public static final String EFFECTIVETIME = "effectiveTime"; // 岗位有效期
	public static final String EXPERIENCE = "experience"; // 工作年限
	public static final String GENDER = "gender"; // 性别
	public static final String HUKOU = "huKou"; // 户口类型
	public static final String JOBNATURE = "jobNature"; // 职位/工作性质
	public static final String MARRIAGE = "marriage"; // 婚否年限
	public static final String SALARY = "salary"; // 期望月薪范围
	public static final String SCALE = "scale"; // 企业规模
	public static final String STATE = "state"; // 目前状态
	public static final String SWITCH = "switch"; // 目前状态
	public static final String USER = "user"; // 用户key
	
	/******************放入cookie中的key值********************/
	public static final String USERID = "userid"; // 用户id
	public static final String USERNAME = "username"; // 用户名
	public static final String USERPASSWORD = "password"; // 密码
	public static final String USERIDENTITY = "identity"; // 身份值
	public static final String USERAUTHORITY = "authority"; // 权限值
	public static final String USERNICKNAME = "nickname";	//用户昵称
	public static final String USERTITLE = "title";	//用户标题--管理员用户显示标题使用
	public static final String USERCOMPANYID = "companyid"; // 企业id
	public static final String USERREGISTERTIME = "registertime"; // 注册时间
	public static final String AREA = "area"; // 地区key
	/******************放入cookie中的key值********************/
	
	
	
	public static final String WORKABILITY = "workAbility"; // 有无劳动能力

	public static final String SWITCH_ON = "on"; //
	public static final String SWITCH_OFF = "off"; // 有无劳动能力

	public static final String NO_LIMIT = "不限"; // 不限字段

	/**
	 * 文章类型
	 */
	public static final String ARTICLE_TYPE = "article"; // 文章类型
	/**
	 * 文章类型： 最新资讯类型
	 */
	public static final String ARTICLE_TYPE_NEWS = "news"; // 最新资讯类型
	/**
	 * 文章类型： 就业指导类型
	 */
	public static final String ARTICLE_TYPE_DIRECT = "direct"; // 就业指导类型

	// 几种用户类型
	public enum Identity {
		// 利用构造函数传参 级.
		SUPERADMIN("superAdmin"), ADMIN("admin"), PERSON("person"), COMPANY(
				"company");

		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private Identity(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public String getValue() {
			return this.val;
		}
	}

	// 几种权限值
	public enum Authority {
		// 利用构造函数传参 级.
		SUPERADMIN(999), ADMIN(600), PERSON(100), COMPANY(200);

		// 定义私有变量
		private int val;

		// 构造函数, 枚举类型只能为私有
		private Authority(int val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public int getValue() {
			return this.val;
		}
	}

	public static void main(String[] args) {
		System.out.println(Authority.SUPERADMIN.getValue());
	}

	// 几种审核状态
	public enum CheckStatus {
		// 利用构造函数传参 级.
		DAISHEN("daiShen"), WEITONGGUO("weiTongGuo"), OK("ok");

		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private CheckStatus(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public String getValue() {
			return this.val;
		}
	}

	// 几种开关名称
	public enum Switch {
		// 利用构造函数传参 级.
		USER("user"), COMPANY("company"), JOB("job"), RESUME("resume");

		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private Switch(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public String getValue() {
			return this.val;
		}
	}

	// json格式前台返回提示符
	public enum Notice {
		SUCCESS("success"), ERROR("error"), WRONG("wrong"), INFO("info"), FAILURE(
				"failure");
		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private Notice(String val) {
			this.val = val;
		}

		@Override
		public String toString() {
			return String.valueOf(val);
		}

		public String getValue() {
			return this.val;
		}
	}

}
