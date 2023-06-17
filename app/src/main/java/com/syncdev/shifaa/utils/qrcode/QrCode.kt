package com.syncdev.shifaa.utils.qrcode

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.syncdev.domain.model.Medication

object QrCode {

    fun serializeMedicines(medicines: List<Medication>): String {
        val gson = Gson()
        return gson.toJson(medicines)
    }


    fun deserializeMedications(jsonString: String): List<Medication> {
        val gson = Gson()
        return gson.fromJson(jsonString, Array<Medication>::class.java).toList()
    }

    fun generateQRCode(data: String): Bitmap? {
        val barcodeEncoder = BarcodeEncoder()
        return try {
            barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 500, 500)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}