package esd.common.disability;

import org.springframework.stereotype.Service;

/**
 * 验证姓名和残疾证号 业务工具类
 * 
 * @author yufu
 * @email ilxly01@126.com 2015-2-2
 */
@Service
public class CheckCardService {

	/**
	 * 检测姓名, 残疾证号是否一致
	 * 
	 * @param name
	 * @param disabilityCard
	 * @return
	 */
	public Boolean check(String name, String disabilityCard) {
		CheckDisabilityCard cdc = new CheckDisabilityCard();
		String session = cdc.init();
		String rand = cdc.rand();
		return cdc.check(session, name, disabilityCard, rand);
	}
}
