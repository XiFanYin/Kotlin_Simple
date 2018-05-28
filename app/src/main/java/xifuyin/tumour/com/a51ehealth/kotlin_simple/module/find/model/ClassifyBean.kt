package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.find.model

import java.io.Serializable


data class ClassifyBean(
    val bgColor: String,
    val bgPicture: String,
    val defaultAuthorId: Int,
    val description: String,
    val headerImage: String,
    val id: Long,
    val name: String
):Serializable