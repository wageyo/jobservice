package esd.common.disability;

public class test {

	/**
	 * @param args
	 */ 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// AutoDiscern autoDiscern = new AutoDiscern();
		// System.out.println(autoDiscern.discernPic("rand (7).jpg"));
		CheckDisabilityCard cdc = new CheckDisabilityCard();
		String session = cdc.init();
		String rand = cdc.rand();
		Long l = System.currentTimeMillis();
		System.out.println(cdc.check(session, "张铁军", "23010319670828241624", rand));
		System.out.println(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		System.out.println(cdc.check(session, "王云虓", "23010719740308061044", rand));
		System.out.println(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		System.out.println(cdc.check(session, "张铁军1", "23010319670828241624", rand));
		System.out.println(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		System.out.println(cdc.check(session, "张铁军", "23010319670828241624", rand));
		System.out.println(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		System.out.println(cdc.check(session, "常亚杰", "23262219660423502342", rand));
		System.out.println(System.currentTimeMillis()-l);
		l = System.currentTimeMillis();
		System.out.println(cdc.check(session, "于富", "23102519841012187432", rand));
		System.out.println(System.currentTimeMillis()-l);
		cdc.destroy();
		
	}

}
