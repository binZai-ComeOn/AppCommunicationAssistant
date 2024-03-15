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
package me.f1reking.serialportlib.util

/**
 * @author F1ReKing
 * @date 2019/11/1 14:08
 * @Description
 */
object ByteUtils {
    fun isOdd(num: Int): Int {
        return num and 0x1
    }

    fun HexToInt(inHex: String): Int {
        return inHex.toInt(16)
    }

    fun HexToByte(inHex: String): Byte {
        return inHex.toInt(16).toByte()
    }

    fun hexToByteArr(hex: String): ByteArray {
        var hex = hex
        var hexLen = hex.length
        val result: ByteArray
        if (isOdd(hexLen) == 1) {
            hexLen++
            result = ByteArray(hexLen / 2)
            hex = "0$hex"
        } else {
            result = ByteArray(hexLen / 2)
        }
        var j = 0
        var i = 0
        while (i < hexLen) {
            result[j] = HexToByte(hex.substring(i, i + 2))
            j++
            i += 2
        }
        return result
    }
}