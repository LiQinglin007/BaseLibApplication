package com.lixiaomi.baselibapplication.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.lixiaomi.baselib.utils.LogUtils;
import com.lixiaomi.baselib.utils.loadImageUtils.MiLoadImageUtil;
import com.lixiaomi.baselibapplication.R;
import com.lixiaomi.baselibapplication.bean.JsonTestBean;
import com.lixiaomi.baselibapplication.bean.NoticeBean;
import com.lixiaomi.baselibapplication.ui.baseui.XMBaseFragment;
import com.lixiaomi.mvplib.base.BasePresenter;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/4
 * 内容：
 * 最后修改：
 */

public class HomeFragment extends XMBaseFragment implements View.OnClickListener {

    public static HomeFragment getInstance() {
        return HomeFragmentHolder.mHomeFragment;
    }

    private static final class HomeFragmentHolder {
        private final static HomeFragment mHomeFragment = new HomeFragment();
    }

    Button mChartButton;
    Button mLauncherNumberButton;
    Button mExRecyclerButton;
    Button mPDFButton;
    Button mJSONButton;
    Button mTcpClientButton;
    Button mTcpServerButton;
    Button mAddWifiButton;
    Button mJsonToObject;
    Button mViewPageButton;
    ImageView mTestImageview;

    @Override
    protected Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

        mTestImageview = rootView.findViewById(R.id.test_img);
        String url = "https://fms.ipinyou.com/6/0B/31/B5/F001461Sm_wp001r_wtn.jpg";
        MiLoadImageUtil.loadImage(getActivity(), url, mTestImageview);
        mChartButton = rootView.findViewById(R.id.home_chart);
        mLauncherNumberButton = rootView.findViewById(R.id.home_launcher);
        mExRecyclerButton = rootView.findViewById(R.id.home_recycler);
        mPDFButton = rootView.findViewById(R.id.home_pdf);
        mJSONButton = rootView.findViewById(R.id.home_json);
        mTcpClientButton = rootView.findViewById(R.id.home_tcp_client);
        mTcpServerButton = rootView.findViewById(R.id.home_tcp_server);
        mAddWifiButton = rootView.findViewById(R.id.home_wifi);
        mJsonToObject = rootView.findViewById(R.id.home_jsonarray_to_object_array);
        mViewPageButton = rootView.findViewById(R.id.home_viewpage);

