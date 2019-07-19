package com.wmtc.wmtb.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonPresenterActivity;
import com.wmtc.wmtb.mvp.bean.ShopsBean;
import com.wmtc.wmtb.ui.adapter.SearchShopAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

import static com.wmtc.wmtb.base.WmtApplication.mActivityArrayList;

/**
 * Created by Obl on 2019/3/23.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SearchShopActivity extends CommonPresenterActivity {
    @BindView(R.id.viewToolBar)
    View mViewToolBar;
    @BindView(R.id.ivLoginBack)
    ImageView mIvLoginBack;
    @BindView(R.id.tvTitleTip)
    TextView mTvTitleTip;
    @BindView(R.id.editSearch)
    EditText mEditSearch;
    @BindView(R.id.tvSearch)
    TextView mTvSearch;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.btnNext)
    Button mBtnNext;
    @BindView(R.id.tvRegisterT)
    TextView mTvRegisterT;
    @BindView(R.id.line1)
    View mLine1;
    @BindView(R.id.tvDoorT)
    TextView mTvDoorT;
    @BindView(R.id.line2)
    View mLine2;
    @BindView(R.id.tvPushT)
    TextView mTvPushT;
    @BindView(R.id.llShopTip)
    LinearLayout mLlShopTip;
    private Unbinder mUnbinder;
    private List<Tip> mTipList;
    private SearchShopAdapter mAdapter;
    private String mCity;
    private Map<String, String> mStringMap;
    private ShopsBean.DataBean mShopBean;

    @Override
    protected int initRootLayout() {
        return R.layout.activity_search_shop;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(mViewToolBar).init();
    }

    @Override
    public void initRootData(View view) {
        super.initRootData(view);
        mActivityArrayList.add(this);
        mUnbinder = ButterKnife.bind(this, view);
        mTipList = new ArrayList<>();
        mCity = mBundle.getString("city");
        String json = mBundle.getString("json");
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        mStringMap = new Gson().fromJson(json, type);
        mAdapter = new SearchShopAdapter(mTipList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mTvSearch.setOnClickListener(v -> {
            if (StringUtils.init().isEmpty(mEditSearch)) {
                ToastUtils.init().showInfoToast(mActivity, "请输入门店名进行搜索");
                return;
            }
            String keySearch = StringUtils.init().fixNullStr(mEditSearch.getText());
            toSearch(keySearch, mCity);
        });
        mAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            Tip tip = mAdapter.getData().get(position);
            if (view1.getId() == R.id.tvSelect) {
                mStringMap.put("address", tip.getAddress());
                LatLonPoint point = tip.getPoint();
                if (point != null) {
                    mStringMap.put("location", point.getLongitude() + "," + point.getLatitude());
                }
                mStringMap.put("shopstatus", "在营业");
                mStringMap.put("shopName", tip.getName());
                LogUtil.e(mStringMap);
                Bundle bundle = new Bundle();
                bundle.putString("json", new Gson().toJson(mStringMap));
                ActivityUtils.init().start(mActivity, CreateShopActivity.class, "", bundle);
            }
            return false;
        });
        mIvLoginBack.setOnClickListener(v -> {
            finish();
        });
        mBtnNext.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("json", new Gson().toJson(mStringMap));
            bundle.putParcelable("bean", mBundle.getParcelable("bean"));
            ActivityUtils.init().start(mActivity, CreateShopByInputActivity.class, "", bundle);
        });
    }

    @SuppressLint("CheckResult")
    private void toSearch(String keySearch, String city) {
        InputtipsQuery inputtipsQuery = new InputtipsQuery(keySearch, city);
        inputtipsQuery.setCityLimit(true);
        Inputtips inputtips = new Inputtips(mActivity, inputtipsQuery);
        try {
            Observable.just(inputtips)
                    .subscribeOn(Schedulers.io())
                    .map(Inputtips::requestInputtips)
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(tips -> {
                LogUtil.e(tips);
                mAdapter.setNewData(tips);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mActivityArrayList.remove(this);
    }
}
