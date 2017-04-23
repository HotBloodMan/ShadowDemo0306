package com.example.a1.shadowdemo0306;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
//TV左侧的适配器
public class AppWallListBaseAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;

	public AppWallListBaseAdapter(Context context,List<String> list) {
		this.context = context;
		this.list=list;
	}
	@Override
	public int getCount() {

		return list.size();
	}
	@Override
	public Object getItem(int position) {

		return null;
	}
	@Override
	public long getItemId(int position) {

		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_app_wall_list, parent,false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(list.get(position));
		return convertView;
	}

	class ViewHolder {
		TextView name;
	}

}
