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

import android.os.Parcel
import android.os.Parcelable
import java.io.File

/**
 * @author F1ReKing
 * @date 2019/10/31 18:47
 * @Description
 */
class Device : Parcelable {
    var name: String?
    var root: String?
    var file: File?

    constructor(name: String?, root: String?, file: File?) {
        this.name = name
        this.root = root
        this.file = file
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(this.root)
        dest.writeSerializable(file)
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        this.root = `in`.readString()
        file = `in`.readSerializable() as File?
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Device> = object : Parcelable.Creator<Device> {
            override fun createFromParcel(source: Parcel): Device? {
                return Device(source)
            }

            override fun newArray(size: Int): Array<Device?> {
                return arrayOfNulls(size)
            }
        }
    }
}