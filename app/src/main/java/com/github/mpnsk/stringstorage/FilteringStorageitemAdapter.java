package com.github.mpnsk.stringstorage;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

/**
 * Created by topsykrett on 29.05.16.
 */
public class FilteringStorageitemAdapter extends FilterableRealmBaseAdapter {

    public FilteringStorageitemAdapter(Context context, OrderedRealmCollection<Storageitem> data) {
        super(context, data);
    }

    @Override
    protected List<Storageitem> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<Storageitem> results) {

        RealmResults<Storageitem> r = results.where().contains("description", constraint.toString()).findAll();
        return r;
    }
}
