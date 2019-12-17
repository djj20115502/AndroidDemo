package djjtest.com.androiddemo.test.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaDeveloper implements Developer {
    private String name;

    public JavaDeveloper(String name) {
        this.name = name;
    }

    @Override
    public void code() {

        System.out.println(name+" code");
    }

    @Override
    public void debug() {
        System.out.println(name+" debug");
    }

    public void other(){
        System.out.println(name+" other");
    }

    public static void test() {
        JavaDeveloper djj = new JavaDeveloper("djj");
        Developer developer = (Developer) Proxy.newProxyInstance(djj.getClass().getClassLoader(),
                djj.getClass().getInterfaces(), (proxy, method, agrs) -> {
                    switch (method.getName()) {
                        case "debug":
                            System.out.println(" 改了");
                            break;
                        case "code":
                            return method.invoke(djj, agrs);
                        case "other":
                            System.out.println(" 改了");
                            return method.invoke(djj, agrs);
                    }
                    return null;
                });
        developer.code();
        developer.debug();
        System.out.println("  "+developer.getClass().getName());

    }



    class CglibProxy implements MethodInterceptor {
        /**
         * 拦截所有目标类方法的调用
         * obj  目标类的实例
         * m   目标方法的反射对象
         * args  方法的参数
         * proxy代理类的实例
         */
        @Override
        public Object intercept(Object obj, Method m, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("日志开始...");
            //代理类调用父类的方法
            proxy.invokeSuper(obj, args);
            System.out.println("日志结束...");
            return null;
        }
    }

}
