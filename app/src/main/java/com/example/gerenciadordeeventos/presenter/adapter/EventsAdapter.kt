package com.example.gerenciadordeeventos.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gerenciadordeeventos.R
import com.example.gerenciadordeeventos.presenter.model.EventUiModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_event.view.*

class EventsAdapter(
    private val events : List<EventUiModel>,
    private val onCLickEvent: OnCLickEvent
) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.itemView.apply {
            tvTitle.text = event.id +" - "+ event.title
            //tvId.text = event.id
            Picasso.get().load(event.image).into(imgEvent)
            clRoot.setOnClickListener { onCLickEvent.onClickEvent(event) }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }
}