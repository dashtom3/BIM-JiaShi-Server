package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.MessageDao;
import com.my.spring.model.Message;
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
public class MessageDaoImpl extends BaseDao<Message> implements MessageDao {

    @Override
    public boolean addMessage(Message Message) {
        return save(Message);
    }

    @Override
    public boolean deleteMessage(Long id) {
        return delete(get(id));
    }

    @Override
    public boolean updateMessage(Message Message) {
        return update(Message);
    }

    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Message>> getMessageList(Long projectId, Integer pageIndex, Integer pageSize, Message message) {
        DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
        List<Message> ret = new ArrayList<Message>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Message.class);
//      criteria.add(Restrictions.eq("projectId", projectId));
       
        if(message.getUserId()!=null){
        	criteria.add(Restrictions.eq("userId", message.getUserId()));
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
	public DataWrapper<List<Message>> getMessageListByUserId(Long userId) {
		 DataWrapper<List<Message>> retDataWrapper = new DataWrapper<List<Message>>();
	        
	        //ret=find("select * from User where userId=?"+userId);
		List<Message> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(Message.class);
        criteria.add(Restrictions.eq("userId",userId));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			retDataWrapper.setData(ret);
		}
		return retDataWrapper;
	}

	@Override
	public Message getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}
}
