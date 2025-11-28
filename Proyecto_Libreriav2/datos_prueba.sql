-- EDITORIAL
INSERT INTO editorial (nombre, pais, ciudad, direccion, telefono) VALUES 
('Editorial Clásicos 1', 'España', 'Madrid', 'Calle Clásica 1', '900001'),
('Editorial Clásicos 2', 'España', 'Madrid', 'Calle Clásica 2', '900002'),
('Editorial Clásicos 3', 'España', 'Madrid', 'Calle Clásica 3', '900003'),
('Editorial Clásicos 4', 'España', 'Madrid', 'Calle Clásica 4', '900004'),
('Editorial Clásicos 5', 'España', 'Madrid', 'Calle Clásica 5', '900005'),
('Editorial Clásicos 6', 'España', 'Madrid', 'Calle Clásica 6', '900006'),
('Editorial Clásicos 7', 'España', 'Madrid', 'Calle Clásica 7', '900007'),
('Editorial Clásicos 8', 'España', 'Madrid', 'Calle Clásica 8', '900008'),
('Editorial Clásicos 9', 'España', 'Madrid', 'Calle Clásica 9', '900009'),
('Editorial Clásicos 10', 'España', 'Madrid', 'Calle Clásica 10', '900010'),
('Editorial Clásicos 11', 'España', 'Madrid', 'Calle Clásica 11', '900011'),
('Editorial Clásicos 12', 'España', 'Madrid', 'Calle Clásica 12', '900012'),
('Editorial Clásicos 13', 'España', 'Madrid', 'Calle Clásica 13', '900013'),
('Editorial Clásicos 14', 'España', 'Madrid', 'Calle Clásica 14', '900014'),
('Editorial Clásicos 15', 'España', 'Madrid', 'Calle Clásica 15', '900015');

-- AUTOR
INSERT INTO autor (nombre, apellido, fecha_nacimiento, nacionalidad) VALUES 
('Miguel', 'de Cervantes', '1800-01-02', 'Internacional'),
('Jane', 'Austen', '1800-01-03', 'Internacional'),
('Fyodor', 'Dostoevsky', '1800-01-04', 'Internacional'),
('Leo', 'Tolstoy', '1800-01-05', 'Internacional'),
('Charles', 'Dickens', '1800-01-06', 'Internacional'),
('Herman', 'Melville', '1800-01-07', 'Internacional'),
('Mark', 'Twain', '1800-01-08', 'Internacional'),
('Emily', 'Bronte', '1800-01-09', 'Internacional'),
('Bram', 'Stoker', '1800-01-10', 'Internacional'),
('Mary', 'Shelley', '1800-01-11', 'Internacional'),
('Victor', 'Hugo', '1800-01-12', 'Internacional'),
('Jules', 'Verne', '1800-01-13', 'Internacional'),
('H.G.', 'Wells', '1800-01-14', 'Internacional'),
('Arthur', 'Conan Doyle', '1800-01-15', 'Internacional'),
('Alexandre', 'Dumas', '1800-01-16', 'Internacional');

-- OBRA
INSERT INTO obra (titulo, isbn, tipo_obra_id, editorial_id, anio_publicacion, idioma) VALUES 
('Don Quijote de la Mancha', 'ISBNPD0001', 1, 1, 1850, 'Español'),
('Orgullo y Prejuicio', 'ISBNPD0002', 1, 2, 1850, 'Español'),
('Crimen y Castigo', 'ISBNPD0003', 1, 3, 1850, 'Español'),
('Guerra y Paz', 'ISBNPD0004', 1, 4, 1850, 'Español'),
('Oliver Twist', 'ISBNPD0005', 1, 5, 1850, 'Español'),
('Moby Dick', 'ISBNPD0006', 1, 6, 1850, 'Español'),
('Las Aventuras de Tom Sawyer', 'ISBNPD0007', 1, 7, 1850, 'Español'),
('Cumbres Borrascosas', 'ISBNPD0008', 1, 8, 1850, 'Español'),
('Drácula', 'ISBNPD0009', 1, 9, 1850, 'Español'),
('Frankenstein', 'ISBNPD0010', 1, 10, 1850, 'Español'),
('Los Miserables', 'ISBNPD0011', 1, 11, 1850, 'Español'),
('Viaje al Centro de la Tierra', 'ISBNPD0012', 1, 12, 1850, 'Español'),
('La Máquina del Tiempo', 'ISBNPD0013', 1, 13, 1850, 'Español'),
('Estudio en Escarlata', 'ISBNPD0014', 1, 14, 1850, 'Español'),
('El Conde de Montecristo', 'ISBNPD0015', 1, 15, 1850, 'Español');

