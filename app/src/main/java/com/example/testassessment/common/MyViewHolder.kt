package com.example.testassessment.common
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.testassessment.BR


class MyViewHolder(val binding: ViewDataBinding) :RecyclerView.ViewHolder(binding.root) {
    fun bind(obj: Any, cb: Any?, pos: Int) {
        binding.setVariable(BR.callback, cb)
        binding.setVariable(BR.position, pos)
        binding.setVariable(BR.data, obj)
        binding.executePendingBindings()
    }
}