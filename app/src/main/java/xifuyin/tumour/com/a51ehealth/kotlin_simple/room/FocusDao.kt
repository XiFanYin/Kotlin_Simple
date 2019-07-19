package xifuyin.tumour.com.a51ehealth.kotlin_simple.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update
import xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model.FocusBean

@Dao
interface FocusDao {

    /*插入一个列表*/
    @Insert
    fun insertAll(focus: FocusBean)

    @Update
    fun UpdateAll(focus: FocusBean)


}