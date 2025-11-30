# Cómo Instalar y Configurar Payara Server

Esta guía te ayudará a instalar Payara paso a paso para que funcione el proyecto de biblioteca.

## Qué necesitas antes de empezar

Asegúrate de tener instalado:
- Java 17 o más nuevo
- PostgreSQL funcionando en tu computadora
- Una base de datos llamada "Libreria" en PostgreSQL
- Internet para descargar Payara

## Paso 1: Verificar que tienes Java

Abre una terminal (CMD en Windows o Terminal en Mac/Linux) y escribe:

```bash
java -version
```

Deberías ver algo como:
```
java version "17.0.x" 
```

Si no tienes Java 17, descárgalo aquí:
- Para cualquier sistema: https://adoptium.net/ (recomendado, es gratis)
- Oracle JDK: https://www.oracle.com/java/technologies/downloads/

## Paso 2: Descargar Payara Server

Hay dos formas de descargarlo:

### Forma 1: Descarga manual (más fácil)

1. Ve a esta página: https://www.payara.fish/downloads/payara-platform-community-edition/
2. Busca "Payara Server 6.2023.12" o una versión más nueva
3. Descarga el archivo ZIP (Full Profile)

## Paso 3: Instalar Payara

### En Windows

1. Haz clic derecho en el archivo ZIP que descargaste
2. Selecciona "Extraer todo..."
3. Extrae a `C:\` (te quedará una carpeta `C:\payara6`)


## Paso 4: Arrancar Payara por primera vez

Ahora vamos a encender el servidor.

### En Windows

Abre CMD o PowerShell y escribe:
```cmd
cd C:\payara6\bin
asadmin start-domain
```

Si configuraste las variables de entorno en el paso anterior, simplemente escribe:
```cmd
asadmin start-domain
```


### Comprobar que funciona

Espera unos segundos y luego abre tu navegador. Visita estas direcciones:

- http://localhost:4848 (consola de administración de Payara)
- http://localhost:8080 (página principal del servidor)

Si ves la página de Payara, felicidades, está funcionando.

## Paso 5: Instalar el driver de PostgreSQL

Para que Payara pueda hablar con PostgreSQL, necesita un archivo especial llamado "driver".

### Conseguir el driver

El driver ya viene con el proyecto. Búscalo aquí:
```
target/Proyecto_Libreria-1.0-SNAPSHOT/WEB-INF/lib/postgresql-42.7.4.jar
```

Si no lo encuentras, descárgalo aquí:
https://jdbc.postgresql.org/download/postgresql-42.7.4.jar

### Copiar el driver a Payara

Una vez que tengas el archivo `postgresql-42.7.4.jar`, cópialo a la carpeta de Payara:

En Windows (abre CMD en la carpeta donde está el archivo):
```cmd
copy postgresql-42.7.4.jar C:\payara6\glassfish\domains\domain1\lib\
```



### Reiniciar Payara

Para que reconozca el driver, reinicia Payara:
```bash
asadmin restart-domain
```

## Paso 6: Conectar Payara con tu base de datos

Ahora vamos a decirle a Payara cómo conectarse a PostgreSQL.

### Forma fácil: Usar la consola web

1. Abre tu navegador y ve a: http://localhost:4848

2. En el menú de la izquierda, haz clic en:
   - Resources (Recursos)
   - JDBC
   - JDBC Connection Pools

3. Haz clic en el botón "New..." (Nuevo)

4. Llena estos datos:
   - Pool Name: `LibreriaPool`
   - Resource Type: `javax.sql.DataSource`
   - Database Driver Vendor: `PostgreSQL`

5. Haz clic en "Next" (Siguiente)

6. Baja hasta "Additional Properties" (Propiedades Adicionales) y busca estas propiedades. Cámbiales los valores:
   - **ServerName**: `localhost`
   - **PortNumber**: `5432`
   - **DatabaseName**: `Libreria`
   - **User**: `postgres`
   - **Password**: `*****` (o la contraseña que uses)

7. Haz clic en "Finish" (Finalizar)


8. Ahora crea el recurso JDBC:
   - Ve a: Resources > JDBC > JDBC Resources
   - Haz clic en "New..."
   - Escribe:
     - JNDI Name: `jdbc/LibreriaDS`
     - Pool Name: `LibreriaPool`
   - Haz clic en "OK"

Listo. Payara ya sabe cómo conectarse a tu base de datos.

### Forma rápida: Usar comandos

Si prefieres hacerlo más rápido con comandos, abre CMD o Terminal y ejecuta esto:

En Windows:
```cmd
cd C:\payara6\bin
asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property ServerName=localhost:PortNumber=5432:DatabaseName=Libreria:User=postgres:Password=gonza LibreriaPool

