package com.wmtc.wmtb.ui.activity;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.wmtc.wmtb.R;
import com.wmtc.wmtb.base.CommonWmtActivity;
import com.wmtc.wmtb.mvp.bean.ProjectSelListBean;
import com.wmtc.wmtb.mvp.event.ProSelEvent;
import com.wmtc.wmtb.mvp.pojo.ProSelPojo;
import com.wmtc.wmtb.mvp.presenter.ProSelPresenter;
import com.wmtc.wmtb.ui.adapter.ProSelAdapter;

import org.greenrobot.eventbus.EventBus;

import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;

/**
 * Created by Obl on 2019/4/3.
 * com.wmtc.wmtb.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProSelActivity extends CommonWmtActivity {

    private ProSelPresenter mPresenter;
    private int curPage = 1;
    private ProSelAdapter mAdapter;
    private ProSelPojo mPojo;
    private EditText mEditSearch;

    @Override
    public int initAddLayout() {
        return R.layout.activity_pro_sel;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mPresenter = new ProSelPresenter(this);
        showLoading();
        mPojo = new ProSelPojo();
        mPojo.setCurrentPage(curPage + "");
        mPresenter.selPro(mPojo);
        mAdapter = new ProSelAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            ++curPage;
            mPojo.setCurrentPage(curPage + "");
            mPresenter.selPro(mPojo);
        });
        mTvToolRight.setVisibility(View.INVISIBLE);
        mEditSearch = rootView.findViewById(R.id.editSearch);
        mEditSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mPojo.setProjectName(StringUtils.init().fixNullStr(mEditSearch.getText()));
                curPage = 1;
                mPojo.setCurrentPage(curPage + "");
                mPresenter.selPro(mPojo);
                KeyboardUtils.init().hideSoftInput(this);
            }
            return false;
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProjectSelListBean.DataBean.RecordsBean recordsBean = mAdapter.getData().get(position);
            EventBus.getDefault().post(new ProSelEvent(recordsBean.projectName, recordsBean.rate,
                    recordsBean.projectId, recordsBean.ppropTime));
            finish();
        });
    }

    @Override
    public void refreshStart() {
        super.refreshStart();
        curPage = 1;
        mPojo.setCurrentPage(curPage + "");
        mPojo.setProjectName("");
        mEditSearch.setText("");
        mPresenter.selPro(mPojo);
    }

    public void selPro(ProjectSelListBean bean) {
        responseSuccess();
        mSmartRefreshLayout.finishLoadMore();
        if (bean != null && bean.data != null && bean.data.records != null && bean.data.records
                .size() > 0) {
            if (curPage == 1) {
                mAdapter.setNewData(bean.data.records);
            } else {
                mAdapter.addData(bean.data.records);
            }
        } else {
            if (curPage == 1) {
                showEmpty();
            }
        }
    }
}
