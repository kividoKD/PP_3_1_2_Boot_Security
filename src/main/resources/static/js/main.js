// Функция для загрузки данных пользователей
function loadUsers() {
    fetch('/api/admin/users')
        .then(response => response.json())
        .then(users => {
            const tableBody = document.querySelector('#userTable tbody');
            tableBody.innerHTML = ''; // Очищаем таблицу перед добавлением новых данных
            users.forEach(user => {
                const row = document.createElement('tr');
                const roles = user.roles.map(role => role.name.replace('ROLE_', '')).join(', ');
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                    <td>
                        <button class="btn btn-info" onclick="openUserModal(${user.id}, 'edit')">Изменить</button>
                        <button class="btn btn-danger" onclick="openUserModal(${user.id}, 'delete')">Удалить</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error loading users:', error));
}


// Функция для загрузки ролей
function loadRoles() {
    fetch('/api/admin/roles')
        .then(response => response.json())
        .then(roles => {
            const rolesSelect = document.querySelector('#roles');
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;  // Сохраняем id роли
                option.textContent = role.name.replace('ROLE_', '');
                rolesSelect.appendChild(option);
            });
            window.roles = roles;
        })
        .catch(error => console.error('Error loading roles:', error));
}


// Функция для открытия модального окна (редактирование или удаление)
function openUserModal(id, mode) {
    fetch(`/api/admin/users/${id}`)
        .then(response => response.json())
        .then(user => {
            // Заполняем форму данными пользователя
            document.querySelector('#id').value = user.id;
            document.querySelector('#username').value = user.username;
            document.querySelector('#email').value = user.email;
            document.querySelector('#password').value = ''; // Очищаем поле пароля для редактирования
            document.querySelector('#roles').innerHTML = ''; // Очищаем список ролей

            const rolesSelect = document.querySelector('#roles');
            roles.forEach(role => {
                const option = document.createElement('option');
                option.value = role.id;  // Сохраняем id роли
                option.textContent = role.name.replace('ROLE_', '');
                if (user.roles.some(r => r.id === role.id)) {
                    option.selected = true;
                }
                rolesSelect.appendChild(option);
            });

            // Включаем или выключаем поля в зависимости от режима
            const userModalLabel = document.querySelector('#userModalLabel');
            const updateButton = document.querySelector('#updateButton');
            const deleteButton = document.querySelector('#deleteButton');

            if (mode === 'edit') {
                userModalLabel.textContent = 'Edit User';
                updateButton.style.display = 'inline-block';
                deleteButton.style.display = 'none';
                enableForm(true); // Включаем поля для редактирования
            } else if (mode === 'delete') {
                userModalLabel.textContent = 'Delete User';
                updateButton.style.display = 'none';
                deleteButton.style.display = 'inline-block';
                enableForm(false); // Делаем поля только для чтения
            }

            // Открываем модальное окно
            $('#userModal').modal('show');
        })
        .catch(error => console.error('Error loading user data:', error));
}


// Функция для включения/выключения полей формы
function enableForm(isEditable) {
    document.querySelector('#username').disabled = !isEditable;
    document.querySelector('#email').disabled = !isEditable;
    document.querySelector('#password').disabled = !isEditable;
    document.querySelector('#roles').disabled = !isEditable;
}


// Функция для сохранения изменений пользователя
function updateUser() {
    const id = document.querySelector('#id').value;
    const username = document.querySelector('#username').value;
    const email = document.querySelector('#email').value;
    const password = document.querySelector('#password').value;
    const roles = Array.from(document.querySelector('#roles').selectedOptions)
        .map(option => ({ id: option.value }));  // Преобразуем в массив объектов

    const user = {
        id: id,
        username: username,
        email: email,
        password: password,
        roles: roles
    };

    fetch(`/api/admin/users/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => response.json())
        .then(() => {
            loadUsers(); // Обновляем таблицу
            $('#userModal').modal('hide'); // Закрываем модальное окно
        })
        .catch(error => console.error('Error updating user:', error));
}


// Функция добавления нового пользователя
function addUser() {
    const username = document.querySelector('#username').value;
    const email = document.querySelector('#email').value;
    const password = document.querySelector('#password').value;
    const roles = Array.from(document.querySelector('#roles').selectedOptions)
        .map(option => ({ id: option.value }));  // Преобразуем в массив объектов

    const newUser = {
        username: username,
        email: email,
        password: password,
        roles: roles
    };

    // Отправка запроса на создание пользователя
    fetch('/api/admin/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    })
        .then(response => response.json())
        .then(() => {
            loadUsers(); // Обновляем список пользователей
            alert("User added successfully!");

            document.querySelector('#userForm').reset();
        })
        .catch(error => console.error('Error adding user:', error));
}


// Функция для подтверждения удаления пользователя
function confirmDeleteUser() {
    const id = document.querySelector('#id').value;

    fetch(`/api/admin/users/${id}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(() => {
            loadUsers(); // Обновляем таблицу пользователей
            $('#userModal').modal('hide'); // Закрываем модальное окно
        })
        .catch(error => console.error('Error deleting user:', error));
}


// Инициализация страницы
document.addEventListener('DOMContentLoaded', function () {
    loadUsers(); // Загружаем данные при загрузке страницы
    loadRoles();
});