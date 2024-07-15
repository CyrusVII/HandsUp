document.addEventListener('DOMContentLoaded', function() {
    var form = document.getElementById('loginForm');
    var passwordErrorDiv = document.getElementById('passwordError');

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Impedisce l'invio del form

        var password = document.getElementById('password').value;
        var errorMessage = '';

        // Validazione della password
        if (password.length < 6) {
            errorMessage += 'The password must contain at least 6 characters.<br>';
        }
        if (!/[A-Z]/.test(password)) {
            errorMessage += 'The password must contain at least one capital letter.<br>';
        }
        if (!/\d/.test(password)) {
            errorMessage += 'The password must contain at least one number.<br>';
        }
        if (!/[?!]/.test(password)) {
            errorMessage += 'The password must contain at least one special character between ? And !.<br>';
        }

        if (errorMessage) {
            if (passwordErrorDiv) {
                passwordErrorDiv.innerHTML = errorMessage;
            } else {
                console.error('Element with id "passwordError" not found.');
            }
        } else {
            // Rimuovi eventuali messaggi di errore precedenti
            if (passwordErrorDiv) {
                passwordErrorDiv.innerHTML = '';
            }

            // Invia il form al server
            form.submit();
        }
    });
});
