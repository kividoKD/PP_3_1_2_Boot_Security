// $(document).ready(function(){
//     $('.table .eBtn').on('click',function(event){
//         // event.preventDefault();
//         let href = $(this).attr('href');
//
//         $.get(href, function(user, status) {
//             $('.myForm #id').val(user.id);
//             $('.myForm #name').val(user.username);
//             $('.myForm #email').val(user.email);
//             $('.myForm #roles').val(user.roles);
//             $('.myForm #password').val(user.password);
//         });
//
//         $('.myForm #exampleModal').modal('show');
//     });
// });

//
// document.addEventListener('DOMContentLoaded', function () {
//     // Находим все кнопки "Изменить"
//     const editButtons = document.querySelectorAll('.eBtn');
//
//     // Добавляем обработчик для каждой кнопки
//     editButtons.forEach(function (button) {
//         button.addEventListener('click', function (event) {
//             const userId = event.target.getAttribute('data-id');  // ID пользователя
//
//             // Делаем AJAX запрос для получения данных пользователя
//             fetch(`/admin/findOne/${userId}`)
//                 .then(response => response.json())
//                 .then(user => {
//                     // Заполняем форму данными пользователя
//                     document.querySelector('#id').value = user.id;
//                     document.querySelector('#name').value = user.username;
//                     document.querySelector('#email').value = user.email;
//
//                     // Заполняем селект с ролями
//                     const rolesSelect = document.querySelector('#roles');
//                     rolesSelect.innerHTML = '';  // Очищаем текущие опции
//                     user.roles.forEach(role => {
//                         const option = document.createElement('option');
//                         option.value = role.id;
//                         option.textContent = role.name;
//                         rolesSelect.appendChild(option);
//                     });
//
//                     // Открываем модальное окно
//                     const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
//                     modal.show();
//                 })
//                 .catch(error => {
//                     console.error('Error fetching user data:', error);
//                     alert('Ошибка при загрузке данных пользователя');
//                 });
//         });
//     });
//
//     // Обработчик для отправки формы
//     const form = document.querySelector('form');
//     form.addEventListener('submit', function (event) {
//         event.preventDefault();  // Предотвращаем стандартное поведение формы
//
//         // Собираем данные формы
//         const formData = new FormData(form);
//
//         // Отправляем данные с помощью fetch
//         fetch(form.action, {
//             method: 'POST',
//             body: formData
//         })
//             .then(response => response.json())
//             .then(data => {
//                 if (data.success) {
//                     // Закрываем модальное окно
//                     const modal = bootstrap.Modal.getInstance(document.getElementById('exampleModal'));
//                     modal.hide();
//
//                     // Обновляем таблицу или выполняем другие действия
//                     alert('Данные успешно сохранены!');
//                     location.reload();  // Перезагружаем страницу для обновления таблицы
//                 } else {
//                     alert('Ошибка при сохранении данных!');
//                 }
//             })
//             .catch(error => {
//                 console.error('Error saving user data:', error);
//                 alert('Ошибка при сохранении данных');
//             });
//     });
// });

