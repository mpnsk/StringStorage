package com.github.mpnsk.stringstorage.persistence.adapter.generic;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmModel;
import io.realm.RealmResults;

public abstract class FilterableRealmBaseAdapter<T extends RealmModel> extends ArrayAdapter<String> implements Filterable {

    private final RealmResults<T> mRealmObjectList;
    private List<String> mResults;
    private String filteringFor;

    public FilterableRealmBaseAdapter(Context context, @LayoutRes int layout, OrderedRealmCollection<T> data, String filteringFor) {
        super(context, layout);
        mRealmObjectList = data.where().findAll();
        this.filteringFor = filteringFor;
    }

    @Override
    public int getCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public String getItem(int position) {
        return mResults == null ? null : mResults.get(position);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            private boolean mHasResults = false;

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                // do nothing here because it's executed in another thread and Realm really
                // doesn't like treating data from another thread.
                final FilterResults results = new FilterResults();
                results.count = mHasResults ? 1 : 0; // AutoCompleteTextView already hides dropdown here if count is 0, so correct it.
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // back on the main thread, we can do the query and notify
                if (constraint != null) {
                    mResults = performRealmFiltering(constraint, mRealmObjectList);
                    mHasResults = mResults.size() > 0;
                    notifyDataSetChanged();
                }
            }
        };
    }

    protected abstract List<String> performRealmFiltering(@NonNull CharSequence constraint, RealmResults<T> results);
}
