import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    companion object {
        private const val TAGX = "FollowingViewModel"
    }

    private val listOfFollowing = MutableLiveData<List<DetailResponse>>()
    private val isLoading = MutableLiveData<Boolean>()
    private var currentPage = 1

    fun setListFollowing(username: String) {
        isLoading.value = true
        loadFollowing(username, currentPage)
    }
    private fun loadFollowing(username: String,page: Int){
        val client = ApiConfig.getApiService().getUsersFollowing(username, page)
        client.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val following = response.body()
                    if (following != null) {
                        if (following.isNotEmpty()) {
                            val currentList = listOfFollowing.value ?: emptyList()
                            listOfFollowing.value = currentList + following
                            currentPage++
                            loadFollowing(username, currentPage)
                        }
                    } else {
                        Log.e(TAGX, "No Following")
                    }
                    listOfFollowing.value = response.body()
                } else {
                    Log.e(TAGX, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailResponse>>, t: Throwable) {
                isLoading.value = false
                Log.e(TAGX, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(): MutableLiveData<List<DetailResponse>> {
        return listOfFollowing
    }
}
