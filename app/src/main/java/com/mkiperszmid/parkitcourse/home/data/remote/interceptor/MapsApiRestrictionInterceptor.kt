package com.mkiperszmid.parkitcourse.home.data.remote.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest

class MapsApiRestrictionInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val packageName = context.packageName
        val signingCertSha1 = getSigningCertificateSha1(context)

        val newRequest = request.newBuilder()
            .header("X-Android-Package", packageName)
            .header("X-Android-Cert", signingCertSha1)
            .header("User-Agent", "com.mkiperszmid.parkitcourse")
            .build()

        return chain.proceed(newRequest)
    }

    private fun getSigningCertificateSha1(context: Context): String {
        try {
            val packageInfo = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    android.content.pm.PackageManager.GET_SIGNING_CERTIFICATES
                )
            } else {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    android.content.pm.PackageManager.GET_SIGNATURES
                )
            }

            val signingCertificate = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                packageInfo.signingInfo.apkContentsSigners[0]
            } else {
                @Suppress("DEPRECATION")
                packageInfo.signatures[0]
            }

            val messageDigest = MessageDigest.getInstance("SHA-1")
            val sha1Bytes = messageDigest.digest(signingCertificate.toByteArray())

            return sha1Bytes.joinToString("") { byte -> "%02X".format(byte) }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Unable to compute SHA-1 fingerprint", e)
        }
    }

}