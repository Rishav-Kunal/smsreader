package com.rishav.messagetracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rishav.messagetracker.R
import com.rishav.messagetracker.data.StoredSms
import com.rishav.messagetracker.databinding.SmsListItemBinding
import com.rishav.messagetracker.util.ItemClickListener
import javax.inject.Inject

class SmsListAdapter @Inject constructor() : RecyclerView.Adapter<SmsListAdapter.SmsViewHolder>() {

    private var data: List<StoredSms> = ArrayList()
    var clickListener : ItemClickListener<StoredSms>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val smsListItemBinding: SmsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.sms_list_item, parent, false
        )
        return SmsViewHolder(smsListItemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) =
        holder.bind(data[position])

    fun updateData(newList: List<StoredSms>) {
        val diffResult: DiffUtil.DiffResult =
            DiffUtil.calculateDiff(ResultDiffUtil(data, newList))
        diffResult.dispatchUpdatesTo(this)
        data = newList
    }

    inner class SmsViewHolder(private val binding: SmsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoredSms) = with(itemView) {
            binding.sms = item

            binding.clSms.setOnClickListener { clickListener?.onItemClicked(item) }
            binding.tvName.setOnClickListener { clickListener?.onItemClicked(item) }
            binding.tvMessage.setOnClickListener { clickListener?.onItemClicked(item) }
        }
    }

    inner class ResultDiffUtil(var oldList: List<StoredSms>, var newList: List<StoredSms>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].timeInMillis == newList[newItemPosition].timeInMillis
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}