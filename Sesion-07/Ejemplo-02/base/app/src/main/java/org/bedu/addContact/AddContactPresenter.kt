package org.bedu.addContact

import android.app.Activity
import android.content.Intent
import android.view.View

class AddContactPresenter(view: View) {

    val contact = Contact()

    //Actualizamos nuestro Model desde el presenter cada que se actualiza el nombre
    fun updateName(name: String){
        contact.name = name
    }

    //Actualizamos nuestro Model desde el presenter cada que se actualiza el teléfono
    fun updatePhone(phone: String){
        contact.phone = phone
    }

    fun addContact(activity: Activity){
        val returnIntent = Intent()
        returnIntent.putExtra("new_contact", contact)
        activity.setResult(Activity.RESULT_OK, returnIntent)
        activity.finish()
    }

    //interfaz que define nuestra vista
    interface View{
        fun addContact()
    }

}