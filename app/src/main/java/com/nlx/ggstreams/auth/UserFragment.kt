package com.nlx.ggstreams.auth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.nlx.ggstreams.R
import com.nlx.ggstreams.auth.login.di.AuthManager
import com.nlx.ggstreams.auth.user.mvp.UserProfileMVP
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fr_user.*
import javax.inject.Inject

class UserFragment : Fragment(), UserProfileMVP.View {


    @Inject
    lateinit var presenter: UserProfileMVP.Presenter

    companion object {
        fun newInstance(): UserFragment {
            val newsFragment = UserFragment()
            val args = Bundle()
            newsFragment.arguments = args
            return newsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fr_user, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (presenter.getProfile()?.userId != -1) {
            tvProfileUsername.text = presenter.getProfile()?.login
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_fr_account_info, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        if (id == R.id.action_sign_out) {
            presenter.logout()
            activity?.finish()
        }

        return super.onOptionsItemSelected(item)

    }

    override fun handleErrors(e: Throwable) {

    }
}