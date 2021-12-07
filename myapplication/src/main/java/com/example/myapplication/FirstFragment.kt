package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.art_dev_chapter2.MessengerService
import com.example.myapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    companion object {
        private const val TAG = "FirstFragment"
    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var messenger: Messenger? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            messenger = Messenger(service).apply {
                send(Message.obtain(null, 1).also {
                    Bundle().apply {
                        putString("123", "123")
                        it.data = this
                    }
                })
            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(TAG, "断开了")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unbindService(connection)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val intent = Intent(context, MessengerService::class.java)
            context?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}