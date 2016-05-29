package com.github.mpnsk.stringstorage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class StorageitemListAdapter extends RealmBaseAdapter<Storageitem> implements ListAdapter {
    public StorageitemListAdapter(Context context, OrderedRealmCollection<Storageitem> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.linearlist, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.itemLocation = (TextView) convertView.findViewById(R.id.item_location);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Storageitem item = adapterData.get(position);
        Storageitem item = getItem(position);
        viewHolder.itemLocation.setText(item.getLocation());
        viewHolder.itemName.setText(item.getDescription());
        return convertView;
    }

    private static class ViewHolder {
        TextView itemName;
        TextView itemLocation;
    }
}
