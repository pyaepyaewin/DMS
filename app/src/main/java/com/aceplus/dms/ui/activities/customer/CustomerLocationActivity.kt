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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_customer_location.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CustomerLocationActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_customer_location

    companion object {

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

        fun newIntentFromCustomer(context: Context, customer: Customer): Intent{

            val intent = Intent(context, CustomerLocationActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent

        }

    }

    private var customer: Customer? = null
    private var map: GoogleMap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)

        setupUI()
        catchEvent()

    }

    private fun setupUI(){

        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(baseContext)

        if (status != ConnectionResult.SUCCESS) {

            val requestCode = 10
            GooglePlayServicesUtil.getErrorDialog(status, this, requestCode).show()

        } else{

            val mapView = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment

            mapView.getMapAsync{

                this.map = it
                this.map?.uiSettings?.isMyLocationButtonEnabled = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        map?.isMyLocationEnabled = true

                        /*val gpsTracker = GPSTracker(this)
                        var lat = 0.0
                        var lon = 0.0

                        if (gpsTracker.canGetLocation()) {
                            lat = gpsTracker.getLatitude()
                            lon = gpsTracker.getLongitude()
                        }
                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))*/

                    } else {
                        checkLocationPermission() //To Request Location Permission
                    }
                } else{

                    map?.isMyLocationEnabled = true

                    /*val gpsTracker = GPSTracker(this)
                    var lat = 0.0
                    var lon = 0.0

                    if (gpsTracker.canGetLocation()) {
                        lat = gpsTracker.getLatitude()
                        lon = gpsTracker.getLongitude()
                    }
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))*/

                }

                if (customer?.latitude != null) {

                    val point = LatLng(customer!!.latitude!!.toDouble(), customer!!.longitude!!.toDouble())

                    if (customer?.visit_record == "1")
                        drawMarker2(point, customer!!.customer_name.toString(), customer!!.address.toString())
                    else
                        drawMarker(point, customer!!.customer_name.toString(), customer!!.address.toString())

                    //To check - visit record null

                }

            }

        }

    }

    private fun catchEvent(){

        cancel_img.setOnClickListener { onBackPressed() }

    }

    private fun checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()

            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION
                )
            }

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        map!!.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
            // Other condition can be added here
        }

    }

    private fun drawMarker(point: LatLng, title: String, snippet: String) {
        val markerOptions = MarkerOptions()
        markerOptions
            .position(point)
            .title(title)
            .snippet(snippet)
        this.map?.addMarker(markerOptions)
        this.map?.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
    }

    private fun drawMarker2(point: LatLng, title: String, snippet: String) {
        val markerOptions = MarkerOptions()
        markerOptions
            .position(point)
            .title(title)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            .snippet(snippet)
        this.map?.addMarker(markerOptions)
        this.map?.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
    }

}
