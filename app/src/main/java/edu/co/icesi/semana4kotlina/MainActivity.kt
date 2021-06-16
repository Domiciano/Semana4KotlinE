package edu.co.icesi.semana4kotlina

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
        ), PERMISSIONS_CALLBACK)

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
}