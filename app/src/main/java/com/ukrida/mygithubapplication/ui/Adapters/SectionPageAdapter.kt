import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ukrida.mygithubapplication.ui.FollowersFragment
import com.ukrida.mygithubapplication.ui.FollowingFragment

class SectionPageAdapter(activity: FragmentActivity, private val bundle: Bundle) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                val fragment = FollowersFragment()
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                val fragment = FollowingFragment()
                fragment.arguments = bundle
                fragment
            }
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

}
