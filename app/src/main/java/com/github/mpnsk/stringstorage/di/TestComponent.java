package com.github.mpnsk.stringstorage.di;

import com.github.mpnsk.stringstorage.EditQueryActivity;
import com.github.mpnsk.stringstorage.di.module.RealmTest;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

@Singleton
@Component(modules = {RealmTest.class})
public interface TestComponent extends ProductionComponent {
    void inject(EditQueryActivity activity);

    Realm realm();
}
