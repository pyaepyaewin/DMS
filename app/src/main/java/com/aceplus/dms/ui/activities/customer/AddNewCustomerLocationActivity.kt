package com.aceplus.dms.ui.activities.customer

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.customer.CustomerViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.shared.utils.GPSTracker
import com.aceplussolutions.rms.ui.activities.BaseActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_add_new_customer_location.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.io.IOException

class AddNewCustomerLocationActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_add_new_customer_location

    companion object {
        private const val IE_FROM = "IE_FROM"
        private const val IE_SALE_MAN_ID = "IE_SALE_MAN_ID"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomerActivity(context: Context, salesmanId: String, customer: Customer): Intent {
            val intent = Intent(context, AddNewCustomerLocationActivity::class.java)
            intent.putExtra(IE_FROM, "customerActivity")
            intent.putExtra(IE_SALE_MAN_ID, salesmanId)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent
        }
    }

    private val customerViewModel: CustomerViewModel by viewModel()

    private var customer: Customer? = null
    private var from: String? = null
    private var salesmanId: String? = null
    private var map: GoogleMap? = null
    private var markerCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView.onCreate(savedInstanceState) //Error
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        getIntentData()
        setupUI()
        catchEvents()
    }

    private fun getIntentData(){
        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        from = intent.getStringExtra(IE_FROM)
        salesmanId = intent.getStringExtra(IE_SALE_MAN_ID)
    }

    private fun setupUI(){

        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(baseContext)
        if (status != ConnectionResult.SUCCESS){
            val requestCode = 10
            GooglePlayServicesUtil.getErrorDialog(status, this, requestCode).show()
        } else{
            mapView.getMapAsync {
                this.map = it
                map!!.uiSettings.isMyLocationButtonEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                        map!!.isMyLocationEnabled = true
                        val gpsTracker = GPSTracker(this)
                        var lat = 0.0
                        var lon = 0.0

                        if (gpsTracker.canGetLocation()){
                            lat = gpsTracker.getLatitude()
                            lon = gpsTracker.getLongitude()
                        }

                        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))

                    } else{
                        checkLocationPermission() //Request Location Permission
                    }
                } else{
                    map!!.isMyLocationEnabled = true
                    val gpsTracker = GPSTracker(this)
                    var lat = 0.0
                    var lon = 0.0

                    if (gpsTracker.canGetLocation()){
                        lat = gpsTracker.getLatitude()
                        lon = gpsTracker.getLongitude()
                    }

                    map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))
                }

                map?.setOnMapClickListener { location ->
                    if (markerCount == 0){
                        drawMarker(location)
                        customer?.latitude = location.latitude.toString()
                        customer?.longitude = location.longitude.toString()
                        markerCount++
                    }
                }

                map?.setOnMapLongClickListener {
                    map?.clear()
                    markerCount = 0
                    customer?.latitude = null
                    customer?.longitude = null
                }

                if (customer!!.latitude != null){
                    drawMarker(LatLng(customer!!.latitude!!.toDouble(), customer!!.longitude!!.toDouble()))
                }
            }
        }

    }

    private fun catchEvents(){

        back_img.setOnClickListener {
            if (from.equals("AddNewCustomerActivity", true)){
                // ToDo go to AddNewCustomerActivity with data
                finish()
            } else{
                customer!!.flag = "2"
                customerViewModel.updateCustomerData(customer!!)
                if (customer!!.latitude != null){
                    // ToDo updateDepartureTimeForSalemanRoute
                    finish()
                }
            }
        }

        search_img.setOnClickListener {
            val location = address_txt.text.toString()
            if (!location.isNullOrBlank()){
                var addressList: List<Address> = listOf()
                val geoCoder = Geocoder(this)
                try {
                    addressList = geoCoder.getFromLocationName(location, 1)
                } catch (e: IOException){
                    e.printStackTrace()
                }
                try {
                    val address = addressList[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                } catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }

    }

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    private fun checkLocationPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        ActivityCompat.requestPermissions(
                            this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()

            } else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            }

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        map!!.isMyLocationEnabled = true
                    }
                } else{
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

    private fun drawMarker(point: LatLng) {
        val markerOptions = MarkerOptions()
        markerOptions.position(point)
        map!!.addMarker(markerOptions)
    }

}
