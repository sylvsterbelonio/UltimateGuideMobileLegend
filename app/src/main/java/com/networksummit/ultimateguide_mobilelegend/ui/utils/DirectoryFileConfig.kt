package com.networksummit.ultimateguide_mobilelegend.ui.utils
import android.content.Context
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.networksummit.ultimateguide_mobilelegend.R
import java.io.File
import java.io.IOException

class DirectoryFileConfig{

    private val rootFolderName = "Ultimate Guide Mobile Legend - Data"
    enum class WriteType { AppStorage, SdCardStorage, ExternalStorage, None }

    private fun getStorageType(context: Context): WriteType
    {
        return when(context.getString(R.string.storageType))
        {
            "sdCard" -> return WriteType.SdCardStorage
            "internalStorage" -> WriteType.AppStorage
            "externalStorage" -> WriteType.ExternalStorage
            else -> WriteType.None
        }
    }

    private fun initializeDirectory(context: Context)
    {
        when(getStorageType(context))
        {
            WriteType.SdCardStorage ->
            {
                var path = "/sdcard/$rootFolderName"
                val mFolder = File(path)
                if (!mFolder.exists())  File(path).mkdir()
            }
            else -> null
        }
    }

    fun createRootDirectory(context: Context) {
        when(getStorageType(context))
        {
           WriteType.SdCardStorage ->
               {
                   var path = "/sdcard/$rootFolderName"
                   val mFolder = File(path)
                   if (!mFolder.exists())
                   {

                       val success = File(path).mkdir()
                       if (success)  Toast.makeText(context,"Folder Created in SdCardStorage", Toast.LENGTH_LONG).show()
                       else          Toast.makeText(context,"Error: No Folder Created. Please allow the permission to access your storage", Toast.LENGTH_LONG).show()

                   }

               }
           WriteType.AppStorage ->
               {
                   var path = context.filesDir.absolutePath + "/$rootFolderName"
                   val mFolder = File(path)
                   if (!mFolder.exists())
                   {
                       val success = File(path).mkdirs()
                       if (success)  Toast.makeText(context,"Folder Created in AppStorage", Toast.LENGTH_LONG).show()
                       else          Toast.makeText(context,"Error: No Folder Created", Toast.LENGTH_LONG).show()
                   }
                   else Toast.makeText(context,"Folder already existed.", Toast.LENGTH_LONG).show()

               }
            WriteType.ExternalStorage ->
                {
                    var path = "/sdcard/$rootFolderName/"
                    val mFolder = File(path)
                    if (!mFolder.exists()) File(path).mkdirs()
                }
        }

    }

    fun getBasePath(context: Context): String
    {
        return when(getStorageType(context)) {
            WriteType.SdCardStorage -> {
                context.filesDir.absolutePath + "/$rootFolderName/"
            }
            else -> "";
        }
    }

    fun checkBattleSpellData(context: Context): Boolean {
        var path = getBattleSpellPath(context) + context.getString(R.string.battle_spell_data_list)
        return when(getStorageType(context)) {
            WriteType.SdCardStorage -> {
                var file = File(path)
                return file.exists()
            }
            else -> false
        }
    }

    fun getBattleSpellPath(context: Context): String
    {
        initializeDirectory(context)

        return when(getStorageType(context)) {
            WriteType.SdCardStorage -> {
                var path = "/sdcard/$rootFolderName/Battle Spells"
                val mFolder = File(path)
                if (!mFolder.exists()) File(path).mkdirs()
                "$path/"
            }
            else -> ""
        }
    }



}