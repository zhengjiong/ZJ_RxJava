package c.rxandroid_0240;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static rx.android.app.AppObservable.bindActivity;

/**
 * Created by wangwei on 2016/3/21.
 */
public abstract class RxBaseActivity extends AppCompatActivity {
    private BaseViewModel viewModel;
    public final CompositeSubscription subscription = new CompositeSubscription();

    public void initViewModel(final BaseViewModel viewModel) {
        this.viewModel = viewModel;
        if(this.viewModel!=null)
        {
            bindData(viewModel.getError(), new Action1<String>() {
                @Override
                public void call(String error) {
                    if(!TextUtils.isEmpty(error)) {
                        if (viewModel != null) {
                            viewModel.clearError();
                        }
                        error(error);
                    }
                }
            });
        }
    }
    public abstract void error(String error);

    public <T> void bindData(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        subscription.add(bindActivity(getActivity(), observable.subscribeOn(Schedulers.io())).subscribe(onNext, onError));
    }

    public <T> void bindData(Observable<T> observable, Action1<? super T> onNext) {
        subscription.add(bindActivity(getActivity(), observable.subscribeOn(Schedulers.io())).subscribe(onNext,
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    public <T> void bindUi(Observable<T> observable, Action1<? super T> onNext, Action1<Throwable> onError) {
        subscription.add(bindActivity(this, observable).subscribe(onNext, onError));
    }

    public <T> void bindUi(Observable<T> observable, Action1<? super T> onNext) {
        subscription.add(bindActivity(this, observable).subscribe(onNext, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.clear();
    }
    public Activity getActivity() {
        return this;
    }
}
