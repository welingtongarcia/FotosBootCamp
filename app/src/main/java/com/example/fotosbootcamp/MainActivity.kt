package com.example.fotosbootcamp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // pedir para usuario permissão de acesso  -> feito dinamico
        // adicionado no gradle paraler o botão
        pick_button.setOnClickListener {
            // pergunta se existe permição para versões acima do marshamellow
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //pedindo permissão ao usuario
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    pickImageFromGalery()
                }
            } else {
                pickImageFromGalery()
            }
        }

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            when (requestCode) {
                PERMISSION_CODE -> {
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickImageFromGalery()
                    } else {
                        Toast.makeText(this, "Permissão Negada!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        private fun pickImageFromGalery(){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PIKE_CODE)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK && requestCode== IMAGE_PIKE_CODE){
            image_view.setImageURI(data?.data)
        }
    }

        // objeto para startar a activity que pede permissão
        companion object {
            private val PERMISSION_CODE = 1000
            private val IMAGE_PIKE_CODE = 1001
        }

}