package com.nlx.ggstreams.auth.login

import android.content.Context
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.nlx.ggstreams.R
import com.nlx.ggstreams.auth.login.mvp.LoginMVP
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fr_login.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginFragment : RxFragment(), LoginMVP.View {

    @Inject
    lateinit var presenter: LoginMVP.Presenter

    companion object {
        const val TAG = "LoginFragment"
        const val DELAY = 300L
        fun newInstance(): LoginFragment {
            val loginFragment = LoginFragment()
            val args = Bundle()
            loginFragment.arguments = args
            return loginFragment
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fr_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        val (_, _, login) = presenter.getProfile()
        etLogin.setText(login)

        // disable until fields filled
        buttonLogin.isEnabled = false

        Observable.combineLatest(
                RxTextView.textChanges(etLogin).skip(1).delay(DELAY, TimeUnit.MILLISECONDS),
                RxTextView.textChanges(etPassword).skip(1).delay(DELAY, TimeUnit.MILLISECONDS),
                BiFunction {
                    login: CharSequence, pass: CharSequence ->
                    presenter.isValid(login, pass)
                }
                )
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    buttonLogin.isEnabled = it
                }, {
                    Snackbar.make(loginContainer, R.string.error_general, Snackbar.LENGTH_SHORT).show()
                })


        RxView.clicks(buttonLogin)
                //.delay(DELAY, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .subscribe {
                    presenter.chatLogin(etLogin.text.toString(), etPassword.text.toString())
                }
*/
    }

    override fun userLoggedIn() {
        activity?.finish()
    }

    override fun handleErrors(e: Throwable) {
        Snackbar.make(loginContainer, R.string.auth_error_login, Snackbar.LENGTH_LONG).show()
    }
}