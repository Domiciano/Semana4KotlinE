package edu.co.icesi.semana4kotlina

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Registro de callback
        launcher = registerForActivityResult(StartActivityForResult(), ::onResult)

        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
        ), PERMISSIONS_CALLBACK)



        openCamaraBtn.setOnClickListener(this)

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
                launcher.launch(i)
            }
        }
    }

    private fun onResult(activityResult: ActivityResult?) {
        val image = BitmapFactory.decodeFile(file.path)
        mainImage.setImageBitmap(image)
    }
}