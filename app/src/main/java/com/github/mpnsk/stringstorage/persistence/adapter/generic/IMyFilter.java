package com.github.mpnsk.stringstorage.persistence.adapter.generic;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.RealmCollection;
import io.realm.RealmModel;

public interface IMyFilter<T extends RealmModel, U> {
    List<U> performRealmFiltering(@NonNull CharSequence constraint, RealmCollection<T> results);
}
