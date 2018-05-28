package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.hot.model



data class TabInfoBean(
    val tabInfo: TabInfo
) {
    data class TabInfo(
        val tabList: List<Tab>,
        val defaultIdx: Int
    ) {
        data class Tab(
            val id: Int,
            val name: String,
            val apiUrl: String
        )
    }
}