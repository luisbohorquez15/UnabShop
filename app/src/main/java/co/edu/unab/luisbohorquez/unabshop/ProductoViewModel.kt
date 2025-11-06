package co.edu.unab.luisbohorquez.unabshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar el estado y la lógica de negocio de los productos.
 * Actúa como intermediario entre la UI (Compose) y el Repositorio.
 */
class ProductoViewModel : ViewModel() {

    private val repository = ProductoRepository()

    // Flujo de estado privado para la lista de productos.
    // Solo el ViewModel puede modificarlo.
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    // Flujo de estado público y de solo lectura para que la UI lo observe.
    val productos: StateFlow<List<Producto>> = _productos

    init {
        // Al iniciar el ViewModel, comenzamos a escuchar los productos en tiempo real.
        escucharCambiosEnProductos()
    }

    private fun escucharCambiosEnProductos() {
        repository.obtenerProductosEnTiempoReal(
            onError = { /* Opcional: Manejar el error, por ej. mostrar un Toast */ },
            onResult = { listaDeProductos ->
                // Cuando el repositorio nos da una nueva lista, actualizamos el estado.
                _productos.value = listaDeProductos
            }
        )
    }

    /**
     * Llama al repositorio para agregar un nuevo producto.
     * Se ejecuta en una corutina para no bloquear el hilo principal.
     */
    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            try {
                repository.agregarProducto(producto)
                // Opcional: Mostrar mensaje de éxito.
            } catch (e: Exception) {
                // Opcional: Manejar error, por ej. mostrar un mensaje al usuario.
            }
        }
    }

    /**
     * Llama al repositorio para eliminar un producto.
     */
    fun eliminarProducto(productoId: String) {
        viewModelScope.launch {
            try {
                repository.eliminarProducto(productoId)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}
