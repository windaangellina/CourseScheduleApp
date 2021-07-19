package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.databinding.ActivityAddBinding
import com.dicoding.courseschedule.util.FunctionLibrary
import com.dicoding.courseschedule.util.TimePickerFragment

class AddActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    // time picker
    private var tagTimePicker : String? = null
    private var startTime = ""
    private var endTime = ""

    // view binding
    private var _binding : ActivityAddBinding? = null
    private val binding get() = _binding!!

    // view model
    private lateinit var addCourseViewModel : AddCourseViewModel

    companion object{
        const val TAG_TP_START = "timePickerStart"
        const val TAG_TP_END = "timePickerEnd"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tagTimePicker = TAG_TP_START
        addCourseViewModel = AddCourseViewModel(
            DataRepository.getInstance(applicationContext)!!
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getByDayName(dayName : String) : Int {
        return when(dayName) {
            "Monday" -> 1
            "Tuesday" -> 2
            "Wednesday" -> 3
            "Thursday" -> 4
            "Friday" -> 5
            "Saturday" -> 6
            "Sunday" -> 7
            else -> 1
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_insert -> {
                val day = binding.spinnerDay.selectedItem.toString()
                val dayNumber = getByDayName(dayName = day)
                addCourseViewModel.insertCourse(
                    binding.addEdCourseName.text.toString(),
                    dayNumber,
                    startTime,
                    endTime,
                    binding.addEdLecturerName.text.toString(),
                    binding.addEdNote.text.toString()
                )
                finish()
                addCourseViewModel.saved.observe(this, { event ->
                    event.getContentIfNotHandled().let { isSaved ->
                        if (isSaved == true){
                            FunctionLibrary.showToast(applicationContext,
                                "New schedule has been saved"
                            )
                        }
                    }
                })
            }
            android.R.id.home -> finish() //default back button
        }
        return super.onOptionsItemSelected(item)
    }

    fun showTimePicker(view: View) {
        val dialogTimePicker = TimePickerFragment()
        when(view.id){
            R.id.add_iv_start_time -> {
                tagTimePicker = TAG_TP_START
                dialogTimePicker.show(supportFragmentManager, tagTimePicker)
            }
            R.id.add_iv_end_time -> {
                tagTimePicker = TAG_TP_END
                dialogTimePicker.show(supportFragmentManager, tagTimePicker)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if (tagTimePicker == TAG_TP_START){
            // left pad dengan char '0' sepanjang 2 karakter
            startTime = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            binding.addTvStartTime.text = startTime
        }
        else if (tagTimePicker == TAG_TP_END){
            endTime = String.format("%02d", hour) + ":" + String.format("%02d", minute)
            binding.addTvEndTime.text = endTime
        }
    }


}