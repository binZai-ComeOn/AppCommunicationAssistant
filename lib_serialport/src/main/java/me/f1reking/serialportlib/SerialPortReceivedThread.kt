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

import java.io.IOException
import java.io.InputStream

/**
 * @author F1ReKing
 * @date 2019/11/1 11:43
 * @Description
 */
abstract class SerialPortReceivedThread(private var mInputStream: InputStream?) : Thread() {
    private val mReceivedBuffer: ByteArray = ByteArray(1024)

    override fun run() {
        super.run()
        while (!isInterrupted) {
            try {
                if (null == mInputStream) {
                    return
                }
                val size = mInputStream!!.read(mReceivedBuffer)
                if (0 >= size) {
                    return
                }
                val receivedBytes = ByteArray(size)
                System.arraycopy(mReceivedBuffer, 0, receivedBytes, 0, size)
                onDataReceived(receivedBytes)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    abstract fun onDataReceived(bytes: ByteArray?)

    /**
     * 释放
     */
    fun release() {
        interrupt()
        if (null != mInputStream) {
            try {
                mInputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mInputStream = null
        }
    }

    companion object {
        private val TAG = SerialPortReceivedThread::class.java.simpleName
    }
}