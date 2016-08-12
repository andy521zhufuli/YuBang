package com.tec.android.utils;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by andy on 15/9/3.
 * 验证码, 点击之后显示60秒后在重新获取,
 * 用计时器去实现
 * 这是一种方法, 这个方法比较简单, 但是我现在没时间,  我也建议第二种
 * 第二种就是自己实现一个Button   继承自系统自带的Button  然后在里面写逻辑代码
 * 等我以后有时间了  就写了 放在github上
 */
public class TimerCount extends CountDownTimer{

    private Button btn;

    public TimerCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimerCount(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        this.btn = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);
        btn.setText(millisUntilFinished / 1000 + "秒后重新获取");
    }

    @Override
    public void onFinish() {
        btn.setClickable(true);
        btn.setText("获取验证码");
    }
}
