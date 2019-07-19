package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model

import android.arch.persistence.room.*
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.fromJson
import xifuyin.tumour.com.a51ehealth.kotlin_simple.utils.toJson

/**
 * 记得测试一下到底是list《自定义类型》还是其他的效果
 */
@Entity
@TypeConverters(Converter::class)
data class FocusBean(val itemList: ArrayList<Item>, @Ignore val nextPageUrl: String) {
    data class Item(val data: Data) {
        data class Data(val header: Header, val itemList: ArrayList<Item>) {
            data class Header(val description: String, val icon: String, val title: String)
            data class Item(val data: Data) {
                data class Data(val cover: Cover, val description: String, val playUrl: String, val tags: List<Tag>, val title: String) {
                    data class Cover(val detail: String)
                    data class Tag(val name: String)
                }
            }
        }
    }

    @PrimaryKey
    var currenturl: String = ""

}


class Converter {

    // FocusBean.Item
    @TypeConverter
    fun storeItemToString(data: ArrayList<FocusBean.Item>): String = data.toJson()

    @TypeConverter
    fun storeStringToItem(value: String): ArrayList<FocusBean.Item> = value.fromJson()


    // FocusBean.Item.Data
    @TypeConverter
    fun storeDataToString(data: FocusBean.Item.Data): String = data.toJson()

    @TypeConverter
    fun storeStringToData(value: String): FocusBean.Item.Data = value.fromJson()

    //Header
    @TypeConverter
    fun storeHeaderToString(data: FocusBean.Item.Data.Header): String = data.toJson()

    @TypeConverter
    fun storeStringToHeader(value: String): FocusBean.Item.Data.Header = value.fromJson()

    //item2
    @TypeConverter
    fun storeHeaderToItem2(data: ArrayList<FocusBean.Item.Data.Item>): String = data.toJson()

    @TypeConverter
    fun storeStringToitem2(value: String): ArrayList<FocusBean.Item.Data.Item> = value.fromJson()

    //data
    @TypeConverter
    fun storeHeaderTodata2(data: FocusBean.Item.Data.Item.Data): String = data.toJson()

    @TypeConverter
    fun storeStringTodata2(value: String): FocusBean.Item.Data.Item.Data = value.fromJson()

    //Cover
    @TypeConverter
    fun storeHeaderToCover(data: FocusBean.Item.Data.Item.Data.Cover): String = data.toJson()

    @TypeConverter
    fun storeStringToCover(value: String): FocusBean.Item.Data.Item.Data.Cover = value.fromJson()


    //Tag
    @TypeConverter
    fun storeHeaderToTag(data: ArrayList<FocusBean.Item.Data.Item.Data.Tag>): String = data.toJson()

    @TypeConverter
    fun storeStringToTag(value: String): ArrayList<FocusBean.Item.Data.Item.Data.Tag> = value.fromJson()
}