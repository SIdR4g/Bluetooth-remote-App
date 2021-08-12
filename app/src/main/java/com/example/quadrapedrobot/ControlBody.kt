package com.example.quadrapedrobot

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_control_body.*
import java.io.IOException
import java.util.*

class ControlBody : AppCompatActivity() {

    companion object{
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_body)

        m_address = intent.getStringExtra(ConnectBluetooth.EXTRA_ADDRESS).toString()

        ConnectToDevice(this).execute()

        up.setOnLongClickListener { sendCommand("w")}
        up.setOnClickListener { sendCommand("x") }
        left.setOnLongClickListener { sendCommand("a") }
        left.setOnClickListener { sendCommand("x") }
        down.setOnLongClickListener { sendCommand("s") }
        down.setOnClickListener { sendCommand("x") }
        right.setOnLongClickListener { sendCommand("d") }
        right.setOnClickListener { sendCommand("x") }
        anti_clockwise.setOnLongClickListener { sendCommand("q") }
        anti_clockwise.setOnClickListener { sendCommand("x") }
        clockwise.setOnLongClickListener { sendCommand("e") }
        clockwise.setOnClickListener { sendCommand("x") }
        reset.setOnClickListener { sendCommand("r") }

        Connected.setOnClickListener { disconnect() }
    }
    private fun sendCommand(input: String): Boolean {
            if (m_bluetoothSocket != null) {
                try {
                    Toast.makeText(this, " $input ", Toast.LENGTH_SHORT).show()
                    m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                    Toast.makeText(this, " $input ",Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
    return false
    }
    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }
    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                m_isConnected = true
            }
            m_progress.dismiss()
        }
    }
}



