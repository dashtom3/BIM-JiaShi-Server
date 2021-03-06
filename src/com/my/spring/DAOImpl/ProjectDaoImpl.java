package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.ProjectDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Project;
import com.my.spring.utils.DaoUtil;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao {

    @Override
    public boolean addProject(Project project) {
        return save(project);
    }

    @Override
    public boolean deleteProject(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateProject(Project project) {
        return update(project);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Project>> getProjectList(Integer pageSize, Integer pageIndex, Project project) {
        DataWrapper<List<Project>> retDataWrapper = new DataWrapper<List<Project>>();
        List<Project> ret = new ArrayList<Project>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Project.class);
//        criteria.addOrder(Order.desc("publishDate"));
        if(project.getName() != null && !project.getName().equals("")) {
        	criteria.add(Restrictions.like("name", "%" + project.getName() + "%"));
        }
        
        if(project.getNum() != null && !project.getNum().equals("")) {
        	criteria.add(Restrictions.like("num", "%" + project.getNum() + "%"));
        }
        if(project.getConstructionUnit() != null && !project.getConstructionUnit().equals("")) {
        	criteria.add(Restrictions.like("constructionUnit", "%" + project.getConstructionUnit() + "%"));
        }
        if(project.getLeader() != null && !project.getLeader().equals("")) {
        	criteria.add(Restrictions.like("leader", "%" + project.getLeader() + "%"));
        }
        if(project.getBuildingUnit() != null && !project.getBuildingUnit().equals("")) {
        	criteria.add(Restrictions.like("buildingUnit", "%" + project.getBuildingUnit() + "%"));
        }
        
        if(project.getPlace() != null && !project.getPlace().equals("")) {
        	criteria.add(Restrictions.like("place", "%" + project.getPlace() + "%"));
        }
        if(project.getDescription() != null && !project.getDescription().equals("")) {
        	criteria.add(Restrictions.like("description", "%" + project.getDescription() + "%"));
        }
        if(project.getDesignUnit() != null && !project.getDesignUnit().equals("")) {
        	criteria.add(Restrictions.like("designUnit", "%" + project.getDesignUnit() + "%"));
        }
        if(project.getVersion() !=null && !project.getVersion().equals("")){
        	criteria.add(Restrictions.like("version", "%" + project.getVersion() + "%"));
        }
        if(project.getStartDate() !=null && !project.getStartDate().equals("")){
        	criteria.add(Restrictions.like("startDate", "%" + project.getStartDate() + "%"));
        }
        if(project.getPhase() !=null && !project.getPhase().equals("")){
        	criteria.add(Restrictions.like("phase", "%" + project.getPhase() + "%"));
        }
        
        if (pageSize == null) {
			pageSize = 10;
		}
        if (pageIndex == null) {
			pageIndex = 1;
		}
        
        // 取总页数
        criteria.setProjection(Projections.rowCount());
        int totalItemNum = ((Long)criteria.uniqueResult()).intValue();
        int totalPageNum = DaoUtil.getTotalPageNumber(totalItemNum, pageSize);

        // 真正取值
        criteria.setProjection(null);
        if (pageSize > 0 && pageIndex > 0) {
            criteria.setMaxResults(pageSize);// 最大显示记录数
            criteria.setFirstResult((pageIndex - 1) * pageSize);// 从第几条开始
        }
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        retDataWrapper.setTotalNumber(totalItemNum);
        retDataWrapper.setCurrentPage(pageIndex);
        retDataWrapper.setTotalPage(totalPageNum);
        retDataWrapper.setNumberPerPage(pageSize);

        return retDataWrapper;
    }

	@Override
	public Project getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<Project>> findProjectLike(Project project) {
		
			List<Project> ret = null;
			DataWrapper<List<Project>> projects=new DataWrapper<List<Project>>();
	        Session session = getSession();
	        Criteria criteria = session.createCriteria(Project.class);
	        criteria.add(Restrictions.like("name",project.getName()))
	        .add(Restrictions.like("num", project.getNum()))
	        .add(Restrictions.like("constructionUnit", project.getConstructionUnit()))
	        .add(Restrictions.like("leader", project.getLeader()))
	        .add(Restrictions.like("buildingUnit", project.getBuildingUnit()))
	        .add(Restrictions.like("picId", project.getPicId()))
	        .add(Restrictions.like("modelId", project.getModelId()))
	        .add(Restrictions.like("place", project.getPlace()))
	        .add(Restrictions.like("description", project.getDescription()))
	        .add(Restrictions.like("designUnit", project.getDesignUnit()))
	        .add(Restrictions.like("version", project.getVersion()))
	        .add(Restrictions.like("startDate", project.getStartDate()))
	        .add(Restrictions.like("phase", project.getPhase()));
	        
	        try {
	            ret = criteria.list();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        if (ret != null && ret.size() > 0) {
	        	projects.setData(ret);;
			}else{
				projects.setErrorCode(ErrorCodeEnum.Error);
			}
			return projects;
		
	}
}
