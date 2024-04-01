import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ukrida.mygithubapplication.data.respond.DetailResponse
import com.ukrida.mygithubapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    companion object {
        private const val TAGX = "FollowingViewModel"
        private const val PER_PAGE = 100
    }

    private val listOfFollowing = MutableLiveData<ArrayList<DetailResponse>>()
    private val isLoading = MutableLiveData<Boolean>()
    private var currentPage = 1

    fun setListFollowing(username: String) {
        isLoading.value = true

        val firstPageClient = ApiConfig.getApiService().getUsersFollowing(username, currentPage, PER_PAGE)
        firstPageClient.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                if (response.isSuccessful) {
                    val totalCount = response.headers()["X-Total-Count"]?.toIntOrNull() ?: 0
                    val perPage = if (totalCount > 0) totalCount else PER_PAGE
                    fetchFollowingPages(username, perPage)
                } else {
                    isLoading.value = false
                    Log.e(TAGX, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetailResponse>>, t: Throwable) {
                isLoading.value = false
                Log.e(TAGX, "onFailure: ${t.message}")
            }
        })
    }

    private fun fetchFollowingPages(username: String, perPage: Int) {
        val client = ApiConfig.getApiService().getUsersFollowing(username, currentPage, perPage)
        client.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val newList = response.body() as ArrayList<DetailResponse>
                    val currentList = listOfFollowing.value ?: ArrayList()
                    currentList.addAll(newList)
                    listOfFollowing.value = currentList
                    if (newList.size == perPage) {
                        currentPage++
                        fetchFollowingPages(username, perPage)
                    }
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

    fun getFollowing(): LiveData<ArrayList<DetailResponse>> {
        return listOfFollowing
    }
}