-- OBRA_AUTOR
INSERT INTO obra_autor (obra_id, autor_id, tipo_contribucion) VALUES 
(1, 1, 'Autor'),
(2, 2, 'Autor'),
(3, 3, 'Autor'),
(4, 4, 'Autor'),
(5, 5, 'Autor'),
(6, 6, 'Autor'),
(7, 7, 'Autor'),
(8, 8, 'Autor'),
(9, 9, 'Autor'),
(10, 10, 'Autor'),
(11, 11, 'Autor'),
(12, 12, 'Autor'),
(13, 13, 'Autor'),
(14, 14, 'Autor'),
(15, 15, 'Autor');

-- EJEMPLAR
INSERT INTO ejemplar (obra_id, codigo_barras, numero_inventario, ubicacion) VALUES 
(1, 'CB-PD0001', 'INV-PD0001', 'Estante 1'),
(2, 'CB-PD0002', 'INV-PD0002', 'Estante 2'),
(3, 'CB-PD0003', 'INV-PD0003', 'Estante 3'),
(4, 'CB-PD0004', 'INV-PD0004', 'Estante 4'),
(5, 'CB-PD0005', 'INV-PD0005', 'Estante 5'),
(6, 'CB-PD0006', 'INV-PD0006', 'Estante 6'),
(7, 'CB-PD0007', 'INV-PD0007', 'Estante 7'),
(8, 'CB-PD0008', 'INV-PD0008', 'Estante 8'),
(9, 'CB-PD0009', 'INV-PD0009', 'Estante 9'),
(10, 'CB-PD0010', 'INV-PD0010', 'Estante 10'),
(11, 'CB-PD0011', 'INV-PD0011', 'Estante 11'),
(12, 'CB-PD0012', 'INV-PD0012', 'Estante 12'),
(13, 'CB-PD0013', 'INV-PD0013', 'Estante 13'),
(14, 'CB-PD0014', 'INV-PD0014', 'Estante 14'),
(15, 'CB-PD0015', 'INV-PD0015', 'Estante 15');

-- USUARIO
INSERT INTO usuario (dni, nombre, apellido, email, password_hash, tipo_usuario) VALUES 
('DNI0001', 'Usuario1', 'Real', 'usuario1@mail.com', 'hash', 'SOCIO'),
('DNI0002', 'Usuario2', 'Real', 'usuario2@mail.com', 'hash', 'SOCIO'),
('DNI0003', 'Usuario3', 'Real', 'usuario3@mail.com', 'hash', 'SOCIO'),
('DNI0004', 'Usuario4', 'Real', 'usuario4@mail.com', 'hash', 'SOCIO'),
('DNI0005', 'Usuario5', 'Real', 'usuario5@mail.com', 'hash', 'SOCIO'),
('DNI0006', 'Usuario6', 'Real', 'usuario6@mail.com', 'hash', 'SOCIO'),
('DNI0007', 'Usuario7', 'Real', 'usuario7@mail.com', 'hash', 'SOCIO'),
('DNI0008', 'Usuario8', 'Real', 'usuario8@mail.com', 'hash', 'SOCIO'),
('DNI0009', 'Usuario9', 'Real', 'usuario9@mail.com', 'hash', 'SOCIO'),
('DNI0010', 'Usuario10', 'Real', 'usuario10@mail.com', 'hash', 'SOCIO'),
('DNI0011', 'Usuario11', 'Real', 'usuario11@mail.com', 'hash', 'SOCIO'),
('DNI0012', 'Usuario12', 'Real', 'usuario12@mail.com', 'hash', 'SOCIO'),
('DNI0013', 'Usuario13', 'Real', 'usuario13@mail.com', 'hash', 'SOCIO'),
('DNI0014', 'Usuario14', 'Real', 'usuario14@mail.com', 'hash', 'SOCIO'),
('DNI0015', 'Usuario15', 'Real', 'usuario15@mail.com', 'hash', 'SOCIO');

