package com.networksummit.ultimateguide_mobilelegend.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Environment
import android.view.LayoutInflater
import android.widget.*
import com.networksummit.ultimateguide_mobilelegend.R
import kotlinx.android.synthetic.main.progressbar_custom_dialog.view.*
import java.io.*
import java.net.URL
import java.net.URLConnection


class AsyncImageDownload(
    val context: Context,
    val rootLayout: LinearLayout
)
    : AsyncTask<String, Int, MutableList<Bitmap>>(){

    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTextView: TextView
    private lateinit var mAlertDialog: androidx.appcompat.app.AlertDialog

    // Before async task start
    override fun onPreExecute() {

        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.progressbar_custom_dialog, null)

        mProgressBar = dialogView.progressBar
        mTextView = dialogView.textViewPercentage
        mProgressBar.progress = 0
        mTextView.text = "0 %"

        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setNegativeButton("Cancel"
        )
        { dialog, _ ->
            cancel(true)
            dialog?.dismiss()
        }

        mAlertDialog = dialogBuilder.create()
        mAlertDialog.show()

        val dfc = DirectoryFileConfig()
        dfc.createRootDirectory(context)


        super.onPreExecute()
    }


    // Manage background async task
    override fun doInBackground(vararg  params: String): ArrayList<Bitmap>{
        val list = ArrayList<Bitmap>()

        // Loop through the task and execute them
        for( i in params.indices){
            val urlOfImage = URL(params[i])
            val urlData = params[i].split("=").toTypedArray()

            try {



            val conn: URLConnection = urlOfImage.openConnection()
            val contentLength: Int = conn.contentLength
            val stream = DataInputStream(urlOfImage.openStream())
            val buffer = ByteArray(contentLength)
            stream.readFully(buffer)
            stream.close()

            val cPath = DirectoryFileConfig() //calling the directory file

            val fos = DataOutputStream(FileOutputStream( cPath.getBattleSpellPath(context) + urlData[urlData.size-1]))
            fos.write(buffer)
            fos.flush()
            fos.close()

            } catch(e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }




            try {
                val inputStream = urlOfImage.openStream()
                list.add(BitmapFactory.decodeStream(inputStream)!!)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
            }

            // Publish the async task progress in percentage
            publishProgress(((i + 1) / params.size.toFloat() * 100).toInt())

            // If user cancel the task at runtime+
            if(isCancelled)break
        }
        return list
    }


    // Display the async tas progress
    override fun onProgressUpdate(vararg values: Int?) {
        mProgressBar.progress = values[0]!!
        mTextView.text = "${values[0]} %"
        super.onProgressUpdate(values[0])
    }


    // Manage completed task after user cancel async task
    override fun onCancelled(result: MutableList<Bitmap>?) {
        Toast.makeText(context,"Downloading resources cancelled!", Toast.LENGTH_LONG).show()
        for (bitmap in result!!){
            rootLayout.addView(newImageView(bitmap))
        }

        super.onCancelled(result)
    }


    // Manage result after completed async task
    override fun onPostExecute(result: MutableList<Bitmap>?) {
        for (bitmap in result!!){
            rootLayout.addView(newImageView(bitmap))
        }
        Toast.makeText(context,"Download resources completed!", Toast.LENGTH_LONG).show()
        mAlertDialog.dismiss()
        super.onPostExecute(result)
    }


    // Method to create a new ImageView instance
    private fun newImageView(bitmap: Bitmap): ImageView {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300)
        params.bottomMargin = 15
        val imageView = ImageView(context)
        imageView.layoutParams = params
        imageView.setImageBitmap(bitmap)
        imageView.setBackgroundColor(Color.LTGRAY)
        imageView.setPadding(10,10,10,10)
        return imageView
    }
}