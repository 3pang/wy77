package com.lenovo.wy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lenovo.wy.entity.Operator;


public interface IndexOperatorDao{
	public void saveOrUpdateUser(Operator operator);
	public List<Operator> queryUserList();
	public Operator queryUserById(Integer userId);
	public void delUser(Integer id);
}
