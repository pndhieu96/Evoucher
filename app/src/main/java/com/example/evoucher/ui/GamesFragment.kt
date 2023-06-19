package com.example.evoucher.ui

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.adapter.GamesAdapter
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentGamesBinding
import com.example.evoucher.model.Game
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_GAME_LUCKY_NUMBER
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_GAME_ROLL_DICE
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_GAME_ROULETTE
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.GamesVM
import dagger.hilt.android.AndroidEntryPoint
import java.util.stream.Collectors

@AndroidEntryPoint
class GamesFragment : BaseFragment<FragmentGamesBinding>(FragmentGamesBinding::inflate) {

    private val vm : GamesVM by viewModels()
    private val args: GamesFragmentArgs by navArgs()
    lateinit var adapter: GamesAdapter
    lateinit var gameIds : Array<String>

    override fun initObserve() {
        vm.games.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = View.GONE
                if(it.size > 0) {

                    adapter.list = it.filter {
                        obj -> gameIds.contains(obj.id.toString())
                    }
                    adapter.notifyDataSetChanged()
                    binding.rv.visibility = View.VISIBLE
                } else {
                    binding.rv.visibility = View.GONE
                }
            }, onError = {
                showError(it.statusMessage[0])
            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
    }

    override fun initialize() {
        val campaign = args.campaignArg
        val partner = args.partnerArg
        gameIds = args.gameIdsArg ?: arrayOf()
        vm.getGames()
        binding.tb.setTitle("Danh sách trò chơi")
        binding.tb.callBack = object : TopBar.CallBack {
            override fun onClick() {
                navController.popBackStack()
            }

        }

        adapter = GamesAdapter(listOf())
        binding.rv.adapter = adapter
        adapter.callBack = object : GamesAdapter.CallBack {

            override fun onClick(item: Game) {
                var action : NavDirections? = null
                if(item.id == TYPE_GAME_LUCKY_NUMBER) {
                    action = GamesFragmentDirections.actionGamesFragmentToLuckyNumberFragment(
                        campaign,
                        item,
                        partner
                    )
                } else if(item.id == TYPE_GAME_ROLL_DICE) {
                    action = GamesFragmentDirections.actionGamesFragmentToRollDiceFragment(
                        campaign,
                        item,
                        partner
                    )
                } else if(item.id == TYPE_GAME_ROULETTE) {
                    action = GamesFragmentDirections.actionGamesFragmentToRouletteFragment(
                        campaign,
                        item,
                        partner
                    )
                }
                action?.let {
                    navController.navigate(it)
                }
            }
        }
    }

    private fun showError(messager: String) {
        CustomToast.makeText(context, messager, CustomToast.LENGTH_LONG, CustomToast.ERROR, false).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GamesFragment()
    }
}