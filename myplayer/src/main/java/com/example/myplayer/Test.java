package com.example.myplayer;

import android.view.View;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

public class Test {
    public static void main(String[] m) {
//        A a = new A();
//        a.mainPresenter.printName();

    }


    public static class A {
        @Inject
        MainPresenter mainPresenter;
        public A(){

         }

    }

    public static class MainPresenter {
        //MainContract是个接口，View是他的内部接口，这里看做View接口即可
        private String name;

        @Inject
        MainPresenter(String name) {

        }

        public void printName() { //调用model层方法，加载数据 ... //回调方法成功时
            System.out.println("name" + name);
        }
    }

    @Module
    public static class MainModule {
        private final View mView;

        public MainModule(View view) {
            mView = view;
        }

        @Provides
        View provideMainView() {
            return mView;
        }
    }

    @Component(modules = MainModule.class)
    public interface MainComponent {
        void inject(MainActivity activity);
    }


}


