package uk.co.bbc.elections.ui.results

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import uk.co.bbc.elections.databinding.FragmentResultsBinding
import uk.co.bbc.elections.helper.ConnectivityHelper


class ResultsFragment : Fragment() {

    private val viewModel by viewModels<ResultsViewModel>()

    private var _binding: FragmentResultsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentResultsBinding get() = _binding!!

    private val resultsAdapter: ResultsAdapter = ResultsAdapter()

    private val connectivityManager by lazy {
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private var isNetworkAvailable = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isNetworkAvailable = true
            // Notify app about network availability (optional)
            // You can implement a listener interface or broadcast a message here
            networkState()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isNetworkAvailable = false
            // Notify app about network loss (optional)
            networkState()
        }
    }

    fun networkState(){
        requireActivity().runOnUiThread {
            if(!isNetworkAvailable){
                binding.genericError.visibility = View.VISIBLE
            }else{
                binding.genericError.visibility = View.GONE
            }
        }
    }

    private fun registerNetworkCallback() {
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    private fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        registerNetworkCallback()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterNetworkCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareViews()
        observeViewModel()
    }

    private fun prepareViews() {
        with(binding.resultsRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            adapter = resultsAdapter
        }
        loadData()
        binding.refreshBtn.setOnClickListener {
            loadData(true)
        }
    }

    private fun loadData(showDialog: Boolean = false) {
        if(!ConnectivityHelper.checkInternetConnection(requireContext())){
            binding.genericError.visibility = View.VISIBLE
            if(showDialog)ConnectivityHelper.showDialog(requireContext())
        }else{
            binding.genericError.visibility = View.GONE
            viewModel.refresh()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { renderViewState(it) }
        }
    }

    private fun renderViewState(viewState: ResultsViewState) {
        if (viewState is ResultsViewState.Loaded) {
            // Find result with most votes and Set winner flag for result with most votes
            viewState.results.maxByOrNull { it.votes }?.isWinner = true
            resultsAdapter.submitList(viewState.results)
            binding.refreshBtn.visibility = if(!viewState.metadata.isComplete) View.VISIBLE else View.GONE
            binding.countingDone.visibility = if(viewState.metadata.isComplete) View.VISIBLE else View.GONE
            resultsAdapter.markResultsDeclared(viewState.metadata.isComplete)
        }
    }

    companion object {
        fun newInstance() = ResultsFragment()
    }
}