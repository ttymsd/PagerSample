package com.bonboru.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bonboru.fragment.MyFragmentPagerAdapter;

public class PagerSampleActivity extends FragmentActivity {
	private MyFragmentPagerAdapter mPager;
	private ViewPager mViewPager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPager = new MyFragmentPagerAdapter(getSupportFragmentManager(), getContentResolver());
        mViewPager.setAdapter(mPager);
    }
}