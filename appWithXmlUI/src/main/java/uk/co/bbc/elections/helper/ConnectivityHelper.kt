package uk.co.bbc.elections.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AlertDialog
import uk.co.bbc.elections.R

object ConnectivityHelper {

    fun checkInternetConnection(context: Context) : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    // Connected to internet
                    return true
                }
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
                // Connected to internet
                return true
            }
        }

        return false

    }

    fun showDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.no_internet_connection))
                .setTitle(context.getString(R.string.attention))
                .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false) // Optional: prevent user from dismissing without clicking OK
        builder.create().show()
    }
}
