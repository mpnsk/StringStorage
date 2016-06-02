package com.github.mpnsk.stringstorage.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

@Module(includes = {RealmRequired.class})
public class RealmProduction {
    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(Context context) {
        return new RealmConfiguration.Builder(context).name("default.realm").build();
    }

}
