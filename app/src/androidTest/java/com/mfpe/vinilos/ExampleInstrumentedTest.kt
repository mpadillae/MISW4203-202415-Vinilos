package com.mfpe.vinilos

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mfpe.vinilos.utils.PrefsManager

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.lang.Thread.sleep

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        PrefsManager.getInstance(InstrumentationRegistry.getInstrumentation().targetContext).clear();
        sleep(5000)
        assertEquals("com.mfpe.vinilos", appContext.packageName)
    }
}