package com.github.mpnsk.stringstorage.persistence.adapter.generic;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.github.mpnsk.stringstorage.R;
import com.github.mpnsk.stringstorage.persistence.StorageItem;

import io.realm.OrderedRealmCollection;

public class FilteringRealmListAdapter extends FilteringRealmBaseAdapter<StorageItem, StorageItem> implements ListAdapter {
    public FilteringRealmListAdapter(Context context, @LayoutRes int layout, OrderedRealmCollection<StorageItem> data, IMyFilter<StorageItem, StorageItem> filter) {
        super(context, layout, data, filter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.linearlist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.location = (TextView) convertView.findViewById(R.id.item_location);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        StorageItem item = mResults.get(position);
//StorageItem item = mRealmCollection.where().findAll().get(position);
        viewHolder.name.setText(item.getDescription());
        viewHolder.location.setText(item.getLocation());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView location;
    }
}
