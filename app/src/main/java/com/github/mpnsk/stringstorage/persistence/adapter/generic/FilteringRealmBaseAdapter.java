package com.github.mpnsk.stringstorage.persistence.adapter.generic;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.github.mpnsk.stringstorage.persistence.Storageitem;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class FilteringRealmBaseAdapter<T extends RealmModel> extends FilterableRealmBaseAdapter<T> {
    private final Context context;
    @LayoutRes
    private final int layout;
    private String filteringFor;

    public FilteringRealmBaseAdapter(Context context, @LayoutRes int layout, OrderedRealmCollection data, String filteringFor) {
        super(context, layout, data, filteringFor);
        this.context = context;
        this.layout = layout;
        this.filteringFor = filteringFor.toLowerCase();
    }

    protected List<String> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results) {
        List<String> filteredList = new ArrayList<>();

        RealmResults list = results.where().contains(filteringFor, constraint.toString()).findAll();
        for (Object t : list) {
            if (filteringFor.equalsIgnoreCase("location")) {
                filteredList.add(((Storageitem) t).getLocation());
            } else {
                filteredList.add(((Storageitem) t).getDescription());

            }
        }
        return filteredList;
    }
}


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
//        TextView text;
//
//        if (convertView == null) {
//            LayoutInflater inflater = ((Activity) parent.getContext()).getLayoutInflater();
//            view = inflater.inflate(layout, parent, false);
//        } else {
//            view = convertView;
//        }
//
//        text = (TextView) view;
//
//        Storageitem item = getItem(position);
//        if (filteringFor.equals("location")) {
//            text.setText(item.getLocation());
//        } else {
//            text.setText(item.getDescription());
//        }
//
//        return view;
//
//
//    }



