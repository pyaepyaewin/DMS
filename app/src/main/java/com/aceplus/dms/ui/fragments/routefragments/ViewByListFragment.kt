package com.aceplus.dms.ui.fragments.routefragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aceplus.data.database.dao.route.RouteDao
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.routeadapters.ViewByListAdapter
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.dms.viewmodel.routeviewmodels.ViewByListViewModel
import com.aceplus.domain.model.route.Route_Township
import com.aceplus.domain.model.route.Routedata
import com.aceplus.domain.model.routedataclass.TownshipDataClass
import com.aceplus.domain.model.routedataclass.ViewByListDataClass
import kotlinx.android.synthetic.main.fragment_e_route_listview.*
import kotlinx.android.synthetic.main.fragment_e_route_listview.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.ArrayList

class ViewByListFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val  viewByListAdapter: ViewByListAdapter by lazy { ViewByListAdapter() }


    private val viewByListViewModel: ViewByListViewModel by lazy {
        ViewModelProviders.of(this, KodeinViewModelFactory((kodein)))
            .get(ViewByListViewModel::class.java)
    }

    var route_townships = ArrayList<TownshipDataClass>()
   //var routedataArrayList= mutableListOf<ViewByListDataClass>()

    var allList: MutableList<ViewByListDataClass> = mutableListOf()
    var townshiplist: MutableList<String> = ArrayList()
    lateinit var townshipAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_e_route_listview, container, false)
        townshiplist.add("All Township")
        townshipAdapter =
            ArrayAdapter(activity!!, android.R.layout.simple_spinner_item, townshiplist)
        townshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.townshipspinner.adapter = townshipAdapter

        viewByListViewModel.townshipListSuccessState.observe(this, android.arch.lifecycle.Observer
        {
            this.route_townships = it as ArrayList<TownshipDataClass>
            for (i in route_townships.indices) {
                townshiplist.add(route_townships[i].township_name)
            }
            townshipAdapter.notifyDataSetChanged()
        })

        viewByListViewModel.townshipErrorState.observe(this, android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        viewByListViewModel.loadTownShipData()
        viewByListViewModel.townShipDetailListSuccessState.observe(this, Observer {
            viewByListAdapter.setNewList(it as ArrayList<ViewByListDataClass>)
            allList=it
           // this.routedataArrayList = it as ArrayList<ViewByListDataClass>
        })
        viewByListViewModel.townShipDetailErrorState.observe(this, android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvViewByList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewByListAdapter
        }
        viewByListViewModel.loadTownShipDetail()

        townshipspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val filterList: ArrayList<ViewByListDataClass> = ArrayList()

                if (townshiplist[position] != townshiplist[0]){
                    for (c in allList) {
                        if (c.township_name == townshiplist[position]) {
                            filterList.add(c)
                        }
                    }
                }else{
                    for (c in allList) {
                            filterList.add(c)
                    }
                }



                viewByListAdapter.setNewList(filterList)


            }

        }
    }
}