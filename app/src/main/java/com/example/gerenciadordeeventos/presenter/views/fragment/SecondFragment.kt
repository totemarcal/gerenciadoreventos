package com.example.gerenciadordeeventos.presenter.views.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventIntent
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventState
import com.example.gerenciadordeeventos.R
import com.example.gerenciadordeeventos.databinding.FragmentSecondBinding
import com.example.gerenciadordeeventos.presenter.model.EventUiModel
import com.example.gerenciadordeeventos.presenter.viewmodel.EventViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.item_event.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel : EventViewModel by viewModel()
    private var id: String = ""

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onResume() {
        id = arguments?.getString("id").toString()
        viewModel.setId(id)

        viewModel.liveData.observe(this,{ state ->
            when(state) {
                is EventState.ResultEventId -> {
                    loadDisplay(state.data)
                }
                is EventState.Error -> {
                    Toast.makeText(context, state.error?.message, Toast.LENGTH_LONG).show()
                }
                is EventState.Loading -> {
                    if(state.loading) {
                        showLoading(true)
                    }
                    else {
                        showLoading(false)
                    }
                }
            }
        })

        Handler().postDelayed({
            viewModel.dispatchIntent(EventIntent.LoadEventId)
        }, 2000)

        super.onResume()
    }

    private fun loadDisplay(data: EventUiModel) {
        Picasso.get().load(data.image).into(imgEventDetails)
        tvEventDetails.text = data.description
        textView3.text = data.price
    }


    private fun showLoading(show: Boolean) {
        progressBarSec.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}