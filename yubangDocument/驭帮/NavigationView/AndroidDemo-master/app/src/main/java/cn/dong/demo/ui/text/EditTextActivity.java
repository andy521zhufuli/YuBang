package cn.dong.demo.ui.text;

import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import cn.dong.demo.R;
import cn.dong.demo.ui.common.BaseActivity;
import cn.dong.demo.util.L;
import cn.dong.demo.util.inputfilter.EmojiInputFilter;
import cn.dong.demo.util.inputfilter.EmsLenghtFilter;
import cn.dong.demo.util.inputfilter.LinebreakInputFilter;
import timber.log.Timber;

/**
 * 文字输入测试
 *
 * @author dong on 15/5/29.
 */
public class EditTextActivity extends BaseActivity {
    @InjectView(R.id.edit)
    EditText mEditView;
    @InjectView(R.id.edit_switch)
    SwitchCompat editSwitch;
    @InjectView(R.id.filter_edit)
    EditText mFilterEdit;
    @InjectView(R.id.button_del)
    Button mDelButton;

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_edittext;
    }

    @Override
    protected void initPageViewListener() {
        mEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                L.d(TAG, "onEditorAction %d", actionId);
                return false;
            }
        });

        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Timber.d("beforeTextChanged %s %d %d %d", s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Timber.d("onTextChanged %s %d %d %d", s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Timber.d("afterTextChanged %s", s);
            }
        });

        mFilterEdit.setFilters(new InputFilter[]{new EmsLenghtFilter(20), new EmojiInputFilter(), new LinebreakInputFilter()});

        editSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // 恢复编辑
                    setEditable(true);
                } else {
                    // 禁止编辑
                    setEditable(false);
                }
            }
        });

        mDelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                mEditView.onKeyDown(code, new KeyEvent(action, code));
            }
        });
    }

    private void setEditable(boolean editable) {
        mEditView.setEnabled(editable);
        mEditView.setFocusableInTouchMode(editable);
        mEditView.setFocusable(editable);
        mEditView.setClickable(editable);
        mEditView.setLongClickable(editable);
    }
}
