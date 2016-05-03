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
 *
 *
 * Schedulers.io()
 * 这个调度器时用于I/O操作。它基于根据需要，增长或缩减来自适应的线程池。我们将使用它来修复我们之前看到的StrictMode违规做法。由于它专用于I/O操作，所以并不是RxJava的默认方法；正确的使用它是由开发者决定的。
 * 重点需要注意的是线程池是无限制的，大量的I/O调度操作将创建许多个线程并占用内存。一如既往的是，我们需要在性能和简捷两者之间找到一个有效的平衡点。
 *
 *
 * Schedulers.computation()
 * 这个是计算工作默认的调度器，它与I/O操作无关。它也是许多RxJava方法的默认调度器：buffer(),debounce(),delay(),interval(),sample(),skip()。
 *
 *
 * Schedulers.immediate()
 * 这个调度器允许你立即在当前线程执行你指定的工作。它是timeout(),timeInterval(),以及timestamp()方法默认的调度器。
 *
 *
 * Schedulers.newThread()
 * 这个调度器正如它所看起来的那样：它为指定任务启动一个新的线程。
 *
 *
 * Schedulers.trampoline()
 * 当我们想在当前线程执行一个任务时，并不是立即，我们可以用.trampoline()将它入队。这个调度器将会处理它的队列并且按序运行队列中每一个任务。它是repeat()和retry()方法默认的调度器。

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
