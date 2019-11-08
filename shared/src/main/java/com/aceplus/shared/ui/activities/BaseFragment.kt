package com.aceplus.shared.ui.activities
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import org.kodein.di.KodeinAware
import org.kodein.di.direct
//import kotlinx.android.synthetic.main.BaseFragment.*
import org.kodein.di.generic.instance
import java.util.ArrayList


abstract class BaseFragment:Fragment() {

    inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : Fragment {
        return lazy { ViewModelProviders.of(this, direct.instance()).get(VM::class.java) }
    }
}