-- PRESTAMO
INSERT INTO prestamo (usuario_id, ejemplar_id, fecha_prestamo, fecha_devolucion_prevista) VALUES 
(1, 1, '2025-01-05', '2025-01-20'),
(2, 2, '2025-01-05', '2025-01-20'),
(3, 3, '2025-01-05', '2025-01-20'),
(4, 4, '2025-01-05', '2025-01-20'),
(5, 5, '2025-01-05', '2025-01-20'),
(6, 6, '2025-01-05', '2025-01-20'),
(7, 7, '2025-01-05', '2025-01-20'),
(8, 8, '2025-01-05', '2025-01-20'),
(9, 9, '2025-01-05', '2025-01-20'),
(10, 10, '2025-01-05', '2025-01-20'),
(11, 11, '2025-01-05', '2025-01-20'),
(12, 12, '2025-01-05', '2025-01-20'),
(13, 13, '2025-01-05', '2025-01-20'),
(14, 14, '2025-01-05', '2025-01-20'),
(15, 15, '2025-01-05', '2025-01-20');

-- RESERVA
INSERT INTO reserva (usuario_id, obra_id, estado, posicion_cola) VALUES 
(1, 1, 'PENDIENTE', 1),
(2, 2, 'PENDIENTE', 1),
(3, 3, 'PENDIENTE', 1),
(4, 4, 'PENDIENTE', 1),
(5, 5, 'PENDIENTE', 1),
(6, 6, 'PENDIENTE', 1),
(7, 7, 'PENDIENTE', 1),
(8, 8, 'PENDIENTE', 1),
(9, 9, 'PENDIENTE', 1),
(10, 10, 'PENDIENTE', 1),
(11, 11, 'PENDIENTE', 1),
(12, 12, 'PENDIENTE', 1),
(13, 13, 'PENDIENTE', 1),
(14, 14, 'PENDIENTE', 1),
(15, 15, 'PENDIENTE', 1);

-- MULTA
INSERT INTO multa (usuario_id, tipo, monto, motivo, fecha_vencimiento) VALUES 
(1, 'RETRASO', 2.00, 'Retraso en devolución', '2025-03-01'),
(2, 'RETRASO', 4.00, 'Retraso en devolución', '2025-03-01'),
(3, 'RETRASO', 6.00, 'Retraso en devolución', '2025-03-01'),
(4, 'RETRASO', 8.00, 'Retraso en devolución', '2025-03-01'),
(5, 'RETRASO', 10.00, 'Retraso en devolución', '2025-03-01'),
(6, 'RETRASO', 12.00, 'Retraso en devolución', '2025-03-01'),
(7, 'RETRASO', 14.00, 'Retraso en devolución', '2025-03-01'),
(8, 'RETRASO', 16.00, 'Retraso en devolución', '2025-03-01'),
(9, 'RETRASO', 18.00, 'Retraso en devolución', '2025-03-01'),
(10, 'RETRASO', 20.00, 'Retraso en devolución', '2025-03-01'),
(11, 'RETRASO', 22.00, 'Retraso en devolución', '2025-03-01'),
(12, 'RETRASO', 24.00, 'Retraso en devolución', '2025-03-01'),
(13, 'RETRASO', 26.00, 'Retraso en devolución', '2025-03-01'),
(14, 'RETRASO', 28.00, 'Retraso en devolución', '2025-03-01'),
(15, 'RETRASO', 30.00, 'Retraso en devolución', '2025-03-01');

-- NOTIFICACION
INSERT INTO notificacion (usuario_id, tipo, titulo, mensaje) VALUES 
(1, 'RECORDATORIO', 'Aviso 1', 'Recuerde devolver su libro.'),
(2, 'RECORDATORIO', 'Aviso 2', 'Recuerde devolver su libro.'),
(3, 'RECORDATORIO', 'Aviso 3', 'Recuerde devolver su libro.'),
(4, 'RECORDATORIO', 'Aviso 4', 'Recuerde devolver su libro.'),
(5, 'RECORDATORIO', 'Aviso 5', 'Recuerde devolver su libro.'),
(6, 'RECORDATORIO', 'Aviso 6', 'Recuerde devolver su libro.'),
(7, 'RECORDATORIO', 'Aviso 7', 'Recuerde devolver su libro.'),
(8, 'RECORDATORIO', 'Aviso 8', 'Recuerde devolver su libro.'),
(9, 'RECORDATORIO', 'Aviso 9', 'Recuerde devolver su libro.'),
(10, 'RECORDATORIO', 'Aviso 10', 'Recuerde devolver su libro.'),
(11, 'RECORDATORIO', 'Aviso 11', 'Recuerde devolver su libro.'),
(12, 'RECORDATORIO', 'Aviso 12', 'Recuerde devolver su libro.'),
(13, 'RECORDATORIO', 'Aviso 13', 'Recuerde devolver su libro.'),
(14, 'RECORDATORIO', 'Aviso 14', 'Recuerde devolver su libro.'),
(15, 'RECORDATORIO', 'Aviso 15', 'Recuerde devolver su libro.');