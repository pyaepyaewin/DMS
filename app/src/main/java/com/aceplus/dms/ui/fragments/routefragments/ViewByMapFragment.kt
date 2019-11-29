package com.aceplus.dms.ui.fragments.routefragments

import android.Manifest
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.routeviewmodels.CustomerLocationViewModel
import com.aceplus.domain.model.routedataclass.CustomerLocationDataClass
import com.aceplus.shared.ui.activities.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.M
import java.util.zip.Inflater

class ViewByMapFragment : BaseFragment(), KodeinAware {

    var latlngList: MutableList<CustomerLocationDataClass>? = null
    var mMap: GoogleMap? = null

    override val kodein: Kodein by kodein()
    final val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private val customerLocationViewModel: CustomerLocationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_e_route_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { p0 ->
            mMap = p0!!

            if (mMap != null) {
                mMap!!.isTrafficEnabled = true
                mMap!!.uiSettings.isMyLocationButtonEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        mMap!!.isMyLocationEnabled = true
                    } else {
                        CheckLocationPermission()
                    }
                } else {
                    mMap!!.isMyLocationEnabled = true
                }

            }
        }

        customerLocationViewModel.customerLocationSuccessState.observe(this, Observer {
            latlngList = it as MutableList<CustomerLocationDataClass>
            CustomerLocation()
        })
        customerLocationViewModel.customerLocationErrorState.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        customerLocationViewModel.loadCustomerLocation()

        return fragmentView


    }

    private fun CustomerLocation() {
        for (i in latlngList!!) {

            var latlng = LatLng(i.latitude, i.longitude)
            val latlng1 = LatLng(0.0, 0.0)
            if (latlng != latlng1) {
                var markerOptions = MarkerOptions().position(latlng)

                if (mMap != null) {
                    mMap!!.addMarker(markerOptions)
                    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                    mMap!!.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                latlng.latitude,
                                latlng.longitude
                            ), 12.0f
                        )
                    )

                }
}


            }
        }


    private fun CheckLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(activity).setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK"
                    ) { dialog, which ->
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf<String>
                                (Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }.create().show()

            }

        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            context!!,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        mMap!!.isMyLocationEnabled = true
                        CustomerLocation()
                    }

                } else {
                    Toast.makeText(context, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(context, "Destroyed", Toast.LENGTH_SHORT).show()
    }
}





