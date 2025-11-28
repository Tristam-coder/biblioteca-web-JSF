# Instrucciones de Configuración y Despliegue

## Requisitos Previos

1. **Java Development Kit (JDK) 17 o superior**
   - Descargar desde: https://www.oracle.com/java/technologies/downloads/
   - Verificar instalación: `java -version`

2. **Apache Maven**
   - Descargar desde: https://maven.apache.org/download.cgi
   - Configurar variable de entorno `MAVEN_HOME`
   - Agregar Maven al PATH
   - Verificar instalación: `mvn -version`

3. **PostgreSQL**
   - Descargar desde: https://www.postgresql.org/download/
   - Puerto por defecto: 5432 (el proyecto está configurado para 5433, ajustar según necesidad)

4. **GlassFish 7.x o superior**
   - Descargar desde: https://glassfish.org/download
   - Compatible con Jakarta EE 11

## Configuración de la Base de Datos

### Paso 1: Crear la base de datos y usuario

```sql
-- Conectarse a PostgreSQL como administrador
psql -U postgres

-- Crear el usuario
CREATE USER appuser WITH PASSWORD 'apppass';

-- Crear la base de datos
CREATE DATABASE libreria OWNER appuser;

-- Otorgar privilegios
GRANT ALL PRIVILEGES ON DATABASE libreria TO appuser;

-- Conectarse a la base de datos libreria
\c libreria

-- Otorgar privilegios en el esquema public
GRANT ALL ON SCHEMA public TO appuser;
```

### Paso 2: Ejecutar el script de creación de tablas (opcional)

Si deseas crear manualmente las tablas antes de que Hibernate lo haga automáticamente:

```bash
psql -U appuser -d libreria -f biblioteca_optimizada.sql
```

**Nota:** Hibernate está configurado en modo `update`, por lo que creará/actualizará las tablas automáticamente al iniciar la aplicación.

### Paso 3: (Opcional) Cargar datos de prueba

```bash
psql -U appuser -d libreria -f datos_prueba.sql
```

## Configuración del Proyecto

### Verificar persistence.xml

El archivo `src/main/resources/META-INF/persistence.xml` debe tener la configuración correcta:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5433/libreria"/>
<property name="jakarta.persistence.jdbc.user" value="appuser"/>
<property name="jakarta.persistence.jdbc.password" value="apppass"/>
```

Si tu PostgreSQL está en un puerto diferente (por ejemplo, 5432), actualiza la URL:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/libreria"/>
```

## Compilación del Proyecto

### Desde la línea de comandos:

```bash
cd C:\Users\Trist\Desktop\Proyecto_Libreria\Proyecto_Libreria
mvn clean package
```

Esto generará un archivo WAR en `target/Proyecto_Libreria-1.0-SNAPSHOT.war`

### Desde NetBeans:

1. Abrir el proyecto en NetBeans
2. Click derecho en el proyecto → "Clean and Build"
3. El archivo WAR se generará automáticamente

## Despliegue en GlassFish

### Método 1: Desde la Consola de Administración

1. Iniciar GlassFish:
   ```bash
   asadmin start-domain
   ```

2. Abrir la consola de administración: http://localhost:4848

3. Navegar a: Applications → Deploy

4. Seleccionar el archivo WAR generado

5. Click en "OK" para desplegar

### Método 2: Desde la línea de comandos

```bash
asadmin deploy target/Proyecto_Libreria-1.0-SNAPSHOT.war
```

### Método 3: Desde NetBeans

1. Click derecho en el proyecto → "Run" o "Deploy"
2. NetBeans desplegará automáticamente en GlassFish

## Probar la API

Una vez desplegado, la API estará disponible en:

```
http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/
```

### Endpoints disponibles:

- **Usuarios:** `/api/usuarios`
- **Autores:** `/api/autores`
- **Editoriales:** `/api/editoriales`
- **Obras:** `/api/obras`
- **Tipos de Obra:** `/api/tipo-obras`
- **Ejemplares:** `/api/ejemplares`
- **Préstamos:** `/api/prestamos`
- **Reservas:** `/api/reservas`
- **Multas:** `/api/multas`
- **Notificaciones:** `/api/notificaciones`
- **Historial:** `/api/historial`
- **Obra-Autor:** `/api/obra-autor`

### Ejemplo de prueba con cURL:

```bash
# Listar todos los usuarios
curl http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios

# Crear un nuevo usuario
curl -X POST http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Juan\",\"apellido\":\"Pérez\",\"email\":\"juan@example.com\",\"password\":\"123456\"}"

# Obtener un usuario por ID
curl http://localhost:8080/Proyecto_Libreria-1.0-SNAPSHOT/api/usuarios/1
```

## Solución de Problemas

### Error: "Cannot resolve class" en persistence.xml

- Este es un error del IDE, no afecta la compilación
- Solución: Click derecho en el proyecto → "Reload Maven Project" o "Reload from Disk"

### Error: "Connection refused" al conectar a PostgreSQL

- Verificar que PostgreSQL esté ejecutándose: `pg_isready`
- Verificar el puerto en persistence.xml
- Verificar firewall/antivirus

### Error: "Class not found: org.postgresql.Driver"

- Verificar que la dependencia de PostgreSQL esté en pom.xml
- Ejecutar `mvn clean install` para descargar dependencias

### Error: "Authentication failed"

- Verificar usuario y contraseña en persistence.xml
- Verificar que el usuario tenga permisos en la base de datos

## Estructura de Logs

GlassFish genera logs en:
```
<GLASSFISH_HOME>/glassfish/domains/domain1/logs/server.log
```

Hibernate mostrará las consultas SQL en la consola si `hibernate.show_sql` está en `true`.

## Contacto y Soporte

Para reportar problemas o contribuir al proyecto, consultar el archivo README.md

