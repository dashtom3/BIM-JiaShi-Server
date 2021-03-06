package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.PaperDao;
import com.my.spring.model.Paper;
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
public class PaperDaoImpl extends BaseDao<Paper> implements PaperDao {

    @Override
    public boolean addPaper(Paper paper) {
        return save(paper);
    }

    @Override
    public boolean deletePaper(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updatePaper(Paper paper) {
        return update(paper);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Paper>> getPaperList(Long projectId,Integer pageSize, Integer pageIndex,Paper paper) {
    	DataWrapper<List<Paper>> retDataWrapper = new DataWrapper<List<Paper>>();
        List<Paper> ret = new ArrayList<Paper>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Paper.class);
        ///////////////////////////////
        criteria.add(Restrictions.eq("projectId", projectId));
        if(paper.getProfessionType()!=null){
        	criteria.add(Restrictions.eq("professionType", paper.getProfessionType()));
        }
        if(paper.getBuildingNum() !=null){
        	criteria.add(Restrictions.eq("buildingNum",paper.getBuildingNum()));
        }
        if(paper.getFloorNum() !=null){
        	criteria.add(Restrictions.eq("floorNum", paper.getFloorNum()));
        }
        /////////////////////////////////////
   
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
	public Paper getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
