package com.networksummit.ultimateguide_mobilelegend.ui.preparations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.networksummit.ultimateguide_mobilelegend.BuildConfig
import com.networksummit.ultimateguide_mobilelegend.R
import com.networksummit.ultimateguide_mobilelegend.ui.preparations.battle_spell.BattleSpellList
import com.networksummit.ultimateguide_mobilelegend.ui.utils.CustomDialogBox
import com.networksummit.ultimateguide_mobilelegend.ui.utils.DirectoryFileConfig


class PreparationFragment : Fragment() {
    private lateinit var adView : AdView
    private lateinit var listViewPreparations: ListView
    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View?
    {
        val root = inflater.inflate(R.layout.fragment_preparations, container, false)

        listViewPreparations = root.findViewById<View>(R.id.listViewPreparations) as ListView
        val prepName = resources.getStringArray(R.array.prepList)
        val imageId = arrayOf(
            R.drawable.default_image,R.drawable.default_image,R.drawable.default_image
        )


        val myListAdapter = ListViewAdapter_Preparations( requireActivity() ,imageId,prepName)
        listViewPreparations.adapter = myListAdapter

        listViewPreparations.setOnItemClickListener { _, _, position: Int, _: Long ->

            if(position==0)
            {


                val dFC = DirectoryFileConfig()
                if(dFC.checkBattleSpellData(root.context))
                    {
                        val intent = Intent(root.context, BattleSpellList::class.java)
                        intent.putExtra("title", "Battle Spell: List")
                        startActivity(intent)
                    }
                else
                    {

                        val dialog = CustomDialogBox()
                        with(dialog)
                        {
                            setDialog(root.context,"No Data Found","Do you want to download the battle spell list?",CustomDialogBox.DialogStyle.YesOrNo)
                            show()
                            onClickEvent = fun(value:CustomDialogBox.Response):CustomDialogBox.Response {
                                when(value)
                                {
                                    CustomDialogBox.Response.Yes ->
                                    {
                                        Toast.makeText(root.context, "Yes to download",Toast.LENGTH_LONG).show()




                                    }
                                    CustomDialogBox.Response.No -> Toast.makeText(root.context, "No to download",Toast.LENGTH_LONG).show()
                                    else -> return value
                                }
                              return value
                            }
                        }

                    }





            }
            else
            {
                Toast.makeText(root.context, prepName[position],Toast.LENGTH_LONG).show()
            }




    }


    adView = root.findViewById<View>(R.id.adViewPreparations) as AdView


        if(BuildConfig.DEBUG) {
            val testDeviceIds = listOf(AdRequest.DEVICE_ID_EMULATOR)
            val config = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
            MobileAds.setRequestConfiguration(config)
            MobileAds.initialize(root.context)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)

        }
        else
        {
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }




        adView.adListener = object : AdListener(){
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                val toastMessage: String = "ad fail to load"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdLoaded() {
                super.onAdLoaded()
                val toastMessage: String = "ad loaded"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdOpened() {
                super.onAdOpened()
                val toastMessage: String = "ad is open"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdClicked() {
                super.onAdClicked()
                val toastMessage: String = "ad is clicked"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                val toastMessage: String = "ad is closed"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdImpression() {
                super.onAdImpression()
                val toastMessage: String = "ad impression"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
                val toastMessage: String = "ad left application"
                Toast.makeText(root.context, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
        }



















        return root
    }

    override fun onPause() {
        if (adView!=null) {
            adView.pause();
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (adView != null) {
            adView.resume();
        }
    }

    override fun onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}

