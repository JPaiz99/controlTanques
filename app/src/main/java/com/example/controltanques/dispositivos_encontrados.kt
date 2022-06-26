package com.example.controltanques

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dispositivos_encontrados.*

class dispositivos_encontrados : AppCompatActivity() {

    var bluetoothAdapter: BluetoothAdapter? =null
    lateinit var d_encontrados: Set<BluetoothDevice>
    val REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String="Device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispositivos_encontrados)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (bluetoothAdapter ==null){
            val toast = Toast.makeText(applicationContext,"dispositivo no es compatible con BT",Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        if (bluetoothAdapter?.isEnabled ==false){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE).apply {
                startActivity(this)
            }

        }
        bt.setOnClickListener {
            dispositivosVinculados()
        }
    }


    @SuppressLint("MissingPermission")
    private fun dispositivosVinculados(){
        d_encontrados = bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()
        if (!d_encontrados.isEmpty()){
            for (device: BluetoothDevice in d_encontrados){
                list.add(device)
                Log.i("device", ":$device")
            }
        }else{
            val toast = Toast.makeText(applicationContext,"No se encontraro dispositivos",Toast.LENGTH_SHORT)
            toast.show()
        }
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
        listabt.adapter = adapter
        listabt.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address

            val intent = Intent(this,ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS,address)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== REQUEST_ENABLE_BLUETOOTH){
            if (resultCode== Activity.RESULT_OK){
                if (bluetoothAdapter!!.isEnabled){
                    val toast = Toast.makeText(applicationContext,"Dispositivo Activo",Toast.LENGTH_SHORT)
                    toast.show()
                }else {
                    val toast = Toast.makeText(applicationContext,"Dispositivo Desconectado",Toast.LENGTH_SHORT)
                    toast.show()
                }
            }else if (resultCode== Activity.RESULT_CANCELED){
                val toast = Toast.makeText(applicationContext,"Dispositivo Cancelado",Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    }

}