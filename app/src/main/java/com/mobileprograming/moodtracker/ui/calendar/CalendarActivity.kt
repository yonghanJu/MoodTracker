package com.mobileprograming.moodtracker.ui.calendar

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import com.mobileprograming.moodtracker.data.Diary
import com.mobileprograming.moodtracker.data.MyDBHelper
import com.mobileprograming.moodtracker.databinding.ActivityCalendarBinding
import com.mobileprograming.moodtracker.ui.detail.DetailActivity
import com.mobileprograming.moodtracker.ui.diarylist.DiaryListActivity
import com.mobileprograming.moodtracker.ui.setting.SettingActivity
import com.mobileprograming.moodtracker.ui.writing.TestWritingActivity
import com.mobileprograming.moodtracker.ui.writing.WritingActivity
import com.mobileprograming.moodtracker.util.IntentKey
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var myDBHelper: MyDBHelper
    lateinit var diaryList : List<Diary>
    lateinit var localDate : LocalDate
    lateinit var adapter : CalendarViewAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        localDate = LocalDate.now()
        initDB()
        initList()

        initRecyclerVIew()
        initBtnListener()
        initMoodImageListener()

        initTestWritingListener()

        setMonthYearTextView(localDate)
        setRecyclerView(localDate)

        initEmoticon()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setRecyclerView(localDate)
    }

    private fun initDB() {
        myDBHelper = MyDBHelper(this)
    }

    private fun initList() {
        // ?????? ????????? ?????? ???????????????????????? ???????????? ????????? ?????? ??????
        // diaryList = myDBHelper.getAllDiary()
        // ???????????? ???????????? ??????????????????.
//        diaryList = listOf(
//            Diary(0,0,"test 0",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
//            Diary(1,1,"test 1",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()),
//            Diary(2,2,"test 2",
//                ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()))
    }

    private fun intentSettingActivity(){
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun intentDiaryListActivity(){
        val intent = Intent(this, DiaryListActivity::class.java)
        startActivity(intent)
    }

    // ????????? ???????????? WritingActivity ??????
    private fun intentWriteDiary(mood:Int){
        val intent = Intent(this, WritingActivity::class.java)
        intent.putExtra(IntentKey.MOOD_KEY, mood)
        startActivity(intent)
    }

    // Diary ????????? ????????? DetailActivity ??????
//    private fun intentDetail(diary: Diary){
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra(IntentKey.DIARY_KEY, diary)
//        startActivity(intent)
//    }

    private fun intentDetail(ldate : Long){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(IntentKey.DIARY_KEY, ldate)
        startActivity(intent)
    }

    private fun initMoodImageListener(){
        binding.apply {
            activityCalendarMood0.setOnClickListener {
                intentWriteDiary(0)
            }
            activityCalendarMood1.setOnClickListener {
                intentWriteDiary(1)
            }
            activityCalendarMood2.setOnClickListener {
                intentWriteDiary(2)
            }
            activityCalendarMood3.setOnClickListener {
                intentWriteDiary(3)
            }
            activityCalendarMood4.setOnClickListener {
                intentWriteDiary(4)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(): Long {
        val localDate = LocalDate.now()
        val y = localDate.year
        val m = localDate.monthValue
        val d = localDate.dayOfMonth
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val formatStr = y.toString() + "." + m.toString() + "." + d.toString()
        val date = sdf.parse(formatStr)
        val ldate = date.time
        return ldate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initEmoticon(){
        initDB()
        val ldate = getTime()
        var diary = myDBHelper.getDiary(ldate)
        if (diary.isNotEmpty()) {
            val mood = diary[0].mood
            emoticonSelector(mood)
        }

    }

    private fun emoticonSelector(mood: Int){

        val selected: Int = (2 * resources.displayMetrics.density).toInt()
        val notSelected: Int = (5 * resources.displayMetrics.density).toInt()

        with(binding) {
            when (mood) {
                0 -> {
                    activityCalendarMood0.setPadding(selected)
                    activityCalendarMood1.setPadding(notSelected)
                    activityCalendarMood2.setPadding(notSelected)
                    activityCalendarMood3.setPadding(notSelected)
                    activityCalendarMood4.setPadding(notSelected)
                }
                1 -> {
                    activityCalendarMood0.setPadding(notSelected)
                    activityCalendarMood1.setPadding(selected)
                    activityCalendarMood2.setPadding(notSelected)
                    activityCalendarMood3.setPadding(notSelected)
                    activityCalendarMood4.setPadding(notSelected)
                }
                2 -> {
                    activityCalendarMood0.setPadding(notSelected)
                    activityCalendarMood1.setPadding(notSelected)
                    activityCalendarMood2.setPadding(selected)
                    activityCalendarMood3.setPadding(notSelected)
                    activityCalendarMood3.setPadding(notSelected)
                }
                3 -> {
                    activityCalendarMood0.setPadding(notSelected)
                    activityCalendarMood1.setPadding(notSelected)
                    activityCalendarMood2.setPadding(notSelected)
                    activityCalendarMood3.setPadding(selected)
                    activityCalendarMood4.setPadding(notSelected)
                }
                4 -> {
                    activityCalendarMood0.setPadding(notSelected)
                    activityCalendarMood1.setPadding(notSelected)
                    activityCalendarMood2.setPadding(notSelected)
                    activityCalendarMood3.setPadding(notSelected)
                    activityCalendarMood4.setPadding(selected)
                }
                else -> {
                    activityCalendarMood0.setPadding(notSelected)
                    activityCalendarMood1.setPadding(notSelected)
                    activityCalendarMood2.setPadding(notSelected)
                    activityCalendarMood3.setPadding(notSelected)
                    activityCalendarMood4.setPadding(notSelected)
                }
            }
        }
    }

    private fun initRecyclerVIew(){
        //empty adapter ??????, layoutmanager ??????
        adapter = CalendarViewAdapter(ArrayList<Diary>())
        binding.calendarRecylcerView.adapter = adapter
        adapter.itemClickListener = object : CalendarViewAdapter.onItemClickListener{
            override fun onItemClick(cellData: Diary) {
                if(cellData.date != (-1).toLong() && cellData.mood != -1)
                    intentDetail(cellData.date)
            }
        }
        binding.calendarRecylcerView.layoutManager = GridLayoutManager(this, 7)
    }

    //selectedDate??? month??? ????????? daysInMonthArray(size : 7 * 6 = 42)??? ??????, ???????????? month??? ?????? ????????? -1??? ?????????. ???????????? month??? ?????? ????????? ??? ????????? ??????
    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(selectedDate : LocalDate) : ArrayList<Int>{
        val DaysInMonthArray = ArrayList<Int>()
        val yearMonth = YearMonth.from(selectedDate)

        //?????? month??? ??????
        val daysInMonth = yearMonth.lengthOfMonth()
        //?????? month??? ??????????????? ????????? ????????? ??? ??????  ??????????????? 1, ??????????????? 2.....??? ???????????? dayOfWeek??? ??????
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for(i in 1..42){
            if(dayOfWeek <= i && i < dayOfWeek + daysInMonth){
                DaysInMonthArray.add(i - dayOfWeek + 1)
            } else {
                DaysInMonthArray.add(-1)
            }
        }
        return DaysInMonthArray
    }

    //year??? month textview??? selectedDate??? ?????? ??????
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthYearTextView(selectedDate : LocalDate){
        val monthF = DateTimeFormatter.ofPattern("MM")
        val yearF = DateTimeFormatter.ofPattern("yyyy")
        binding.activityCalendarMonthText.text = selectedDate.format(monthF)
        binding.activityCalendarYearText.text = selectedDate.format(yearF)
    }

    //selectedDate??? ?????? calendar recycler view ??????
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerView(selectedDate: LocalDate){

        initEmoticon()
        //set adapter.DiaryListfd
        adapter.DiaryList.clear()
        //selectedDate??? month, year??? string????????? ??????
        val monthF = DateTimeFormatter.ofPattern("MM")
        val yearF = DateTimeFormatter.ofPattern("yyyy")
        val monthStr = selectedDate.format(monthF)
        val yearStr = selectedDate.format(yearF)
        for(i in 0..41){
            val DaysInMonthArray = daysInMonthArray(selectedDate)
            var ldate : Long
            var mood : Int
            //content??? image??? calendarView?????? ??????x dummy??????
            var content = ""
            val image = null
            if(DaysInMonthArray[i] != -1){//??? ?????? ???????????? ????????? ?????? cell
                val sdf = SimpleDateFormat("yyyy.MM.dd")
                var dayStr = DaysInMonthArray[i].toString()
//                if(dayStr.length == 1){
//                    dayStr = "0" + dayStr
//                }
//                val formatStr = binding.activityCalendarYearText.text.toString() + "." + binding.activityCalendarMonthText.text.toString() + "." + dayStr
                val formatStr = yearStr + "." + monthStr + "." + dayStr
                val date = sdf.parse(formatStr)
                ldate = date.time
                val diaryList = myDBHelper.getDiary(ldate)
                if(diaryList.size > 0){//??? ???????????? ????????? ???????????????.
                    mood = diaryList[0].mood
                }else{//???????????? ???????????? ????????? ?????? ???, ??????????????? ????????? ???????????? ?????????.
                    mood = -1
                }
            }else{//??? ?????? ???????????? ????????? ?????? cell
                ldate = -1
                mood = -1
            }
//            val image = ResourcesCompat.getDrawable(resources, R.drawable.test, null)?.toBitmap()
            adapter.DiaryList.add(Diary(ldate, mood, content, image))
        }
        adapter.notifyItemRangeChanged(0, 42)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initBtnListener(){
        binding.activityCalendarPrev.setOnClickListener {
            localDate = localDate.minusMonths(1)
            setMonthYearTextView(localDate)
            setRecyclerView(localDate)
        }
        binding.activityCalendarNext.setOnClickListener {
            localDate = localDate.plusMonths(1)
            setMonthYearTextView(localDate)
            setRecyclerView(localDate)
        }
        binding.activityCalendarListBtn.setOnClickListener {
            intentDiaryListActivity()
        }
        binding.activityCalendarSettingBtn.setOnClickListener {
            intentSettingActivity()
        }
    }

    private fun initTestWritingListener(){
        binding.testWriting.setOnClickListener {
            val intent = Intent(this, TestWritingActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }
}