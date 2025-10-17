package co.edu.unab.luisbohorquez.unabshop

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable

fun NavigationApp(){
 val navController= rememberNavController()
    val startDestination: String ="login"

    NavHost(
        navController = navController,
        startDestination=startDestination
    ){
        composable("login"){

            LoginScreen(onClickRegister={
                navController.navigate("Register")

            })
        }
        composable("Register "){
            RegisterScreen(onClickBack = {
                navController.popBackStack()
            })
        }
    }
}



