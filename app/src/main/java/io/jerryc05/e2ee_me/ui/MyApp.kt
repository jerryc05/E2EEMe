package io.jerryc05.e2ee_me.ui

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.jerryc05.e2ee_me.core.weakActivity
import java.lang.ref.WeakReference

class MyApp : Application(), Application.ActivityLifecycleCallbacks {
  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(this)
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    weakActivity = WeakReference(activity)
  }

  override fun onActivityStarted(activity: Activity) {
  }

  override fun onActivityResumed(activity: Activity) {
  }

  override fun onActivityPaused(activity: Activity) {
  }

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
  }

  override fun onActivityStopped(activity: Activity) {
  }

  override fun onActivityDestroyed(activity: Activity) {
  }
}