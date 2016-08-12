/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.android.andy.yubang;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.VersionInfo;
import com.android.andy.yubang.R;

public class BMapApiDemoMain extends Activity {
    private static final String LTAG = BMapApiDemoMain.class.getSimpleName();

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			TextView text = (TextView) findViewById(com.android.andy.yubang.R.id.text_Info);
			text.setTextColor(Color.RED);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				text.setText("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
                    .equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
			    text.setText("key 验证成功! 功能可以正常使用");
			    text.setTextColor(Color.YELLOW);
			}
			else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				text.setText("网络出错");
			}
		}
	}

    private SDKReceiver mReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView text = (TextView) findViewById(R.id.text_Info);
        text.setTextColor(Color.YELLOW);
        text.setText("欢迎使用百度地图Android SDK v" + VersionInfo.getApiVersion());
        ListView mListView = (ListView) findViewById(com.android.andy.yubang.R.id.listView);
        // 添加ListItem，设置事件响应
        mListView.setAdapter(new DemoListAdapter());
        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int index,
                    long arg3) {
                onListItemClick(index);
            }
        });

		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}

    void onListItemClick(int index) {
        Intent intent = null;
        intent = new Intent(BMapApiDemoMain.this, DEMOS[index].demoClass);
        this.startActivity(intent);
    }

    private static final DemoInfo[] DEMOS = {
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_basemap,
                    com.android.andy.yubang.R.string.demo_desc_basemap, BaseMapDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_map_fragment,
                    com.android.andy.yubang.R.string.demo_desc_map_fragment, MapFragmentDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_layers, com.android.andy.yubang.R.string.demo_desc_layers,
                    LayersDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_multimap,
                    com.android.andy.yubang.R.string.demo_desc_multimap, MultiMapViewDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_control,
                    com.android.andy.yubang.R.string.demo_desc_control, MapControlDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_ui, com.android.andy.yubang.R.string.demo_desc_ui,
                    UISettingDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_location,
                    com.android.andy.yubang.R.string.demo_desc_location, LocationDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_geometry,
                    com.android.andy.yubang.R.string.demo_desc_geometry, GeometryDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_overlay,
                    com.android.andy.yubang.R.string.demo_desc_overlay, OverlayDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_heatmap, com.android.andy.yubang.R.string.demo_desc_heatmap,
                    HeatMapDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_geocode,
                    com.android.andy.yubang.R.string.demo_desc_geocode, GeoCoderDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_poi, com.android.andy.yubang.R.string.demo_desc_poi,
                    PoiSearchDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_route, com.android.andy.yubang.R.string.demo_desc_route,
                    RoutePlanDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_districsearch,
                    com.android.andy.yubang.R.string.demo_desc_districsearch,
                    DistrictSearchDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_bus, com.android.andy.yubang.R.string.demo_desc_bus,
                    BusLineSearchDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_share, com.android.andy.yubang.R.string.demo_desc_share,
                    ShareDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_offline,
                    com.android.andy.yubang.R.string.demo_desc_offline, OfflineDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_radar,
                    com.android.andy.yubang.R.string.demo_desc_radar, RadarDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_open_baidumap, com.android.andy.yubang.R.string.demo_desc_open_baidumap,
                    OpenBaiduMap.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_favorite,
                    com.android.andy.yubang.R.string.demo_desc_favorite, FavoriteDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_cloud, com.android.andy.yubang.R.string.demo_desc_cloud,
                    CloudSearchDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_opengl, com.android.andy.yubang.R.string.demo_desc_opengl,
                    OpenglDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_cluster, com.android.andy.yubang.R.string.demo_desc_cluster, MarkerClusterDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_title_tileoverlay, com.android.andy.yubang.R.string.demo_desc_tileoverlay,
                    TileOverlayDemo.class),
            new DemoInfo(com.android.andy.yubang.R.string.demo_desc_texturemapview, com.android.andy.yubang.R.string.demo_desc_texturemapview,
                    TextureMapViewDemo.class),
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听 SDK 广播
        unregisterReceiver(mReceiver);
    }

    private class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            convertView = View.inflate(BMapApiDemoMain.this,
                    com.android.andy.yubang.R.layout.demo_info_item, null);
            TextView title = (TextView) convertView.findViewById(com.android.andy.yubang.R.id.title);
            TextView desc = (TextView) convertView.findViewById(com.android.andy.yubang.R.id.desc);
            title.setText(DEMOS[index].title);
            desc.setText(DEMOS[index].desc);
            if (index >= 16) {
                title.setTextColor(Color.YELLOW);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int index) {
            return DEMOS[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }
    }

    private static class DemoInfo {
        private final int title;
        private final int desc;
        private final Class<? extends Activity> demoClass;

        public DemoInfo(int title, int desc,
                Class<? extends Activity> demoClass) {
            this.title = title;
            this.desc = desc;
            this.demoClass = demoClass;
        }
    }
}