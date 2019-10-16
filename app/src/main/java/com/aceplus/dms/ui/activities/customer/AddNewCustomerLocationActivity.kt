package com.aceplus.dms.ui.activities.customer

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.shared.utils.GPSTracker
import com.aceplussolutions.rms.ui.activities.BaseActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_add_new_customer_location.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

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

    private var customer: Customer? = null
    private var from: String? = null
    private var salesmanId: String? = null
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            mapView.getMapAsync{
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
                mapView.onStart()
            }
            /*val fm: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            fm.getMapAsync {
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
                }
            }*/
        }

    }

    private fun catchEvents(){

        back_img.setOnClickListener { onBackPressed() }

        map?.setOnMapClickListener {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        }

    }

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    private fun checkLocationPermission(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
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

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION
                )

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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
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

}
