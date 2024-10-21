package com.example.basesdedatos.Screen.AutoresScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.basesdedatos.Model.Autores
import com.example.basesdedatos.Repository.AutorRepository
import com.example.basesdedatos.Repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutorScreen(autorRepository: AutorRepository, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") }

    var autores by remember { mutableStateOf(emptyList<Autores>()) }
    var expanded by remember { mutableStateOf(false) }

    var selectedAutor by remember { mutableStateOf<Autores?>(null) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var editandoAutor by remember { mutableStateOf(false) } // Para controlar la edición

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Agregar Autor",
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x80FAE3EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.width(290.dp).height(70.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x80FAE3EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.width(290.dp).height(70.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = nacionalidad,
                onValueChange = { nacionalidad = it },
                label = { Text("Nacionalidad") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x80FAE3EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.width(290.dp).height(70.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val autor = Autores(
                    nombre = nombre,
                    apellido = apellido,
                    nacionalidad = nacionalidad
                )
                if (nombre.isNotEmpty() && apellido.isNotEmpty() && nacionalidad.isNotEmpty()) {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            autorRepository.insertAutor(autor)
                        }
                        nombre = ""
                        apellido = ""
                        nacionalidad = ""
                    }
                    Toast.makeText(context, "Autor agregado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Llene todos los campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Agregar Autor")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    autores = withContext(Dispatchers.IO) {
                        autorRepository.getAllAutores()
                    }
                    showDialog = true
                }
            }) {
                Text(text = "Mostrar Autores")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Autores") },
                    text = {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = selectedAutor?.nombre ?: "",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Seleccionar Autor") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                autores.forEach { autor ->
                                    DropdownMenuItem(
                                        text = { Text("${autor.nombre} ${autor.apellido} ${autor.nacionalidad}") },
                                        onClick = {
                                            selectedAutor = autor
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            navController.navigate("Libro")
                        }) {

                            Icon(Icons.Default.Add, contentDescription = "Agregar libro")
                        }
                    },
                    dismissButton = {
                        selectedAutor?.let {
                            IconButton(onClick = {
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        autorRepository.deleteAutorById(it.id)
                                    }
                                    autores = autorRepository.getAllAutores()
                                    selectedAutor = null
                                }
                                Toast.makeText(context, "Autor eliminado", Toast.LENGTH_SHORT)
                                    .show()
                                showDialog = false
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                            IconButton(onClick = {
                                editandoAutor = true
                                // Llena los campos con los valores del autor seleccionado para editar
                                nombre = it.nombre
                                apellido = it.apellido
                                nacionalidad = it.nacionalidad
                                showDialog = false
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Actualizar")
                            }
                        }
                    }
                )
            }
        }



        item {
            // Diálogo de edición de autor
            if (editandoAutor) {
                AlertDialog(
                    onDismissRequest = { editandoAutor = false },
                    title = { Text(text = "Editar Autor") },
                    text = {
                        Column {
                            TextField(
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = { Text("Nombre") }
                            )
                            TextField(
                                value = apellido,
                                onValueChange = { apellido = it },
                                label = { Text("Apellido") }
                            )
                            TextField(
                                value = nacionalidad,
                                onValueChange = { nacionalidad = it },
                                label = { Text("Nacionalidad") }
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {

                            val actualizarAutor = selectedAutor?.copy(
                                nombre = nombre,
                                apellido = apellido,
                                nacionalidad = nacionalidad
                            )
                            if (actualizarAutor != null) {
                                selectedAutor?.let {
                                    scope.launch {
                                        if (nombre.isNotEmpty() && apellido.isNotEmpty() && nacionalidad.isNotEmpty()) {
                                            withContext(Dispatchers.IO) {
                                                autorRepository.getAutorById(
                                                    actualizarAutor.id,
                                                    actualizarAutor.nombre,
                                                    actualizarAutor.apellido,
                                                    actualizarAutor.nacionalidad
                                                )
                                                autores = autorRepository.getAllAutores()
                                            }
                                            nombre = ""
                                            apellido = ""
                                            nacionalidad = ""

                                            Toast.makeText(
                                                context,
                                                "Autor actualizado",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Todos los campos son obligatorios",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                                editandoAutor = false
                            }
                        }) {
                            Text("Actualizar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { editandoAutor = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
