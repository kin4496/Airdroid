package com.example.airdroid.services

import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import com.example.airdroid.receivers.BluetoothConnectionReceiver

/** Service class for bluetooth connection/disconnection **/
class BluetoothConnectionService : Service() {

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val bluetoothReceiver = BluetoothConnectionReceiver()
    private val bluetoothDeviceConnectedIntentFilter = IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED)
    private val bluetoothDeviceDisconnectedIntentFilter = IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (bluetoothAdapter == null) {
            stopSelf() // If the device does not support bluetooth, the service doesn't run
            return super.onStartCommand(intent, flags, startId)
        }

        registerReceiver(bluetoothReceiver, bluetoothDeviceConnectedIntentFilter)
        registerReceiver(bluetoothReceiver, bluetoothDeviceDisconnectedIntentFilter)

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}