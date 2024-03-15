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

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import me.f1reking.serialportlib.entity.Device
import me.f1reking.serialportlib.listener.IOpenSerialPortListener
import me.f1reking.serialportlib.listener.ISerialPortDataListener
import me.f1reking.serialportlib.listener.Status
import me.f1reking.serialportlib.util.ByteUtils
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author F1ReKing
 * @date 2019/11/1 09:38
 * @Description
 */
class SerialPortHelper {
    private var mIOpenSerialPortListener: IOpenSerialPortListener? = null
    private var mISerialPortDataListener: ISerialPortDataListener? = null
    private var mSendingHandlerThread: HandlerThread? = null
    private var mSendingHandler: Handler? = null
    private var mSerialPortReceivedThread: SerialPortReceivedThread? = null
    private var mSerialPortFinder: SerialPortFinder? = null
    private var mFD: FileDescriptor? = null
    private var mFileInputStream: FileInputStream? = null
    private var mFileOutputStream: FileOutputStream? = null

    /**
     * 串口状态
     * @return true:打开 false:关闭
     */
    //是否打开串口标志
    var isOpen = false
        private set

    val allDeicesPath: Array<String>
        /**
         * 获得所有串口设备的地址
         *
         * @return 所有串口设备的地址
         */
        get() {
            if (mSerialPortFinder == null) {
                mSerialPortFinder = SerialPortFinder()
            }
            return mSerialPortFinder!!.allDeicesPath
        }
    val allDevices: List<Device?>
        /**
         * 获取所有串口设备
         *
         * @return 所有串口设备
         */
        get() {
            if (mSerialPortFinder == null) {
                mSerialPortFinder = SerialPortFinder()
            }
            return mSerialPortFinder!!.allDevices
        }

    /**
     * 打开串口
     *
     * @return 串口打开状态 true:打开 false：打开失败
     */
    fun open(): Boolean {
        return openSerialPort(File(port), baudRate, stopBits, dataBits, parity, flowCon, flags)
    }

    /**
     * 关闭串口
     */
    fun close() {
        closeSerialPort()
    }

    fun setPort(port: String): Boolean {
        if (isOpen) {
            return false
        }
        Companion.port = port
        return true
    }

    fun setBaudRate(baudRate: Int): Boolean {
        if (isOpen) {
            return false
        }
        Companion.baudRate = baudRate
        return true
    }

    fun setDataBits(dataBits: Int): Boolean {
        if (isOpen) {
            return false
        }
        Companion.dataBits = dataBits
        return true
    }

    fun setStopBits(stopBits: Int): Boolean {
        if (isOpen) {
            return false
        }
        Companion.stopBits = stopBits
        return true
    }

    fun setParity(parity: Int): Boolean {
        if (isOpen) {
            return false
        }
        Companion.parity = parity
        return true
    }

    fun setFlowCon(flowCon: Int): Boolean {
        if (isOpen) {
            return false
        }
        Companion.flowCon = flowCon
        return true
    }

    fun setFlags(flags: Int): Boolean {
        if (isOpen) {
            return false
        }
        Companion.flags = flags
        return true
    }

    class Builder(port: String, baudRate: Int) {
        init {
            Companion.port = port
            Companion.baudRate = baudRate
        }

        fun setStopBits(stopBits: Int): Builder {
            Companion.stopBits = stopBits
            return this
        }

        fun setDataBits(dataBits: Int): Builder {
            Companion.dataBits = dataBits
            return this
        }

        fun setParity(parity: Int): Builder {
            Companion.parity = parity
            return this
        }

        fun setFlowCon(flowCon: Int): Builder {
            Companion.flowCon = flowCon
            return this
        }

        fun setFlags(flags: Int): Builder {
            Companion.flags = flags
            return this
        }

        fun build(): SerialPortHelper {
            return SerialPortHelper()
        }
    }

    /**
     * 发送数据
     *
     * @param bytes 发送的字节
     * @return 发送状态 true:发送成功 false：发送失败
     */
    fun sendBytes(bytes: ByteArray?): Boolean {
        if (null != mSendingHandler) {
            val message = Message.obtain()
            message.obj = bytes
            return mSendingHandler!!.sendMessage(message)
        }
        return false
    }

    /**
     * 发送Hex
     *
     * @param hex 16进制文本
     */
    fun sendHex(hex: String) {
        val hexArray = ByteUtils.hexToByteArr(hex)
        sendBytes(hexArray)
    }

    /**
     * 发送文本
     *
     * @param txt 文本
     */
    fun sendTxt(txt: String) {
        val txtArray = txt.toByteArray()
        sendBytes(txtArray)
    }

    /**
     * 设置串口打开的监听
     *
     * @param iOpenSerialPortListener 监听
     */
    fun setIOpenSerialPortListener(iOpenSerialPortListener: IOpenSerialPortListener?) {
        mIOpenSerialPortListener = iOpenSerialPortListener
    }

    /**
     * 设置串口数据收发的监听
     *
     * @param iSerialPortDataListener 监听
     */
    fun setISerialPortDataListener(iSerialPortDataListener: ISerialPortDataListener?) {
        mISerialPortDataListener = iSerialPortDataListener
    }

    /**
     * 打开串口
     *
     * @param device 串口设备的绝对路径
     * @param baudRate 波特率
     * @param stopBits 停止位
     * @param dataBits 数据位
     * @param parity 校验位
     * @param flowCon 流控
     * @param flags O_RDWR  读写方式打开 | O_NOCTTY  不允许进程管理串口 | O_NDELAY   非阻塞
     * @return 打开状态
     */
    private fun openSerialPort(
        device: File,
        baudRate: Int,
        stopBits: Int,
        dataBits: Int,
        parity: Int,
        flowCon: Int,
        flags: Int,
    ): Boolean {
        isOpen = openSafe(device, baudRate, stopBits, dataBits, parity, flowCon, flags)
        return isOpen
    }

