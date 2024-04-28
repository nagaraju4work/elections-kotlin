package uk.co.bbc.elections.ui.home

import kotlinx.coroutines.CompletableDeferred
import uk.co.bbc.elections.api.Candidate
import uk.co.bbc.elections.api.Results
import uk.co.bbc.elections.api.ResultsService

class StubResultsService : ResultsService {

    private var _latestResults: CompletableDeferred<Results> = CompletableDeferred()

    private var _allCandidates: CompletableDeferred<List<Candidate>> = CompletableDeferred()

    fun dispatchResults(results: Results = Results(false, emptyList())) {
        _latestResults.complete(results)
        _latestResults = CompletableDeferred()
    }

    fun dispatchCandidates(candidates: List<Candidate> = emptyList()) {
        _allCandidates.complete(candidates)
        _allCandidates = CompletableDeferred()
    }

    override suspend fun latestResults() = _latestResults.await()
    override suspend fun allCandidates(): List<Candidate> = _allCandidates.await()
}
