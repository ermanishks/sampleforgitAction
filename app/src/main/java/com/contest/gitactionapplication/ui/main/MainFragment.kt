package com.contest.gitactionapplication.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.contest.gitactionapplication.R
import com.contest.gitactionapplication.base.Status
import com.contest.gitactionapplication.base.ViewModelFactory
import com.contest.gitactionapplication.databinding.MainFragmentBinding
import com.contest.gitactionapplication.network.NetworkService
import com.contest.gitactionapplication.repo.MovieRepository
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        binding = DataBindingUtil.bind(view)!!
        binding.lifecycleOwner = this
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieRepository = MovieRepository(NetworkService(context!!))
        val  viewModel: MainViewModel by viewModels<MainViewModel> { ViewModelFactory(movieRepository) }

        viewModel.searchMovie("Titanic")
        binding.txtSearchMovie.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                binding.btnSearch.isEnabled = s.isNotEmpty()
            }
        })
        binding.btnSearch.setOnClickListener {
            viewModel.searchMovie(binding.txtSearchMovie.text.toString())
        }
        viewModel.movieInfo.observe(viewLifecycleOwner){ resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    lbl_plot.visibility= View.VISIBLE
                    progress_bar.visibility = View.GONE
                    binding.model = resource.data
                    Glide.with(binding.imgPoster.context)
                        .load(resource.data!!.poster)
                        .into(binding.imgPoster)
                }
                Status.LOADING -> {
                    lbl_plot.visibility= View.GONE
                    progress_bar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    lbl_plot.visibility= View.GONE
                    progress_bar.visibility = View.GONE
                    //Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        }

}
