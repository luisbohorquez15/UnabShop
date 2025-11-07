package co.edu.unab.luisbohorquez.unabshop

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await



// se hace el repositorio para gestionar las operaciones de los productos en firestores ya que,
// se comunica directamente con la base de datos
class ProductoRepository {


    private val db = Firebase.firestore


     // Obtiene todos los productos de la colección "productos" en tiempo real.
     // Escucha cambios y actualiza la lista automáticamente.

    fun obtenerProductosEnTiempoReal(
        onError: (Exception) -> Unit,
        onResult: (List<Producto>) -> Unit
    ) {

        db.collection("productos").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Firestore", "Error al escuchar productos", error)
                onError(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val productos = snapshot.documents.mapNotNull { doc ->
                    // Convierte el documento a un objeto Producto
                    val producto = doc.toObject(Producto::class.java)
                    // Asigna el ID del documento al objeto
                    producto?.id = doc.id
                    producto
                }
                onResult(productos)
            }
        }
    }

    // agrega un producto
    suspend fun agregarProducto(producto: Producto) {
        try {
            // El .await() espera a que la operación termine sin bloquear el hilo principal
            db.collection("productos").add(producto).await() // Este add es de Firestore
            Log.d("Firestore", "Producto agregado exitosamente")
        } catch (e: Exception) {
            Log.e("Firestore", "Error al agregar producto", e)
            throw e // Lanza la excepción para que el ViewModel la maneje.
        }
    }
    //Elimina un producto de Firestore usando su ID
    suspend fun eliminarProducto(productoId: String) {
        try {
            db.collection("productos").document(productoId).delete().await() // Este delete es de Firestore
            Log.d("Firestore", "Producto eliminado exitosamente")
        } catch (e: Exception) {
            Log.e("Firestore", "Error al eliminar producto", e)
            throw e
        }
    }
}
