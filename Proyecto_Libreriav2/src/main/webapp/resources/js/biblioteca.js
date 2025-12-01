/**
 * Funciones JavaScript para el Sistema de Biblioteca
 * Utiliza jQuery y AJAX para mejorar la experiencia de usuario
 */

// Namespace para evitar conflictos
var Biblioteca = Biblioteca || {};

/**
 * Módulo de utilidades
 */
Biblioteca.Utils = {

    /**
     * Muestra un mensaje de confirmación
     */
    confirmar: function(mensaje) {
        return confirm(mensaje || '¿Está seguro de realizar esta acción?');
    },

    /**
     * Formatea una fecha a dd/MM/yyyy
     */
    formatearFecha: function(fecha) {
        if (!fecha) return '';
        var d = new Date(fecha);
        var dia = ('0' + d.getDate()).slice(-2);
        var mes = ('0' + (d.getMonth() + 1)).slice(-2);
        var anio = d.getFullYear();
        return dia + '/' + mes + '/' + anio;
    },

    /**
     * Valida un email
     */
    validarEmail: function(email) {
        var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    },

    /**
     * Valida un DNI español
     */
    validarDNI: function(dni) {
        var regex = /^\d{8}[A-Z]$/;
        return regex.test(dni);
    },

    /**
     * Muestra un mensaje toast (requiere Bootstrap)
     */
    mostrarToast: function(titulo, mensaje, tipo) {
        tipo = tipo || 'info'; // info, success, warning, danger
        var html = '<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="3000">' +
                   '  <div class="toast-header bg-' + tipo + ' text-white">' +
                   '    <strong class="mr-auto">' + titulo + '</strong>' +
                   '    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast">&times;</button>' +
                   '  </div>' +
                   '  <div class="toast-body">' + mensaje + '</div>' +
                   '</div>';

        $('#toast-container').append(html);
        $('.toast').last().toast('show');
    }
};

/**
 * Módulo de AJAX para llamadas REST
 */
Biblioteca.Ajax = {

    baseUrl: '/Proyecto_Libreria-1.0-SNAPSHOT/api',

    /**
     * GET genérico
     */
    get: function(endpoint, successCallback, errorCallback) {
        $.ajax({
            url: this.baseUrl + endpoint,
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                if (successCallback) successCallback(data);
            },
            error: function(xhr, status, error) {
                console.error('Error en GET:', error);
                if (errorCallback) errorCallback(xhr, status, error);
            }
        });
    },

    /**
     * POST genérico
     */
    post: function(endpoint, data, successCallback, errorCallback) {
        $.ajax({
            url: this.baseUrl + endpoint,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function(response) {
                if (successCallback) successCallback(response);
            },
            error: function(xhr, status, error) {
                console.error('Error en POST:', error);
                if (errorCallback) errorCallback(xhr, status, error);
            }
        });
    },

    /**
     * PUT genérico
     */
    put: function(endpoint, id, data, successCallback, errorCallback) {
        $.ajax({
            url: this.baseUrl + endpoint + '/' + id,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'json',
            success: function(response) {
                if (successCallback) successCallback(response);
            },
            error: function(xhr, status, error) {
                console.error('Error en PUT:', error);
                if (errorCallback) errorCallback(xhr, status, error);
            }
        });
    },

    /**
     * DELETE genérico
     */
    delete: function(endpoint, id, successCallback, errorCallback) {
        $.ajax({
            url: this.baseUrl + endpoint + '/' + id,
            method: 'DELETE',
            success: function() {
                if (successCallback) successCallback();
            },
            error: function(xhr, status, error) {
                console.error('Error en DELETE:', error);
                if (errorCallback) errorCallback(xhr, status, error);
            }
        });
    }
};

/**
 * Módulo de filtrado de tablas
 */
