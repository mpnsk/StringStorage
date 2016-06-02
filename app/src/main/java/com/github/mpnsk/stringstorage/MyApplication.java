package com.github.mpnsk.stringstorage;

import android.app.Application;

import com.github.mpnsk.stringstorage.di.DaggerProductionComponent;
import com.github.mpnsk.stringstorage.di.ProductionComponent;
import com.github.mpnsk.stringstorage.di.module.RealmProduction;
import com.github.mpnsk.stringstorage.di.module.RealmRequired;

public class MyApplication extends Application {
    ProductionComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerProductionComponent.builder()
                .realmRequired(new RealmRequired(getApplicationContext()))
                .realmProduction(new RealmProduction())
                .build();
    }

    public ProductionComponent getComponent() {
        return component;
    }
}