        mChartButton.setOnClickListener(this);
        mLauncherNumberButton.setOnClickListener(this);
        mExRecyclerButton.setOnClickListener(this);
        mPDFButton.setOnClickListener(this);
        mJSONButton.setOnClickListener(this);
        mTcpClientButton.setOnClickListener(this);
        mTcpServerButton.setOnClickListener(this);
        mAddWifiButton.setOnClickListener(this);
        mJsonToObject.setOnClickListener(this);
        mViewPageButton.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_chart:
                showLoading();
//                startActivity(new Intent(getActivity(), MPChartActivity.class));
                break;
            case R.id.home_launcher:
                showLoading();
                showLoading();
//                startActivity(new Intent(getActivity(), LauncherMessageNumberActivity.class));
                break;
            case R.id.home_recycler:
                hineLoading();
//                startActivity(new Intent(getActivity(), ExRecyclerActivity.class));
                break;
            case R.id.home_pdf:
                startActivity(new Intent(getActivity(), PDFActivity.class));
                break;
            case R.id.home_json:
                JsonTestBean jsonTestBean = new Gson().fromJson(testJson, JsonTestBean.class);
                LogUtils.loge(new Gson().toJson(jsonTestBean));
                break;
            case R.id.home_tcp_client:
                startActivity(new Intent(getActivity(), TcpClientActivity.class));
                break;
            case R.id.home_tcp_server:
                startActivity(new Intent(getActivity(), TcpServerActivity.class));
                break;
            case R.id.home_wifi:
                startActivity(new Intent(getActivity(), WifiActivity.class));
                break;
            case R.id.home_jsonarray_to_object_array:
                jsonListToObjectArray();
                break;
            case R.id.home_viewpage:
                startActivity(new Intent(getActivity(), ViewPagerTestActivity.class));
                break;
            default:
                break;
        }
    }

    ArrayList<NoticeBean> noticeList = new ArrayList<>();

    private void jsonListToObjectArray() {
        ArrayList<NoticeBean> noticeListData = new ArrayList<>();
        noticeListData.add(new NoticeBean(null, "title1", "content1"));
        noticeListData.add(new NoticeBean(null, "title2", "content2"));
        noticeListData.add(new NoticeBean(null, "title3", "content3"));
        noticeListData.add(new NoticeBean(null, "title4", "content4"));
        noticeListData.add(new NoticeBean(null, "title5", "content5"));
        Gson gson = new Gson();
        String response = gson.toJson(noticeListData);
        LogUtils.loge("JSON数组转换成对象原始数据：---》response:" + response + "  data.size:" + noticeListData.size());
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(response).getAsJsonArray();
        noticeList.clear();
        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            NoticeBean noticeBean = gson.fromJson(user, NoticeBean.class);
            noticeList.add(noticeBean);
        }
        LogUtils.loge("JSON数组转换成对象结果---》noticeList.size():" + noticeList.size());
    }

    String testJson = "{\n" +
            "\"code\": 0,\n" +
            "\"message\": \"成功\",\n" +
            "\"data\": {\n" +
            "\"structureContactName\": \"石家庄_小米1\",\n" +
            "\"structureChildren\": [\n" +
            "{\n" +
            "\"structureContactName\": \"桥西_小米1\",\n" +
            "\"structureChildren\": [\n" +
            "{\n" +
            "\"structureContactName\": \"振头_小米1\",\n" +
            "\"structureChildren\": [],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"桥西区振头国土资源中心\",\n" +
            "\"structureParentName\": \"桥西区国土资源分局\",\n" +
            "\"structureId\": \"6\",\n" +
            "\"structurePeople\": [\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222203\",\n" +
            "\"userLoginName\": \"qxxiaomi1\",\n" +
            "\"userName\": \"桥西_小米1\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222204\",\n" +
            "\"userLoginName\": \"qxxiaomi2\",\n" +
            "\"userName\": \"桥西_小米2\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222205\",\n" +
            "\"userLoginName\": \"qxxiaomi3\",\n" +
            "\"userName\": \"桥西_小米3\"\n" +
            "}\n" +
            "],\n" +
            "\"structureEmail\": \"1111@163.com\",\n" +
            "\"structureContactPhone\": \"15222222209\",\n" +
            "\"structureParentId\": \"2\",\n" +
            "\"structureAddress\": \"桥西区新石北路与友谊大街交叉口\",\n" +
            "\"structureIsLast\": 1\n" +
            "},\n" +
            "{\n" +
            "\"structureContactName\": \"新石_小米1\",\n" +
            "\"structureChildren\": [],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"桥西区新石国土资源中心\",\n" +
            "\"structureParentName\": \"桥西区国土资源分局\",\n" +
            "\"structureId\": \"7\",\n" +
            "\"structurePeople\": [\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222203\",\n" +
            "\"userLoginName\": \"qxxiaomi1\",\n" +
            "\"userName\": \"桥西_小米1\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222204\",\n" +
            "\"userLoginName\": \"qxxiaomi2\",\n" +
            "\"userName\": \"桥西_小米2\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222205\",\n" +
            "\"userLoginName\": \"qxxiaomi3\",\n" +
            "\"userName\": \"桥西_小米3\"\n" +
            "}\n" +
            "],\n" +
            "\"structureEmail\": \"1112@qq.com\",\n" +
            "\"structureContactPhone\": \"15222222212\",\n" +
            "\"structureParentId\": \"2\",\n" +
            "\"structureAddress\": \"桥西区新石北路与友谊大街交叉口1\",\n" +
            "\"structureIsLast\": 1\n" +
            "}\n" +
            "],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"桥西区国土资源分局\",\n" +
            "\"structureParentName\": \"石家庄市国土资源局\",\n" +
            "\"structureId\": \"2\",\n" +
            "\"structurePeople\": [],\n" +
            "\"structureEmail\": \"111@163.com\",\n" +
            "\"structureContactPhone\": \"15222222203\",\n" +
            "\"structureParentId\": \"1\",\n" +
            "\"structureAddress\": \"石家庄市桥西区新石中路111号\",\n" +
            "\"structureIsLast\": 0\n" +
            "},\n" +
            "{\n" +
            "\"structureContactName\": \"裕华_小米1\",\n" +
            "\"structureChildren\": [],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"裕华区国土资源分局\",\n" +
            "\"structureParentName\": \"石家庄市国土资源局\",\n" +
            "\"structureId\": \"3\",\n" +
            "\"structurePeople\": [],\n" +
            "\"structureEmail\": \"112@163.com\",\n" +
            "\"structureContactPhone\": \"15222222206\",\n" +
            "\"structureParentId\": \"1\",\n" +
            "\"structureAddress\": \"石家庄市桥西区新石中路112号\",\n" +
            "\"structureIsLast\": 0\n" +
            "},\n" +
            "{\n" +
            "\"structureContactName\": \"长安_小米1\",\n" +
            "\"structureChildren\": [],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"长安区国土资源分局\",\n" +
            "\"structureParentName\": \"石家庄市国土资源局\",\n" +
            "\"structureId\": \"4\",\n" +
            "\"structurePeople\": [\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222200\",\n" +
            "\"userLoginName\": \"sjzxiaomi1\",\n" +
            "\"userName\": \"石家庄_小米1\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222201\",\n" +
            "\"userLoginName\": \"sjzxiaomi2\",\n" +
            "\"userName\": \"石家庄_小米2\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222202\",\n" +
            "\"userLoginName\": \"sjzxiaomi3\",\n" +
            "\"userName\": \"石家庄_小米3\"\n" +
            "}\n" +
            "],\n" +
            "\"structureEmail\": \"113@163.com\",\n" +
            "\"structureContactPhone\": \"15222222207\",\n" +
            "\"structureParentId\": \"1\",\n" +
            "\"structureAddress\": \"石家庄市桥西区新石中路113号\",\n" +
            "\"structureIsLast\": 0\n" +
            "},\n" +
            "{\n" +
            "\"structureContactName\": \"鹿泉_小米1\",\n" +
            "\"structureChildren\": [],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"鹿泉区国土资源分局\",\n" +
            "\"structureParentName\": \"石家庄市国土资源局\",\n" +
            "\"structureId\": \"5\",\n" +
            "\"structurePeople\": [\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222200\",\n" +
            "\"userLoginName\": \"sjzxiaomi1\",\n" +
            "\"userName\": \"石家庄_小米1\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222201\",\n" +
            "\"userLoginName\": \"sjzxiaomi2\",\n" +
            "\"userName\": \"石家庄_小米2\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222202\",\n" +
            "\"userLoginName\": \"sjzxiaomi3\",\n" +
            "\"userName\": \"石家庄_小米3\"\n" +
            "}\n" +
            "],\n" +
            "\"structureEmail\": \"114@163.com\",\n" +
            "\"structureContactPhone\": \"15222222208\",\n" +
            "\"structureParentId\": \"1\",\n" +
            "\"structureAddress\": \"石家庄市桥西区新石中路114号\",\n" +
            "\"structureIsLast\": 0\n" +
            "}\n" +
            "],\n" +
            "\"structureLevel\": 1,\n" +
            "\"structureName\": \"石家庄市国土资源局\",\n" +
            "\"structureId\": \"1\",\n" +
            "\"structurePeople\": [\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222200\",\n" +
            "\"userLoginName\": \"sjzxiaomi1\",\n" +
            "\"userName\": \"石家庄_小米1\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222201\",\n" +
            "\"userLoginName\": \"sjzxiaomi2\",\n" +
            "\"userName\": \"石家庄_小米2\"\n" +
            "},\n" +
            "{\n" +
            "\"userSex\": 1,\n" +
            "\"userState\": 1,\n" +
            "\"userPhone\": \"15222222202\",\n" +
            "\"userLoginName\": \"sjzxiaomi3\",\n" +
            "\"userName\": \"石家庄_小米3\"\n" +
            "}\n" +
            "],\n" +
            "\"structureEmail\": \"110@163.com\",\n" +
            "\"structureContactPhone\": \"15222222200\",\n" +
            "\"structureParentId\": \"0\",\n" +
            "\"structureAddress\": \"石家庄市桥西区新石中路110号\",\n" +
            "\"structureIsLast\": 0\n" +
            "}\n" +
            "}";
}
