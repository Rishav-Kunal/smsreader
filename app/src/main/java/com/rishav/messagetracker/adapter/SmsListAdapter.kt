package com.rishav.messagetracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rishav.messagetracker.R
import com.rishav.messagetracker.data.StoredSms
import com.rishav.messagetracker.databinding.SmsListHeaderBinding
import com.rishav.messagetracker.databinding.SmsListItemBinding
import com.rishav.messagetracker.util.ApplicationConstants
import com.rishav.messagetracker.util.ItemClickListener
import javax.inject.Inject

class SmsListAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<Any> = ArrayList()
    var clickListener : ItemClickListener<StoredSms>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ApplicationConstants.VIEW_TYPE_SMS) {
            val smsListItemBinding: SmsListItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.sms_list_item, parent, false
            )
            return SmsViewHolder(smsListItemBinding)
        } else{
            val smsListHeaderItemBinding: SmsListHeaderBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.sms_list_header, parent, false
            )
            return SmsHeaderViewHolder(smsListHeaderItemBinding)
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SmsViewHolder){
            holder.bind(data[position] as StoredSms)
        } else{
            (holder as SmsHeaderViewHolder).bind(data[position] as String)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (data[position] is StoredSms) ApplicationConstants.VIEW_TYPE_SMS else ApplicationConstants.VIEW_TYPE_HEADER
    }

    fun updateData(newList: List<Any>) {
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
    inner class SmsHeaderViewHolder(private val binding: SmsListHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(itemView) {
            binding.header = item
        }
    }

    inner class ResultDiffUtil(var oldList: List<Any>, var newList: List<Any>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return if ( oldList[oldItemPosition].javaClass == newList[newItemPosition].javaClass){
                if (oldList[oldItemPosition] is StoredSms && newList[newItemPosition] is StoredSms){
                    (oldList[oldItemPosition] as StoredSms).timeInMillis == (newList[newItemPosition] as StoredSms).timeInMillis
                } else{
                    oldList[oldItemPosition] == newList[newItemPosition]
                }
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}