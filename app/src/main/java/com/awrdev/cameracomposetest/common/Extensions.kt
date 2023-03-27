package com.awrdev.cameracomposetest.common

import android.content.Context
import androidx.datastore.dataStore
import com.awrdev.cameracomposetest.AppSettingsSerializer

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)