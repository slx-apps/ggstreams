package com.nlx.ggstreams.auth.user

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.nlx.ggstreams.R
import com.nlx.ggstreams.auth.AuthActivity
import javax.inject.Inject

class UserFragment : Fragment() {


    @Inject
    lateinit var viewModel: AuthViewModel

    companion object {
        fun newInstance(): UserFragment {
            val newsFragment = UserFragment()
            val args = Bundle()
            newsFragment.arguments = args
            return newsFragment
        }
    }

    override fun onAttach(context: Context) {
        (activity as AuthActivity).useComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fr_user, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fr_account_info, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_sign_out) {
            viewModel.logout()
            activity?.finish()
        }

        return super.onOptionsItemSelected(item)

    }
}