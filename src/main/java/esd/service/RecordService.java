package esd.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Job;
import esd.bean.Record;
import esd.bean.Resume;
import esd.dao.RecordDao;

@Service
public class RecordService {

	@Autowired
	private RecordDao recordDao;

	/**
	 * 向指定职位投递简历/向指定人发送邀请
	 * 
	 * @param resume
	 * @param job
	 * @param direction
	 *            true--个人投向公司, false--公司发送邀请
	 * @return
	 */
	public boolean sendResumeOrInvite(Resume resume, Job job, Boolean direction) {
		Record re = transformToRecord(resume, job, direction);
		boolean bl = recordDao.save(re);
		if (bl) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查N天内 是否存在同样的数据(个人投递简历/公司发送邀请), 查询个人是否想某职位投递简历, 使用uid, jid, true;
	 * 查询公司是否向某 职位发送邀请, 使用rid, cid, false
	 * 
	 * @param uid
	 * @param jid
	 * @param rid
	 * @param cid
	 * @param bl
	 *            true--个人投向公司, false--公司发送邀请
	 * @return
	 */
	public int checkSentInSomeDays(Integer uid, Integer jid, Integer rid,
			Integer cid, Boolean direction) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("jid", jid);
		map.put("rid", rid);
		map.put("cid", cid);
		Date today = new Date();
		// 7天前的日期
		Date sevenDaysAgo = new Date(today.getTime() - 3 * 24 * 60 * 60 * 1000);
		map.put("someDaysAgo", sevenDaysAgo);
		map.put("direction", direction);
		return recordDao.checkSentInSomeDays(map);
	}

	// 将传递进来的 Resume, Job对象转化为对应的Record对象
	private Record transformToRecord(Resume resume, Job job, Boolean direction) {
		Record re = new Record(resume.getId(), job.getId());
		re.setrAge(resume.getAge());
		re.setrDisabilityCategory(resume.getDisabilityCategory());
		re.setrEducation(resume.getEducation());
		re.setrGender(resume.getGender());
		re.setrMajor(resume.getMajor());
		re.setrName(resume.getName());
		re.setrSchool(resume.getSchool());
		re.setrTitle(resume.getTitle());
		re.setuID(resume.getUser().getId());
		re.setjContactPerson(job.getContactPerson());
		re.setjContactTel(job.getContactTel());
		re.setjDescription(job.getDescription());
		re.setjName(job.getName());
		re.setjNature(job.getNature());
		re.setjSalary(job.getSalary());
		re.setcID(job.getCompany().getId());
		re.setDirection(direction);
		if (job.getMark() != null && !"".equals(job.getMark())) {
			re.setComment(job.getMark());
		}
		return re;
	}

}
