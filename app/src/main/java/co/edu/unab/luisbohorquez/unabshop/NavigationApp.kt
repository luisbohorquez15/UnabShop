package co.edu.unab.luisbohorquez.unabshop

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.auth
import com.google.firebase.ktx.Firebase

@Composable

fun NavigationApp(){
 val navController= rememberNavController()
    var startDestination: String ="login"

    val auth = com.google.firebase.Firebase.auth
    var currentUser=auth.currentUser

    if(currentUser!=null){
        startDestination="home"
    }else{
        startDestination="login"
    }

    NavHost(
        navController = navController,
        startDestination=startDestination
    ){
        composable("login"){

            LoginScreen(onClickRegister={
                navController.navigate("Register")

            }, onSuccessfullLogin = {
                navController.navigate("home"){
                    popUpTo("login"){inclusive=true}
                }
            })
        }
        composable("Register"){
            RegisterScreen(onClickBack = {
                navController.popBackStack()
            }, onSuccesfulRegister = {
                navController.navigate("home"){
                    popUpTo(0)
                }
            })
        }
        composable("home") {
            HomeScreen(onClickLogout = {
                navController.navigate("login"){
                    popUpTo(0)
                }
            })
        }
    }
}



