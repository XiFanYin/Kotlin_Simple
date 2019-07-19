package xifuyin.tumour.com.a51ehealth.kotlin_simple.room

import android.arch.persistence.room.Room
import xifuyin.tumour.com.a51ehealth.kotlin_simple.app.App


object DbManagr {
    private var dao: AppDataBase? = null

    val instance: AppDataBase
        @Synchronized get() {
            if (DbManagr.dao == null) {
                dao = Room.databaseBuilder(
                    App.application,
                    AppDataBase::class.java, "mvvm_simple")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return dao!!
        }


}