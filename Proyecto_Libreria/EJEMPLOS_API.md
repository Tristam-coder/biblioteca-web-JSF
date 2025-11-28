# Ejemplos de Uso de la API

## Base URL
```
http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api
```

---

## 📚 USUARIOS

### Listar todos los usuarios
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios
```

### Obtener un usuario por ID
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios/1
```

### Crear un nuevo usuario
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "dni": "12345678",
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "password": "password123",
    "telefono": "555-1234",
    "direccion": "Calle Principal 123",
    "tipoUsuario": "SOCIO"
  }'
```

### Actualizar un usuario
```bash
curl -X PUT http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "dni": "12345678",
    "nombre": "Juan Carlos",
    "apellido": "Pérez",
    "email": "juan.perez@example.com",
    "password": "password123",
    "telefono": "555-5678",
    "direccion": "Calle Secundaria 456",
    "tipoUsuario": "SOCIO"
  }'
```

### Eliminar un usuario
```bash
curl -X DELETE http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios/1
```

---

## 📖 AUTORES

### Listar todos los autores
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/autores
```

### Crear un nuevo autor
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/autores \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Gabriel",
    "apellido": "García Márquez",
    "fechaNacimiento": "1927-03-06",
    "nacionalidad": "Colombiana",
    "biografia": "Escritor colombiano, premio Nobel de Literatura 1982"
  }'
```

---

## 🏢 EDITORIALES

### Listar todas las editoriales
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/editoriales
```

### Crear una nueva editorial
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/editoriales \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Penguin Random House",
    "direccion": "123 Editorial Avenue",
    "telefono": "555-9999",
    "email": "info@penguinrandomhouse.com",
    "pais": "Estados Unidos"
  }'
```

---

## 📚 TIPOS DE OBRA

### Listar todos los tipos de obra
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/tipo-obras
```

### Crear un nuevo tipo de obra
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/tipo-obras \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Novela",
    "descripcion": "Obras de ficción narrativa extensa",
    "diasPrestamoMaximo": 15
  }'
```

---

## 📕 OBRAS

### Listar todas las obras
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/obras
```

### Crear una nueva obra
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/obras \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Cien años de soledad",
    "isbnIssn": "978-0307474728",
    "anioPublicacion": 1967,
    "edicion": 1,
    "numeroPaginas": 417,
    "idioma": "Español",
    "areaTematica": "Literatura latinoamericana",
    "descripcion": "Obra maestra del realismo mágico"
  }'
```

**Nota:** Para asociar una obra con un autor y editorial, primero crea la obra y luego usa los endpoints para establecer las relaciones.

---

## 📚 EJEMPLARES

### Listar todos los ejemplares
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/ejemplares
```

### Crear un nuevo ejemplar
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/ejemplares \
  -H "Content-Type: application/json" \
  -d '{
    "codigoBarras": "EJ001234567",
    "numeroEjemplar": 1,
    "ubicacion": "Estantería A-3",
    "estado": "DISPONIBLE"
  }'
```

---

## 📤 PRÉSTAMOS

### Listar todos los préstamos
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/prestamos
```

### Crear un nuevo préstamo
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/prestamos \
  -H "Content-Type: application/json" \
  -d '{
    "fechaPrestamo": "2025-11-25",
    "fechaDevolucionPrevista": "2025-12-10",
    "estado": "ACTIVO"
  }'
```

**Nota:** Necesitas asociar el préstamo con un usuario y un ejemplar usando sus IDs.

---

## 🔖 RESERVAS

### Listar todas las reservas
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/reservas
```

### Crear una nueva reserva
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "fechaReserva": "2025-11-25",
    "estado": "ACTIVA"
  }'
```

---

## 💰 MULTAS

### Listar todas las multas
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/multas
```

### Crear una nueva multa
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/multas \
  -H "Content-Type: application/json" \
  -d '{
    "monto": 10.50,
    "motivo": "Devolución tardía",
    "estado": "PENDIENTE"
  }'
```

---

## 🔔 NOTIFICACIONES

### Listar todas las notificaciones
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/notificaciones
```

### Crear una nueva notificación
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/notificaciones \
  -H "Content-Type: application/json" \
  -d '{
    "mensaje": "Tu préstamo vence en 3 días",
    "tipo": "RECORDATORIO",
    "leido": false
  }'
```

---

## 📊 HISTORIAL

### Listar todo el historial
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/historial
```

### Crear un registro de historial
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/historial \
  -H "Content-Type: application/json" \
  -d '{
    "accion": "PRESTAMO_REALIZADO",
    "descripcion": "Préstamo del libro Cien años de soledad"
  }'
```

---

## 🔗 OBRA-AUTOR (Relación muchos a muchos)

### Listar todas las relaciones obra-autor
```bash
curl -X GET http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/obra-autor
```

### Crear una relación obra-autor
```bash
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/obra-autor \
  -H "Content-Type: application/json" \
  -d '{
    "rol": "Autor principal"
  }'
```

---

## 🧪 Pruebas con Postman

### Importar Colección

Puedes crear una colección en Postman con los siguientes pasos:

1. Crear una nueva colección llamada "Librería API"
2. Agregar una variable `baseUrl` con el valor: `http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api`
3. Crear requests para cada endpoint usando `{{baseUrl}}/usuarios`, etc.
4. Configurar Headers: `Content-Type: application/json`

### Variables de Entorno

```json
{
  "baseUrl": "http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api",
  "userId": "1",
  "autorId": "1",
  "obraId": "1"
}
```

---

## 🔍 Respuestas Comunes

### Éxito (200 OK)
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com"
}
```

### Creado (201 Created)
```
Location: http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios/1
```

### No Encontrado (404 Not Found)
```
Sin contenido (el recurso no existe)
```

### Sin Contenido (204 No Content)
```
Respuesta exitosa sin cuerpo (típicamente después de DELETE)
```

---

## 💡 Consejos

1. **Formato de Fechas:** Usar formato ISO 8601: `YYYY-MM-DD` o `YYYY-MM-DDTHH:mm:ss`
2. **JSON válido:** Asegurarse de que el JSON esté bien formateado
3. **Content-Type:** Siempre incluir el header `Content-Type: application/json`
4. **IDs de relaciones:** Crear primero las entidades principales antes de establecer relaciones

---

## 🐛 Solución de Problemas

### Error 404 en todos los endpoints
- Verificar que la aplicación esté desplegada
- Verificar la URL base y el context path

### Error 500 Internal Server Error
- Revisar logs de GlassFish
- Verificar conexión a la base de datos
- Verificar que las entidades relacionadas existan

### Error de JSON parsing
- Verificar formato del JSON
- Verificar que todos los campos requeridos estén presentes
- Verificar tipos de datos (números, strings, fechas)

