package example.com.houzz.ticktacktoe.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.com.houzz.ticktacktoe.R

class MainFragment : Fragment(), GameController {

    private lateinit var mainView: CustomView

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainView = view.findViewById(R.id.mainView)
    }

    override fun undo() {}
    override fun redo() {}
    override fun reset() {}
}
