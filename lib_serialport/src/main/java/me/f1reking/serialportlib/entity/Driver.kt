/*
 * Copyright 2019 F1ReKing.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.f1reking.serialportlib.entity

import android.util.Log
import java.io.File

/**
 * @author F1ReKing
 * @date 2019/10/31 18:45
 * @Description
 */
class Driver(val name: String, private val deviceRoot: String) {
    val devices: ArrayList<File>
        get() {
            val devices = ArrayList<File>()
            val dev = File("/dev")
            if (!dev.exists()) {
                Log.i(TAG, "getDevices: " + dev.absolutePath + " no found")
                return devices
            }
            if (!dev.canRead()) {
                Log.i(TAG, "getDevices: " + dev.absolutePath + " no read permissions")
                return devices
            }
            val files = dev.listFiles()
            var i = 0
            while (i < files.size) {
                if (files[i].absolutePath.startsWith(deviceRoot)) {
                    Log.d(TAG, "Found new device: " + files[i])
                    devices.add(files[i])
                }
                i++
            }
            return devices
        }

    companion object {
        private val TAG = Driver::class.java.simpleName
    }
}