package com.car.yubangapk.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.andy.android.yubang.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyRecommendedPartnerFirstFragmentActivityFragment extends Fragment {

    ListView listview;

    public MyRecommendedPartnerFirstFragmentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity();
        View view = inflater.inflate(R.layout.fragment_my_recommended_partner_first, container, false);
        listview = (ListView) view.findViewById(R.id.my_recommended_partner_first_fragment_listview);
        listview.setAdapter(new TestAdapter());
        return view;
    }


    // 定义图片的资源
    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };

    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class TestAdapter extends BaseAdapter {

        public TestAdapter() {
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
            view = View.inflate(getActivity(), R.layout.item_test_listview_data, null);
//            TextView species = (TextView) view.findViewById(R.id.item_search_recommend_textview);
//            species.setText(strings[position]);
            return view;
        }
    }
}
