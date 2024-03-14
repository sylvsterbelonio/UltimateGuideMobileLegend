package com.networksummit.ultimateguide_mobilelegend.ui.utils

class DownloadUrlConfig
{
    private val GoogleDriveUrl: String = "https://drive.google.com/uc?export=download&id="
    
    fun getGoogleDrivePath(): String
    {
        return GoogleDriveUrl
    }
}