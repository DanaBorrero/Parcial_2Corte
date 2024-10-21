package com.example.basesdedatos.Screen.LibroScreen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.basesdedatos.Model.AutorConLibros
import com.example.basesdedatos.Model.Autores
import com.example.basesdedatos.Model.Libro
import com.example.basesdedatos.Repository.AutorRepository
import com.example.basesdedatos.Repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroScreen(libroRepository: LibroRepository, navController: NavController, autorRepository: AutorRepository) {
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var autor_id by remember { mutableStateOf("") }
    var autoresConLibros by remember { mutableStateOf(emptyList<AutorConLibros>()) }



    var libros by remember { mutableStateOf(emptyList<Libro>()) }
    var autores by remember { mutableStateOf(emptyList<Autores>()) }
    var expanded by remember { mutableStateOf(false) }

    var selectedLibro by remember { mutableStateOf<Libro?>(null) }
    var selectedAutor by remember { mutableStateOf<Autores?>(null) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var editandoLibro by remember { mutableStateOf(false) } // Para controlar la edición



    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        item {
            Text(
                text = "Agregar Libro",
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Titulo") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x80FAE3EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(290.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = genero,
                onValueChange = { genero = it },
                label = { Text("Genero") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0x80FAE3EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .width(290.dp)
                    .height(70.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }

        item {
            LaunchedEffect(Unit) {
                scope.launch {
                    autores = autorRepository.getAllAutores()
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            // Selección de autor
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
                            text = { Text("${autor.nombre} ${autor.apellido}") },
                            onClick = {
                                selectedAutor = autor
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (titulo.isNotEmpty() && genero.isNotEmpty() && selectedAutor != null) {
                    val libro = Libro(
                        libro_id = 0,
                        titulo = titulo,
                        genero = genero,
                        autor_id = selectedAutor!!.id // Asigna el autor_id al libro
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            libroRepository.insertLibro(libro)
                        }
                        titulo = ""
                        genero = ""
                        selectedAutor = null
                    }
                    Toast.makeText(context, "Libro registrado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Llene todos los campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Agregar Libro")
            }

        }

        item{

        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    libros = withContext(Dispatchers.IO) {
                        libroRepository.getAllLibros()
                    }
                    showDialog = true
                }
            }) {
                Text(text = "Mostrar Libros")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Libros") },
                    text = {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = selectedLibro?.titulo ?: "",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Seleccionar Libro") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                libros.forEach { libro ->
                                    DropdownMenuItem(
                                        text = { Text("${libro.titulo} ${libro.genero}") },
                                        onClick = {
                                            selectedLibro = libro
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                    },
                    dismissButton = {
                        selectedLibro?.let {
                            IconButton(onClick = {
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        libroRepository.deleteLibroById(it.libro_id)
                                    }
                                    libros = libroRepository.getAllLibros()
                                    selectedLibro = null
                                }
                                Toast.makeText(context, "Libro eliminado", Toast.LENGTH_SHORT)
                                    .show()
                                showDialog = false
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                            IconButton(onClick = {
                                editandoLibro = true
                                // Llena los campos con los valores del autor seleccionado para editar
                                titulo = it.titulo
                                genero = it.genero
                                //autor_id = it.autor_id

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
            // Diálogo de edición de libro
            if (editandoLibro) {
                AlertDialog(
                    onDismissRequest = { editandoLibro = false },
                    title = { Text(text = "Editar Libro") },
                    text = {
                        Column {
                            TextField(
                                value = titulo,
                                onValueChange = { titulo = it },
                                label = { Text("Titulo") }
                            )
                            TextField(
                                value = genero,
                                onValueChange = { genero = it },
                                label = { Text("Genero") }
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {

                            val actualizarLibro = selectedLibro?.copy(
                                titulo = titulo,
                                genero = genero,
                                //autor_id = autor_id
                            )
                            if (actualizarLibro != null) {
                                selectedLibro?.let {
                                    scope.launch {
                                        if (titulo.isNotEmpty() && genero.isNotEmpty()) {
                                            withContext(Dispatchers.IO) {
                                                libroRepository.getLibroById(
                                                  actualizarLibro.libro_id,
                                                    actualizarLibro.titulo,
                                                    actualizarLibro.genero,
                                                    actualizarLibro.autor_id
                                                )
                                                libros = libroRepository.getAllLibros()
                                            }
                                            titulo = ""
                                            genero = ""
                                            //autor_id = ""

                                            Toast.makeText(
                                                context,
                                                "Libro actualizado",
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
                                editandoLibro = false
                            }
                        }) {
                            Text("Actualizar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { editandoLibro = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
        items(autoresConLibros) { autorWithLibros ->
            // Mostrar el nombre del autor
            Text(text = "Autor: ${autorWithLibros.autores.nombre} ${autorWithLibros.autores.apellido}")
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar la lista de libros asociados
            if (autorWithLibros.libros.isNotEmpty()) {
                Text(text = "Libros:")
                autorWithLibros.libros.forEach { libro ->
                    Text(text = " - ${libro.titulo}")
                }
            } else {
                Text(text = "No tiene libros asociados")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para realizar alguna acción relacionada con el autor
            Button(onClick = {
                navController.navigate("AutorDetailScreen/${autorWithLibros.autores.id}")
            }) {
                Text(text = "Acción")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

