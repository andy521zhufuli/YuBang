package com.car.yubangapk.ui.myrecommendpartner;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.android.yubang.R;
import com.car.yubangapk.utils.L;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyRecommendedPartnerThirdFragmentActivityFragment extends Fragment {

    public MyRecommendedPartnerThirdFragmentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_recommended_partner_third, container, false);
    }



    @Override
    public void onResume() {
        super.onResume();
        L.e("TAG ", " 3 onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.e("TAG ", " 3 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("TAG ", " 3 onDestroy");

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            L.e("TAG ", " 3 onDestroy");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        L.e("TAG ", " 3 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.e("TAG ", " 3 onStop");
    }
}

