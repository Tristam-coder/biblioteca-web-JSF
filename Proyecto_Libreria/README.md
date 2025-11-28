# 📚 Proyecto Librería - Sistema de Gestión de Biblioteca

## 📖 Descripción
Sistema completo de gestión de biblioteca desarrollado con Jakarta EE 11, Hibernate, y PostgreSQL. Incluye gestión de usuarios, obras, préstamos, reservas, multas y notificaciones a través de una API REST.

## 🚀 Inicio Rápido
1. Configurar PostgreSQL (ver [INSTRUCCIONES.md](INSTRUCCIONES.md))
2. Compilar: `mvn clean package`
3. Desplegar en GlassFish
4. Acceder a: `http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/`

## 🛠️ Tecnologías Utilizadas
- **Java 17**
- **Jakarta EE 11** (Jakarta REST, JPA)
- **Hibernate 6.4.4** (ORM)
- **PostgreSQL 42.7.0** (Base de datos)
- **Jersey 3.1.5** (JAX-RS Implementation)
- **Maven** (Gestión de dependencias)
- **GlassFish 7.x** (Servidor de aplicaciones)

## Estructura del Proyecto

### Modelos (com.libreria.model)
- `Autor.java` - Información de autores
- `Editorial.java` - Información de editoriales
- `Obra.java` - Información de obras (libros, revistas, etc.)
- `TipoObra.java` - Clasificación de obras
- `Ejemplar.java` - Copias físicas de obras
- `Usuario.java` - Usuarios del sistema
- `Prestamo.java` - Gestión de préstamos
- `Reserva.java` - Gestión de reservas
- `Multa.java` - Gestión de multas
- `Notificacion.java` - Notificaciones a usuarios
- `Historial.java` - Historial de operaciones
- `ObraAutor.java` - Relación muchos a muchos entre obras y autores

### Servicios (com.libreria.service)
Capa de lógica de negocio que gestiona las operaciones CRUD para cada entidad.

### Recursos REST (com.libreria.rs)
API REST con endpoints para todas las entidades del sistema.
- Base URL: `/api`
- Endpoints disponibles: `/usuarios`, `/obras`, `/autores`, `/editoriales`, `/prestamos`, `/reservas`, `/multas`, `/notificaciones`, `/ejemplares`, `/historial`, `/tipo-obras`

## Configuración

### Base de Datos
Configurar PostgreSQL con los siguientes parámetros en `persistence.xml`:
- **Host:** localhost
- **Puerto:** 5433
- **Base de datos:** libreria
- **Usuario:** appuser
- **Contraseña:** apppass

### Compilación y Despliegue
1. Asegurarse de tener Maven instalado
2. Compilar el proyecto:
   ```bash
   mvn clean package
   ```
3. Desplegar el archivo WAR generado en GlassFish

## 📚 Documentación

- **[INSTRUCCIONES.md](INSTRUCCIONES.md)** - Guía completa de instalación, configuración y despliegue
- **[RESUMEN_CAMBIOS.md](RESUMEN_CAMBIOS.md)** - Detalle de cambios realizados y estado del proyecto
- **[EJEMPLOS_API.md](EJEMPLOS_API.md)** - Ejemplos de uso de la API REST con cURL y Postman

## ✅ Estado del Proyecto

### Completado
- ✅ 12 Modelos de entidad (JPA)
- ✅ 12 Servicios con operaciones CRUD
- ✅ 13 Recursos REST con endpoints completos
- ✅ Configuración de persistencia corregida
- ✅ Archivos de configuración validados
- ✅ 0 Errores de compilación
- ✅ Documentación completa

### Cambios Realizados
- ✅ Corregido el paquete de las clases de modelo en `persistence.xml` de `com.apirest.model` a `com.libreria.model`
- ✅ Verificada la estructura completa del proyecto
- ✅ Verificadas todas las clases de modelo, servicio y recursos REST
- ✅ Creada documentación de instalación y despliegue
- ✅ Agregado archivo .gitignore

## ⚠️ Notas Importantes
- El proyecto utiliza Jakarta EE 11.0.0-M1 (Milestone)
- La configuración de Hibernate está en modo `update` para sincronizar automáticamente el esquema de base de datos
- Los errores mostrados en el IDE sobre `persistence.xml` son advertencias del IDE y se resolverán al cargar las dependencias de Maven correctamente
- Para producción, se recomienda cambiar `hibernate.hbm2ddl.auto` a `validate`

## 👥 Contribuir
Para contribuir al proyecto, por favor crear un fork y enviar un pull request con los cambios propuestos.

