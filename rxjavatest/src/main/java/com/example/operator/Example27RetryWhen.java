package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;

/**
 *
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0206/3953.html
 *
 * retryWhen:
 *
 * retryWhen操作符类似于retry操作符，都是在源observable出现错误或者异常时，重新尝试执行源observable的逻辑，
 * 不同在于retryWhen操作符是在源Observable出现错误或者异常时，通过回调第二个Observable来判断是否重新尝试执
 * 行源Observable的逻辑，如果第二个Observable没有错误或者异常出现，则就会重新尝试执行源Observable的逻辑，
 * 否则就会直接回调执行订阅者的onError方法。
 *
 *
 * 当.retry()接收到.onError()事件后触发重订阅。
 * 然而，这种简单的叙述尚不能令人满意。试想如果你要实现一个延迟数秒的重订阅该如何去做？或者想通过观察错误来决定是否应该重订阅呢？
 * 这种情况下就需要.repeatWhen()和.retryWhen()的介入了，因为它们允许你为重试提供自定义逻辑。
 *
 *
 *          输入参数                                                    输出参数
 * retryWhen(Func1<? super Observable<? extends java.lang.Throwable>,? extends Observable<?>> notificationHandler)
 * 它包括三个部分：
 * 1.Func1像个工厂类，用来实现你自己的重试逻辑。
 * 2.输入的是一个Observable<Throwable>。
 * 3.输出的是一个Observable<?>。
 *
 * 首先，让我们来看一下最后一部分。被返回的Observable<?>所要发送的事件决定了重订阅是否会发生。如果发送的是onCompleted或者onError事件，
 * 将不会触发重订阅。相对的，如果它发送onNext事件，则触发重订阅（不管onNext实际上是什么事件）。这就是为什么使用了通配符作为泛型类型：
 * 这仅仅是个通知（next, error或者completed），一个很重要的通知而已。
 *
 * source每次一调用onError(Throwable)，Observable<Throwable>都会被作为输入传入方法中。换句话说就是，它的每一次调用你都需要决定是否需要重订阅。
 *
 * 当订阅发生的时候，工厂Func1被调用，从而准备重试逻辑。那样的话，当onError被调用后，你已经定义的重试逻辑就能够处理它了。
 *
 * Created by zhengjiong on 16/5/8.
 */
public class Example27RetryWhen {

    /**
     * 运行结果:
     * 
     * Observer -> onNext s=0
     * Observer -> onNext s=0
     * Observer -> onNext s=0
     * Observer -> onError e=err-1
     * @param args
     */
    public static void main(String[] args){
        test1();
        //test2();
        //test3();
        //test4();
    }

    /**
     * 源 Observable 发射两个数字 然后遇到异常；当异常发生的时候，retryWhen 返回的
     * 判断条件 Observable 会获取到这个异常，这里等待 100毫秒然后把这个异常当做数据发射出
     * 去告诉 retryWhen 开始重试。take(2) 参数确保判断条件 Observable 只发射两个数
     * 据（源 Observable 出错两次）然后结束。所以当源 Observable 出现两次错误以后就不再重试了。
     */
    private static void test4() {
        Observable<Integer> source = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> o) {

                //o.onError(new IOException("IOException-1"));
                System.out.println("Exception-1");
                o.onNext(1);
                o.onNext(2);
                o.onError(new Exception("Exception-1"));
            }
        });

        source.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> errors) {
                //这里必须用flatmap,或者其他操作符吧error转换一下
                return errors.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable error) {
                        System.out.println("Throwable");
                        // For IOExceptions, we retry
                        if (error instanceof IOException) {
                            return Observable.just(null);
                        }

                        // For anything else, don't retry
                        //return Observable.error(error);

                        return Observable.just(null);
                    }
                });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("subscribe complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Next:" + value);
            }
        });

    }

    private static void test3() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("subscribing");
                subscriber.onError(new RuntimeException("always fails"));
            }
        });

        observable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                //System.out.println("retryWhen");
                //return Observable.just("retryWhen");
                return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {
                    @Override
                    public Integer call(Throwable throwable, Integer integer) {
                        return integer;
                    }
                }).flatMap(new Func1<Integer, Observable<?>>() {
                    @Override
                    public Observable<?> call(Integer integer) {
                        System.out.println("delay retry by " + integer + " second(s)");
                        //每一秒中执行一次
                        return Observable.timer(integer, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Next:" + value);
            }
        });
    }

    /**
     *
     */
    void test2(){
        Observable.create((Subscriber<? super String> s) -> {
            System.out.println("subscribing");
            s.onError(new RuntimeException("always fails"));
        }).retryWhen(attempts -> {
            return attempts.zipWith(Observable.range(1, 3), (n, i) -> i).flatMap(i -> {
                System.out.println("delay retry by " + i + " second(s)");
                return Observable.timer(i, TimeUnit.SECONDS);
            });
        }).toBlocking().forEach(System.out::println);
    }

    /**
     *
     * 最上面的Observable.just一共执行3次(本身1次+ 重试2次)
     *
     * 运行结果:
     *
     * retryWhen
     * Observer -> onNext s=1
     * Observer -> onNext s=2
     * flatMap error
     * Observer -> onNext s=1
     * Observer -> onNext s=2
     * flatMap error
     * Observer -> onNext s=1
     * Observer -> onNext s=2
     * Observer -> onCompleted
     */
    private static void test1() {
        Observable.just(1, 2, 3, 4)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer i) {
                        if (i == 3) {
                            return Observable.error(new IOException("IOException-1"));
                        } else {
                            return Observable.just(String.valueOf(i));
                        }
                    }
                })
                /**
                 *
                 * retryWhen默认在trampoline调度器上执行，你可以通过参数指定其它的调度器。
                 */
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        System.out.println("retryWhen");
                        //return Observable.just(null);这里直接这样是不会触发重试操作的,必须使用操作符转换一下,flatMap或者直接take也可以,其他操作符应该也可以
                        //take(2) 参数确保判断条件 Observable 只发射两个数据（源 Observable 出错2次）然后结束。所以当源 Observable 出现2次错误以后就不再重试了。
                        return observable.take(2).flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable error) {
                                System.out.println("flatMap error");

                                // For IOExceptions, we  retry
                                if (error instanceof IOException) {
                                    return Observable.just("");//开始重试, 重新发射上面的1234数据
                                }

                                // For anything else, don't retry
                                return Observable.error(error);//不重试, 将进入到订阅者的onError()后结束.
                            }
                        });
                    }
                }, Schedulers.immediate())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Observer -> onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Observer -> onError e=" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("Observer -> onNext s=" + s);
                    }
                });
    }
}
