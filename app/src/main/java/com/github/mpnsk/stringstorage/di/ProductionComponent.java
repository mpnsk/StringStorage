package com.github.mpnsk.stringstorage.di;

import com.github.mpnsk.stringstorage.EditQueryActivity;
import com.github.mpnsk.stringstorage.di.module.RealmProduction;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

@Singleton
@Component(modules = {RealmProduction.class})
public interface ProductionComponent {
    void inject(EditQueryActivity activity);

    Realm realm();
}

