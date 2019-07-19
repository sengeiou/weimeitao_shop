package com.wmtc.wmtb.base;

import android.util.ArrayMap;

import com.wmtc.wmtb.ui.fragment.AppointFragment;
import com.wmtc.wmtb.ui.fragment.MainFragment;
import com.wmtc.wmtb.ui.fragment.MeFragment;
import com.wmtc.wmtb.ui.fragment.W7Fragment;

import java.util.Map;

import top.jplayer.baseprolibrary.ui.fragment.SuperBaseFragment;
import top.jplayer.baseprolibrary.ui.fragment.TestFragment;

/**
 * Created by Obl on 2019/3/25.
 * com.wmtc.wmtb.base
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FragmentFactory {
    private static FragmentFactory mFactory;

    public static FragmentFactory instance() {
        if (mFactory == null) {
            mFactory = new FragmentFactory();
        }
        return mFactory;
    }

    private TestFragment mHomeFragment;

    public TestFragment singleFirstFragment() {
        if (mHomeFragment == null) {
            mHomeFragment = new TestFragment();
        }
        return mHomeFragment;
    }

    private Map<Integer, SuperBaseFragment> map = new ArrayMap<>();

    public SuperBaseFragment getSingleFragment(int position) {
        SuperBaseFragment fragment;
        if (map.containsKey(position)) {
            return map.get(position);
        }
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new AppointFragment();
                break;
            case 2:
                fragment = new TestFragment();
                break;
            case 3:
                fragment = new W7Fragment();
                break;
            default:
                fragment = new MeFragment();
                break;
        }
        map.put(position, fragment);
        return fragment;
    }
}
