package edu.co.icesi.semana4kotlina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_modal_dialog.*

class ModalDialog : DialogFragment() {

    private lateinit var okBtn:Button
    private lateinit var urlET:EditText

    var listener:OnOkListener? = null
        get() = field
        set(listener){
            field = listener
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_modal_dialog, container, false)
        okBtn = view.findViewById(R.id.okBtn)
        urlET = view.findViewById(R.id.urlET)
        okBtn.setOnClickListener {
            listener?.let {
                it.onOk(urlET.text.toString())
            }
        }
        return view
    }

    interface OnOkListener{
        fun onOk(url:String)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }



    companion object {
        @JvmStatic
        fun newInstance() = ModalDialog().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                }
            }
    }
}