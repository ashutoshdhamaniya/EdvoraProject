package com.codingfreak.edvora

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

//    Initialize Variables and Layouts

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private lateinit var rideInfoArrayList: ArrayList<RideInfo>

    private lateinit var username: TextView
    private lateinit var userImage: CircleImageView
    private lateinit var filterLayout: LinearLayout
    private var userStationCode: Int = 0
    var distance: Int = 0

    // Variables used to count total rides and show
    var upcomingTotalRides: Int = 0
    var pastTotalRides: Int = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assign Variables and Layouts

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        username = findViewById(R.id.username)
        userImage = findViewById(R.id.userImage)
        filterLayout = findViewById(R.id.filterLayout)
        rideInfoArrayList = ArrayList()

        /*
        *
        * JSON file of the ride details are present in the assets folder
        * we are extracting data from the rideInfo.json file
        *
        * */

        try {
            val rideInfoJsonObject: JSONObject = JSONObject(getJsonDataFromAssets())
            val rideInformationArray: JSONArray = rideInfoJsonObject.getJSONArray("Ride")
            val userInfoObject: JSONObject = rideInfoJsonObject.getJSONObject("user")

            // User Info extraction and set to text view
            username.text = userInfoObject.getString("name")
            userStationCode = userInfoObject.getInt("station_code")

            upcomingTotalRides = rideInformationArray.length()

            /*
            * extract all the rides info and make objects which can be added into array list
            * */
            for (i in 0 until rideInformationArray.length()) {
                val rideDataObject: JSONObject = rideInformationArray.getJSONObject(i)

                // call function which is used to find nearest form current station
                findDistance(rideDataObject.getJSONArray("station_path"), userStationCode)

                // Add extracted data to make RideInfo Object
                val rideInfo = RideInfo(
                    rideDataObject.getString("id"),
                    rideDataObject.getInt("origin_station_code"),
                    rideDataObject.getJSONArray("station_path"),
                    rideDataObject.getInt("destination_station_code"),
                    rideDataObject.getLong("date"),
                    rideDataObject.getString("map_url"),
                    rideDataObject.getString("state"),
                    rideDataObject.getString("city"),
                    distance
                )
                // RideInfo Object added into arraylist
                rideInfoArrayList.add(rideInfo)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        /*
        * Tab layout titles array list creation
        * */
        val titleArrayList: ArrayList<String> = ArrayList()
        titleArrayList.add("Nearest")
        titleArrayList.add("Upcoming (${upcomingTotalRides})")
        titleArrayList.add("Past (${pastTotalRides})")

        // view pager implementation function
        initViewPager(viewPager, titleArrayList)

        // setting up view pager with tab layout
        tabLayout.setupWithViewPager(viewPager)

        filterLayout.setOnClickListener {
            openFilterDialog()
        }
    }

    /*
    * Function used to find distance nearest distance from station
    * */
    private fun findDistance(stationPath: JSONArray, userStationCode: Int) {
        distance = kotlin.math.abs(stationPath.get(0) as Int - userStationCode)
        for (j in 0 until stationPath.length()) {
            if (kotlin.math.abs((stationPath.get(j) as Int) - userStationCode) < distance) {
                distance = kotlin.math.abs((stationPath[j] as Int) - userStationCode)
            }
        }
    }

    private fun openFilterDialog() {
        val filterDialogBox: FilterDialogBox = FilterDialogBox()
        filterDialogBox.show(supportFragmentManager, "Filter Dialog")
    }

    // Getting JSON data from rideInfo.json file which is present inside assets folder
    private fun getJsonDataFromAssets(): String? {

        var json: String? = null

        try {
            val inputStream: InputStream = assets.open("rideInfo.json")
            val size: Int = inputStream.available()
            val buffer: ByteArray = ByteArray(size)

            inputStream.read(buffer)
            inputStream.close()

            json = String(buffer)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return json
    }

    /*
    * View pager initializer
    * */
    private fun initViewPager(viewPager: ViewPager?, titleArrayList: ArrayList<String>) {
        val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        var fragment = MainFragment()

        for (i in 0 until titleArrayList.size) {
            val bundle = Bundle()
            bundle.putString("title", titleArrayList[i])
            bundle.putInt("tabId", i)
            bundle.putSerializable("rideInfoArrayList", rideInfoArrayList)
            fragment.arguments = bundle
            viewPagerAdapter.addFragment(fragment, titleArrayList[i])
            fragment = MainFragment()
        }
        viewPager?.adapter = viewPagerAdapter
    }

    /*
    * Adapter class which is used to attach tab layout, viewpager and fragments
    * */
    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        val arrayList: ArrayList<String> = ArrayList()
        val fragmentList: ArrayList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment, title: String) {
            arrayList.add(title)
            fragmentList.add(fragment)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return arrayList[position]
        }
    }
}

