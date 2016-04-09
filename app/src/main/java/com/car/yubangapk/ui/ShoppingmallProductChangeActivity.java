package com.car.yubangapk.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.andy.android.yubang.R;
import com.car.yubangapk.configs.Configs;
import com.car.yubangapk.okhttp.OkHttpUtils;
import com.car.yubangapk.okhttp.callback.StringCallback;
import com.car.yubangapk.utils.L;
import com.car.yubangapk.utils.toastMgr;

import okhttp3.Call;

/**
 * ShoppingmallProductChangeActivity: 产品包 更换产品 界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingmallProductChangeActivity extends BaseActivity {

    private Context mContext;
    private static final String TAG = ShoppingmallProductChangeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shoppingmall_product_change);

        mContext = this;

        findViews();

        String categoryId = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            categoryId = bundle.getString(Configs.categoryId);
        }


        httpGetChangeProduct(categoryId);
    }

    /**
     * 根据商品的分类id获取可更换商品
     * @param categoryId
     */
    private void httpGetChangeProduct(String categoryId) {

        OkHttpUtils.post()
                .url(Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA)
                .addParams("sqlName", "clientSearchCategoryProduct")
                .addParams("dataReqModel.args.needTotal", "needTotal")
                .addParams("dataReqModel.args.category", categoryId)
                .build()
                .execute(new GetChangeProductCallback());

        L.i(TAG, "获取更换产品url = " + Configs.IP_ADDRESS + Configs.IP_ADDRESS_ACTION_GETDATA + "?"
                        + "sqlName=" + "clientSearchCategoryProduct"
                        + "&dataReqModel.args.productPackage=" + categoryId
                        + "&dataReqModel.args.needTotal=needTotal"
        );

    }

    class GetChangeProductCallback extends StringCallback
    {

        @Override
        public void onError(Call call, Exception e) {
            toastMgr.builder.display("网络错误,请重新连接", 1);
        }

        @Override
        public void onResponse(String response) {
            L.d(TAG,"获取更换产品 json = " + response);
        }
    }


    /**
     * 绑定控件
     */
    private void findViews() {

    }
}
