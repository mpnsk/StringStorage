package com.github.mpnsk.stringstorage;

import android.support.test.InstrumentationRegistry;

import com.github.mpnsk.stringstorage.di.DaggerTestComponent;
import com.github.mpnsk.stringstorage.di.TestComponent;
import com.github.mpnsk.stringstorage.di.module.RealmRequired;
import com.github.mpnsk.stringstorage.di.module.RealmTest;

public class TestMyApplication extends MyApplication {
    TestComponent component;


//        super(applicationClass);
//        component = DaggerTestComponent.builder()
//                .realmRequired(new RealmRequired(InstrumentationRegistry.getTargetContext()))
//                .realmTest(new RealmTest())
//                .build();


    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerTestComponent.builder()
                .realmRequired(new RealmRequired(InstrumentationRegistry.getTargetContext()))
                .realmTest(new RealmTest())
                .build();
    }

    @Override
    public TestComponent getComponent() {
        return component;
    }
}
