package com.aceplus.dms.ui.activities
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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

        const val IR_EXTRA_DEVICE_ADDRESS = "device_address"

        fun getIntentFromPrintInvoice(context: Context): Intent = Intent(context, DeviceListActivity::class.java)

    }

    private val mPairedDevicesArrayAdapter: ArrayAdapter<String> by lazy { ArrayAdapter<String>(this, R.layout.device_name) }
    private val mNewDevicesArrayAdapter: ArrayAdapter<String> by lazy { ArrayAdapter<String>(this, R.layout.device_name) }

    private var mBluetoothAdapter: BluetoothAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setResult(Activity.RESULT_CANCELED)

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

        var intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(mReceiver, intentFilter)

        intentFilter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(mReceiver, intentFilter)

        button_scan.setOnClickListener {
            mNewDevicesArrayAdapter.clear()
            doDiscovery()
            it.isEnabled = false
        }

    }

    private fun doDiscovery(){

        // ToDo - show loading
        title_new_devices.visibility = View.VISIBLE
        if (mBluetoothAdapter != null){
            if (mBluetoothAdapter!!.isDiscovering) mBluetoothAdapter!!.cancelDiscovery()
            mBluetoothAdapter!!.startDiscovery()
        }

    }

    private val mDeviceClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->

        mBluetoothAdapter?.cancelDiscovery()

        val info = (view as TextView).text.toString()
        val noDevices = "No Paired Device"
        val noNewDevice = "No New Device"

        if (info != noDevices && info != noNewDevice){
            var address = info
            if (info.length > 17) address = info.substring(info.length - 17)
            val intent = Intent()
            intent.putExtra(IR_EXTRA_DEVICE_ADDRESS, address)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            val action = p1?.action

            if (BluetoothDevice.ACTION_FOUND == action){
                val device = p1?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                if (device.bondState != BluetoothDevice.BOND_BONDED)
                    mNewDevicesArrayAdapter.add("${device.name}\n${device.address}")
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action){
                // ToDo - hide loading
                //title = "Select Device"
                if (mNewDevicesArrayAdapter.count == 0)
                    mNewDevicesArrayAdapter.add("No New Device")

                button_scan.isEnabled = true
            }

        }
    }

}
