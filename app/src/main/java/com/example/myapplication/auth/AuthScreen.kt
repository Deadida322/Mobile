package com.example.myapplication.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAuthScreenBinding
import com.example.myapplication.help.HelpScreen
import com.jakewharton.rxbinding.widget.RxTextView
import com.utils.loadFragment
import rx.Observable
import rx.Observer
import rx.Subscription
const val PASS_KEY = "PASS"
const val MAIL_KEY = "MAIL"
class AuthScreen : Fragment() {
    private lateinit var binding: FragmentAuthScreenBinding
    private var subscription: Subscription? = null
    private var currentEmail: String = ""
    private var currentPassword: String = ""
    lateinit var emailInputObservable: Observable<CharSequence>
    lateinit var passwordInputObservable: Observable<CharSequence>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthScreenBinding.inflate(inflater)

        if (savedInstanceState !== null) {
            currentEmail = savedInstanceState.getString(MAIL_KEY).toString()
            currentPassword = savedInstanceState.getString(PASS_KEY).toString()
            binding.authPassword.setText(currentPassword)
            binding.authMail.setText(currentEmail)
        }
        binding.apply {
            authBtn.setOnClickListener {
                loadFragment(parentFragmentManager, HelpScreen(), R.id.fragmentContainer)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailInputObservable = RxTextView.textChanges(binding.authMail)
        passwordInputObservable = RxTextView.textChanges(binding.authPassword)
        combineEvent()
    }

    private fun combineEvent() {
        subscription = Observable.combineLatest(
            emailInputObservable,
            passwordInputObservable
        ) { newEmail, newPassword ->
            currentEmail = newEmail.toString()
            currentPassword = newPassword.toString()
            val emailValid = (!TextUtils.isEmpty(newEmail) && newEmail.length > 6)
            if (!emailValid) {
                binding.authMail.error = "Слишком короткая почта"
            }
            val passValid = (!TextUtils.isEmpty(newPassword) && newPassword.length > 6)
            if (!passValid) {
                binding.authPassword.error = "Слишком короткий пароль"
            }
            emailValid && passValid
        }.subscribe(object : Observer<Boolean?> {
            override fun onCompleted() {}
            override fun onError(e: Throwable?) {}
            override fun onNext(t: Boolean?) {
                binding.authBtn.isEnabled = t == true
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(MAIL_KEY, currentEmail)
        outState.putString(PASS_KEY, currentPassword)
    }
}
