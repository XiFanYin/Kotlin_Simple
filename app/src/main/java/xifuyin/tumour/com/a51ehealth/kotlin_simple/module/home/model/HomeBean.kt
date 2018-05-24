package xifuyin.tumour.com.a51ehealth.kotlin_simple.module.home.model

/**
 * Created by Administrator on 2018/5/22.
 */

data class HomeBean(val issueList: List<Issue>, val newestIssueType: String, val nextPageUrl: String, val nextPublishTime: Long) {


    data class Issue(var count: Int, val date: Long, val itemList: ArrayList<Item>, val publishTime: Long, val releaseTime: Long, val type: String) {


        data class Item(val adIndex: Int, val data: Data, val id: Int, val type: String) {

            data class Data(
                    val author: Author,
                    val category: String,
                    val collected: Boolean,
                    val consumption: Consumption,
                    val cover: Cover,
                    val dataType: String,
                    val date: Long,
                    val description: String,
                    val descriptionEditor: String,
                    val duration: Int,
                    val id: Int,
                    val idx: Int,
                    val ifLimitVideo: Boolean,
                    val labelList: List<Any>,
                    val library: String,
                    val playInfo: List<PlayInfo>,
                    val playUrl: String,
                    val played: Boolean,
                    val provider: Provider,
                    val releaseTime: Long,
                    val remark: String,
                    val resourceType: String,
                    val searchWeight: Int,
                    val slogan: String,
                    val subtitles: List<Any>,
                    val tags: ArrayList<Tag>,
                    val title: String,
                    val type: String,
                    val webUrl: WebUrl) {

                data class Consumption(val collectionCount: Int, val replyCount: Int, val shareCount: Int)


                data class PlayInfo(val height: Int, val name: String, val type: String, val url: String, val urlList: List<Url>, val width: Int) {

                    data class Url(val name: String, val size: Int, val url: String)
                }


                data class Tag(
                        val actionUrl: String,
                        val bgPicture: String,
                        val headerImage: String,
                        val id: Int,
                        val name: String,
                        val tagRecType: String
                )

                data class Author(
                        val approvedNotReadyVideoCount: Int,
                        val description: String,
                        val follow: Follow,
                        val icon: String,
                        val id: Int,
                        val ifPgc: Boolean,
                        val latestReleaseTime: Long,
                        val link: String,
                        val name: String,
                        val shield: Shield,
                        val videoNum: Int
                ) {
                    data class Follow(
                            val followed: Boolean,
                            val itemId: Int,
                            val itemType: String
                    )

                    data class Shield(
                            val itemId: Int,
                            val itemType: String,
                            val shielded: Boolean
                    )
                }

                data class Cover(
                        val blurred: String,
                        val detail: String,
                        val feed: String,
                        val homepage: String
                )

                data class WebUrl(
                        val forWeibo: String,
                        val raw: String
                )

                data class Provider(
                        val alias: String,
                        val icon: String,
                        val name: String
                )
            }
        }
    }
}