asadmin ping-connection-pool LibreriaPool

asadmin create-jdbc-resource --connectionpoolid LibreriaPool jdbc/LibreriaDS
```


### Forma automática: Usar el script del proyecto

El proyecto ya trae un script que hace todo automáticamente. Solo tienes que:

En Windows:
```cmd
cd "D:\Desarrollo WEB\biblioteca-web-JSF\Proyecto_Libreriav2"
configurar-datasource.bat
```

IMPORTANTE: Antes de ejecutarlo, abre el archivo con el Bloc de notas y cambia la línea que dice `PAYARA_HOME` para que apunte a donde instalaste Payara.

### Comprobar que quedó bien configurado

Para verificar que todo está listo, ejecuta:
```bash
asadmin list-jdbc-resources
```

Deberías ver en la lista: `jdbc/LibreriaDS`

También puedes ver los pools:
```bash
asadmin list-jdbc-connection-pools
```

Deberías ver: `LibreriaPool`

## Paso 8: Subir tu aplicación a Payara

Ya casi terminamos. Ahora vamos a poner tu aplicación en el servidor.

### La forma más fácil: Desde NetBeans

1. Abre el proyecto en NetBeans
2. Haz clic derecho sobre el nombre del proyecto
3. Selecciona "Run" (Ejecutar) o "Deploy" (Desplegar)
4. NetBeans hace todo solo: compila, empaqueta y sube la aplicación

### Si usas Maven por tu cuenta

Abre una terminal en la carpeta del proyecto y ejecuta:
```bash
mvn clean package
```

Esto te creará un archivo WAR en la carpeta `target`. Luego despliégalo:
```bash
asadmin deploy target/Proyecto_Libreria-1.0-SNAPSHOT.war
```

### Usando la consola web de Payara

1. Ve a: http://localhost:4848
2. Haz clic en "Applications" (Aplicaciones) en el menú izquierdo
3. Haz clic en el botón "Deploy..." (Desplegar)
4. Haz clic en "Choose File" y busca el archivo: `target/Proyecto_Libreria-1.0-SNAPSHOT.war`
5. En "Context Root" escribe: `/biblioteca`
6. Haz clic en "OK"

## Paso 9: Probar que todo funciona

### Ver en la consola de Payara

Ve a http://localhost:4848 y haz clic en "Applications". Deberías ver tu aplicación en la lista con un ícono verde.

### Probar en el navegador

Abre tu navegador y visita estas direcciones:

1. Página principal del proyecto:
   ```
   http://localhost:8080/biblioteca/
   ```

2. Ver los autores (API):
   ```
   http://localhost:8080/biblioteca/api/autores
   ```

3. Ver las obras (API):
   ```
   http://localhost:8080/biblioteca/api/obras
   ```

Si ves texto en formato JSON (aunque esté vacío como `[]`), significa que está funcionando perfecto.

## Comandos útiles que debes conocer

Estos son los comandos más importantes que vas a usar:

### Encender, apagar y reiniciar Payara

```bash
asadmin start-domain      # Encender Payara
asadmin stop-domain       # Apagar Payara
asadmin restart-domain    # Reiniciar Payara
asadmin list-domains      # Ver si está encendido o apagado
```

### Manejar tu aplicación

```bash
asadmin list-applications    # Ver qué aplicaciones están instaladas
asadmin deploy archivo.war   # Subir una aplicación nueva
asadmin redeploy Proyecto_Libreria-1.0-SNAPSHOT   # Actualizar la aplicación
asadmin undeploy Proyecto_Libreria-1.0-SNAPSHOT   # Quitar la aplicación
```

### Revisar la base de datos

```bash
asadmin list-jdbc-resources           # Ver las conexiones disponibles
asadmin list-jdbc-connection-pools    # Ver los pools configurados
asadmin ping-connection-pool LibreriaPool   # Probar si conecta con la BD
```

### Ver qué está pasando (logs)

```bash
asadmin view-log --lines=100      # Ver las últimas 100 líneas del log
asadmin view-log --follow=true    # Ver el log en vivo
```

También puedes abrir el archivo de log directamente:
- Windows: `C:\payara6\glassfish\domains\domain1\logs\server.log`
- Linux/Mac: `/opt/payara6/glassfish/domains/domain1/logs/server.log`

## Problemas comunes y cómo solucionarlos

### "Address already in use" - El puerto está ocupado

Esto pasa cuando otro programa está usando el puerto 8080 o 4848.

Opción 1 - Cerrar el programa que usa el puerto:

En Windows:
```cmd
netstat -ano | findstr :8080
taskkill /PID <numero_que_aparece> /F
```

Opción 2 - Cambiar el puerto de Payara:
```bash
asadmin set server.network-config.network-listeners.network-listener.http-listener-1.port=9090
asadmin restart-domain
```

### "Cannot connect to database" - No conecta con PostgreSQL

Revisa estos puntos:
1. Verifica que PostgreSQL esté corriendo (abre pgAdmin o revisa los servicios)
2. Asegúrate que la base de datos "Libreria" exista
3. Revisa que el usuario y contraseña sean correctos
4. Confirma que el archivo `postgresql-42.7.4.jar` esté en `payara6/glassfish/domains/domain1/lib/`

Prueba la conexión con:
```bash
asadmin ping-connection-pool LibreriaPool
```

### "Application not found" - No encuentra la aplicación

La aplicación no se instaló bien. Intenta:
```bash
asadmin list-applications
asadmin redeploy target/Proyecto_Libreria-1.0-SNAPSHOT.war
```

### Payara está muy lento o da error de memoria

Si la aplicación está lenta, dale más memoria a Payara:
```bash
asadmin delete-jvm-options "-Xmx512m"
asadmin create-jvm-options "-Xmx1024m"
asadmin restart-domain
```

### La consola web no abre (http://localhost:4848)

Verifica que Payara esté encendido:
```bash
asadmin list-domains
```

Si dice "not running", enciéndelo:
```bash
asadmin start-domain
```

## Si quieres desinstalar Payara

1. Apaga Payara:
   ```bash
   asadmin stop-domain
   ```

2. Borra la carpeta:
   - Windows: Elimina `C:\payara6`
   - Linux/Mac: `sudo rm -rf /opt/payara6`

3. Si configuraste variables de entorno, elimínalas también

## Links útiles

- Documentación de Payara: https://docs.payara.fish/
- Foro para preguntas: https://forum.payara.fish/
- Código fuente: https://github.com/payara/Payara

## Resumen rápido de todo

### Qué versiones usamos
- Payara Server: 6.2023.12 o más nuevo
- Java: 17 o más nuevo
- PostgreSQL Driver: 42.7.4
- Hibernate: 6.4.4.Final

### Configuración importante
- Nombre del DataSource: `jdbc/LibreriaDS`
- Nombre del Pool: `LibreriaPool`
- Base de datos: `Libreria`
- Ruta de la app: `/biblioteca`
- Ruta de la API: `/api`

### Direcciones para acceder

Una vez que todo esté listo, estas son las URLs que vas a usar:

- Tu aplicación: http://localhost:8080/biblioteca/
- La API REST: http://localhost:8080/biblioteca/api/
- Consola de Payara: http://localhost:4848

### Ejemplo de URLs de la API

- Ver autores: http://localhost:8080/biblioteca/api/autores
- Ver obras: http://localhost:8080/biblioteca/api/obras
- Ver usuarios: http://localhost:8080/biblioteca/api/usuarios
- Ver préstamos: http://localhost:8080/biblioteca/api/prestamos

---


