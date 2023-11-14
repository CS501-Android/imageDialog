package com.bignerdranch.android.criminalintent

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.io.File

private const val ARG_IMAGE = "image"

class ZoomedPhotoDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val photoFile = arguments?.getSerializable(ARG_IMAGE) as? File

        return activity?.let { activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val view = inflater.inflate(R.layout.dialog_picture, null)

            val crimePicture = view.findViewById<ImageView>(R.id.crimePicture)

            photoFile?.let {
                if (it.exists()) {
                    val desiredWidth = 400
                    val desiredHeight = 400

                    val bitmap = getScaledBitmap(it.path, desiredWidth, desiredHeight)
                    crimePicture.setImageBitmap(bitmap)
                }
            }

            builder.setView(view)
                .setTitle(R.string.crime_photo)
                .setNegativeButton(R.string.dismiss) { _, _ -> dialog?.cancel() }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(photoFile: File): ZoomedPhotoDialogFragment {
            return ZoomedPhotoDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_IMAGE, photoFile)
                }
            }
        }
    }
}