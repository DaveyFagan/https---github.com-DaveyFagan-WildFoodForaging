package org.wit.foraging.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.foraging.R
import org.wit.foraging.databinding.ActivityForagingLoginBinding
import org.wit.foraging.main.MainApp
import timber.log.Timber
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreen
import android.view.View
import android.view.ViewTreeObserver


class ForagingLoginActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityForagingLoginBinding
    var contentHasLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        startLoadingContent()
        setupSplashScreen(splashScreen)

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

    private fun startLoadingContent() {
        binding = ActivityForagingLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        contentHasLoaded = true

    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (contentHasLoaded) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }
}

