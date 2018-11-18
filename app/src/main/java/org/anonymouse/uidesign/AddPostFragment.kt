package org.anonymouse.uidesign

import android.app.Activity
import android.app.Activity.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.ImageReader
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_post.*
import kotlinx.android.synthetic.main.fragment_add_post.view.*
import kotlinx.android.synthetic.main.fragment_post_list.*
import java.util.jar.Manifest

class AddPostFragment : Fragment() {
    val IMAGEREQUESTCODE = 2

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        view.btn_add_photo.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, IMAGEREQUESTCODE)
        }
        view.btn_delete_image.setOnClickListener {
            deleteImage()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        return
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //获取图片路径
        if (requestCode == IMAGEREQUESTCODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data!!.getData()
            val filePathColumns = arrayOf<String>(MediaStore.Images.Media.DATA)
            val c = activity!!.getContentResolver()!!.query(selectedImage, filePathColumns, null, null, null)
            c!!.moveToFirst()
            val columnIndex = c!!.getColumnIndex(filePathColumns[0])
            val imagePath = c!!.getString(columnIndex!!)
            showImage(imagePath!!)
            c.close()
        }
    }

    //加载图片
    private fun showImage(imaePath: String) {
        val bm = BitmapFactory.decodeFile(imaePath)
        preview_image.setImageBitmap(bm)
    }

    private fun deleteImage(){
        preview_image.setImageResource(android.R.color.transparent)
    }
}