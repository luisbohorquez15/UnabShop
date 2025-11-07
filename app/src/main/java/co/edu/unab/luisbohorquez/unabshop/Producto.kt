package co.edu.unab.luisbohorquez.unabshop


//property id El ID único del documento en Firestore. Se deja nulo porque Firestore lo genera.
//property name El nombre del producto.
//property price El precio del producto.
//property description La descripción del producto.
//Modelo de datos que representa un producto
//  Los nombres de las propiedades (id, name, price) deben coincidir
//con los campos en de la base de datos de Firestore para que la conversión automática funcione
data class Producto(
        var id: String? = null, // Usamos 'var' para poder asignarlo después desde el repositorio.
        val name: String = "",
        val price: Double = 0.0,
        val description: String = ""
)
