package com.test.noteappkb.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.test.noteappkb.R
import com.test.noteappkb.core.data.DbDao
import com.test.noteappkb.core.data.DbNote
import com.test.noteappkb.core.data.NoteEntities
import com.test.noteappkb.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter = MainAdapter()
    private lateinit var noteDao: DbDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initController()
    }

    private fun initController() {
        initUI()
        initListener()
    }

    private fun initUI() {
        val db = DbNote.getInstance(this)
        noteDao = db.dbDao()
        binding.rvMain.adapter = mainAdapter
        getData()
    }

    private fun insertData(data: NoteEntities) {
        lifecycleScope.launch {
            noteDao.addNewNote(data)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        lifecycleScope.launch {
            mainAdapter.data.clear()
            mainAdapter.data.addAll(noteDao.getList())
            mainAdapter.notifyDataSetChanged()
        }
    }

    private fun initListener() {
        binding.apply {
            llcNewPayment.setOnClickListener {
                CustomDialog(WeakReference(this@MainActivity), R.layout.view_dialog) {
                    lifecycleScope.launch {
                        if (it.name.isNullOrEmpty() || it.total == 0L || it.type.isNullOrEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.fill_data_first),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }
                        insertData(data = it.copy(date = getCurrentDate()))
                        delay(500)
                        getData()
                    }
                }
            }

        }
    }
}