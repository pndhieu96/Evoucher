package com.example.codebaseandroidapp.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseFragment<T: ViewBinding>
    (private val inflateMethod : (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment() {

    private var _binding : T? = null
    val binding: T get() = _binding!!
    val navController: NavController by lazy { NavHostFragment.findNavController(this) }
    var isInitView = AtomicBoolean(false)

    abstract fun initObserve()
    abstract fun initialize()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onCreateView")

        if(_binding == null) {
            _binding = inflateMethod.invoke(inflater, container, false)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onViewCreated")

        initObserve()
        if(isInitView.getAndSet(true).not())
            initialize()
    }

    override fun onStart() {
        super.onStart()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onDestroy")
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("FragmentLifecycle", "${this.javaClass.simpleName} - onDetach")
    }
}