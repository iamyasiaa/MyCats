package com.example.appwithcats.presentation

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.appwithcats.R
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.app.Activity





class Authorization : Fragment() {
    private lateinit var loginButton: Button
    private lateinit var email: TextInputEditText
    private lateinit var description: TextInputEditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_autorization, container, false)
        loginButton = view!!.findViewById(R.id.loginButton)
        loginButton.setEnabled(false);
        email = view!!.findViewById(R.id.email)
        description = view!!.findViewById(R.id.description)
        val edList = arrayOf<TextInputEditText>(email, description)
        val textWatcher = CustomTextWatcher(edList = edList, loginButton)
        for (editText in edList) editText.addTextChangedListener(textWatcher)


        loginButton.setOnClickListener{
            if (id == R.id.loginButton){
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.add(R.id.nav_host_fragment_container, KeyApi())
                transaction?.disallowAddToBackStack()
                transaction?.commit()


                Toast.makeText(activity, "вперед", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)







    }



}
