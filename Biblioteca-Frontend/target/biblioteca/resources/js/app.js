/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Funciones generales de la aplicación
$(document).ready(function() {
    // Inicializar tooltips de Bootstrap
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Inicializar popovers
    var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
    
    // Configurar fechas por defecto para reportes
    setupDefaultDates();
    
    // Inicializar componentes personalizados
    inicializarComponentes();
});

// Configurar fechas por defecto (últimos 30 días)
function setupDefaultDates() {
    const today = new Date();
    const thirtyDaysAgo = new Date(today.getTime() - (30 * 24 * 60 * 60 * 1000));
    
    $('#fechaFin').val(today.toISOString().split('T')[0]);
    $('#fechaInicio').val(thirtyDaysAgo.toISOString().split('T')[0]);
}

// Funciones para mostrar/ocultar contraseñas
function togglePasswordVisibility(inputId) {
    const input = document.getElementById(inputId);
    const icon = event.currentTarget.querySelector('i');
    
    if (input.type === 'password') {
        input.type = 'text';
        icon.className = 'fas fa-eye-slash';
    } else {
        input.type = 'password';
        icon.className = 'fas fa-eye';
    }
}

// Funciones para AJAX
function realizarBusquedaAvanzada() {
    const termino = $('#searchTerm').val();
    
    mostrarCargando();
    
    $.ajax({
        url: '/biblioteca/api/buscar',
        method: 'POST',
        data: { 
            termino: termino,
            idioma: $('#idioma').val(),
            tema: $('#tema').val()
        },
        success: function(data) {
            actualizarResultados(data);
            ocultarCargando();
        },
        error: function(xhr, status, error) {
            console.error('Error en búsqueda:', error);
            mostrarError('Error al realizar la búsqueda');
            ocultarCargando();
        }
    });
}

