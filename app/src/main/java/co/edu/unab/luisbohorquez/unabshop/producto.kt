package co.edu.unab.luisbohorquez.unabshop

class producto {
    data class Producto(
        val id: String? = null,
        val nombre: String = "",
        val descripcion: String = "",
        val precio: Double = 0.0
    )
}