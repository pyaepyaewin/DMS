package com.aceplus.dms.ui.activities

import android.content.Intent
import com.aceplus.dms.R
import com.daimajia.androidanimations.library.Techniques
import com.viksaa.sssplash.lib.activity.AwesomeSplash
import com.viksaa.sssplash.lib.cnst.Flags
import com.viksaa.sssplash.lib.model.ConfigSplash

class SplashActivity : AwesomeSplash() {
    override fun initSplash(configSplash: ConfigSplash?) {
        //Customize Circular Reveal
        configSplash?.backgroundColor = R.color.colorAccent //any color you want form colors.xml
        configSplash?.animCircularRevealDuration = 2000 //int ms
        configSplash?.revealFlagX = Flags.REVEAL_RIGHT  //or Flags.REVEAL_LEFT
        configSplash?.revealFlagY = Flags.REVEAL_BOTTOM //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash?.logoSplash = R.drawable.aceplus_logo //or any other drawable
        configSplash?.animLogoSplashDuration = 2000 //int ms
        configSplash?.animLogoSplashTechnique =
                Techniques.Bounce //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        //configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
        //configSplash.setPathSplash(R.drawable.splash_img); //set path String
        configSplash?.originalHeight = 400 //in relation to your svg (path) resource
        configSplash?.originalWidth = 400 //in relation to your svg (path) resource
        configSplash?.animPathStrokeDrawingDuration = 3000
        configSplash?.pathSplashStrokeSize = 3 //I advise value be <5
        configSplash?.pathSplashStrokeColor = R.color.colorAccent //any color you want form colors.xml
        configSplash?.animPathFillingDuration = 3000
        configSplash?.pathSplashFillColor = R.color.colorWhite //path object filling color


        //Customize Title
        configSplash?.titleSplash = "W E L C O M E"
        configSplash?.titleTextColor = android.R.color.holo_red_light
        configSplash?.titleTextSize = 30f //float value
        configSplash?.animTitleDuration = 3000
        configSplash?.animTitleTechnique = Techniques.FlipInX
    }

    override fun animationsFinished() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}
