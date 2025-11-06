package co.edu.unab.luisbohorquez.unabshop

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para gestionar las operaciones de los productos en Firestore.
 * Se comunica directamente con la base de datos.
 */
class ProductoRepository {

    // Usamos la versión KTX de Firestore, que es más moderna y amigable con corutinas.
    private val db = Firebase.firestore

    /**
     * Obtiene todos los productos de la colección "productos" en tiempo real.
     * Escucha cambios y actualiza la lista automáticamente.
     *
     * @param onError Callback que se ejecuta si ocurre un error.
     * @param onResult Callback que devuelve la lista de productos.
     */
    fun obtenerProductosEnTiempoReal(
        onError: (Exception) -> Unit,
        onResult: (List<Producto>) -> Unit
    ) {
        // Asegúrate de que tu colección se llame "productos". Si no, cámbiala aquí.
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

    /**
     * Agrega un nuevo producto a Firestore. Usa corutinas para ser asíncrono.
     */
    suspend fun agregarProducto(producto: Producto) {
        try {
            // El .await() espera a que la operación termine sin bloquear el hilo principal.
            db.collection("productos").add(producto).await() // Este 'add' es de Firestore.
            Log.d("Firestore", "Producto agregado exitosamente")
        } catch (e: Exception) {
            Log.e("Firestore", "Error al agregar producto", e)
            throw e // Lanza la excepción para que el ViewModel la maneje.
        }
    }

    /**
     * Elimina un producto de Firestore usando su ID. Usa corutinas.
     */
    suspend fun eliminarProducto(productoId: String) {
        try {
            db.collection("productos").document(productoId).delete().await() // Este 'delete' es de Firestore.
            Log.d("Firestore", "Producto eliminado exitosamente")
        } catch (e: Exception) {
            Log.e("Firestore", "Error al eliminar producto", e)
            throw e
        }
    }
}
