# Car Management System - Recuperación DI

Proyecto final para la recuperación de Desarrollo de Interfaces.

## Características implementadas (Rúbrica)
- **Idioma:** Código programado íntegramente en inglés.
- **Patrón FXML y Service:** Interfaz completamente separada de la lógica de negocio y de acceso a base de datos (DAOs).
- **Concurrencia (Task):** Consultas pesadas a base de datos delegadas en hilos secundarios para garantizar que la UI no se congele durante el Login, Registro y la carga del catálogo.
- **Seguridad:** Contraseñas encriptadas con algoritmo SHA-256.
- **Validaciones de Registro:** Contraseña requerida de mínimo 6 caracteres, formato alfanumérico estricto y comprobación de igualdad en ambos campos.
- **Navegación Dinámica:** Inyección de vistas limpias dentro de un `StackPane` central en lugar de abrir múltiples ventanas flotantes.
- **Componentes Dinámicos:** Catálogo de vehículos autogenerado mediante la instanciación FXML dinámica e inserción en un `TilePane`.
- **Reglas de Negocio:**
    - Sistema de favoritos personal y cálculo de favorito global.
    - Tabla de administración `TableView` protegida por permisos (solo el rol `ADMIN` puede visualizar y usar el borrado de usuarios).

## Instrucciones de ejecución para el Profesor
1. Ejecutar íntegramente el script `recu_di.sql` (ubicado en la raíz del proyecto) en su servidor MySQL. Este script autogenera la base de datos, tablas, un usuario administrador listo para usar y el catálogo de vehículos.
2. Modificar si es necesario el archivo `src/main/resources/config.properties` con las credenciales locales de su MySQL.
3. Compilar e iniciar la aplicación desde `MainApp.java`.

### Credenciales de prueba
- **Administrador:** Usuario: `admin` | Contraseña: `admin123`
- (Opcional) Recomendable registrar un nuevo usuario en la pantalla de "Sign Up" para testear las validaciones asíncronas y el control de errores.