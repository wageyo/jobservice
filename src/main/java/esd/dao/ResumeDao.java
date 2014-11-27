package esd.dao;

import java.util.List;
import java.util.Map;

import esd.bean.Resume;

/**
 * 简历操作Dao接口
 * 
 * @author Administrator
 * 
 */
public interface ResumeDao extends IDao<Resume> {

	
	//根据账号, 删除对应的简历
	Integer deleteByUser(Integer cid);
	
	// 根据用户id, 得到此人简历
	public List<Resume> getByUser(int id);

	//得到个人用户默认选中作为投递的简历
	public Resume getDefaultResume(int uid);
	///
	///以下给录入员等管理员使用
	///
	
	//录入员--根据id得到一个简历对象
	public Resume getByIdForRecorder(int id);
	
	//录入员--根据录入员id, 得到他所管理的所有简历列表
	public List<Resume> getByRecorder(Map<String,Object> map);
	
	//录入员--根据录入员id, 得到他所管理的所有简历的个数
	public int getCountByRecorder(int arid);
	
	//根据地区id, 得到一个地区的所有被录入员管理的残疾人数量
	public List<Resume> getByArea(int aid);
	
	//根据地区code, 得到一个地区的所有被录入员管理的残疾人数量
	public int getCountByAreaCode(String code);
	
	//根据地区code, 得到一个地区的所有被录入员管理的残疾人列表
	public List<Resume> getByAreaCode(String code);
}
