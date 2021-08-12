package com.example.quadrapedrobot

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_connect_bluetooth.*


class ConnectBluetooth : AppCompatActivity() {
    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object{
        val EXTRA_ADDRESS:String="Device_Address"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_bluetooth)

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null){
            Toast.makeText(this,"This device doesn't support bluetooth", Toast.LENGTH_SHORT).show()
            return
        }
        if (!m_bluetoothAdapter!!.isEnabled){
            val enableBluetoothIntent=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent,REQUEST_ENABLE_BLUETOOTH)
        }





        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list2: ArrayList<String> = ArrayList()
        val list: ArrayList<BluetoothDevice> = ArrayList()
        if(!m_pairedDevices.isEmpty()){
            for(device:BluetoothDevice in m_pairedDevices){
                list2.add(device.name)
                list.add(device)
                Log.e("DEVICE","Device name"+device.name+"Device address"+device.address)

            }
        }
        else{
            Toast.makeText(this, "No paired devices found", Toast.LENGTH_SHORT).show()
        }
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list2)
        availabledevices.adapter=adapter

        availabledevices.setOnItemClickListener{_, _, position, _ ->
        val device: BluetoothDevice=list[position]
        val address:String= device.address
        val intent = Intent(this,ControlBody::class.java)
        intent.putExtra(EXTRA_ADDRESS,address)
        startActivity(intent)

    }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==REQUEST_ENABLE_BLUETOOTH){
            if (resultCode == Activity.RESULT_OK){
                if(m_bluetoothAdapter!!.isEnabled){
                    Toast.makeText(this,"Bluetooth enabled",Toast.LENGTH_SHORT).show()
                }
            }
            else if(resultCode== Activity.RESULT_CANCELED){
                Toast.makeText(this,"Bluetooth enabelling cancelled",Toast.LENGTH_SHORT).show()
            }

        }
    }
}