function actualizarResultados(data) {
    const resultados = JSON.parse(data);
    const container = $('#resultados-container');
    
    let html = '';
    resultados.resultados.forEach(function(libro) {
        html += `
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                <div class="card book-card h-100 shadow-sm">
                    <div class="card-body d-flex flex-column">
                        <img src="/biblioteca/resources/IMG/cien_años.jpg" class="book-cover-small mb-3 rounded" alt="${libro.titulo}">
                        <h5 class="card-title">${libro.titulo}</h5>
                        <p class="card-subtitle mb-2 text-muted">${libro.autor}</p>
                        <div class="mt-auto">
                            <span class="badge ${libro.disponible ? 'bg-success' : 'bg-danger'}">
                                ${libro.disponible ? 'Disponible' : 'No Disponible'}
                            </span>
                            <button class="btn btn-primary btn-sm w-100 mt-2" onclick="verDetalle(${libro.id})">
                                Ver Detalles
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
    });
    
    container.html(html);
    $('#resultCount').text(`Mostrando ${resultados.total} resultados`);
}

function verDetalle(libroId) {
    window.location.href = `/biblioteca/detalle-obra.xhtml?bookId=${libroId}`;
}

function mostrarError(mensaje) {
    const alerta = `<div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>Error:</strong> ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>`;
    
    $('#notifications').html(alerta);
}

function mostrarExito(mensaje) {
    const alerta = `<div class="alert alert-success alert-dismissible fade show" role="alert">
        <strong>Éxito:</strong> ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>`;
    
    $('#notifications').html(alerta);
}

// Funciones para el catálogo
function cargarMasLibros() {
    mostrarCargando();
    
    $.ajax({
        url: '/biblioteca/api/mas-libros',
        method: 'GET',
        success: function(response) {
            agregarLibrosATabla(response.libros);
            ocultarCargando();
        },
        error: function() {
            mostrarError('Error al cargar más libros');
            ocultarCargando();
        }
    });
}

function agregarLibrosATabla(libros) {
    const tabla = $('#bookList');
    libros.forEach(function(libro) {
        // Implementar lógica de agregado
    });
}

// Funciones para validación
function validarFormulario(formId) {
    const form = document.getElementById(formId);
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return false;
    }
    return true;
}

function validarPassword() {
    const password = $('#password').val();
    const confirmPassword = $('#confirmPassword').val();
    
    if (password !== confirmPassword) {
        mostrarError('Las contraseñas no coinciden');
        return false;
    }
    
    if (password.length < 6) {
        mostrarError('La contraseña debe tener al menos 6 caracteres');
        return false;
    }
    
    return true;
}

// Funciones para UI/UX
function mostrarCargando() {
    $('#loading-spinner').show();
}

function ocultarCargando() {
    $('#loading-spinner').hide();
}

// Funciones para reportes
function generarReporte(tipo) {
    mostrarCargando();
    
    const formato = $('#formatoReporte').val();
    const fechaInicio = $('#fechaInicio').val();
    const fechaFin = $('#fechaFin').val();
    
    $.ajax({
        url: '/biblioteca/api/reports/generate',
        method: 'POST',
        data: {
            tipo: tipo,
            formato: formato,
            fechaInicio: fechaInicio,
            fechaFin: fechaFin
        },
        xhrFields: {
            responseType: 'blob'
        },
        success: function(blob) {
            // Descargar el archivo
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = url;
            a.download = `reporte_${tipo}_${new Date().toISOString().split('T')[0]}.${formato}`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(url);
            ocultarCargando();
            mostrarExito('Reporte generado exitosamente');
        },
        error: function() {
            ocultarCargando();
            mostrarError('Error al generar el reporte');
        }
    });
}

// Funciones para préstamos
function renovarPrestamo(prestamoId) {
    if (!confirm('¿Está seguro de que desea renovar este préstamo por 15 días adicionales?')) {
        return;
    }
    
    mostrarCargando();
    
    $.ajax({
        url: `/biblioteca/api/loans/${prestamoId}/renew`,
        method: 'POST',
        success: function() {
            ocultarCargando();
            mostrarExito('Préstamo renovado exitosamente');
            setTimeout(() => location.reload(), 2000);
        },
        error: function() {
            ocultarCargando();
            mostrarError('Error al renovar el préstamo');
        }
    });
}

// Funciones para pagos
function procesarPago() {
    if (!confirm('¿Está seguro de que desea procesar el pago?')) {
        return;
    }
    
    mostrarCargando();
    
    const metodoPago = $('#metodoPago').val();
    const datosPago = {
        metodo: metodoPago,
        monto: $('#totalPago').val(),
        // otros datos del pago...
    };
    
    $.ajax({
        url: '/biblioteca/api/payments/process',
        method: 'POST',
        data: JSON.stringify(datosPago),
        contentType: 'application/json',
        success: function(response) {
            ocultarCargando();
            mostrarExito('Pago procesado exitosamente');
            setTimeout(() => {
                window.location.href = '/biblioteca/panel-usuario.xhtml';
            }, 2000);
        },
        error: function() {
            ocultarCargando();
            mostrarError('Error al procesar el pago');
        }
    });
}

// Funciones para estadísticas
function cargarEstadisticas() {
    $.ajax({
        url: '/biblioteca/api/estadisticas',
        method: 'GET',
        success: function(data) {
            const stats = JSON.parse(data);
            actualizarEstadisticasUI(stats);
        },
        error: function() {
            console.error('Error al cargar estadísticas');
        }
    });
}

function actualizarEstadisticasUI(stats) {
    $('#totalBooks').text(stats.totalLibros);
    $('#activeLoans').text(stats.prestamosActivos);
    $('#totalUsers').text(stats.usuariosRegistrados);
    $('#availableBooks').text(stats.librosDisponibles);
}

// Utilidades de fecha
function formatearFecha(fecha) {
    return new Date(fecha).toLocaleDateString('es-ES');
}

function calcularDiasRestantes(fechaDevolucion) {
    const hoy = new Date();
    const devolucion = new Date(fechaDevolucion);
    const diffTime = devolucion - hoy;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays > 0 ? diffDays : 0;
}

// Notificaciones del sistema
function mostrarNotificacion(titulo, mensaje, tipo = 'info') {
    const notificacion = `
        <div class="toast align-items-center text-bg-${tipo} border-0" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <strong>${titulo}</strong><br>${mensaje}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `;
    
    $('#notifications').append(notificacion);
    $('.toast').toast('show');
}

// Inicialización de componentes
function inicializarComponentes() {
    // Inicializar todos los tooltips
    $('[data-bs-toggle="tooltip"]').tooltip();
    
    // Inicializar todos los popovers
    $('[data-bs-toggle="popover"]').popover();
    
    // Configurar validación de formularios
    $('form').on('submit', function(e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
        }
        $(this).addClass('was-validated');
    });
    
    // Configurar animaciones de entrada
    $('.fade-in').each(function(i) {
        $(this).delay(i * 100).queue(function() {
            $(this).addClass('show').dequeue();
        });
    });
    
    // Configurar eventos para campos de pago
    $('#paymentForm\\:metodoPago').on('change', function() {
        const metodo = $(this).val();
        $('#cardFields, #yapeFields, #transferFields, #cashFields').hide();
        
        switch(metodo) {
            case 'card':
                $('#cardFields').show();
                break;
            case 'yape':
                $('#yapeFields').show();
                break;
            case 'transfer':
                $('#transferFields').show();
                break;
            case 'cash':
                $('#cashFields').show();
                break;
        }
    });
    
    // Cargar estadísticas al iniciar
    cargarEstadisticas();
}

// Funciones para gestión de sesión
function verificarSesion() {
    // Verificar si el usuario está autenticado
    // Esta función podría ser útil para páginas que requieren autenticación
}

// Funciones para mejoras de UX
function agregarEfectosHover() {
    $('.book-card').hover(
        function() {
            $(this).addClass('shadow-lg');
        },
        function() {
            $(this).removeClass('shadow-lg');
        }
    );
}

// Manejo de errores global
window.onerror = function(msg, url, lineNo, columnNo, error) {
    console.error('Error:', msg, 'URL:', url, 'Line:', lineNo, 'Column:', columnNo, 'Error object:', error);
    return false;
};

// Funciones para responsive design
function ajustarVistaResponsive() {
    if ($(window).width() < 768) {
        $('.container').addClass('container-fluid').removeClass('container');
    } else {
        $('.container-fluid').addClass('container').removeClass('container-fluid');
    }
}

$(window).resize(function() {
    ajustarVistaResponsive();
});

// Inicialización cuando el documento esté listo
$(document).ready(function() {
    inicializarComponentes();
    ajustarVistaResponsive();
    agregarEfectosHover();
    
    // Configurar eventos de teclado
    $(document).keydown(function(e) {
        // ESC para cerrar modales
        if (e.keyCode === 27) {
            $('.modal').modal('hide');
        }
    });
});

// Funciones para carga perezosa de imágenes
function lazyLoadImages() {
    const images = document.querySelectorAll('img[data-src]');
    
    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.removeAttribute('data-src');
                imageObserver.unobserve(img);
            }
        });
    });
    
    images.forEach(img => imageObserver.observe(img));
}

// Internacionalización simple
const mensajes = {
    es: {
        buscar: "Buscar",
        limpiar: "Limpiar",
        cargando: "Cargando...",
        error: "Error",
        exito: "Éxito",
        confirmar: "Confirmar",
        cancelar: "Cancelar"
    },
    en: {
        buscar: "Search",
        limpiar: "Clear",
        cargando: "Loading...",
        error: "Error",
        exito: "Success",
        confirmar: "Confirm",
        cancelar: "Cancel"
    }
};

function cambiarIdioma(idioma) {
    $('[data-i18n]').each(function() {
        const key = $(this).data('i18n');
        $(this).text(mensajes[idioma][key]);
    });
}