package xifuyin.tumour.com.a51ehealth.kotlin_simple.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean


@Database(entities = arrayOf(FocusBean::class),version = 1,exportSchema = false)
abstract class AppDataBase: RoomDatabase() {


    abstract  fun getGirlDao():FocusDao



}