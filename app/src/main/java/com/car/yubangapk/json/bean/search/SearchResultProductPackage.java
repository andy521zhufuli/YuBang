package com.car.yubangapk.json.bean.search;

import com.car.yubangapk.json.bean.OrderDetail.BaseJson;

import java.util.List;

/**
 * Created by andy on 16/5/14.
 *
 * 搜索后的结果  产品包名字 以及id
 *
 */
public class SearchResultProductPackage extends BaseJson
{
//    {"total":8,
//            "rows":[{"packageName":"美孚刹车油","id":"53df7b5b-5cca-485f-800b-c17311cf51ef"},{"packageName":"美孚速霸保养包","id":"42d3e125-e906-11e5-b9b6-28d244001fe5"},
//            {"packageName":"美孚方向机助力油","id":"e48e73ef-282b-4a76-85c9-63cce5845a14"},{"packageName":"美孚自动变速箱油","id":"6345aee0-5a31-4dca-8195-f147434e8c4c"}]}

    int total;
    List<SearchResultProductPackageRows> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<SearchResultProductPackageRows> getRows() {
        return rows;
    }

    public void setRows(List<SearchResultProductPackageRows> rows) {
        this.rows = rows;
    }
}
