package com.example.thread;

/**
 * Schedulers.computation(): 用于计算任务，如事件循环或回调处理。不要在这个 Scheduler 中进行 IO 操作。
 * Schedulers.from(executor): 从指定的 Executor 创建。
 * Schedulers.immediate(): 在当前线程立即执行。
 * Schedulers.io(): 用于 IO 操作。这个 Scheduler 维护了一个线程池，会根据需求适应。
 * Schedulers.newThread(): 创建新线程。
 * Schedulers.test(): 用于测试。
 * Schedulers.trampoline(): 将任务在当前线程队列。
 *
 *
 * Created by zhengjiong on 16/4/24.
 */
public class Example1 {

    /**
     * subscribeOn 用来指定 Observable.create 中的代码在那个 Scheduler 中执行。
     *
     * observeOn 控制数据流的另外一端。你的 observer 如何收到事件。
     * 也就是在那个线程中回调 observer 的 onNext/onError/onCompleted 函数。
     */
    public static void main(String[] args){
        /*Observable.just("hello rxJava")
                .subscribeOn(Schedulers.io())//指定观察者运行的线程(io线程)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("Action1 call");
                        System.out.println(Thread.currentThread().getName());
                        System.out.println("s=" + s);
                    }
                });*/
    }
}
