package com.android.andy.yubang.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.andy.yubang.utils.L;
import com.andy.android.yubang.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyRecommendedPartnerSecondFragmentActivityFragment extends Fragment {

    private String[] strings = {"保养维护", "电子电路", "发动机件", "底盘配件", "车架配件", "拖架配件" };

    public MyRecommendedPartnerSecondFragmentActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context.getClass().getName();
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onAttach  " + context.getClass().getName());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onCreateView");
        return inflater.inflate(R.layout.fragment_my_recommended_partner_second, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.i("MyRecommendedPartnerSecondFragmentActivityFragment", " ---> onDetach");
    }



    /**
     * 适配器的定义,要继承BaseAdapter
     */
    public class SearchHistory extends BaseAdapter {

        public SearchHistory() {
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
            view = View.inflate(getActivity(), R.layout.item_search_history_listview, null);
            TextView species = (TextView) view.findViewById(R.id.item_search_history_textview);
            species.setText(strings[position]);
            return view;
        }
    }
}
