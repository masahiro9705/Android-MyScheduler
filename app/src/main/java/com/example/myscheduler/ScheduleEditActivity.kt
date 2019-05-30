package com.example.myscheduler

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_schedule_edit.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ScheduleEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)
        realm = Realm.getDefaultInstance()

        save.setOnClickListener { view: View ->
            realm.executeTransaction{ db: Realm ->
                val maxId = db.where<Schedule>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1
                val schedule = db.createObject<Schedule>(nextId)
                val date = dateEdit.text.toString().toDate("yyyy/MM/dd")
                if (date != null) schedule.date = date
                schedule.title = titleEdit.text.toString()
                schedule.detail = detailEdit.text.toString()
            }
            Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                .setAction("戻る") { finish() }
                .setActionTextColor(Color.YELLOW)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    private  fun String.toDate( pattern: String = "yyyy/MM/dd HH:mm"): Date? {
        return  try {
            SimpleDateFormat(pattern).parse(this)
        }catch (e: IllegalAccessException) {
            return null
        }catch (e: ParseException) {
            return null
        }
    }
}

