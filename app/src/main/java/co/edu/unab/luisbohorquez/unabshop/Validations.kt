package co.edu.unab.luisbohorquez.unabshop

import android.util.Patterns

// returnar true si es valido y false si no es valido
// tambien que retorne una cadena que me diga porque no es valido


fun validateEmail(email: String): Pair<Boolean, String>{
    return when{
        email.isEmpty()->Pair(false, "el correo es requerido ")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()-> Pair(false,"el correo es invalido ")

        !email.endsWith("@prueba.com")->Pair(false, "ese email no es corporativo ")

        else -> Pair(true,"")


    }

}


fun validatePassword(password: String): Pair<Boolean, String>{
    return when{
        password.isEmpty()->Pair(false, "la contraseña es requerida  ")
        password.length< 6 ->Pair(false,"la contraseña debe tener al menos 6 caracteres")
        !password.any{it.isDigit()}-> Pair(false,"la contraseña debe tener al menos un digito ")
        else -> Pair(true,"")


    }

}

fun validateName(name: String): Pair<Boolean, String>{
    return when{
        name.isEmpty()->Pair(false, "el nombre es requerido  ")
        name.length< 3 ->Pair(false,"el nombre debe al menos 3 caracteres")
        else -> Pair(true,"")
    }
    }

fun validateConfirmPassword(password: String,confirmPassword:String ): Pair<Boolean, String>{
    return when{
        confirmPassword.isEmpty()->Pair(false, "el nombre es requerido  ")
        confirmPassword != password-> Pair(false,"las contraseñas no coinciden")
        else -> Pair(true,"")
    }
}

