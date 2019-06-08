package com.woniu.dao.impl;

import com.woniu.dao.ICalcDAO;

public class CalcDAOImpl implements ICalcDAO {

	@Override
	public int add(int a, int b) {
		int r = a+b;
		return r;
	}

	@Override
	public int sub(int a, int b) {
		int r = a-b;
		return r;
	}

	@Override
	public int mul(int a, int b) {
		int r = a*b;
		return r;
	}

	@Override
	public int div(int a, int b) {
		int r = a/b;
		return r;
	}

}
