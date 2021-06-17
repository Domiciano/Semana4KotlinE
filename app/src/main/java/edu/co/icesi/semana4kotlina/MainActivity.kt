package edu.co.icesi.semana4kotlina

import android.Manifest
import android.R.attr.data
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(), View.OnClickListener, ModalDialog.OnOkListener {


    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var file: File

    private lateinit var dialog:ModalDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Registro de callback
        cameraLauncher = registerForActivityResult(StartActivityForResult(), ::onCameraResult)
        galleryLauncher = registerForActivityResult(StartActivityForResult(), ::onGalleryResult)

       
        
        requestPermissions(arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
        ), PERMISSIONS_CALLBACK)



        openCamaraBtn.setOnClickListener(this)
        openGalleryBtn.setOnClickListener(this)
        downloadImage.setOnClickListener(this)

    }


    companion object{
        const val PERMISSIONS_CALLBACK = 11
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.contains(PackageManager.PERMISSION_DENIED)){
            Toast.makeText(this, "Alerta!, no todos los permisos fueron concedidos", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Todos los permisos fueron concedidos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.openCamaraBtn -> {
                val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                file = File("${getExternalFilesDir(null)}/photo")
                Log.e(">>>",file.toString())
                val uri = FileProvider.getUriForFile(this, packageName, file)
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                cameraLauncher.launch(i)
            }

            R.id.openGalleryBtn -> {
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.setType("image/*")
                galleryLauncher.launch(i)
            }

            R.id.downloadImage -> {
                dialog = ModalDialog.newInstance()
                dialog.listener = this
                dialog.show(supportFragmentManager, "dialog")
            }
        }
    }

    private fun onCameraResult(activityResult: ActivityResult?) {
        val image = BitmapFactory.decodeFile(file.path)
        mainImage.setImageBitmap(image)
    }

    private fun onGalleryResult(activityResult: ActivityResult?) {
        val imageUri = activityResult?.data?.getData()
        imageUri?.let {
            val path = UtilDomi.getPath(this, it)
            Log.e(">>>",""+path)
            val image = BitmapFactory.decodeFile(path)
            mainImage.setImageBitmap(image)
        }
    }

    override fun onOk(url: String) {
        dialog.dismiss()
        Glide.with(this).load(url).fitCenter().into(mainImage)
    }
}
