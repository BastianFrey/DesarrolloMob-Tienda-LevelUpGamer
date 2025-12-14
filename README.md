🎮 Level-Up Gamer - Tienda Móvil para Gamers
Level-Up Gamer es una aplicación móvil nativa desarrollada en Android (Kotlin + Jetpack Compose) que permite a los usuarios navegar, buscar y comprar productos de tecnología y videojuegos. El sistema está respaldado por una arquitectura de microservicios en Spring Boot con una base de datos PostgreSQL.

👥 Integrantes del Equipo
Nombre Completo         Rol / Tareas Principales
Bastián Frey            "Backend (Security), Integración API, Frontend"
Sebastián Catalán       "Diseño UI/UX, Frontend"
Tomás Zamora            "Documentación"

🚀 Funcionalidades Principales
📱 Aplicación Móvil (Frontend)
Autenticación: Login y Registro con validación de roles (Cliente, Vendedor, Administrador).
Catálogo Dinámico: Listado de productos consumidos desde el backend.
Gestión de Productos (Admin): Formulario para agregar productos con validaciones.
Recursos Nativos: Uso de Cámara y Galería para subir fotos de perfil o productos.
Persistencia Local: Uso de Room/DataStore para guardar la sesión del usuario y carrito de compras offline.
API Externa: Integración con API para mostrar información en tiempo real.
Diseño: Interfaz moderna implementada con Material Design 3 y modo oscuro/claro.

⚙️ Backend (Microservicios)
Seguridad: Implementación de JWT (JSON Web Token) y encriptación BCrypt.
Arquitectura: API RESTful escalable con Spring Boot 3.
Base de Datos: Persistencia relacional con PostgreSQL.


🔗 Endpoints Usados
Método,Endpoint,Rol Requerido,Descripción
POST,/api/auth/login,Público,Iniciar sesión y obtener Token JWT.
POST,/api/users/register,Público,Registrar usuario (asigna rol por dominio de correo).
GET,/api/products,Público,Obtener lista de todos los productos.
POST,/api/products,ADMIN,Crear un nuevo producto en la tienda.
PUT,/api/products/{id},ADMIN,Actualizar stock o precio de un producto.
DELETE,/api/products/{id},ADMIN,Eliminar un producto.
GET,/api/orders/my-orders,Cliente,Ver historial de compras del usuario.

🛠️ Instrucciones de Ejecución
1. Prerrequisitos
Java JDK 17 o superior (Recomendado JDK 21).
Android Studio Ladybug o superior.
PostgreSQL instalado y ejecutándose (Puerto 5432).

2. Ejecutar el Backend (Servidor)
Navegar a la carpeta backend/.
Abrir el archivo src/main/resources/application.properties y configurar las credenciales de base de datos:
spring.datasource.url=jdbc:postgresql://localhost:5432/levelup_db
spring.datasource.password=levelupgamer123

3. Ejecutar el proyecto con Maven o desde IntelliJ IDEA.

4. Verificar que inició en el puerto 8080.

Ejecutar la App Móvil (Android)
1. Abrir el proyecto en Android Studio.
2. erificar el archivo RetrofitClient.kt o Constants.kt:
3. Si usas Emulador: BASE_URL = "http://10.0.2.2:8080/"
4. Si usas Celular Físico: BASE_URL = "http://[TU_IP_PC]:8080/"
5. Sincronizar Gradle (Sync Project with Gradle Files).
6. Seleccionar dispositivo y dar clic en Run.

📦 APK Firmado y Keystore
El archivo instalable (.apk) listo para producción y la llave de firma (.jks) se encuentran en las siguientes rutas dentro del repositorio:
APK Firmado: android-app/app/release/app-release.apk
Keystore (.jks): android-app/keystore/levelup-key.jks

📂 Estructura del Código Fuente
El repositorio está dividido en dos grandes directorios:

📂 /backend-springboot: Contiene todo el código fuente de la API, controladores, modelos de seguridad y repositorios JPA.
📂 /frontend-android: Contiene el código fuente de la aplicación móvil, ViewModels, Composables, configuración de Gradle y recursos XML.

📈 Evidencia de Trabajo Colaborativo
A continuación se adjunta el registro de participación del equipo en GitHub:

🔗 Link al repositorio (Frontend): https://github.com/BastianFrey/DesarrolloMob-Tienda-LevelUpGamer.git 
🔗 Link al repositorio (Backend): https://github.com/BastianFrey/LevelUp-Gamer-Backend.git 
