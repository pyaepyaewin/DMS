package com.aceplus.dms.ui.activities.customer

import android.Manifest
import android.app.Activity
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
import android.util.Log
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
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_add_new_customer_location.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.io.IOException
import java.text.DecimalFormat

class AddNewCustomerLocationActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_add_new_customer_location

    private var latDouble = 0.0
    private var lonDouble = 0.0

    companion object {

        private const val IE_FROM = "IE_FROM"
        private const val IE_SALE_MAN_ID = "IE_SALE_MAN_ID"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        private const val IE_NAME = "name"
        private const val IE_PERSON = "person"
        private const val IE_PHONE = "phone"
        private const val IE_ADDRESS = "address"
        private const val IE_PAYMENT_TYPE = "paymentType"
        private const val IE_STREET_ID = "sid"
        private const val IE_TOWNSHIP_ID = "tid"
        private const val IE_SHOP_TYPE_ID = "stId"
        private const val IE_DISTINCT_ID = "did"
        private const val IE_STATE_DIVISION_ID = "sdId"
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

        fun newIntentFromCustomerActivity(
            context: Context,
            salesmanId: String,
            customer: Customer
        ): Intent {
            val intent = Intent(context, AddNewCustomerLocationActivity::class.java)
            intent.putExtra(IE_FROM, "customerActivity")
            intent.putExtra(IE_SALE_MAN_ID, salesmanId)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent
        }

        fun newIntentFromAddNewCustomerActivity(
            context: Context,
            customerName: String,
            contactPerson: String,
            phone: String,
            address: String,
            paymentType: String,
            streetId: Int,
            townshipId: Int,
            shopTypeId: Int,
            districtId: Int,
            stateDivisionId: Int
        ): Intent {
            val intent = Intent(context, AddNewCustomerLocationActivity::class.java)
            intent.putExtra(IE_FROM, "addNewCustomerActivity")
            intent.putExtra(IE_NAME, customerName)
            intent.putExtra(IE_PERSON, contactPerson)
            intent.putExtra(IE_PHONE, phone)
            intent.putExtra(IE_ADDRESS, address)
            intent.putExtra(IE_PAYMENT_TYPE, paymentType)
            intent.putExtra(IE_STREET_ID, streetId)
            intent.putExtra(IE_TOWNSHIP_ID, townshipId)
            intent.putExtra(IE_SHOP_TYPE_ID, shopTypeId)
            intent.putExtra(IE_DISTINCT_ID, districtId)
            intent.putExtra(IE_STATE_DIVISION_ID, stateDivisionId)
            return intent
        }

    }

    private val customerViewModel: CustomerViewModel by viewModel()

    private var customer: Customer? = null
    private var from: String? = null
    private var salesmanId: String? = null
    private var map: GoogleMap? = null
    private var markerCount: Int = 0

    private var name: String? = null
    private var person: String? = null
    private var phone: String? = null
    private var address: String? = null
    private var paymentType: String? = null
    private var streetId: Int? = null
    private var townshipId: Int? = null
    private var shopTypeId: Int? = null
    private var districtId: Int? = null
    private var stateDivisionId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        getIntentData()

        if (from == "customerActivity")
            setupUI()
        else if (from == "addNewCustomerActivity")
            addNewUI()

        catchEvents()

    }

    private fun getIntentData() {

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        from = intent.getStringExtra(IE_FROM)
        salesmanId = intent.getStringExtra(IE_SALE_MAN_ID)

        if (from == "addNewCustomerActivity") {
            name = intent.getStringExtra(IE_NAME)
            person = intent.getStringExtra(IE_PERSON)
            phone = intent.getStringExtra(IE_PHONE)
            address = intent.getStringExtra(IE_ADDRESS)
            paymentType = intent.getStringExtra(IE_PAYMENT_TYPE)
            streetId = intent.getIntExtra(IE_STREET_ID, 0)
            townshipId = intent.getIntExtra(IE_TOWNSHIP_ID, 0)
            shopTypeId = intent.getIntExtra(IE_SHOP_TYPE_ID, 0)
            districtId = intent.getIntExtra(IE_DISTINCT_ID, 0)
            stateDivisionId = intent.getIntExtra(IE_STATE_DIVISION_ID, 0)
        }

    }

    private fun setupUI() {

        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(baseContext)
        if (status != ConnectionResult.SUCCESS) {
            val requestCode = 10
            GooglePlayServicesUtil.getErrorDialog(status, this, requestCode).show()
        } else {
            val mapView = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
            mapView.getMapAsync {
                this.map = it
                map!!.uiSettings.isMyLocationButtonEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        map!!.isMyLocationEnabled = true
                        val gpsTracker = GPSTracker(this)
                        var lat = 0.0
                        var lon = 0.0

                        if (gpsTracker.canGetLocation()) {
                            lat = gpsTracker.getLatitude()
                            lon = gpsTracker.getLongitude()
                        }
                        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))

                    } else {
                        checkLocationPermission() //To Request Location Permission
                    }
                } else {
                    map!!.isMyLocationEnabled = true
                    val gpsTracker = GPSTracker(this)
                    var lat = 0.0
                    var lon = 0.0

                    if (gpsTracker.canGetLocation()) {
                        lat = gpsTracker.getLatitude()
                        lon = gpsTracker.getLongitude()
                    }

                    map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))
                }

                map?.setOnMapClickListener { location ->
                    if (markerCount == 0) {
                        drawMarker(location)
                        val latDouble = location.latitude
                        val lonDouble = location.longitude
                        val nf = DecimalFormat("#.##########")
                        customer?.latitude = nf.format(latDouble)
                        customer?.longitude = nf.format(lonDouble)
                        markerCount++
                    }
                }

                map?.setOnMapLongClickListener {
                    map?.clear()
                    markerCount = 0
                    customer?.latitude = null
                    customer?.longitude = null
                }

                if (customer?.latitude != null) {
                    drawMarker(LatLng(
                        customer!!.latitude!!.toDouble(),
                        customer!!.longitude!!.toDouble())
                    )
                }
            }
        }

    }

    private fun addNewUI() {

        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(baseContext)
        if (status != ConnectionResult.SUCCESS) {
            val requestCode = 10
            GooglePlayServicesUtil.getErrorDialog(status, this, requestCode).show()
        } else {
            val mapView =
                supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
            mapView.getMapAsync {
                this.map = it
                map!!.uiSettings.isMyLocationButtonEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        map!!.isMyLocationEnabled = true
                        val gpsTracker = GPSTracker(this)
                        var lat = 0.0
                        var lon = 0.0

                        if (gpsTracker.canGetLocation()) {
                            lat = gpsTracker.getLatitude()
                            lon = gpsTracker.getLongitude()
                        }

                        map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))

                    } else {
                        checkLocationPermission() //Request Location Permission
                    }
                } else {
                    map!!.isMyLocationEnabled = true
                    val gpsTracker = GPSTracker(this)
                    var lat = 0.0
                    var lon = 0.0

                    if (gpsTracker.canGetLocation()) {
                        lat = gpsTracker.getLatitude()
                        lon = gpsTracker.getLongitude()
                    }

                    map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 15f))
                }

                map?.setOnMapClickListener { location ->
                    if (markerCount == 0) {
                        drawMarker(location)
                        latDouble = location.latitude
                        lonDouble = location.longitude
                    }
                    Log.d("LAT", "$latDouble")
                    Log.d("LON", "$lonDouble")
                }
            }
        }
    }

    private fun catchEvents() {

        back_img.setOnClickListener {
            onBackPressed()
        }

        search_img.setOnClickListener {
            val location = address_txt.text.toString()
            if (!location.isNullOrBlank()) {
                var addressList: List<Address> = listOf()
                val geoCoder = Geocoder(this)
                try {
                    addressList = geoCoder.getFromLocationName(location, 1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                try {
                    val address = addressList[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    map!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
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
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED) {
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

    private fun drawMarker(point: LatLng) {
        val markerOptions = MarkerOptions()
        markerOptions.position(point)
        map!!.addMarker(markerOptions)
    }

    override fun onBackPressed() {

        Log.d("LAT", "$latDouble")
        when {
            from.equals("AddNewCustomerActivity", true) -> {

                val intent = AddNewCustomerActivity.newIntentFromAddNewCustomerLocation(
                    applicationContext,
                    name!!,
                    person!!,
                    phone!!,
                    address!!,
                    latDouble,
                    lonDouble,
                    paymentType!!,
                    streetId!!,
                    townshipId!!,
                    shopTypeId!!,
                    districtId!!,
                    stateDivisionId!!
                )
                startActivity(intent) //Better with intent result (YLA commended)
                finish()

            }
            from == "customerActivity" -> {

                if (customer!!.latitude != null){
                    customer!!.flag = "2"
                    customerViewModel.updateCustomerLocation(customer!!)
                    val resIntent = Intent()
                    resIntent.putExtra(IE_CUSTOMER_DATA, customer)
                    setResult(Activity.RESULT_OK, resIntent)
                }
                finish()

            }
            else -> super.onBackPressed()
        }
    }

}
