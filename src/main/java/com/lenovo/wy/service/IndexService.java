package com.lenovo.wy.service;

import java.util.List;

import com.lenovo.wy.entity.Operator;

public interface IndexService {
	public void saveOrUpdateUser(Operator operator);
	public List<Operator> queryUserList();
	public Operator queryUserById(Integer userId);
	public void delUser(Integer id);
}
