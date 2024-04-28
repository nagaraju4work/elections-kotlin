package uk.co.bbc.elections.ui.results

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uk.co.bbc.elections.R
import uk.co.bbc.elections.databinding.ViewResultItemBinding
import uk.co.bbc.elections.ui.results.ResultsViewState.Loaded.ResultsItemViewData


class ResultsAdapter :
    ListAdapter<ResultsItemViewData, ResultsAdapter.ResultsItemViewHolder>(ResultsDiffCallback) {

    private var isComplete: Boolean =false

    class ResultsItemViewHolder(private val binding: ViewResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ResultsItemViewData, isComplete: Boolean) {
            binding.partyTv.text = applyColorsToText("Party : ", item.party)
            binding.candidateTv.text = applyColorsToText("Candidate : ", item.candidate.toString())
            binding.votesTv.text = applyColorsToText("Votes : ", item.votes.toString())
            binding.ivWinner.visibility = if(item.isWinner && isComplete) View.VISIBLE else View.GONE
        }

        private fun applyColorsToText(textPart1: String, textPart2: String): SpannableStringBuilder {
            // Create a SpannableStringBuilder
            val spannableStringBuilder = SpannableStringBuilder()

            // Append the first part of the text with the first color
            spannableStringBuilder.append(textPart1)
            spannableStringBuilder.setSpan(ForegroundColorSpan(Color.RED), 0, textPart1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Append the second part of the text with the second color
            spannableStringBuilder.append(textPart2)
            spannableStringBuilder.setSpan(ForegroundColorSpan(Color.BLUE), textPart1.length, spannableStringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // return the SpannableStringBuilder
            return spannableStringBuilder
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsItemViewHolder {
        val binding =
            ViewResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultsItemViewHolder, position: Int) {
        holder.bindItem(getItem(position), isComplete)
        updateHighlight(holder.itemView, getItem(position).isWinner)
    }

    fun markResultsDeclared(isComplete: Boolean){
        this.isComplete = isComplete
    }

    private fun updateHighlight(itemView: View, isWinner: Boolean) {
        if (isComplete && isWinner) {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red_200)) // Set highlight color
        } else {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white)) // Set default color
        }
    }

}

object ResultsDiffCallback : DiffUtil.ItemCallback<ResultsItemViewData>() {
    override fun areItemsTheSame(
        oldItem: ResultsItemViewData,
        newItem: ResultsItemViewData
    ): Boolean = (oldItem === newItem)

    override fun areContentsTheSame(
        oldItem: ResultsItemViewData,
        newItem: ResultsItemViewData
    ): Boolean = (oldItem == newItem)
}
