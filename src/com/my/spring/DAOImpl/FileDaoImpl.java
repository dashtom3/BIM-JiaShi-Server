package com.my.spring.DAOImpl;

import com.my.spring.DAO.BaseDao;
import com.my.spring.DAO.FileDao;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.Item;
import com.my.spring.utils.DataWrapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Repository
public class FileDaoImpl extends BaseDao<Files> implements FileDao {

    @Override
    public boolean addFiles(Files File) {
        return save(File);
    }

    @Override
    public boolean deleteFiles(Long id) {
        return delete(get(id));
    }


    @SuppressWarnings("unchecked")
	@Override
    public DataWrapper<List<Files>> getFilesList() {
        DataWrapper<List<Files>> retDataWrapper = new DataWrapper<List<Files>>();
        List<Files> ret = new ArrayList<Files>();
        Session session = getSession();
        Criteria criteria = session.createCriteria(Files.class);
//        criteria.addOrder(Order.desc("publishDate"));
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        retDataWrapper.setData(ret);
        return retDataWrapper;
    }

	@Override
	public Files getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public DataWrapper<Files> getByName(String name) {
		DataWrapper<Files> dataWrapper = new DataWrapper<Files>();
		String sql="select * from file where name="+name;
		Session session=getSession();
		 try{
	            Query query=session.createSQLQuery(sql).addEntity(Files.class);
	            List<Files> test=query.list();
	            dataWrapper.setData(test.get(0));
	        }catch(Exception e){
	            e.printStackTrace();
	            dataWrapper.setErrorCode(ErrorCodeEnum.Target_Not_Existed);
	        }
		 
		return dataWrapper;
	}
}
