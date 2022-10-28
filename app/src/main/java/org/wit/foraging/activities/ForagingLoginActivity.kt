package org.wit.foraging.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.R
import org.wit.foraging.databinding.ActivityForagingLoginBinding
import org.wit.foraging.main.MainApp
import org.wit.foraging.models.UserModel
import timber.log.Timber


class ForagingLoginActivity: AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityForagingLoginBinding
//    private var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForagingLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        binding.login.setOnClickListener {
            val enteredEmail = binding.enterEmail.text.toString()
            val enteredPassword = binding.enterPassword.text.toString()
            val userList = app.userList.findAllUsers()

            for (user in userList) {
                if (enteredEmail.isEmpty() and enteredPassword.isEmpty()) {
                    Snackbar
                        .make(it, R.string.enter_all_fields, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    if ((enteredEmail == user.email) and (enteredPassword == user.password)) {
                        Timber.i("You entered ${enteredEmail}, $enteredPassword")
                        val launcherIntent = Intent(this, ForagingListActivity::class.java)
                        startActivity(launcherIntent)
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Snackbar
                            .make(it, R.string.enter_correct_fields, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

        binding.createUser.setOnClickListener {
            Timber.i("Create user Pressed")
            val launcherIntent = Intent(this, ForagingUserActivity::class.java)
            startActivity(launcherIntent)
        }



    }





}

