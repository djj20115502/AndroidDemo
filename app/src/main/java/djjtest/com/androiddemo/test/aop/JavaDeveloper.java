package djjtest.com.androiddemo.test.aop;

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
}
