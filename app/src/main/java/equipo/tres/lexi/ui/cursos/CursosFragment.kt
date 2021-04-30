package equipo.tres.lexi.ui.cursos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import equipo.tres.lexi.R

class CursosFragment : Fragment() {

    private lateinit var cursosViewModel: CursosViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cursosViewModel =
            ViewModelProvider(this).get(CursosViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cursos, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        cursosViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }
}