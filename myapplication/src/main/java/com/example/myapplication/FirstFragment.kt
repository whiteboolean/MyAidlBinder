package com.example.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
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
    private var mService: Messenger? = null
    private val messageHandler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            2 -> {
                Log.i(TAG, "receive msg from Server:${msg.data.getString("reply")}")
//                    mService = Messenger(service)
            }
        }
        false
    }

    private val mGetReplyMessenger: Messenger = Messenger(messageHandler)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            mService = Messenger(service).also { it ->
//                it.send(Message.obtain(null, 1).also {
//                    it.data = Bundle().apply {
//                        putString("2324", "234")
//                    }
//                })
//            }

            mService = Messenger(service).also {
                it.send(Message.obtain(null, 2).apply {
                    data = Bundle().apply {
                        putString("msg", "Hello ,this is client")
                        replyTo = mGetReplyMessenger
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
        val intent = Intent(context, MessengerService::class.java)
        context?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        binding.buttonFirst.setOnClickListener {

//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}