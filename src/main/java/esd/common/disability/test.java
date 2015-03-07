package esd.common.disability;

import org.apache.log4j.Logger;

public class test {
	private static Logger log = Logger.getLogger(test.class);
	/**
	 * @param args
	 */ 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// AutoDiscern autoDiscern = new AutoDiscern();
		// log.info(autoDiscern.discernPic("rand (7).jpg"));
		CheckDisabilityCard cdc = new CheckDisabilityCard();
		String session = cdc.init();
		String rand = cdc.rand();
		Long l = System.currentTimeMillis();
		log.info(cdc.check(session, "张铁军", "23010319670828241624", rand));
		log.info(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		log.info(cdc.check(session, "王云虓", "23010719740308061044", rand));
		log.info(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		log.info(cdc.check(session, "张铁军1", "23010319670828241624", rand));
		log.info(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		log.info(cdc.check(session, "张铁军", "23010319670828241624", rand));
		log.info(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		log.info(cdc.check(session, "常亚杰", "23262219660423502342", rand));
		log.info(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		log.info(cdc.check(session, "于富", "23102519841012187432", rand));
		log.info(System.currentTimeMillis()-l);
		cdc.destroy();
		
	}

}
