package com.syncdev.shifaa.utils.qrcode

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.syncdev.domain.model.MedicalHistory
import com.syncdev.domain.model.Medication

object QrCode {

    /*    fun serializeMedicines(medicines: List<Medication>): String {
            val gson = Gson()
            return gson.toJson(medicines)
        }*/
    fun serializeMedicines(medicines: List<Medication>, type: String): String {
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("type", type)
        jsonObject.add("medications", gson.toJsonTree(medicines))

        return jsonObject.toString()
    }

    fun serializeMedicalCard(medicalHistory: MedicalHistory,type:String):String{
        val gson = Gson()
        val jsonObject = JsonObject()
        jsonObject.addProperty("type", type)
        jsonObject.add("medicalCard", gson.toJsonTree(medicalHistory))

        return jsonObject.toString()
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