package com.car.yubangapk.json.formatJson.search;

import com.car.yubangapk.json.JSONUtils;
import com.car.yubangapk.json.bean.search.SearchResultProductPackage;
import com.car.yubangapk.json.bean.search.SearchResultProductPackageRows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 16/5/14.
 */
public class FormatSearchProductPackage
{

    //    {"total":8,
//            "rows":[{"packageName":"美孚刹车油","id":"53df7b5b-5cca-485f-800b-c17311cf51ef"},{"packageName":"美孚速霸保养包","id":"42d3e125-e906-11e5-b9b6-28d244001fe5"},
//            {"packageName":"美孚方向机助力油","id":"e48e73ef-282b-4a76-85c9-63cce5845a14"},{"packageName":"美孚自动变速箱油","id":"6345aee0-5a31-4dca-8195-f147434e8c4c"}]}
    String json;

    public FormatSearchProductPackage()
    {

    }

    public FormatSearchProductPackage(String json)
    {
        this.json = json;
    }


    public SearchResultProductPackage getSearchResult()
    {
        SearchResultProductPackage resultProductPackage = new SearchResultProductPackage();
        JSONObject resultJsonObject = null;
        try {
            resultJsonObject = new JSONObject(json);

            //{"isJson":true,"isReturnStr":false,"returnCode":100,"returneMsg":"SUCCESS","message":"用户未登录"}
            int returnCode = JSONUtils.getInt(resultJsonObject, "returnCode", 0);
            String message = JSONUtils.getString(resultJsonObject, "message", "");
            if (returnCode != 0)
            {
                resultProductPackage.setReturnCode(returnCode);
                resultProductPackage.setMessage(message);
                resultProductPackage.setHasData(false);
                return resultProductPackage;
            }

            int total = JSONUtils.getInt(resultJsonObject, "total", 0);
            JSONArray rowArray = JSONUtils.getJSONArray(resultJsonObject, "rows", null);
            if (rowArray == null)
            {
                resultProductPackage.setHasData(false);
                return resultProductPackage;
            }
            else
            {
                List<SearchResultProductPackageRows> rows = getArray(resultProductPackage, rowArray);
                resultProductPackage.setRows(rows);
                resultProductPackage.setTotal(total);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return resultProductPackage;
    }

    private List<SearchResultProductPackageRows> getArray(SearchResultProductPackage resultProductPackage, JSONArray rowArray) throws JSONException {

        List<SearchResultProductPackageRows> rows = new ArrayList<>();
        int size = rowArray.length();

        for (int index = 0; index < size; index++) {
            SearchResultProductPackageRows row = new SearchResultProductPackageRows();
            JSONObject rowJsonObject = rowArray.getJSONObject(index);
            String packageName = JSONUtils.getString(rowJsonObject, "packageName", "");
            String id = JSONUtils.getString(rowJsonObject, "id", "");
            String repairService = JSONUtils.getString(rowJsonObject, "repairService", "");
            row.setId(id);
            row.setPackageName(packageName);
            row.setRepairService(repairService);
            rows.add(row);
        }
        return rows;
    }
}
