package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andy.android.yubang.R;
import com.car.yubangapk.controller.CommentMyOrder;
import com.car.yubangapk.utils.toastMgr;

public class MakeNewCommentActivity extends BaseActivity implements RatingBar.OnRatingBarChangeListener {


    public static final String TAG = MakeNewCommentActivity.class.getSimpleName();

    Context mContext;
    View common_title;
    ImageView img_back;
    TextView header_name;

    RatingBar comment_grade_star;//等级
    EditText  use_experience_content;//输入评价
    Button    send_comment;//提交评价

    private int mCurrentStar;
    private String mUserid;
    private String mOrderid;
    private int commentType = CommentMyOrder.COMMENT_TYPE_WORD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_make_new_comment);
        mContext = this;
        findViews();
        getExtraData();
    }



    private void getExtraData()
    {
        Bundle bundle = getIntent().getExtras();
        mUserid = bundle.getString("userid");
        mOrderid = bundle.getString("orderId");
    }

    /**
     * 绑定控件
     */
    private void findViews()
    {
        common_title = findViewById(R.id.common_title);
        img_back = (ImageView) common_title.findViewById(R.id.img_back);
        header_name = (TextView) common_title.findViewById(R.id.header_name);


        comment_grade_star = (RatingBar) findViewById(R.id.comment_grade_star);
        use_experience_content = (EditText) findViewById(R.id.use_experience_content);
        send_comment = (Button) findViewById(R.id.send_comment);

        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coommitComment();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setTitle();
        initRatingBar();
    }


    private void initRatingBar()
    {
        comment_grade_star.setMax(5);//设置最大
        comment_grade_star.setProgress(0);//设置当前
        comment_grade_star.setOnRatingBarChangeListener(this);
    }



    /**
     * 设置界面顶部标题
     */
    private void setTitle()
    {
        header_name.setText("评价");
    }

    /**
     * 用户改变评分
     * @param ratingBar 表示当前评分进度条发生改变的时候会回调
     * @param rating    表示当前的值，从 getProgress()方法来获得
     * @param fromUser  true表示进度改变是通过触摸和滑动来实现的。
     */
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int progress = (int)ratingBar.getProgress(); //获得当前的刻度
        mCurrentStar = progress;
        toastMgr.builder.display("progress = " + progress + "rating" + rating, 1);
    }


    private void coommitComment()
    {
        int star = getStar();
        String commentContent = getCommentContent();

        if (commentContent == null || commentContent.equals(""))
        {
            toastMgr.builder.display("请输入评论内容", 1);
            return;
        }

        CommentMyOrder pushComment = new CommentMyOrder(mUserid, mOrderid, commentContent, commentType, star, null, this);
        pushComment.uploadContent();

    }

    private String getCommentContent() {
        String content  = use_experience_content.getText().toString();
        return content;
    }

    private int getStar() {
        return mCurrentStar;
    }


}
