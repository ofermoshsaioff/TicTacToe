package example.com.houzz.ticktacktoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import example.com.houzz.ticktacktoe.ui.main.MainFragment



class MainActivity : AppCompatActivity() {

    private lateinit var fragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        fragment = MainFragment.newInstance()

        if (savedInstanceState == null) {
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.undo -> fragment.undo()
            R.id.redo -> fragment.redo()
            R.id.reset -> fragment.reset()
        }
        return super.onOptionsItemSelected(item)
    }

}
