package org.bedu.addContact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_contact.*
import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import org.bedu.addContact.Contact
import org.bedu.recyclercontacts.R


class AddContactActivity : AppCompatActivity(), AddContactPresenter.View {

    private val presenter = AddContactPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

//        buttonAdd.setOnClickListener{
//            val name = editName.text.toString()
//            val phone = editPhone.text.toString()
//            val status = "disponible"
//            val imgProfile = R.drawable.unknown
//
//            val contact = Contact(name,status,phone,imgProfile)
//
//            val returnIntent = Intent()
//            returnIntent.putExtra("new_contact", contact)
//            setResult(Activity.RESULT_OK, returnIntent)
//            finish()
//        }

        buttonAdd.setOnClickListener{
            addContact()
        }

        editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.updateName(s.toString())
            }
        })

        editPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.updatePhone(s.toString())
            }
        })

    }

    override fun addContact() {
        presenter.addContact(this)
    }


}