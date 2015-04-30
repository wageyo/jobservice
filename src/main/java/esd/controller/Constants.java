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

	public static final Integer START = 0;
	public static final Integer SIZE = 20;

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
	/**
	 * 残疾证号是否校验开关
	 */
	public static final String DISABLEDCHECKSWITCH = "disabledCheckSwitch";

	/****************** 放入cookie中的key值 ********************/
	public static final String USERID = "userid"; // 用户id
	public static final String USERNAME = "username"; // 用户名
	public static final String USERPASSWORD = "password"; // 密码
	public static final String USERIDENTITY = "identity"; // 身份值
	public static final String USERAUTHORITY = "authority"; // 权限值
	public static final String USERNICKNAME = "nickname"; // 用户昵称
	public static final String USERCOMPANYID = "companyid"; // 企业id
	public static final String USERREGISTERTIME = "registertime"; // 注册时间
	public static final String USERHEADIMAGE = "headimage"; // 头像图片id
	public static final String AREACODE = "areacode"; // 地区key
	public static final String AREANAME = "areaname"; // 地区名称

	// 管理员专用的cookie key值
	public static final String ADMINUSERID = "adminuserid"; // 管理员用户id
	public static final String ADMINUSERNAME = "adminusername"; // 管理员用户名
	public static final String ADMINUSERIDENTITY = "adminidentity"; // 管理员用户身份值
	public static final String ADMINUSERAUTHORITY = "adminauthority"; // 管理员用户权限值
	public static final String ADMINUSERTITLE = "admintitle"; // 管理员用户用户标题
	public static final String ADMINUSERNICKNAME = "adminnickname"; // 管理员用户昵称
	public static final String ADMINUSERAREANAME = "adminareaname"; // 管理员所属地区名称
	/****************** 放入cookie中的key值 ********************/

	public static final String AREACOUNTRY = "10000000"; // 全国地区code

	public static final String WORKABILITY = "workAbility"; // 有无劳动能力

	public static final String SWITCH_ON = "on"; // 开关-开
	public static final String SWITCH_OFF = "off"; // 开关-合

	public static final String SHARE_SCOPE_SWITCH = "shareScopeSwitch"; // 分享信息范围
																		// 选项标示符
	public static final String SHARE_SCOPE = "shareScope"; // 分享信息范围 值
	public static final String SHARE_SCOPE_INIT = "local"; // 分享信息范围默认值 默认为本地

	public static final String NO_LIMIT = "不限"; // 不限字段

	public static final String JOB_CATEGORY_MAIN = "main"; // 职位种类--主页显示
	public static final String JOB_CATEGORY_HOT = "hot"; // 职位种类--主页显示

	/**
	 * 匹配数量type关键字
	 */
	public static final String MATCHED_SHOW = "matchedShow";

	/**
	 * 不同地区显示的匹配上的职位/简历数量
	 */
	public static final String MATCHED_SHOW_NUMBER = "matchedShowNumber";

	/**
	 * 不同地区显示的匹配上的职位/简历数量--默认数量5
	 */
	public static final Integer MATCHED_NUMBER_DEFAULT = 5;

	/**
	 * 不同地区推送的匹配上的职位/简历数量
	 */
	public static final String MATCHED_SEND_NUMBER = "matchedSendNumber";

	/**
	 * 匹配条件参数 类型关键字
	 */
	public static final String RESUME_MATCH_PER = "resumeMatchPer";

	/**
	 * 文章类型
	 */
	public static final String ARTICLE_TYPE = "article"; // 文章类型

	/***
	 * 白名单功能开关id
	 */
	public static final String WHITE_LIST_SWITCH = "whitelistswitch";

	/**
	 * 几种用户类型
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
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

	/**
	 * 几种权限值
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
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
		log.info(Authority.SUPERADMIN.getValue());
	}

	/**
	 * 几种审核状态
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
	public enum CheckStatus {
		// 利用构造函数传参 级.
		DAISHEN("daiShen"), WEITONGGUO("weiTongGuo"), OK("ok"), YIJIUYE(
				"yiJiuYe");

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

	/**
	 * 几种开关名称
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
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

	/**
	 * json格式前台返回提示符
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
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

	/**
	 * 几种信息分享范围
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2014-12-11
	 */
	public enum SHARESCOPE {
		// 利用构造函数传参 级.
		COUNTRY("country"), PROVINCE("province"), CITY("city"), DISTRICT(
				"district"), LOCAL("local");

		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private SHARESCOPE(String val) {
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

	/**
	 * 几种文章类型
	 * 
	 * @author yufu
	 * @email ilxly01@126.com 2015-4-24
	 */
	public enum ARTICLETYPE {
		// 利用构造函数传参 级.
		NEWS("news"), DIRECT("direct"), WORKFLOW("workflow"), POLITICIES(
				"politicies"), CONTACT("contact");

		// 定义私有变量
		private String val;

		// 构造函数, 枚举类型只能为私有
		private ARTICLETYPE(String val) {
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
