package esd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import esd.bean.Job;
import esd.bean.Record;
import esd.bean.Resume;
import esd.dao.JobDao;
import esd.dao.RecordDao;
import esd.dao.ResumeDao;

@Service
public class PersonService {

	@Autowired
	private ResumeDao resumeDao;

	@Autowired
	private RecordDao recordDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private KitService kitService;

	// 根据用户id, 得到他的默认投递简历
	public Resume getDefaultResume(int uid) {
		return resumeDao.getDefaultResume(uid);
	}

	// 想指定职位投递简历
	public boolean sendResume(Resume resume, Job job) {
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
		boolean bl = recordDao.save(re);
		if (bl) {
			return true;
		} else {
			return false;
		}
	}

	// 得到一个人投递的职位列表
	public List<Record> getSentJob(int userid, int startPage, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		Record r = new Record();
		r.setuID(userid);
		map.put("record", r);
		map.put("start", (startPage - 1) * size);
		map.put("size", size);
		List<Record> list = recordDao.getByPage(map);
		for (Record record : list) {
			record = kitService.getForShow(record);
		}
		return list;
	}

	// 得到一个人投递的所有职位总数
	public int getSentJobCount(int userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		Record r = new Record();
		r.setuID(userid);
		map.put("record", r);
		return recordDao.getTotalCount(map);
	}
}
