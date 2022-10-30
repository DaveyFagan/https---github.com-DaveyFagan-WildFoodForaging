package org.wit.foraging.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.R
import org.wit.foraging.databinding.ActivityCreateUserBinding
import org.wit.foraging.main.MainApp
import org.wit.foraging.models.UserModel
import timber.log.Timber


class ForagingUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateUserBinding
    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        binding.buttonAddNewuser.setOnClickListener() {
            user.username = binding.createUsername.text.toString()
            user.email = binding.createEmail.text.toString()
            user.password = binding.createPassword.text.toString()
            if (user.username.isNotEmpty() and user.email.isNotEmpty() and user.password.isNotEmpty()) {
                Timber.i("You added ${user.username}, ${user.email}, ${user.password}")
                app.userList.createUser(user.copy())
            } else {
                Snackbar
                    .make(it, R.string.enter_all_fields, Snackbar.LENGTH_LONG)
                    .show()
            }
            setResult(RESULT_OK)
            finish()
        }


    }
}