    /**
     * 关闭串口
     */
    private fun closeSerialPort() {
        stopSendThread()
        stopReceivedThread()
        closeSafe()
        isOpen = false
    }

    /**
     * 开启发送消息线程
     */
    private fun startSendThread() {
        mSendingHandlerThread = HandlerThread("mSendingHandlerThread")
        mSendingHandlerThread!!.start()
        mSendingHandler = object : Handler(mSendingHandlerThread!!.looper) {
            override fun handleMessage(msg: Message) {
                val sendBytes = msg.obj as ByteArray
                if (null != mFileOutputStream && sendBytes.isNotEmpty()) {
                    try {
                        mFileOutputStream!!.write(sendBytes)
                        if (null != mISerialPortDataListener) {
                            mISerialPortDataListener!!.onDataSend(sendBytes)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    /**
     * 停止发送消息线程
     */
    private fun stopSendThread() {
        mSendingHandler = null
        if (null != mSendingHandlerThread) {
            mSendingHandlerThread!!.interrupt()
            mSendingHandlerThread!!.quit()
            mSendingHandlerThread = null
        }
    }

    /**
     * 开启接收消息的线程
     */
    private fun startReceivedThread() {
        mSerialPortReceivedThread = object : SerialPortReceivedThread(mFileInputStream) {
            override fun onDataReceived(bytes: ByteArray?) {
                if (null != mISerialPortDataListener) {
                    mISerialPortDataListener!!.onDataReceived(bytes)
                }
            }
        }
        mSerialPortReceivedThread!!.start()
    }

    /**
     * 停止接收消息的线程
     */
    private fun stopReceivedThread() {
        if (null != mSerialPortReceivedThread) {
            mSerialPortReceivedThread!!.release()
        }
    }

    private fun openSafe(
        device: File,
        baudRate: Int,
        stopBits: Int,
        dataBits: Int,
        parity: Int,
        flowCon: Int,
        flags: Int,
    ): Boolean {
        Log.i(
            TAG,
            String.format(
                "SerialPort: %s: %d,%d,%d,%d,%d,%d",
                device.path,
                baudRate,
                stopBits,
                dataBits,
                parity,
                flowCon,
                flags
            )
        )
        if (!device.canRead() || !device.canWrite()) {
            val chmod777 = chmod777(device)
            if (!chmod777) {
                Log.e(TAG, device.path + " : 没有读写权限")
                if (null != mIOpenSerialPortListener) {
                    mIOpenSerialPortListener!!.onFail(device, Status.NO_READ_WRITE_PERMISSION)
                }
                return false
            }
        }
        try {
            mFD = nativeOpen(
                device.absolutePath,
                baudRate,
                stopBits,
                dataBits,
                parity,
                flowCon,
                flags
            )
            mFileInputStream = FileInputStream(mFD)
            mFileOutputStream = FileOutputStream(mFD)
            startSendThread()
            startReceivedThread()
            if (null != mIOpenSerialPortListener) {
                mIOpenSerialPortListener!!.onSuccess(device)
            }
            Log.i(TAG, device.path + " : 串口已经打开")
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (null != mIOpenSerialPortListener) {
                mIOpenSerialPortListener!!.onFail(device, Status.OPEN_FAIL)
            }
        }
        return false
    }

    private fun closeSafe() {
        if (null != mFD) {
            nativeClose()
            mFD = null
        }
        if (null != mFileInputStream) {
            try {
                mFileInputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mFileInputStream = null
        }
        if (null != mFileOutputStream) {
            try {
                mFileOutputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mFileOutputStream = null
        }
    }

    /**
     * 检查文件权限
     *
     * @param device 文件
     * @return 权限修改是否成功
     */
    private fun chmod777(device: File?): Boolean {
        if (null == device || !device.exists()) {
            return false
        }
        try {
            val su = Runtime.getRuntime().exec("/system/bin/su")
            val cmd = """
                chmod 777${device.absolutePath}
                exit
                
                """.trimIndent()
            su.outputStream.write(cmd.toByteArray())
            if (0 == su.waitFor() && device.canRead() && device.canWrite() && device.canExecute()) {
                return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 关闭串口
     */
    external fun nativeClose()

    companion object {
        private val TAG = SerialPortHelper::class.java.simpleName

        init {
            System.loadLibrary("serialport")
        }

        //串口设置默认值
        var port = "/dev/ttyUSB0"
            private set

        //波特率默认值
        var baudRate = 115200
            private set

        //停止位默认值
        var stopBits = 2
            private set

        //数据位默认值
        var dataBits = 8
            private set

        //校验位默认值
        var parity = 0
            private set

        //流控默认值
        var flowCon = 0
            private set
        var flags = 0
            private set

        /**
         * 打开串口
         *
         * @param path 串口设备的绝对路径
         * @param baudRate 波特率
         * @param stopBits 停止位
         * @param dataBits 数据位
         * @param parity 校验位
         * @param flowCon 流控
         * @param flags O_RDWR  读写方式打开 | O_NOCTTY  不允许进程管理串口 | O_NDELAY   非阻塞
         */
        @JvmStatic
        private external fun nativeOpen(
            path: String,
            baudRate: Int,
            stopBits: Int,
            dataBits: Int,
            parity: Int,
            flowCon: Int,
            flags: Int,
        ): FileDescriptor?
    }
}