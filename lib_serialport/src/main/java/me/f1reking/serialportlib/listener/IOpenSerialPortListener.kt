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
package me.f1reking.serialportlib.listener

import java.io.File

/**
 * @author F1ReKing
 * @date 2019/11/1 10:49
 * @Description 串口打开状态监听
 */
interface IOpenSerialPortListener {
    fun onSuccess(device: File?)
    fun onFail(device: File?, status: Status?)
}