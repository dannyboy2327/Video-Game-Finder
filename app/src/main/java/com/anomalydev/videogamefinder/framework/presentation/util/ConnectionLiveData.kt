package com.anomalydev.videogamefinder.framework.presentation.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import com.anomalydev.videogamefinder.business.interactors.app.DoesNetworkHaveInternet
import com.anomalydev.videogamefinder.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConnectionLiveData(context: Context): LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private val validNetworks: MutableSet<Network> = HashSet()

    /**
     *  Checks if there is network connectivity
     */
    private fun checkValidNetworks() {
        postValue(validNetworks.size > 0)
    }

    override fun onActive() {
        super.onActive()
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
    }

    private fun createNetworkCallback() = object: ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapabilities = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            if (hasInternetCapabilities == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    val hasInternet = DoesNetworkHaveInternet.execute(
                        network.socketFactory
                    )
                    if (hasInternet) {
                        withContext(Dispatchers.Main) {
                            Log.d(Constants.TAG, "onAvailable: This network has internet $network")
                            validNetworks.add(network)
                            checkValidNetworks()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            validNetworks.remove(network)
            checkValidNetworks()
        }
    }
}