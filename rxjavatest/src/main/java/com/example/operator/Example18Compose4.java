package com.example.operator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Transformer;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 使用单例优化Example17
 *
 * 现在你可能会好奇，compose()操作符和flatMap()操作符有何区别。
 * 他们最终都会发送出Observable<R>，这就意味着，两者都能够用于操作符的重用？
 *
 * 不同点在于compose()操作符拥有更高层次的抽象概念：它操作于整个数据流中，不仅仅是某一个被发送的事件。具体如下：
 * 1. compose()是唯一一个能够从数据流中得到原始Observable<T>的操作符，flatMap是新建了一个Observable, 所以，
 * 那些需要对整个数据流产生作用的操作（比如，subscribeOn()和observeOn()）需要使用compose()来实现。
 * 相较而言，如果在flatMap()中使用subscribeOn()或者observeOn()，那么它仅仅对在flatMap()中创建的Observable起作用，
 * 而不会对剩下的流产生影响（译者注：深坑，会在后面的系列着重讲解，欢迎关注）。
 *
 * 2.compose()是唯一一个能够从数据流中得到原始Observable<T>的操作符，所以，那些需要对整个数据流产生作用的操作（
 * 比如，subscribeOn()和observeOn()）需要使用compose()来实现。相较而言，
 * 如果在flatMap()中使用subscribeOn()或者observeOn()，那么它仅仅对在flatMap()中创建的Observable起作用，
 * 而不会对剩下的流产生影响（译者注：深坑，会在后面的系列着重讲解，欢迎关注）。
 *
 * 3.因为每一次调用onNext()后，都不得不新建一个Observable，所以flatMap()的效率较低。事实上，compose()操作符只在主干数据流上执行操作。
 *
 * 最后: 如果想重用一些操作符，还是使用compose()吧，虽然flatMap()的用处很多，但作为重用代码这一点来讲，并不适用。
 *
 * Created by zhengjiong on 16/5/7.
 */
public class Example18Compose4 {

    //现在我们终于把他做成一个“单例”了（译者注：此单例非彼单例）。
    Observable.Transformer mTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object obj) {
            return ((Observable)obj).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.immediate());
        }
    };

    public <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>)mTransformer;
    }

    public static void main(String[] args) {
        Example18Compose4 example = new Example18Compose4();
        example.test();
    }

    private void test() {
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Observable.from(numList).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer num) {
                return String.valueOf(num);
            }
        })
        .compose(applySchedulers())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Subscriber -> onCompleted");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                System.out.println("Subscriber -> onNext " + s);
            }
        });

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
