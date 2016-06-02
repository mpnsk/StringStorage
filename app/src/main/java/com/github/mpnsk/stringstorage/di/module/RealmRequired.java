package com.github.mpnsk.stringstorage.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RealmRequired {
    Context context;

    public RealmRequired(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfiguration) {
        return Realm.getInstance(realmConfiguration);
    }


    @Provides
    Context provideContext() {
        return context;
    }

}
