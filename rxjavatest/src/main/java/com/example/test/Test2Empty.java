package com.example.test;

import rx.Observable;
import rx.Observer;

/**
 * Created by zhengjiong on 16/5/16.
 */
public class Test2Empty {

    public static void main(String[] args){
        Observable.empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("onNext");
                    }
                });
    }
}
