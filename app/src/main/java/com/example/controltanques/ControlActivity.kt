package com.example.controltanques

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_control.*
import kotlinx.android.synthetic.main.activity_dispositivos_encontrados.*
import java.io.IOException
import java.util.*

class ControlActivity : AppCompatActivity() {

    companion object{
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb6")

        var bluetoothSocket: BluetoothSocket?=null
        lateinit var progress: ProgressDialog
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean =false
        lateinit var address:String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        address = intent.getStringExtra(dispositivos_encontrados.EXTRA_ADDRESS).toString()
        ConnectToDevice(this).execute()
        Status.setText(address)
    }

    private fun sendComand(input: String){

    }

    private fun disconect(){
        //if()
    }

    private class ConnectToDevice(c:Context) : AsyncTask<Void, Void, String>(){
        private var connectSucess: Boolean =true
        private val context: Context

        init {
            this.context = c
        }
        override fun onPreExecute() {
            super.onPreExecute()
            progress = ProgressDialog.show(context,"Conectando...","Por Favor Espera")
        }


        @SuppressLint("MissingPermission")
        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (bluetoothSocket==null || !isConnected){
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
                    Log.i("data",""+device)
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    bluetoothSocket!!.connect()
                }

            }catch (e: IOException){
                connectSucess =false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSucess){
                Log.i("data","no se puede conectar")
            }else{
                isConnected =true
                Log.i("data","Conectado")
            }
            progress.dismiss()
        }
    }
}