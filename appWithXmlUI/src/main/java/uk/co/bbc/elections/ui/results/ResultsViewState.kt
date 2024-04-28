package uk.co.bbc.elections.ui.results

import uk.co.bbc.elections.api.Candidate
import uk.co.bbc.elections.api.Results

sealed class ResultsViewState {
    object Loading : ResultsViewState()

    data class Error(val error: String) : ResultsViewState()

    data class Loaded(val results: List<ResultsItemViewData>, val metadata: MetaData) : ResultsViewState() {
        data class ResultsItemViewData(
            val party: String,
            val candidate: String?,
            val votes: Int,
            var isWinner: Boolean = false
        )

        data class MetaData(val isComplete: Boolean)

        companion object {
            fun fromResults(results: Results, fetchedCandidates: List<Candidate> = listOf()): Loaded {
                val resultsItems = results.results.map {
                    ResultsItemViewData(
                        it.party,
                        if(fetchedCandidates.isEmpty()) it.candidateId.toString() else fetchedCandidates.find { candidate -> it.candidateId == candidate.id }?.name,
                        it.votes
                    )
                }
                val metadata = MetaData(results.isComplete)

                return Loaded(resultsItems,metadata)
            }
        }
    }
}