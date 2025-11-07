package co.edu.unab.luisbohorquez.unabshop

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.toObjects


data class Product(
    val name: String = "",
    val price: Double = 0.0,
    val description: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClickLogout: () -> Unit = {}) {

    val auth = Firebase.auth

    // Estados para manejar la lista de productos y la pantalla de carga
    var productList by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) } // Para mostrar un indicador mientras cargan los datos

    // Instancia de la base de datos Firestore
    val db = Firebase.firestore


    LaunchedEffect(Unit) {

        db.collection("products")
            .addSnapshotListener { snapshots, error ->
                isLoading = false
                if (error != null) {
                    Log.w("FIRESTORE_ERROR", "Error al escuchar los datos.", error)
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    // Convierte los documentos de Firestore a una lista de objetos Product
                    val products = snapshots.toObjects<Product>()
                    productList = products
                    Log.d("FIRESTORE_SUCCESS", "Productos cargados: ${products.size}")
                }
            }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        "Unab Shop",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO: Navegar a Notificaciones */ }) {
                        Icon(Icons.Filled.Notifications, "Notificaciones")
                    }
                    IconButton(onClick = { /* TODO: Navegar al Carrito */ }) {
                        Icon(Icons.Filled.ShoppingCart, "Carrito")
                    }
                    // Este es el botón para cerrar sesión,
                    IconButton(onClick = {
                        auth.signOut()
                        onClickLogout() // se ejecuta la navegación hacia atrás
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, "Salir de la app")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFF9900),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Productos Disponibles",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth() // Alinea el título a la izquierda
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Paso 3: Lógica para mostrar la carga, lista vacía o los productos
            if (isLoading) {
                // Muestra un círculo de progreso centrado mientras espera los datos
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (productList.isEmpty()) {
                // Mensaje si no se encontraron productos
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay productos para mostrar.", fontSize = 18.sp, color = Color.Gray)
                }
            } else {
                // Muestra la lista de productos usando LazyColumn para mejor rendimiento
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre tarjetas
                ) {
                    items(productList) { product ->
                        ProductCard(product = product) // Llama a un Composable para cada tarjeta
                    }
                }
            }
        }
    }
}


// es un composable que muestra una tarjeta con información de un producto
@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${"%.2f".format(product.price)}", // Formatea el precio a 2 decimales
                fontSize = 16.sp,
                color = Color(0xFF00796B),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// Preview para visualizar el diseño en Android Studio
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

    HomeScreen(onClickLogout = {})
}
