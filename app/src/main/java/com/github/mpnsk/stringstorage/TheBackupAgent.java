package com.github.mpnsk.stringstorage;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.util.Log;

public class TheBackupAgent extends BackupAgentHelper {
    // The names of the SharedPreferences groups that the application maintains.  These
    // are the same strings that are passed to getSharedPreferences(String, int).
//    static final String PREFS_DISPLAY = "displayprefs";
//    static final String PREFS_SCORES = "highscores";
    static final String PREFS_STRINGS = "com.github.mpnsk.stringstorage.STRINGSFILEKEY";

    // An arbitrary string used within the BackupAgentHelper implementation to
    // identify the SharedPreferencesBackupHelper's data.
    static final String MY_PREFS_BACKUP_KEY = "myprefs";

    // Simply allocate a helper and install it
    public void onCreate() {
        Log.d(Util.logKey, "TheBackupAgent.onCreate()");
        SharedPreferencesBackupHelper helper =
                new SharedPreferencesBackupHelper(this, PREFS_STRINGS);
        addHelper(MY_PREFS_BACKUP_KEY, helper);
    }

    public void requestBackup(Context context) {
        Log.d(Util.logKey, "requestBackup()");
        BackupManager bm = new BackupManager(context);
        bm.dataChanged();
    }

    public int requestRestore(Context context) {
        Log.d(Util.logKey, "requestRestore()");
        BackupManager bm = new BackupManager(context);
        return bm.requestRestore(new RestoreObserver() {
            /**
             * The restore operation has begun.
             *
             * @param numPackages The total number of packages being processed in
             *                    this restore operation.
             */
            @Override
            public void restoreStarting(int numPackages) {
                super.restoreStarting(numPackages);
                Log.d(Util.logKey, "restoreStarting(), "
                        + "total number of packages to restore: "
                        + Integer.toString(numPackages)
                );
            }

            /**
             * An indication of which package is being restored currently, out of the
             * total number provided in the {@link #restoreStarting(int)} callback.  This method
             * is not guaranteed to be called: if the transport is unable to obtain
             * data for one or more of the requested packages, no onUpdate() call will
             * occur for those packages.
             *
             * @param nowBeingRestored The index, between 1 and the numPackages parameter
             *                         to the {@link #restoreStarting(int)} callback, of the package now being
             *                         restored.  This may be non-monotonic; it is intended purely as a rough
             *                         indication of the backup manager's progress through the overall restore process.
             * @param currentPackage   The name of the package now being restored.
             */
            @Override
            public void onUpdate(int nowBeingRestored, String currentPackage) {
                super.onUpdate(nowBeingRestored, currentPackage);
                Log.d(Util.logKey, "package of prefs being restored: "
                        + currentPackage
                        + " ("
                        + Integer.toString(nowBeingRestored)
                        + ")"
                );
            }

            /**
             * The restore process has completed.  This method will always be called,
             * even if no individual package restore operations were attempted.
             *
             * @param error Zero on success; a nonzero error code if the restore operation
             *              as a whole failed.
             */
            @Override
            public void restoreFinished(int error) {
                super.restoreFinished(error);
                Log.d(Util.logKey, "restoreFinished(int error)"
                        + " error = "
                        + Integer.toString(error)
                );
            }
        });
    }

}
