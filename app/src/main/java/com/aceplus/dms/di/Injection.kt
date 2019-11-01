package com.aceplus.dms.di

import android.content.Context
import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.di.createDownloadWebService
import com.aceplus.data.di.createOkHttpClient
import com.aceplus.data.di.createUploadWebService
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.remote.UploadApiService
import com.aceplus.data.repoimpl.CustomerVisitRepoImpl
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.data.repoimpl.SyncRepoImpl
import com.aceplus.data.repoimpl.creditcollectionrepoimpl.CreditCollectionCheckoutRepoImpl
import com.aceplus.data.repoimpl.creditcollectionrepoimpl.CreditCollectionRepoImpl
import com.aceplus.data.repoimpl.report.ReportRepoImpl
import com.aceplus.data.repoimpl.promotionrepoImpl.*
import com.aceplus.data.repoimpl.prospectcustomet.ProspectCustomerRepoImpl
import com.aceplus.data.repoimpl.routrepoimpl.CustomerLocationRepoImpl
import com.aceplus.data.repoimpl.routrepoimpl.ViewByListRepoImpl
import com.aceplus.data.utils.Constant
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.dms.viewmodel.SyncViewModel
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionCheckOutViewModel
import com.aceplus.dms.viewmodel.creditcollection.CreditCollectionViewModel
import com.aceplus.dms.viewmodel.customer.CustomerViewModel
import com.aceplus.dms.viewmodel.customer.sale.SaleCheckoutViewModel
import com.aceplus.dms.viewmodel.customer.prospectcustomer.ProspectCustomerViewModel
import com.aceplus.dms.viewmodel.customer.sale.SaleViewModel
import com.aceplus.dms.viewmodel.customer.sale.SalesReturnViewModel
import com.aceplus.dms.viewmodel.promotionviewmodels.*
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.dms.viewmodel.routeviewmodels.CustomerLocationViewModel
import com.aceplus.dms.viewmodel.routeviewmodels.ViewByListViewModel
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.repo.LoginRepo
import com.aceplus.domain.repo.SyncRepo
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionCheckOutRepo
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionRepo
import com.aceplus.domain.repo.report.ReportRepo
import com.aceplus.domain.repo.promotionrepo.*
import com.aceplus.domain.repo.prospectcustomer.ProspectCustomerRepo
import com.aceplus.domain.repo.routerepo.CustomerLocationRepo
import com.aceplus.domain.repo.routerepo.ViewByListRepo
import com.aceplussolutions.rms.constants.SharedConstants
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

//Network Module
val networkModule = Kodein.Module {
    bind<OkHttpClient>() with singleton { createOkHttpClient() }
    bind<DownloadApiService>() with provider {
        createDownloadWebService(
            okHttpClient = instance(),
            url = Constant.BASE_URL
        )
    }

    bind<UploadApiService>("local") with provider {
        createUploadWebService(
            okHttpClient = instance(),
            url = Constant.BASE_URL
        )
    }
    bind<UploadApiService>("cloud") with singleton {
        createUploadWebService(
            okHttpClient = instance(),
            url = Constant.REAL_TIME_AP_URL
        )
    }
//        bind<RealTimeUploadApiService>("cloud") with singleton {
//            createUploadRealtimeWebService(
//                okHttpClient = instance(),
//                url = Constant.REAL_TIME_AP_URL
//            )
//        }
}

//Repository Module
val repoModule = Kodein.Module {
    bind<LoginRepo>() with singleton {
        LoginRepoImpl(
            downloadApi = instance(),
            db = instance(),
            shf = instance()
        )
    }
    bind<SyncRepo>() with singleton {
        SyncRepoImpl(
            downloadApiService = instance(),
            uploadApiService = instance("local"),
            db = instance(),
            shf = instance()
        )
    }
    bind<CustomerVisitRepo>() with singleton {
        CustomerVisitRepoImpl(db = instance(), shf = instance())
    }
    bind<ReportRepo>() with singleton {
        ReportRepoImpl(instance())
    }

    bind<ProspectCustomerRepo>() with singleton {
        ProspectCustomerRepoImpl(instance())
    }


    bind<PromotionRepo>() with singleton {
        PromotionRepoImpl(instance())
    }

    bind<ViewByListRepo>() with singleton {
        ViewByListRepoImpl(instance())
    }
    bind<CustomerLocationRepo>() with singleton {
        CustomerLocationRepoImpl(instance())
    }
    bind<CreditCollectionRepo>() with singleton {
        CreditCollectionRepoImpl(instance())
    }
    bind<CreditCollectionCheckOutRepo>() with singleton {
        CreditCollectionCheckoutRepoImpl(instance())
    }
}

//ViewModel Module
val vmModule = Kodein.Module {

    bind() from singleton { LoginViewModel(instance(), instance()) }
    bind() from singleton { SyncViewModel(instance(), instance()) }
    bind() from singleton { CustomerViewModel(instance(), instance()) }

    bind() from singleton { SaleViewModel(instance(), instance()) }
    bind() from singleton { SaleCheckoutViewModel(instance(), instance()) }
    bind() from singleton { SalesReturnViewModel(instance(), instance()) }
    bind() from singleton { ReportViewModel(instance(), instance()) }
    bind() from  singleton { ProspectCustomerViewModel(instance(),instance()) }

    bind() from singleton { PromotionViewModel(instance(), instance()) }

    bind() from singleton { ViewByListViewModel(instance(), instance()) }
    bind() from singleton { CustomerLocationViewModel(instance(), instance()) }
    bind() from singleton { CreditCollectionViewModel(instance(), instance()) }
    bind() from singleton { CreditCollectionCheckOutViewModel(instance(), instance(),instance()) }


}


fun provideDB(context: Context): MyDatabase = MyDatabase.getInstance(context)!!
fun provideDownloadApi() = createDownloadWebService(createOkHttpClient(), Constant.BASE_URL)
fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(SharedConstants.SHP_NAME, 0)
