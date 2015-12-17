package com.keshe.contacts_keshe.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.bean.WorldPopulation;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<WorldPopulation> {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	List<WorldPopulation> worldpopulationlist;
	private SparseBooleanArray mSelectedItemsIds;

	public ListViewAdapter(Context context, int resourceId,
						   List<WorldPopulation> worldpopulationlist) {
		super(context, resourceId, worldpopulationlist);
		mSelectedItemsIds = new SparseBooleanArray();
		this.context = context;
		this.worldpopulationlist = worldpopulationlist;
		inflater = LayoutInflater.from(context);
	}

	private class ViewHolder {
		TextView rank;
		TextView country;
		TextView population;
		ImageView flag;
	}

	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.rank = (TextView) view.findViewById(R.id.rank);
			holder.country = (TextView) view.findViewById(R.id.country);
			holder.population = (TextView) view.findViewById(R.id.population);
			// Locate the ImageView in listview_item.xml
			holder.flag = (ImageView) view.findViewById(R.id.flag);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Capture position and set to the TextViews
		holder.rank.setText(worldpopulationlist.get(position).getRank());
		holder.country.setText(worldpopulationlist.get(position).getCountry());
		holder.population.setText(worldpopulationlist.get(position)
				.getPopulation());
		// Capture position and set to the ImageView
		holder.flag.setImageResource(worldpopulationlist.get(position)
				.getFlag());
		return view;
	}

	@Override
	public void remove(WorldPopulation object) {
		worldpopulationlist.remove(object);
		notifyDataSetChanged();
	}

	public List<WorldPopulation> getWorldPopulation() {
		return worldpopulationlist;
	}

	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
}