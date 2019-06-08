package b;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AppTest {
	
		@Test
		public void testName() throws Exception {
			ICalcDAO c = new CalcDAOImpl();
			MyProxy mp = new MyProxy();
			ICalcDAO ic = (ICalcDAO) mp.getMyProxy(c);
			
			System.out.println(ic.add(2, 3));
			
		}
}

class A implements Interceptor{
	@Override
	public void init() {
	}
	@Override
	public void desotry() {
	}
	@Override
	public Object intercept(ActionInvocation invocation) throws Exception {
		System.out.println("11111111111111111111111111111111");
		Object r = invocation.invoke();
		System.out.println("22222222222222222222222222222222");
		return r;
	}
	
}


class B implements Interceptor{

	@Override
	public void desotry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object intercept(ActionInvocation invocation) throws Exception {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
		Object r = invocation.invoke();
		System.out.println("bbbbbbbbbbbbbbbbbbbbbb");
		return r;
	}
	
}



//===============================================================================================
interface Interceptor{
	public void init();
	public void desotry();
	public Object intercept(ActionInvocation invocation)throws Exception;
}


class ActionInvocation {
	private Method method;
	private Object obj;
	private Object[] args;
	
	public ActionInvocation(Method method, Object obj, Object[] args) {
		super();
		this.method = method;
		this.obj = obj;
		this.args = args;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public Object invoke() throws Exception {
		return method.invoke(obj, args);
	}
	
	
	
	
}



class MyProxy{
	public Object getMyProxy(Object obj,Interceptor interceptor) {
		ClassLoader cl = obj.getClass().getClassLoader();
		Class[] clazzs = obj.getClass().getInterfaces();
		MyHandler mh = new MyHandler(obj,interceptor);
		Object ic =  Proxy.newProxyInstance(cl, clazzs, mh);
		return ic;
	}
	
	public Object getMyProxy(Object obj) throws Exception {
		List<Interceptor> list = new ArrayList<>();
		Reader in = new FileReader("src/main/resources/foo.txt");
		BufferedReader br = new BufferedReader(in);
		String line = null;
		while((line = br.readLine())!=null) {
			list.add((Interceptor) Class.forName(line).newInstance());
		}
		for (int i = list.size()-1; i >= 0; i--) {
			obj = getMyProxy(obj, list.get(i));
		}
		return obj;
	}
	
	
}




class MyHandler implements InvocationHandler{
	
	private Object traget;
	private Interceptor interceptor;
	
	public MyHandler(Object target,Interceptor interceptor) {
		this.traget = target;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		Object obj = interceptor.intercept(new ActionInvocation(method, traget, args));
		
		return obj;
	}
	
}
