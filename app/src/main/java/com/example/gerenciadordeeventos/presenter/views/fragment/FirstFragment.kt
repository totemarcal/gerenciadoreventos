package com.example.gerenciadordeeventos.presenter.views.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventIntent
import com.example.cleanmvvmapp.presenter.mvi.features.carro.EventState
import com.example.gerenciadordeeventos.R
import com.example.gerenciadordeeventos.databinding.FragmentFirstBinding
import com.example.gerenciadordeeventos.presenter.adapter.EventsAdapter
import com.example.gerenciadordeeventos.presenter.adapter.OnCLickEvent
import com.example.gerenciadordeeventos.presenter.model.EventUiModel
import com.example.gerenciadordeeventos.presenter.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnCLickEvent {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel : EventViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onResume() {
        super.onResume()
        viewModel.liveData.observe(this,{ state ->
            when(state) {
                is EventState.ResultAllEvents -> {
                    updateEvents(state.data)
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
            viewModel.dispatchIntent(EventIntent.LoadAllEvents)
        }, 2000)

    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if(show) View.VISIBLE else View.GONE
        recycler.visibility = if(show) View.GONE else View.VISIBLE
    }

    private fun updateEvents(events: List<EventUiModel>) {
        recycler.adapter = EventsAdapter(events = events,this)
        recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onClickEvent(carroUiModel: EventUiModel) {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}