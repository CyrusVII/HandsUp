document.addEventListener('DOMContentLoaded', function() {

    // Funzione per verificare l'email
    function checkEmail(email, callback) {
        fetch(`/check_email?email=${email}`)
            .then(response => response.json())
            .then(data => callback(data))
            .catch(error => console.error('Error checking email:', error));
    }

    // Funzione per verificare lo username
    function checkUsername(username, callback) {
        fetch(`/check_username?username=${username}`)
            .then(response => response.json())
            .then(data => callback(data))
            .catch(error => console.error('Error checking username:', error));
    }

    // Funzione per verificare l'ID dell'azienda
    function checkCompanyId(companyId, callback) {
        fetch(`/check_companyId?idAzienda=${companyId}`)
            .then(response => response.json())
            .then(data => callback(data))
            .catch(error => console.error('Error checking company ID:', error));
    }

    // Funzione per verificare il codice fiscale dell'azienda
    function checkCompanyCode(companyCode, callback) {
        fetch(`/check_companyCode?codiceFiscale=${companyCode}`)
            .then(response => response.json())
            .then(data => callback(data))
            .catch(error => console.error('Error checking company code:', error));
    }

    // Funzione per gestire la sottomissione del form
    function handleFormSubmit(event, formType) {
        event.preventDefault();

        var errors = [];
        let email, confEmail, username, pwd, confpwd, birthdate, companyId, companyCode, companyFoundation;

        if (formType === 'user') {
            email = document.getElementById('email')?.value;
            confEmail = document.getElementById('confmail')?.value;
            username = document.getElementById('use')?.value;
            pwd = document.getElementById('pwd')?.value;
            confpwd = document.getElementById('pwdConfirm')?.value;
            birthdate = document.getElementById('born')?.value;

            var today = new Date();
            var birthDate = new Date(birthdate);
            var age = today.getFullYear() - birthDate.getFullYear();
            var monthDiff = today.getMonth() - birthDate.getMonth();
            if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            if (age < 16) {
                errors.push('You must be at least 16 years old to register.');
            }
        } else if (formType === 'company') {
            email = document.getElementById('emailCompany')?.value;
            confEmail = document.getElementById('emailCompanyConf')?.value;
            username = document.getElementById('companyUserName')?.value;
            pwd = document.getElementById('pwdCompany')?.value;
            confpwd = document.getElementById('pwdConfirmCompany')?.value;
            companyId = document.getElementById('companyId')?.value;
			companyArea=document.getElementById('companyZona').value;
            companyCode = document.getElementById('companyId')?.value;
            companyFoundation = document.getElementById('companyFoundation')?.value;

            if (!companyId) {
                errors.push('Company ID is required.');
            }

            if (companyFoundation) {
                var today = new Date().toISOString().split('T')[0];
                if (companyFoundation >= today) {
                    errors.push('The foundation date must be before today.');
                }
            }
        }

        if (!email || !confEmail || !username || !pwd || !confpwd || (formType === 'user' && !birthdate)) {
            errors.push('All fields are required.');
        }

        if (email && confEmail && email !== confEmail) {
            errors.push('The emails do not match.');
        }

        if (pwd && pwd.length < 6) {
            errors.push('The password must contain at least 6 characters.');
        }
        if (pwd && !/[A-Z]/.test(pwd)) {
            errors.push('The password must contain at least one capital letter.');
        }
        if (pwd && !/\d/.test(pwd)) {
            errors.push('The password must contain at least one number.');
        }
        if (pwd && !/[?!]/.test(pwd)) {
            errors.push('The password must contain at least one special character between ? and !.');
        }
        if (pwd && pwd !== confpwd) {
            errors.push('The passwords do not match.');
        }

        var errorDiv = document.getElementById('Error');
        if (errors.length > 0) {
            errorDiv.innerHTML = '<p>' + errors.join('<br>') + '</p>';
        } else {
            if (formType === 'company') {
                checkCompanyCode(companyCode, function(companyCodeExists) {
                    if (!companyCodeExists) {
                        errors.push('The company ID does not exist.');
                    }

                    checkEmail(email, function(emailExists) {
                        if (emailExists) {
                            errors.push('The email is already registered.');
                        }

                        checkUsername(username, function(usernameExists) {
                            if (usernameExists) {
                                errors.push('The username is already registered.');
                            }

                            checkCompanyId(companyId, function(companyIdExists) {
                                if (companyIdExists) {
                                    errors.push('The company ID is already registered.');
                                }

                                if (errors.length > 0) {
                                    errorDiv.innerHTML = '<p>' + errors.join('<br>') + '</p>';
                                } else {
                                    errorDiv.textContent = '';
                                    event.target.submit();
                                }
                            });
                        });
                    });
                });
            } else {
                checkEmail(email, function(emailExists) {
                    if (emailExists) {
                        errors.push('The email is already registered.');
                    }

                    checkUsername(username, function(usernameExists) {
                        if (usernameExists) {
                            errors.push('The username is already registered.');
                        }

                        if (errors.length > 0) {
                            errorDiv.innerHTML = '<p>' + errors.join('<br>') + '</p>';
                        } else {
                            errorDiv.textContent = '';
                            event.target.submit();
                        }
                    });
                });
            }
        }
    }

    // Event listener per la sottomissione del form utente
    document.getElementById('userForm').addEventListener('submit', function(event) {
        handleFormSubmit(event, 'user');
    });

    // Event listener per la sottomissione del form azienda
    document.getElementById('companyForm').addEventListener('submit', function(event) {
        handleFormSubmit(event, 'company');
    });

    // Event listener per il cambiamento del tipo di utente (User o Company)
    document.getElementById('userType').addEventListener('change', function() {
        var userForm = document.getElementById('userForm');
        var companyForm = document.getElementById('companyForm');

        if (this.value === 'user') {
            userForm.classList.remove('hidden');
            companyForm.classList.add('hidden');
        } else if (this.value === 'company') {
            companyForm.classList.remove('hidden');
            userForm.classList.add('hidden');
        }
    });

});







