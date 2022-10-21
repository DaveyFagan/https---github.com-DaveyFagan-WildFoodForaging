package org.wit.foraging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.foraging.databinding.CardForagingBinding
import org.wit.foraging.models.ForagingModel

interface ForagingListener {
    fun onForagingClick(foraging: ForagingModel)
}

class ForagingAdapter constructor(private var foragingList: List<ForagingModel>,
                                  private val listener: ForagingListener) :
    RecyclerView.Adapter<ForagingAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardForagingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val foraging = foragingList[holder.adapterPosition]
        holder.bind(foraging, listener)
    }

    override fun getItemCount(): Int = foragingList.size

    class MainHolder(private val binding : CardForagingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foraging: ForagingModel, listener: ForagingListener) {
            binding.foragingPlantNameTitle.text = foraging.name
            binding.foragingPlantScientificName.text = foraging.scientificName
            Picasso.get().load(foraging.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onForagingClick(foraging) }

        }
    }
}