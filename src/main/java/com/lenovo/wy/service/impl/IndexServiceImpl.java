package com.lenovo.wy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	
import com.lenovo.wy.dao.IndexOperatorDao;
import com.lenovo.wy.entity.Operator;
import com.lenovo.wy.service.IndexService;
@Service("IndexService")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class IndexServiceImpl implements IndexService {
	
	@Autowired
	IndexOperatorDao indexoperatordao;
	
	@Override
	public void saveOrUpdateUser(Operator operator) {
		// TODO Auto-generated method stub
		indexoperatordao.saveOrUpdateUser(operator);
	}

	@Override
	public List<Operator> queryUserList() {
		return indexoperatordao.queryUserList();
	}

	@Override
	public Operator queryUserById(Integer userId) {
		// TODO Auto-generated method stub
		return indexoperatordao.queryUserById(userId);
	}

	@Override
	public void delUser(Integer id) {
		indexoperatordao.delUser(id);
		
	}

}
