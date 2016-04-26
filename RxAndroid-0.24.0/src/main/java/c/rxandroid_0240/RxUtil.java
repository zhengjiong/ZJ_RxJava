package c.rxandroid_0240;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wangwei on 2016/3/19.
 */
public class RxUtil {
    public static Observable<Object> click(final View view) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) view.setOnClickListener(null);
                    }
                }));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        v.setEnabled(false);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        }, 350);

                        subscriber.onNext(new Object());
                    }
                });
            }
        });
    }
    /*public static Observable<Object> click(View view) {
        return Observable.create(subscriber -> {
            subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(() -> {
                if (view != null) view.setOnClickListener(null);
            }));
            view.setOnClickListener(v -> {
                v.setEnabled(false);
                v.postDelayed(() -> {
                    v.setEnabled(true);
                }, 350);
                subscriber.onNext(new Object());
            });
        });
    }*/

    public static Observable<Object> longClick(final View view) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) view.setOnLongClickListener(null);
                    }
                }));
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        v.setEnabled(false);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                v.setEnabled(true);
                            }
                        }, 350);
                        subscriber.onNext(new Object());
                        return true;
                    }
                });
            }
        });
    }

    public static Observable<String> textChanges(final TextView view) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        subscriber.onNext(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                };

                Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) view.removeTextChangedListener(watcher);
                    }
                });
                subscriber.add(subscription);
                view.addTextChangedListener(watcher);
                subscriber.onNext(view.getText().toString());
            }
        });
    }

    /*public static Observable<String> textCount(EditText textView, int minCount, int maxCount,String maxStr) {
        return textCount(textView, minCount, textView.getResources().getString(R.string.text_product_min_count, "" + minCount)
                , maxCount, maxStr);
    }*/

    /*private static Observable<String> textCount(EditText textView, int minCount, String txtMin, int maxCount, String txtMax) {
        return Observable.create(subscriber -> {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(textView.getText().toString())) {
                        int num = Integer.parseInt(textView.getText().toString());
                        if (num < minCount && num > 0) {
                            textView.setText(minCount + "");
                            textView.setSelection(String.valueOf(minCount).toString().length());
                            Snackbar.make(textView, txtMin, Snackbar.LENGTH_LONG).show();
                        }
                        if (num > maxCount) {
                            textView.setText(maxCount + "");
                            textView.setSelection(String.valueOf(maxCount).toString().length());
                            Snackbar.make(textView, txtMax, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            };
            final View.OnFocusChangeListener focusChange = new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        try {
                            String str = ((EditText) v).getText().toString();
                            if (!TextUtils.isEmpty(str)) {
                                Integer d = Integer.valueOf(str);
                                if (d == 0) {
                                    ((EditText) v).setText("");
                                }
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        textView.postDelayed(runnable, 200);
                    }
                }
            };
            final TextWatcher watcher = new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    subscriber.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        int num = Integer.parseInt(textView.getText().toString());
                        if (num > maxCount) {
                            textView.setText(maxCount + "");
                            textView.setSelection(String.valueOf(maxCount).toString().length());
                            Snackbar.make(textView, txtMax, Snackbar.LENGTH_LONG).show();
                            return;
                        }
                        if (!("" + num).equals(textView.getText().toString()))
                            textView.setText("" + num);
                    }
                }
            };
            Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(() -> {
                if (textView != null) {
                    textView.removeTextChangedListener(watcher);
                    textView.setOnFocusChangeListener(null);
                }
            });
            subscriber.add(subscription);
            textView.setOnFocusChangeListener(focusChange);
            textView.addTextChangedListener(watcher);
            subscriber.onNext(textView.getText().toString());
        });
    }*/

    /*public static Observable<String> textMaxCount(EditText textView, int maxCount, String txt) {
        return Observable.create(subscriber -> {
            final TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    subscriber.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s)) {
                        int num = Integer.parseInt(s.toString());
                        if (num > maxCount) {
                            textView.setText(maxCount + "");
                            textView.setSelection(String.valueOf(maxCount).toString().length());
                            Snackbar.make(textView, txt, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            };
            Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(() -> {
                if (textView != null) textView.removeTextChangedListener(watcher);
            });
            subscriber.add(subscription);
            textView.addTextChangedListener(watcher);
            subscriber.onNext(textView.getText().toString());
        });
    }*/


    public static Action1<? super Boolean> enabled(final View view) {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean b) {
                if (view != null)
                    view.setEnabled(b);
            }
        };
    }

    public static Action1<? super String> text(final TextView view) {
        return new Action1<String>() {
            @Override
            public void call(String s) {
                if (view != null)
                    view.setText(s);
            }
        };
    }

    public static Action1<? super String> textE(final EditText view) {
        return new Action1<String>() {
            @Override
            public void call(String s) {
                if (view != null) {
                    view.setText(s);
                    if (s.length() > 0)
                        try {
                            view.setSelection(s.toString().length());
                        } catch (Exception e) {
                        }
                }
            }
        };
    }

    public static Action1<? super String> html(final TextView view) {
        return new Action1<String>() {
            @Override
            public void call(String s) {
                if (view != null)
                    view.setText(Html.fromHtml(s));
            }
        };
    }
}
