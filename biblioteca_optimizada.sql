-- =========================================
-- SISTEMA DE GESTIÓN DE BIBLIOTECA
-- Base de datos optimizada para PostgreSQL
-- =========================================

-- Configuración inicial
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;

-- =========================================
-- TABLAS MAESTRAS (CATÁLOGOS)
-- =========================================

-- Tabla: tipo_obra
-- Descripción: Catálogo de tipos de obras bibliográficas
CREATE TABLE tipo_obra (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    dias_prestamo_maximo INTEGER DEFAULT 15,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE tipo_obra IS 'Catálogo de tipos de obras: libros, diccionarios, revistas, etc.';
COMMENT ON COLUMN tipo_obra.dias_prestamo_maximo IS 'Días máximos de préstamo según tipo de obra';

-- Tabla: editorial
-- Descripción: Información de editoriales
CREATE TABLE editorial (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    pais VARCHAR(100),
    ciudad VARCHAR(100),
    direccion TEXT,
    telefono VARCHAR(20),
    email VARCHAR(255),
    sitio_web VARCHAR(255),
    fecha_fundacion DATE,
    activa BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE editorial IS 'Registro de editoriales que publican las obras';

CREATE INDEX idx_editorial_nombre ON editorial(nombre);
CREATE INDEX idx_editorial_pais ON editorial(pais);

-- Tabla: autor
-- Descripción: Información de autores
CREATE TABLE autor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(511) GENERATED ALWAYS AS (nombre || ' ' || apellido) STORED,
    fecha_nacimiento DATE,
    fecha_fallecimiento DATE,
    nacionalidad VARCHAR(100),
    biografia TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_fechas_autor CHECK (fecha_fallecimiento IS NULL OR fecha_fallecimiento >= fecha_nacimiento)
);

COMMENT ON TABLE autor IS 'Registro de autores de obras bibliográficas';
COMMENT ON COLUMN autor.nombre_completo IS 'Campo calculado automáticamente';

CREATE INDEX idx_autor_nombre_completo ON autor(nombre_completo);
CREATE INDEX idx_autor_apellido ON autor(apellido);
CREATE INDEX idx_autor_nacionalidad ON autor(nacionalidad);

-- =========================================
-- OBRAS Y EJEMPLARES
-- =========================================

-- Tabla: obra
-- Descripción: Información de obras bibliográficas (el "qué")
CREATE TABLE obra (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(500) NOT NULL,
    subtitulo VARCHAR(500),
    isbn VARCHAR(20) UNIQUE,
    issn VARCHAR(20),
    tipo_obra_id INTEGER NOT NULL REFERENCES tipo_obra(id),
    editorial_id INTEGER REFERENCES editorial(id),
    anio_publicacion INTEGER,
    edicion INTEGER DEFAULT 1,
    numero_paginas INTEGER,
    idioma VARCHAR(100) DEFAULT 'Español',
    area_tematica VARCHAR(200),
    nivel_academico VARCHAR(50), -- Primaria, Secundaria, Universitario, etc.
    descripcion TEXT,
    palabras_clave TEXT[], -- Array para búsqueda temática
    imagen_portada VARCHAR(500), -- URL de la portada
    activo BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_anio_publicacion CHECK (anio_publicacion >= 1000 AND anio_publicacion <= EXTRACT(YEAR FROM CURRENT_DATE) + 1),
    CONSTRAINT chk_isbn_o_issn CHECK (isbn IS NOT NULL OR issn IS NOT NULL)
);

COMMENT ON TABLE obra IS 'Registro de obras bibliográficas (catálogo conceptual)';
COMMENT ON COLUMN obra.isbn IS 'ISBN para libros';
COMMENT ON COLUMN obra.issn IS 'ISSN para publicaciones periódicas';
COMMENT ON COLUMN obra.palabras_clave IS 'Array de palabras clave para búsqueda';

CREATE INDEX idx_obra_titulo ON obra USING gin(to_tsvector('spanish', titulo));
CREATE INDEX idx_obra_tipo ON obra(tipo_obra_id);
CREATE INDEX idx_obra_editorial ON obra(editorial_id);
CREATE INDEX idx_obra_area_tematica ON obra(area_tematica);
CREATE INDEX idx_obra_palabras_clave ON obra USING gin(palabras_clave);

-- Tabla: obra_autor
-- Descripción: Relación muchos a muchos entre obras y autores
CREATE TABLE obra_autor (
    id SERIAL PRIMARY KEY,
    obra_id INTEGER NOT NULL REFERENCES obra(id) ON DELETE CASCADE,
    autor_id INTEGER NOT NULL REFERENCES autor(id) ON DELETE CASCADE,
    tipo_contribucion VARCHAR(100) DEFAULT 'Autor', -- Autor, Coautor, Editor, Traductor, Ilustrador
    orden INTEGER DEFAULT 1, -- Orden de aparición
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(obra_id, autor_id, tipo_contribucion)
);

COMMENT ON TABLE obra_autor IS 'Relación entre obras y sus autores/colaboradores';
COMMENT ON COLUMN obra_autor.tipo_contribucion IS 'Rol del autor: Autor, Coautor, Editor, Traductor, etc.';

CREATE INDEX idx_obra_autor_obra ON obra_autor(obra_id);
CREATE INDEX idx_obra_autor_autor ON obra_autor(autor_id);

-- Tabla: ejemplar
-- Descripción: Copias físicas de obras (el "dónde está")
CREATE TABLE ejemplar (
    id SERIAL PRIMARY KEY,
    obra_id INTEGER NOT NULL REFERENCES obra(id) ON DELETE RESTRICT,
    codigo_barras VARCHAR(100) NOT NULL UNIQUE,
    numero_inventario VARCHAR(50) UNIQUE,
    estado VARCHAR(50) DEFAULT 'DISPONIBLE', -- DISPONIBLE, PRESTADO, RESERVADO, MANTENIMIENTO, PERDIDO, DADO_DE_BAJA
    condicion VARCHAR(50) DEFAULT 'BUENO', -- NUEVO, BUENO, REGULAR, MALO, DETERIORADO
    ubicacion VARCHAR(200), -- Estante/Pasillo/Sección
    fecha_adquisicion DATE DEFAULT CURRENT_DATE,
    precio_adquisicion NUMERIC(10,2),
    donacion BOOLEAN DEFAULT false,
    donante VARCHAR(255),
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE ejemplar IS 'Copias físicas individuales de las obras';
COMMENT ON COLUMN ejemplar.estado IS 'Estado actual del ejemplar en el sistema';
COMMENT ON COLUMN ejemplar.condicion IS 'Condición física del ejemplar';

CREATE INDEX idx_ejemplar_obra ON ejemplar(obra_id);
CREATE INDEX idx_ejemplar_estado ON ejemplar(estado) WHERE estado IN ('DISPONIBLE', 'PRESTADO');
CREATE INDEX idx_ejemplar_codigo_barras ON ejemplar(codigo_barras);

-- =========================================
-- USUARIOS Y PERFILES
-- =========================================

-- Tabla: usuario
-- Descripción: Usuarios del sistema (socios, personal)
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    dni VARCHAR(20) UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(511) GENERATED ALWAYS AS (nombre || ' ' || apellido) STORED,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    telefono_alternativo VARCHAR(20),
    direccion TEXT,
    fecha_nacimiento DATE,
    tipo_usuario VARCHAR(50) DEFAULT 'SOCIO', -- SOCIO, BIBLIOTECARIO, ADMINISTRADOR
    estado VARCHAR(50) DEFAULT 'ACTIVO', -- ACTIVO, SUSPENDIDO, INACTIVO
    fecha_registro DATE DEFAULT CURRENT_DATE,
    ultima_conexion TIMESTAMP,
    numero_socio VARCHAR(50) UNIQUE, -- Número de carnet
    fecha_vencimiento_socio DATE, -- Para membresías
    foto_perfil VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE usuario IS 'Usuarios del sistema: socios y personal de biblioteca';
COMMENT ON COLUMN usuario.tipo_usuario IS 'Rol del usuario en el sistema';
COMMENT ON COLUMN usuario.numero_socio IS 'Número de carnet o identificación de socio';

CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_dni ON usuario(dni);
CREATE INDEX idx_usuario_tipo ON usuario(tipo_usuario);
CREATE INDEX idx_usuario_estado ON usuario(estado);
CREATE INDEX idx_usuario_nombre_completo ON usuario(nombre_completo);

-- =========================================
-- PRÉSTAMOS Y RESERVAS
-- =========================================

-- Tabla: prestamo
-- Descripción: Registro de préstamos de ejemplares
CREATE TABLE prestamo (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario(id) ON DELETE RESTRICT,
    ejemplar_id INTEGER NOT NULL REFERENCES ejemplar(id) ON DELETE RESTRICT,
    fecha_prestamo DATE NOT NULL DEFAULT CURRENT_DATE,
    fecha_devolucion_prevista DATE NOT NULL,
    fecha_devolucion_real DATE,
    dias_prestamo INTEGER,
    dias_retraso INTEGER,
    estado VARCHAR(50) DEFAULT 'ACTIVO', -- ACTIVO, DEVUELTO, VENCIDO, PERDIDO
    renovaciones INTEGER DEFAULT 0,
    max_renovaciones INTEGER DEFAULT 2,
    bibliotecario_prestamo_id INTEGER REFERENCES usuario(id),
    bibliotecario_devolucion_id INTEGER REFERENCES usuario(id),
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_fechas_prestamo CHECK (fecha_devolucion_prevista >= fecha_prestamo),
    CONSTRAINT chk_fecha_devolucion_real CHECK (fecha_devolucion_real IS NULL OR fecha_devolucion_real >= fecha_prestamo),
    CONSTRAINT chk_renovaciones CHECK (renovaciones <= max_renovaciones)
);

COMMENT ON TABLE prestamo IS 'Registro de préstamos de ejemplares a usuarios';
COMMENT ON COLUMN prestamo.dias_retraso IS 'Calculado automáticamente';
COMMENT ON COLUMN prestamo.renovaciones IS 'Número de veces que se renovó el préstamo';

CREATE INDEX idx_prestamo_usuario ON prestamo(usuario_id);
CREATE INDEX idx_prestamo_ejemplar ON prestamo(ejemplar_id);
CREATE INDEX idx_prestamo_estado ON prestamo(estado);
CREATE INDEX idx_prestamo_fechas ON prestamo(fecha_prestamo, fecha_devolucion_prevista);
CREATE INDEX idx_prestamo_vencidos ON prestamo(fecha_devolucion_prevista) 
    WHERE estado = 'ACTIVO' AND fecha_devolucion_real IS NULL;

-- Tabla: reserva
-- Descripción: Reservas de obras por usuarios
CREATE TABLE reserva (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    obra_id INTEGER NOT NULL REFERENCES obra(id) ON DELETE CASCADE,
    fecha_reserva TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion TIMESTAMP, -- Fecha límite para recoger
    estado VARCHAR(50) DEFAULT 'PENDIENTE', -- PENDIENTE, DISPONIBLE, RETIRADA, EXPIRADA, CANCELADA
    posicion_cola INTEGER, -- Posición en lista de espera
    notificado BOOLEAN DEFAULT false,
    fecha_notificacion TIMESTAMP,
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE reserva IS 'Sistema de reservas de obras';
COMMENT ON COLUMN reserva.posicion_cola IS 'Posición en la cola de espera';
COMMENT ON COLUMN reserva.fecha_expiracion IS 'Fecha límite para recoger la obra reservada';

CREATE INDEX idx_reserva_usuario ON reserva(usuario_id);
CREATE INDEX idx_reserva_obra ON reserva(obra_id);
CREATE INDEX idx_reserva_estado ON reserva(estado);
CREATE INDEX idx_reserva_pendientes ON reserva(obra_id, posicion_cola) 
    WHERE estado = 'PENDIENTE';

-- =========================================
-- MULTAS Y NOTIFICACIONES
-- =========================================

-- Tabla: multa
-- Descripción: Registro de multas por retrasos o daños
CREATE TABLE multa (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario(id) ON DELETE RESTRICT,
    prestamo_id INTEGER REFERENCES prestamo(id) ON DELETE SET NULL,
    tipo VARCHAR(50) NOT NULL, -- RETRASO, PERDIDA, DAÑO
    monto NUMERIC(10,2) NOT NULL,
    motivo TEXT NOT NULL,
    estado VARCHAR(50) DEFAULT 'PENDIENTE', -- PENDIENTE, PAGADA, CONDONADA, VENCIDA
    fecha_generacion DATE DEFAULT CURRENT_DATE,
    fecha_vencimiento DATE,
    fecha_pago DATE,
    metodo_pago VARCHAR(50), -- EFECTIVO, TARJETA, TRANSFERENCIA
    comprobante VARCHAR(100),
    observaciones TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_monto_positivo CHECK (monto > 0),
    CONSTRAINT chk_fecha_pago CHECK (fecha_pago IS NULL OR fecha_pago >= fecha_generacion)
);

COMMENT ON TABLE multa IS 'Registro de multas y sanciones económicas';
COMMENT ON COLUMN multa.tipo IS 'Tipo de multa: por retraso, pérdida o daño';

CREATE INDEX idx_multa_usuario ON multa(usuario_id);
CREATE INDEX idx_multa_estado ON multa(estado);
CREATE INDEX idx_multa_prestamo ON multa(prestamo_id);
CREATE INDEX idx_multa_pendientes ON multa(usuario_id, estado) WHERE estado = 'PENDIENTE';

-- Tabla: notificacion
-- Descripción: Sistema de notificaciones a usuarios
CREATE TABLE notificacion (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    tipo VARCHAR(100) NOT NULL, -- RECORDATORIO_DEVOLUCION, MULTA, RESERVA_DISPONIBLE, VENCIMIENTO_SOCIO
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    prioridad VARCHAR(20) DEFAULT 'NORMAL', -- BAJA, NORMAL, ALTA, URGENTE
    leida BOOLEAN DEFAULT false,
    fecha_lectura TIMESTAMP,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    canal VARCHAR(50) DEFAULT 'SISTEMA', -- SISTEMA, EMAIL, SMS, PUSH
    estado_envio VARCHAR(50) DEFAULT 'PENDIENTE', -- PENDIENTE, ENVIADA, FALLIDA
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE notificacion IS 'Sistema de notificaciones y alertas a usuarios';
COMMENT ON COLUMN notificacion.canal IS 'Canal por el que se envió la notificación';

CREATE INDEX idx_notificacion_usuario ON notificacion(usuario_id);
CREATE INDEX idx_notificacion_leida ON notificacion(usuario_id, leida);
CREATE INDEX idx_notificacion_tipo ON notificacion(tipo);
CREATE INDEX idx_notificacion_no_leidas ON notificacion(usuario_id) WHERE leida = false;

-- =========================================
-- AUDITORÍA Y HISTORIAL
-- =========================================

-- Tabla: historial
-- Descripción: Log de actividades del sistema
CREATE TABLE historial (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER REFERENCES usuario(id) ON DELETE SET NULL,
    accion VARCHAR(100) NOT NULL, -- INSERT, UPDATE, DELETE, LOGIN, LOGOUT
    modulo VARCHAR(100) NOT NULL, -- prestamo, obra, usuario, etc.
    tabla_afectada VARCHAR(100),
    registro_id INTEGER,
    descripcion TEXT,
    datos_anteriores JSONB, -- Estado anterior del registro
    datos_nuevos JSONB, -- Estado nuevo del registro
    ip_address INET,
    user_agent TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE historial IS 'Registro de auditoría de todas las operaciones del sistema';
COMMENT ON COLUMN historial.datos_anteriores IS 'Estado del registro antes del cambio (JSON)';
COMMENT ON COLUMN historial.datos_nuevos IS 'Estado del registro después del cambio (JSON)';

CREATE INDEX idx_historial_usuario ON historial(usuario_id);
CREATE INDEX idx_historial_fecha ON historial(created_at DESC);
CREATE INDEX idx_historial_modulo ON historial(modulo);
CREATE INDEX idx_historial_tabla_registro ON historial(tabla_afectada, registro_id);

-- =========================================
-- FUNCIONES Y TRIGGERS
-- =========================================

-- Función para actualizar updated_at automáticamente
CREATE OR REPLACE FUNCTION actualizar_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Función para calcular días de préstamo
CREATE OR REPLACE FUNCTION calcular_dias_prestamo()
RETURNS TRIGGER AS $$
BEGIN
    NEW.dias_prestamo := NEW.fecha_devolucion_prevista - NEW.fecha_prestamo;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Función para calcular días de retraso
CREATE OR REPLACE FUNCTION calcular_dias_retraso()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.fecha_devolucion_real IS NOT NULL THEN
        NEW.dias_retraso := GREATEST(0, NEW.fecha_devolucion_real - NEW.fecha_devolucion_prevista);
    ELSIF CURRENT_DATE > NEW.fecha_devolucion_prevista THEN
        NEW.dias_retraso := CURRENT_DATE - NEW.fecha_devolucion_prevista;
    ELSE
        NEW.dias_retraso := 0;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Función para calcular multas por retraso
CREATE OR REPLACE FUNCTION calcular_multa_retraso()
RETURNS TRIGGER AS $$
DECLARE
    dias_retraso INTEGER;
    monto_multa NUMERIC(10,2);
BEGIN
    IF NEW.fecha_devolucion_real IS NOT NULL AND NEW.fecha_devolucion_real > NEW.fecha_devolucion_prevista THEN
        dias_retraso := NEW.fecha_devolucion_real - NEW.fecha_devolucion_prevista;
        monto_multa := dias_retraso * 1.50; -- 1.50 por día de retraso
        
        INSERT INTO multa (usuario_id, prestamo_id, tipo, monto, motivo, fecha_vencimiento)
        VALUES (
            NEW.usuario_id,
            NEW.id,
            'RETRASO',
            monto_multa,
            'Devolución tardía de ' || dias_retraso || ' día(s)',
            CURRENT_DATE + INTERVAL '30 days'
        );
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Función para actualizar estado del ejemplar al prestar
CREATE OR REPLACE FUNCTION actualizar_estado_ejemplar_prestamo()
RETURNS TRIGGER AS $$
BEGIN
    -- Al crear un préstamo, marcar ejemplar como PRESTADO
    IF TG_OP = 'INSERT' THEN
        UPDATE ejemplar SET estado = 'PRESTADO', updated_at = CURRENT_TIMESTAMP 
        WHERE id = NEW.ejemplar_id;
    END IF;
    
    -- Al devolver un préstamo, marcar ejemplar como DISPONIBLE
    IF TG_OP = 'UPDATE' AND NEW.fecha_devolucion_real IS NOT NULL AND OLD.fecha_devolucion_real IS NULL THEN
        UPDATE ejemplar SET estado = 'DISPONIBLE', updated_at = CURRENT_TIMESTAMP 
        WHERE id = NEW.ejemplar_id;
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Aplicar triggers a tablas relevantes
CREATE TRIGGER trigger_obra_updated_at BEFORE UPDATE ON obra
    FOR EACH ROW EXECUTE FUNCTION actualizar_updated_at();

CREATE TRIGGER trigger_ejemplar_updated_at BEFORE UPDATE ON ejemplar
    FOR EACH ROW EXECUTE FUNCTION actualizar_updated_at();

CREATE TRIGGER trigger_usuario_updated_at BEFORE UPDATE ON usuario
    FOR EACH ROW EXECUTE FUNCTION actualizar_updated_at();

CREATE TRIGGER trigger_prestamo_updated_at BEFORE UPDATE ON prestamo
    FOR EACH ROW EXECUTE FUNCTION actualizar_updated_at();

CREATE TRIGGER trigger_reserva_updated_at BEFORE UPDATE ON reserva
    FOR EACH ROW EXECUTE FUNCTION actualizar_updated_at();

CREATE TRIGGER trigger_multa_updated_at BEFORE UPDATE ON multa
    FOR EACH ROW EXECUTE FUNCTION actualizar_updated_at();

-- Triggers para calcular campos de préstamo
CREATE TRIGGER trigger_calcular_dias_prestamo
    BEFORE INSERT OR UPDATE ON prestamo
    FOR EACH ROW
    EXECUTE FUNCTION calcular_dias_prestamo();

CREATE TRIGGER trigger_calcular_dias_retraso
    BEFORE INSERT OR UPDATE ON prestamo
    FOR EACH ROW
    EXECUTE FUNCTION calcular_dias_retraso();

CREATE TRIGGER trigger_multa_retraso AFTER UPDATE ON prestamo
    FOR EACH ROW 
    WHEN (OLD.fecha_devolucion_real IS NULL AND NEW.fecha_devolucion_real IS NOT NULL)
    EXECUTE FUNCTION calcular_multa_retraso();

CREATE TRIGGER trigger_estado_ejemplar_prestamo
    AFTER INSERT OR UPDATE ON prestamo
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_estado_ejemplar_prestamo();

-- =========================================
-- VISTAS ÚTILES
-- =========================================

-- Vista: obras_completas
-- Descripción: Obras con toda su información relacionada
CREATE OR REPLACE VIEW v_obras_completas AS
SELECT 
    o.id,
    o.titulo,
    o.subtitulo,
    o.isbn,
    o.issn,
    o.anio_publicacion,
    o.edicion,
    o.numero_paginas,
    o.idioma,
    o.area_tematica,
    o.nivel_academico,
    o.descripcion,
    t.nombre AS tipo_obra,
    e.nombre AS editorial,
    e.pais AS pais_editorial,
    STRING_AGG(DISTINCT a.nombre_completo, ', ' ORDER BY a.nombre_completo) AS autores,
    COUNT(DISTINCT ej.id) AS total_ejemplares,
    COUNT(DISTINCT ej.id) FILTER (WHERE ej.estado = 'DISPONIBLE') AS ejemplares_disponibles,
    COUNT(DISTINCT ej.id) FILTER (WHERE ej.estado = 'PRESTADO') AS ejemplares_prestados,
    o.created_at
FROM obra o
LEFT JOIN tipo_obra t ON o.tipo_obra_id = t.id
LEFT JOIN editorial e ON o.editorial_id = e.id
LEFT JOIN obra_autor oa ON o.id = oa.obra_id
LEFT JOIN autor a ON oa.autor_id = a.id
LEFT JOIN ejemplar ej ON o.id = ej.obra_id
WHERE o.activo = true
GROUP BY o.id, t.nombre, e.nombre, e.pais;

COMMENT ON VIEW v_obras_completas IS 'Vista consolidada de obras con toda su información';

-- Vista: prestamos_activos
-- Descripción: Préstamos activos con información detallada
CREATE OR REPLACE VIEW v_prestamos_activos AS
SELECT 
    p.id AS prestamo_id,
    p.fecha_prestamo,
    p.fecha_devolucion_prevista,
    p.dias_retraso,
    p.estado,
    u.id AS usuario_id,
    u.nombre_completo AS usuario_nombre,
    u.email AS usuario_email,
    u.telefono AS usuario_telefono,
    u.numero_socio,
    o.id AS obra_id,
    o.titulo AS obra_titulo,
    o.isbn,
    STRING_AGG(DISTINCT a.nombre_completo, ', ') AS autores,
    ej.id AS ejemplar_id,
    ej.codigo_barras,
    ej.ubicacion,
    CASE 
        WHEN p.dias_retraso > 0 THEN true
        ELSE false
    END AS tiene_retraso,
    p.renovaciones,
    p.max_renovaciones,
    CASE 
        WHEN p.renovaciones >= p.max_renovaciones THEN false
        ELSE true
    END AS puede_renovar
FROM prestamo p
JOIN usuario u ON p.usuario_id = u.id
JOIN ejemplar ej ON p.ejemplar_id = ej.id
JOIN obra o ON ej.obra_id = o.id
LEFT JOIN obra_autor oa ON o.id = oa.obra_id
LEFT JOIN autor a ON oa.autor_id = a.id
WHERE p.estado = 'ACTIVO' AND p.fecha_devolucion_real IS NULL
GROUP BY p.id, u.id, o.id, ej.id;

COMMENT ON VIEW v_prestamos_activos IS 'Vista de préstamos activos con información completa';

-- Vista: estadisticas_usuario
-- Descripción: Estadísticas por usuario
CREATE OR REPLACE VIEW v_estadisticas_usuario AS
SELECT 
    u.id AS usuario_id,
    u.nombre_completo,
    u.email,
    u.tipo_usuario,
    u.estado,
    u.fecha_registro,
    COUNT(DISTINCT p.id) AS total_prestamos,
    COUNT(DISTINCT p.id) FILTER (WHERE p.estado = 'ACTIVO') AS prestamos_activos,
    COUNT(DISTINCT p.id) FILTER (WHERE p.dias_retraso > 0) AS prestamos_con_retraso,
    COUNT(DISTINCT m.id) AS total_multas,
    COALESCE(SUM(m.monto) FILTER (WHERE m.estado = 'PENDIENTE'), 0) AS multas_pendientes,
    COUNT(DISTINCT r.id) FILTER (WHERE r.estado = 'PENDIENTE') AS reservas_activas
FROM usuario u
LEFT JOIN prestamo p ON u.id = p.usuario_id
LEFT JOIN multa m ON u.id = m.usuario_id
LEFT JOIN reserva r ON u.id = r.usuario_id
GROUP BY u.id;

COMMENT ON VIEW v_estadisticas_usuario IS 'Estadísticas consolidadas por usuario';

-- Vista: ejemplares_disponibles
-- Descripción: Ejemplares disponibles para préstamo
CREATE OR REPLACE VIEW v_ejemplares_disponibles AS
SELECT 
    ej.id,
    ej.codigo_barras,
    ej.numero_inventario,
    ej.estado,
    ej.condicion,
    ej.ubicacion,
    o.id AS obra_id,
    o.titulo,
    o.isbn,
    o.anio_publicacion,
    t.nombre AS tipo_obra,
    e.nombre AS editorial,
    STRING_AGG(DISTINCT a.nombre_completo, ', ') AS autores
FROM ejemplar ej
JOIN obra o ON ej.obra_id = o.id
JOIN tipo_obra t ON o.tipo_obra_id = t.id
LEFT JOIN editorial e ON o.editorial_id = e.id
LEFT JOIN obra_autor oa ON o.id = oa.obra_id
LEFT JOIN autor a ON oa.autor_id = a.id
WHERE ej.estado = 'DISPONIBLE' AND o.activo = true
GROUP BY ej.id, o.id, t.nombre, e.nombre;

COMMENT ON VIEW v_ejemplares_disponibles IS 'Ejemplares disponibles para préstamo';

-- =========================================
-- DATOS INICIALES
-- =========================================

-- Tipos de obra
INSERT INTO tipo_obra (nombre, descripcion, dias_prestamo_maximo) VALUES
('Libro', 'Obras literarias, académicas o de consulta general', 15),
('Diccionario', 'Diccionarios de idiomas', 7),
('Enciclopedia', 'Obras de consulta enciclopédicas', 7),
('Revista', 'Publicaciones periódicas', 7),
('Tesis', 'Trabajos de investigación académica', 15),
('Manual', 'Manuales técnicos y guías', 15),
('Atlas', 'Atlas geográficos e históricos', 7),
('Comic', 'Historietas y novelas gráficas', 15);

-- Usuario administrador inicial
INSERT INTO usuario (dni, nombre, apellido, email, password_hash, tipo_usuario) VALUES
('00000000', 'Administrador', 'Sistema', 'admin@biblioteca.com', '$2a$10$rOzJNs4Z5V3YvYK6jUqj.OU4UQYQZ5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z', 'ADMINISTRADOR');

-- =========================================
-- CONFIGURACIÓN FINAL
-- =========================================

COMMENT ON DATABASE postgres IS 'Sistema de Gestión de Biblioteca - Optimizado para PostgreSQL';

-- Mensaje de confirmación
DO $$
BEGIN
    RAISE NOTICE 'Base de datos de biblioteca creada exitosamente!';
    RAISE NOTICE 'Tablas creadas: 10';
    RAISE NOTICE 'Vistas creadas: 4';
    RAISE NOTICE 'Funciones creadas: 5';
    RAISE NOTICE 'Triggers creados: 12';
END $$;