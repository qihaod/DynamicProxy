package com.woniu.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.junit.Test;

import com.woniu.dao.impl.CalcDAOImpl;

public class AppTest {
	
		@Test
		public void testName() throws Exception {
			ICalcDAO c = new CalcDAOImpl();
			ClassLoader cl = c.getClass().getClassLoader();
			Class[] clazzs = {ICalcDAO.class};
			MyHandler mh = new MyHandler(c);
			ICalcDAO ic = (ICalcDAO) Proxy.newProxyInstance(cl, clazzs, mh);
			System.out.println(ic.add(1, 2));
		}
}

class MyHandler implements InvocationHandler{
	
	private Object traget;
	
	public MyHandler(Object target) {
		this.traget = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method.getName()+"开始，参数是：" + Arrays.toString(args));
		Object obj = method.invoke(traget, args);
		System.out.println(method.getName()+"结束，结果是：" + obj);
		return obj;
	}
	
}
