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
package me.f1reking.serialportlib

import android.util.Log
import me.f1reking.serialportlib.entity.Device
import me.f1reking.serialportlib.entity.Driver
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.LineNumberReader
import java.util.Vector

/**
 * @author F1ReKing
 * @date 2019/10/31 18:41
 * @Description
 */
class SerialPortFinder {
    init {
        val file = File(DRIVERS_PATH)
        val b = file.canRead()
        Log.i(TAG, "SerialPortFinder: file.canRead() = $b")
    }

    @get:Throws(IOException::class)
    private val drivers: ArrayList<Driver>
        /**
         * get Drivers
         *
         * @return Drivers
         * @throws IOException IOException
         */
        get() {
            val drivers = ArrayList<Driver>()
            val lineNumberReader = LineNumberReader(FileReader(DRIVERS_PATH))
            var readLine: String
            while (lineNumberReader.readLine().also { readLine = it } != null) {
                val driverName = readLine.substring(0, 0x15).trim { it <= ' ' }
                val fields =
                    readLine.split(" +".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (fields.size >= 5 && fields[fields.size - 1] == SERIAL_FIELD) {
                    Log.d(TAG, "Found new driver " + driverName + " on " + fields[fields.size - 4])
                    drivers.add(Driver(driverName, fields[fields.size - 4]))
                }
            }
            return drivers
        }

    @get:Deprecated("", ReplaceWith(
        "allDevices as ArrayList<Device?>",
        "me.f1reking.serialportlib.entity.Device"
    )
    )
    val devices: ArrayList<Device?>
        /**
         * Use [.getAllDevices] instead.
         *
         * @return serialPort
         */
        get() = allDevices as ArrayList<Device?>
    val allDevices: List<Device?>
        /**
         * get serialPort devices
         *
         * @return serialPort
         */
        get() {
            val devices: MutableList<Device?> = ArrayList()
            try {
                val drivers: List<Driver> = drivers
                for (driver in drivers) {
                    val driverName = driver.name
                    val driverDevices: List<File> = driver.devices
                    for (file in driverDevices) {
                        val devicesName = file.name
                        devices.add(Device(devicesName, driverName, file))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return devices
        }
    val allDeicesPath: Array<String>
        /**
         * get serialPort devices path
         *
         * @return serialPort path
         */
        get() {
            val paths = Vector<String>()
            val drivers: Iterator<Driver>
            try {
                drivers = this.drivers.iterator()
                while (drivers.hasNext()) {
                    val driver = drivers.next()
                    val files: Iterator<File> = driver.devices.iterator()
                    while (files.hasNext()) {
                        val devicesPaths = files.next().absolutePath
                        paths.add(devicesPaths)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return paths.toTypedArray()
        }

    companion object {
        private val TAG = SerialPortFinder::class.java.simpleName
        private const val DRIVERS_PATH = "/proc/tty/drivers"
        private const val SERIAL_FIELD = "serial"
    }
}