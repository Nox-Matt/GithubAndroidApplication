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
    }

    private val listOfFollowing = MutableLiveData<ArrayList<DetailResponse>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun setListFollowing(username: String) {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowing(username)
        client.enqueue(object : retrofit2.Callback<List<DetailResponse>> {
            override fun onResponse(
                call: Call<List<DetailResponse>>,
                response: Response<List<DetailResponse>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    listOfFollowing.value = response.body() as ArrayList<DetailResponse>
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
