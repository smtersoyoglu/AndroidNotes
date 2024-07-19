package kekodnotes.appnavigation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        getNavControllerViaFragment()
//        getNavControllerViaView()
//        getNavControllerViaFragment2()
//        getNavControllerViaView2()  //Hata vericektir. NavController'a erişim sorunu var diyor.
//        Burda fragment'ın kendisi yok view var onun da NavHostFragment'ı olmadığı için create anında cast ettirmemiz gerekir.

//        getNavControllerViaFragment3()
//        getNavControllerViaView3() // Hata vericektir. NavController'a erişim sorunu var dicektir. onCreate'e degil de onResume() e koyarsak calisir.
    }

    // NavHostFragment'ı kullanırken eger ki özellikle FragmentContainerView kullaniyorsak NavHostFragment'ımıza erisip onun uzerinden navController'ı almamiz daha onemli.
    // bunu yaparken de birden fazla yontemimiz var.


    // Activity'nin onCreate'inde navController'a ihtiyacimiz varsa sadece bu yontemi kullaniyoruz.
    // fragment'ta kullanıyorsak FragmentContainerView de kullanıyorsak supportFragmentManager uzerinden erisiyorsak kullaniriz.
    fun getNavControllerViaFragment() { // fragment'ta bir spesifik islem yapacaksak cok buyuk bir ihtimalle supportFragmentManager uzerinden yapariz.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

    }       /** supportFragmentManager kullanimlari */
    fun getNavControllerViaView() { // FragmentContainerView'i kullanıyorsak ve navController'a assagida ki gibi erisiyorsak onCreate de kullanamayiz bunu. hata verir.
        // ama onResume() icerisinde bunu kullanabiliriz
        val navHostFragmentView = supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
        val navControllerView = navHostFragmentView.navController

    }

    fun getNavControllerViaFragment2() {
        val navHostFragment = findViewById<View>(R.id.navHostFragment)
        val navController = navHostFragment.findNavController()

    }

    fun getNavControllerViaFragment3() {
        findNavController(R.id.navHostFragment)
    }



    fun getNavControllerViaView2() {
        val navHostFragmentView = findViewById<View>(R.id.navHostFragmentContainerView) // eger as NavHostFragment yaparsak, eklersek onResume'da da hata verir. bir view'ken cast
        // ettigimiz seyin bir fragment'a karsilik gelecegini, cast hatasi almamamiz gerektigini belirtmemiz gerekiyorsa supportFragmentManager uzerinden erismemiz gerekiyor buna.
        /** !!!- bir view'ı fragment'a cast etmek için support Fragment ile bunun bir fragment olduğunu belirtmemiz gerek. */
        val navControllerView = navHostFragmentView.findNavController()
    }

    fun getNavControllerViaView3() {
        findNavController(R.id.navHostFragmentContainerView)
    }


    override fun onResume() { // navController set ile alakali bir hata aliyorsak onResume() de calisiyor demektir.
        super.onResume()
        //getNavControllerViaView()
        //getNavControllerViaView2()
        getNavControllerViaView3()
    }
}

/**  - Ozetle navController'a ulasmamizin 4 yolu var.
 *
 *  1- navHostFragment'in nesnesini olustur. o nesne uzerinden navController'a erisim. getNavControllerViaFragment() en az hata ile karsilasma ihtimalimiz olan yontem.
 *  navHostFragment'a supportFragmentManager uzerinden ulas, ister navHostFragment kullanirken ister navHostFragmentContainerView kullanirken. bu yontem 2 yapi icinde sorunsuz calisir.
 *
 *  2- Activity'nin findNavController() extension'ı bunu kullaniyorsak eger fragment ile sorunsuz calisir. ama FragmentContainerView ile calisicaksak onResume() de sorunsuz calisir. onCreate() de hata verir.
 *
 *  3- fragment'ın findNavController() extension'ı bunu kullaniyorsak eger yine ayni onResume() de sorunsuz calisir. onCreate() de hata verir. getNavControllerViaView2()
 *
 *  4- View'ın findNavController() extension'ı bunu kullaniyorsak eger yine ayni onResume() de sorunsuz calisir. onCreate() de hata verir.
 *
 *  eger fragment'a erisirken cast islemi yapicaksak as NavHostFragment'a mutlaka arama yontemimizin findFragmentById olmasi lazim.
 *  findViewById ile bir component'e erisip onu fragment'a cast edemiyoruz. boyle bir islem yaparsak da ister onCreate() de ister onResume() da olsun kesin olarak typecast hatası aliriz.
 *
 *
 *      !! Kullanicagimiz yontem genellikle, cogunlukla asagida ki yontem olacaktir:
 *
 *      xml de FragmentContainerView kullanip, Activity uzerinden de erisirken asagidaki kodu kullanmamiz gerekiyor.
 *
 *      fun getNavControllerViaView() {
 *         val navHostFragmentView = supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
 *         val navControllerView = navHostFragmentView.navController
 *     }
 */
