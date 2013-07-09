package com.zelic.tabfragment;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment01 extends Fragment {
	DrawerLayout drawer;
	LinearLayout content;
	TextView trigger;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab01, container, false);
		drawer = (DrawerLayout) view.findViewById(R.id.drawer);
		content = (LinearLayout) view.findViewById(R.id.content);
		trigger = (TextView) view.findViewById(R.id.trigger);

		ListView menu = (ListView) view.findViewById(R.id.menu);
		String[] menuArray = getResources().getStringArray(R.array.menu);
		ArrayList<String> items = new ArrayList<String>(
				Arrays.asList(menuArray));

		menu.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, items));

		trigger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!drawer.isDrawerOpen(content)) {
					drawer.openDrawer(content);
				} else {
					drawer.closeDrawer(content);
				}
			}
		});

		drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
			}

		});

		return view;
	}

}
