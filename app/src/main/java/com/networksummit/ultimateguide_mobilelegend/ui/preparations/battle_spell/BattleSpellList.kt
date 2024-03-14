package com.networksummit.ultimateguide_mobilelegend.ui.preparations.battle_spell

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.networksummit.ultimateguide_mobilelegend.R
import com.networksummit.ultimateguide_mobilelegend.ui.utils.AsyncImageDownload
import com.networksummit.ultimateguide_mobilelegend.ui.utils.DownloadUrlConfig
import kotlinx.android.synthetic.main.activity_battle_spell_list.*



class BattleSpellList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle_spell_list)

        val actionbar = supportActionBar
        actionbar!!.title = intent.getStringExtra("title") //set actionbar title
        actionbar.setDisplayHomeAsUpEnabled(true) //set back button


    createfolder.setOnClickListener{

        val gDrive = DownloadUrlConfig()
        val task = AsyncImageDownload(this,root_layout)
        task.execute(
            gDrive.getGoogleDrivePath() + getString(R.string.battle_spell_data_list)
        )

    }

        val task = AsyncImageDownload(this,root_layout)

        button_start.setOnClickListener {
                        task.execute(
                            "https://images.freeimages.com/images/large-previews/310/spring-1-1405906.jpg",
                            "https://images.freeimages.com/images/large-previews/8f3/white-flower-power-1403046.jpg",
                            "https://images.freeimages.com/images/large-previews/81c/flower-1393311.jpg",
                            "https://images.freeimages.com/images/large-previews/7f7/statice-1406388.jpg"
                        )

                        it.isEnabled = false
                        button_cancel.isEnabled = true

                        button_cancel.setOnClickListener{
                            task.cancel(true)
                            it.isEnabled = false
                        }

                        root_layout.setOnClickListener{
                            // Get the async task status and show it using toast message
                            when(task.status){
                                AsyncTask.Status.RUNNING -> toast("Task running")
                                AsyncTask.Status.PENDING -> toast("Task pending")
                                AsyncTask.Status.FINISHED -> toast("Task finished")
                                else -> toast("Task status Unknown")
                            }
                        }



            }

    }

    fun Context.toast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }






    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
