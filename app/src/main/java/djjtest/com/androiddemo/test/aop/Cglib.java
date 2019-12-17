package djjtest.com.androiddemo.test.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Cglib {


    public static class PlayGame {

        public void play() {
            System.out.println("打篮球很厉害");
        }
    }


    public static class CglibProxy implements MethodInterceptor {

        public Object newInstall(Object object) {

            return Enhancer.create(object.getClass(), this);
        }


        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("先热身一会");
            methodProxy.invokeSuper(o, objects);
            System.out.println("打完了");
            return null;
        }


    }

    public static void test() {
        CglibProxy cglibProxy = new CglibProxy();
        PlayGame playGame = (PlayGame) cglibProxy.newInstall(new PlayGame());
        playGame.play();
    }
}
