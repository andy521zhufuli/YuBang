package com.android.andy.yubang.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.andy.yubang.banner.FlashView;
import com.android.andy.yubang.banner.constants.EffectConstants;
import com.android.andy.yubang.utils.toastMgr;
import com.android.andy.yubang.view.ForbiddenScrollGridView;
import com.andy.android.yubang.R;

import java.util.ArrayList;

/**
 * ShoppingMallActivity: 商城界面
 *
 * @author andyzhu
 * @version 1.0
 * @created 2016-02-22
 */
public class ShoppingMallActivity extends BaseActivity {

    private Context mContext;
    private final static String TAG = "FirstPageActivity";

    private FlashView shoppingmall_flashview_banner;
    private ArrayList<String> imageUrls;

    private ImageView shoppingmall_search_imageview;//搜索按钮


    private ForbiddenScrollGridView shoppingmall_species_gridview;//商品种类的标签

    private TextView main_product1;


    // 定义图片的资源
    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_mall);

        mContext = this;

        findViews();


        imageUrls = new ArrayList<String>();

//        imageUrls.add("http://image.baidu.com/search/detail?ct=503316480&tn=baiduimagedetail&statnum=wallpaper&ipn=d&z=0&s=0&ic=0&lm=-1&itg=0&cg=wallpaper&word=%E5%A3%81%E7%BA%B8&ie=utf-8&in=3354&cl=2&st=&pn=0&rn=1&di=0&ln=1000&&fmq=1378374347070_R&se=&sme=0&tab=&face=&&cs=1271743327,1430666038&adpicid=0&pi=0&os=2691782811,855557015&istype=&ist=&jit=&objurl=http%3A%2F%2Fa.ikafan.com%2Fattachment%2Fforum%2Fmonth_0807%2F20080726_877668de4f27856575b3y1PzoiAhYveb.jpg&bdtype=-1&gsm=200003c");
        imageUrls.add("http://www.juzi2.com/uploads/allimg/130619/1_130619193218_1.jpg");
        imageUrls.add("http://a.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f4dd80e61a786c9177f3e5378.jpg");
        imageUrls.add("http://f.hiphotos.baidu.com/zhidao/pic/item/9e3df8dcd100baa12801ad224710b912c8fc2e7e.jpg");
//        imageUrls.add("http://www.kole8.com/desktop/desk_file-11/2/2/2012/11/2012113013552959.jpg");
//        imageUrls.add("http://www.237.cc/uploads/pcline/712_0_1680x1050.jpg");
        shoppingmall_flashview_banner.setImageUris(imageUrls);
        shoppingmall_flashview_banner.setEffect(EffectConstants.DEFAULT_EFFECT);//更改图片切换的动画效果


        //商品种类点击提示
        shoppingmall_species_gridview.setAdapter(new ImageAdapter());

        shoppingmall_species_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                toastMgr.builder.display( "-->>" + position, 0);
            }
        });

    }

    /**
     * 找到控件位置 绑定
     */
    private void findViews() {

        //轮播广告
        shoppingmall_flashview_banner = (FlashView) findViewById(R.id.shoppingmall_flashview_banner);
        //商品种类的标签
        shoppingmall_species_gridview = (ForbiddenScrollGridView) findViewById(R.id.shoppingmall_species_gridview);
        //搜索
        shoppingmall_search_imageview = (ImageView) findViewById(R.id.shoppingmall_search_imageview);
        //主打产品1
        main_product1 = (TextView) findViewById(R.id.main_product1);



        //搜索点击后  跳转到搜索界面
        shoppingmall_search_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallActivity.this, SearchActivity.class);
                mContext.startActivity(intent);
            }
        });

        main_product1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ShoppingMallActivity.this, ShoppingMallGoodsActivity.class);
                startActivity(intent);
            }
        });

    }


    /*
     * 适配器的定义,要继承BaseAdapter
     */
    public class ImageAdapter extends BaseAdapter {

        public ImageAdapter() {
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            /*
             * 1.手工创建对象 2.加载xml文件
             */
            view = View.inflate(mContext, R.layout.item_shoppingmall_species_gridview, null);
            TextView species = (TextView) view.findViewById(R.id.item_shoppingmall_species_textview);
            species.setText(strings[position]);
            return view;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_mall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
