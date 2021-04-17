package com.lenovo.wy.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lenovo.wy.dao.IndexOperatorDao;
import com.lenovo.wy.entity.Operator;

@Repository
public class IndexOperatorDaoImpl implements IndexOperatorDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;
    
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateUser(Operator operator) {
		hibernateTemplate.saveOrUpdate(operator);
	}

	@Override
	public List<Operator> queryUserList() {
		// TODO Auto-generated method stub
		return (List<Operator>) hibernateTemplate.find("from Operator");
	}

	@Override
	public Operator queryUserById(Integer userId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Operator.class, userId, null);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delUser(Integer id) {
		Operator operator = hibernateTemplate.get(Operator.class, id, null);
		hibernateTemplate.delete(operator);
		
	}

}
