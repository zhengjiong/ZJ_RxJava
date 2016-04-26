package c.rxandroid_0240;

import android.support.annotation.StringRes;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

import static rx.android.app.AppObservable.bindActivity;
import static rx.android.app.AppObservable.bindFragment;

/**
 * Created by wangwei on 2016/3/15.
 */
public class BaseViewModel {
    private Object baseActivity;
    protected final CompositeSubscription subscription = new CompositeSubscription();
    protected final BehaviorSubject<String> error=BehaviorSubject.create();
    public BaseViewModel(Object activity) {
        this.baseActivity = activity;
    }

    public BehaviorSubject<String> getError() {
        return error;
    }
    protected void sendError(String error)
    {
        this.error.onNext(error);
    }
    public void clearError()
    {
        this.error.onNext(null);
    }

    /*public <T> void submitRequest(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError, final Action0 onComplete) {
        if (null!=baseActivity&&baseActivity instanceof BaseActivity) {
            subscription.add(bindActivity(getActivity(), observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())).subscribe(onNext, onError, onComplete));
        } else if (null!=baseActivity&&baseActivity instanceof BaseFragment) {
            subscription.add(bindFragment(baseActivity, observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())).subscribe(onNext, onError, onComplete));
        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete);
        }
    }

    public <T> void submitRequest(Observable<T> observable, final Action1<? super T> onNext, final Action1<Throwable> onError) {
        if (null!=baseActivity&&baseActivity instanceof BaseActivity) {
            subscription.add(bindActivity(getActivity(), observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())).subscribe(onNext, onError));
        } else if (null!=baseActivity&&baseActivity instanceof BaseFragment) {
            subscription.add(bindFragment(baseActivity, observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())).subscribe(onNext, onError));
        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError);
        }
    }

    public <T> void submitRequest(Observable<T> observable, final Action1<? super T> onNext) {
        if (null!=baseActivity&&baseActivity instanceof BaseActivity) {
            subscription.add(bindActivity(getActivity(), observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())).subscribe(onNext));
        } else if (null!=baseActivity&&baseActivity instanceof BaseFragment) {
            subscription.add(bindFragment(baseActivity, observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())).subscribe(onNext));
        } else {
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
        }
    }*/

    public void onDestroy() {
        subscription.clear();
        baseActivity = null;
    }
    public void cancelTask()
    {
        subscription.clear();
    }
    public String getError(Throwable throwable)
    {
        if(throwable!=null) return throwable.getMessage();
        return "";
    }
    public static String getStringValue(String s)
    {
        return s==null?"":s;
    }
}
