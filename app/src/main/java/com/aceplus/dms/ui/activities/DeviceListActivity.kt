package com.aceplus.dms.ui.activities

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.device_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class DeviceListActivity: BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.device_list

    companion object{

        fun getIntentFromPrintInvoice(context: Context): Intent = Intent(context, DeviceListActivity::class.java)

    }

    private val mPairedDevicesArrayAdapter: ArrayAdapter<String> by lazy { ArrayAdapter<String>(this, R.layout.device_name) }
    private val mNewDevicesArrayAdapter: ArrayAdapter<String> by lazy { ArrayAdapter<String>(this, R.layout.device_name) }

    private var mBluetoothAdapter: BluetoothAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setResult(Activity.RESULT_CANCELED)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        setupUI()
        catchEvents()

    }

    private fun setupUI(){

        paired_devices.adapter = mPairedDevicesArrayAdapter
        new_devices.adapter = mNewDevicesArrayAdapter

        val pairedDevices = mBluetoothAdapter?.bondedDevices

        if (pairedDevices?.size ?: 0 > 0){
            title_paired_devices.visibility = View.VISIBLE
            for (device in pairedDevices!!){
                mPairedDevicesArrayAdapter.add("${device.name}\n${device.address}")
            }
        } else{
            mPairedDevicesArrayAdapter.add("No Paired Device")
        }

    }

    private fun catchEvents(){

        paired_devices.onItemClickListener = mDeviceClickListener
        new_devices.onItemClickListener = mDeviceClickListener

        button_scan.setOnClickListener {
            doDiscovery()
            it.isEnabled = false
        }

    }

    private fun doDiscovery(){

    }

    private val mDeviceClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->

    }

}