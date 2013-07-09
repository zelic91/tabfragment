package com.zelic.tabfragment;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;

public class MainActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener {

	Button tab01;
	Button tab02;
	Button tab03;
	TabHost tabHost;
	TabInfo lastTab;
	Map<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tab01 = (Button) findViewById(R.id.tab01);
		tab02 = (Button) findViewById(R.id.tab02);
		tab03 = (Button) findViewById(R.id.tab03);
		tab01.setOnClickListener(new TabButtonOnClickListener());
		tab02.setOnClickListener(new TabButtonOnClickListener());
		tab03.setOnClickListener(new TabButtonOnClickListener());
		initTabs();
	}

	protected void initTabs() {
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		TabInfo temp;
		addTab(tabHost.newTabSpec("tab01").setIndicator("tab01"),
				(temp = new TabInfo("tab01", Fragment01.class)));
		mapTabInfo.put(temp.name, temp);
		addTab(tabHost.newTabSpec("tab02").setIndicator("tab02"),
				(temp = new TabInfo("tab02", Fragment02.class)));
		mapTabInfo.put(temp.name, temp);
		addTab(tabHost.newTabSpec("tab03").setIndicator("tab03"),
				(temp = new TabInfo("tab03", Fragment03.class)));
		mapTabInfo.put(temp.name, temp);
		onTabChanged("tab01");
		tabHost.setOnTabChangedListener(this);
	}

	protected void addTab(TabHost.TabSpec tabSpec, TabInfo info) {
		tabSpec.setContent(new TabFragmentFactory(this));
		String tag = tabSpec.getTag();

		// Remove any fragment with the tag
		info.fragment = getSupportFragmentManager().findFragmentByTag(tag);
		if (info.fragment != null && !info.fragment.isDetached()) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.detach(info.fragment);
			transaction.commit();
			getSupportFragmentManager().executePendingTransactions();
		}

		tabHost.addTab(tabSpec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onTabChanged(String tabId) {
		TabInfo info = mapTabInfo.get(tabId);
		if (lastTab != info) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (lastTab != null) {
				if (lastTab.fragment != null && !lastTab.fragment.isDetached()) {
					transaction.detach(lastTab.fragment);
				}
			}

			if (info != null) {
				if (info.fragment == null) {
					info.fragment = Fragment.instantiate(this,
							info.cls.getName());
					transaction.add(R.id.realtabcontent, info.fragment, info.name);
				} else {
					transaction.attach(info.fragment);
				}
			}
			lastTab = info;
			transaction.commit();
			getSupportFragmentManager().executePendingTransactions();
		}

	}

	class TabInfo {
		public String name;
		public Fragment fragment;
		public Class cls;

		public TabInfo(String name, Class cls) {
			super();
			this.name = name;
			this.cls = cls;
		}

	}

	class TabFragmentFactory implements TabContentFactory {

		private final Context context;

		public TabFragmentFactory(Context context) {
			super();
			this.context = context;
		}

		@Override
		public View createTabContent(String tag) {
			View v = new View(context);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	class TabButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.tab01:
				onTabChanged("tab01");
				break;

			case R.id.tab02:
				onTabChanged("tab02");
				break;
			case R.id.tab03:
				onTabChanged("tab03");
				break;
			}

		}

	}

}
