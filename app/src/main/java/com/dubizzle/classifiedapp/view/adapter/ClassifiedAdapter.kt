package com.dubizzle.classifiedapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dubizzle.classifiedapp.R
import com.dubizzle.classifiedapp.databinding.ListItemClassifiedBinding
import com.dubizzle.classifiedapp.model.ClassifiedResults
import com.dubizzle.classifiedapp.utils.Constants
import com.dubizzle.dubicache.core.DubiCache
import javax.inject.Inject

class ClassifiedAdapter @Inject constructor() : RecyclerView.Adapter<ClassifiedAdapter.ClassifiedViewHolder>(){

    private var dataList = ArrayList<ClassifiedResults>()
    var onItemClick: ((item: ClassifiedResults, view: View) -> Unit)? = null

    /*fun setData(data: List<ClassifiedResults>) {
        this.dataList = data
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassifiedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemClassifiedBinding.inflate(inflater, parent, false)
        return ClassifiedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassifiedViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    fun updateAdapter(newList: List<ClassifiedResults>) {
        val diffCallback = ClassifiedDiffCallback(dataList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
       return dataList.size
    }

    inner class ClassifiedViewHolder(private var binding: ListItemClassifiedBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(data: ClassifiedResults) {
            binding.apply {
                nameClassified.text = data.name
                priceClassified.text = data.price
                DubiCache.getInstance(itemView.context, Constants.CACHE_SIZE)
                    .displayImage(data.image_urls_thumbnails[0], imgClassified, R.drawable.ic_gift)
            }
        }

        override fun onClick(v: View?) {
            if (v != null) {
                onItemClick?.invoke(dataList[adapterPosition], v)
            }
        }
    }


    class ClassifiedDiffCallback(
        private val oldList: List<ClassifiedResults?>,
        private val newList: List<ClassifiedResults?>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return true
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldVersion = if (oldPosition < oldList.size) oldList[oldPosition] else null
            val newVersion = if (newPosition < newList.size) newList[newPosition] else null

            return oldVersion?.name == newVersion?.name &&
                    oldVersion?.uid == newVersion?.uid &&
                    oldVersion?.image_urls?.get(0) == newVersion?.image_urls?.get(0)
        }
    }
}