Biblioteca.Tabla = {

    /**
     * Filtra una tabla por texto
     */
    filtrar: function(inputId, tableId) {
        var input = document.getElementById(inputId);
        var filter = input.value.toUpperCase();
        var table = document.getElementById(tableId);
        var tr = table.getElementsByTagName('tr');

        for (var i = 1; i < tr.length; i++) {
            var td = tr[i].getElementsByTagName('td');
            var mostrar = false;

            for (var j = 0; j < td.length; j++) {
                if (td[j]) {
                    var txtValue = td[j].textContent || td[j].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        mostrar = true;
                        break;
                    }
                }
            }

            tr[i].style.display = mostrar ? '' : 'none';
        }
    },

    /**
     * Exporta tabla a CSV
     */
    exportarCSV: function(tableId, filename) {
        var csv = [];
        var rows = document.querySelectorAll('#' + tableId + ' tr');

        for (var i = 0; i < rows.length; i++) {
            var row = [], cols = rows[i].querySelectorAll('td, th');

            for (var j = 0; j < cols.length; j++) {
                row.push('"' + cols[j].innerText + '"');
            }

            csv.push(row.join(','));
        }

        var csvFile = new Blob([csv.join('\n')], { type: 'text/csv' });
        var downloadLink = document.createElement('a');
        downloadLink.download = filename || 'export.csv';
        downloadLink.href = window.URL.createObjectURL(csvFile);
        downloadLink.style.display = 'none';
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
    }
};

/**
 * Módulo específico de Usuarios
 */
Biblioteca.Usuarios = {

    /**
     * Carga usuarios vía AJAX
     */
    cargar: function(callback) {
        Biblioteca.Ajax.get('/usuarios', callback);
    },

    /**
     * Busca usuario por ID
     */
    buscarPorId: function(id, callback) {
        Biblioteca.Ajax.get('/usuarios/' + id, callback);
    },

    /**
     * Valida formulario de usuario
     */
    validarFormulario: function(formData) {
        var errores = [];

        if (!formData.nombre || formData.nombre.trim() === '') {
            errores.push('El nombre es obligatorio');
        }

        if (!formData.email || !Biblioteca.Utils.validarEmail(formData.email)) {
            errores.push('Email inválido');
        }

        if (formData.dni && !Biblioteca.Utils.validarDNI(formData.dni)) {
            errores.push('DNI inválido');
        }

        return errores;
    }
};

/**
 * Módulo específico de Préstamos
 */
Biblioteca.Prestamos = {

    /**
     * Calcula días de retraso
     */
    calcularDiasRetraso: function(fechaDevolucionPrevista) {
        var hoy = new Date();
        var fechaPrevista = new Date(fechaDevolucionPrevista);
        var diff = hoy - fechaPrevista;
        var dias = Math.floor(diff / (1000 * 60 * 60 * 24));
        return dias > 0 ? dias : 0;
    },

    /**
     * Calcula multa por retraso
     */
    calcularMulta: function(diasRetraso, tarifaDiaria) {
        tarifaDiaria = tarifaDiaria || 0.50; // 0.50€ por defecto
        return diasRetraso * tarifaDiaria;
    },

    /**
     * Formatea multa como moneda
     */
    formatearMulta: function(monto) {
        return monto.toFixed(2) + ' €';
    }
};

/**
 * Inicialización al cargar el DOM
 */
$(document).ready(function() {

    // Crear contenedor para toasts si no existe
    if ($('#toast-container').length === 0) {
        $('body').append('<div id="toast-container" class="position-fixed top-0 right-0 p-3" style="z-index: 9999;"></div>');
    }

    // Inicializar tooltips de Bootstrap
    $('[data-toggle="tooltip"]').tooltip();

    // Confirmación antes de eliminar
    $('.btn-delete').on('click', function(e) {
        if (!Biblioteca.Utils.confirmar('¿Está seguro de eliminar este registro?')) {
            e.preventDefault();
            return false;
        }
    });

    // Auto-hide alerts después de 5 segundos
    setTimeout(function() {
        $('.alert').fadeOut('slow');
    }, 5000);

    console.log('Sistema de Biblioteca - JavaScript cargado correctamente');
});

// Exportar para uso global
window.Biblioteca = Biblioteca;

