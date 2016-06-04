package com.github.mpnsk.stringstorage.persistence.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.mpnsk.stringstorage.persistence.Storageitem;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class FilteringForNameStorageitemAdapter extends FilterableRealmListAdapter {

    public FilteringForNameStorageitemAdapter(Context context, OrderedRealmCollection<Storageitem> data) {
        super(context, data);
    }

    @Override
    protected List<Storageitem> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<Storageitem> results) {

        RealmResults<Storageitem> r = results.where().contains("description", constraint.toString()).findAll();
        return r;
    }
}
