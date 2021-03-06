План тестирования
---

# Cодержание
1 [Введение](#introduction)  
2 [Объект тестирования](#items)  
3 [Атрибуты качества](#quality)  
4 [Риски](#risk)  
5 [Аспекты тестирования](#features)  
6 [Подходы к тестированию](#approach)  
7 [Представление результатов](#pass)  
8 [Выводы](#conclusion)  

<a name="introduction"/>

### 1. Введение
Данный план тестирования предназначен для приложения "Eventime". Целью выполнения данного плана 
тестирования является проверка работоспособности приложения, а также выявление фактов несоответствия 
предъявляемым к проекту требованиям.
<a name="items"/>
### 2. Объект тестирования
В качестве объектов тестирования можно выделить атрибуты качества платформы по ISO 25010:
1. функциональность
	- функциональная полнота: приложение должно выполнять все заявленные функции
	- функциональная корректность: приложение должно выполнять все заявленные функции корректно
	- функциональная целесообразность: отсутствуют не заявленные функции, которые бы мешали приложению выполнять первоначально поставленные задачи
2. удобство использования
	- эстетика пользовательского интерфейса: элементы управления объектами должны быть всегда доступны пользователю
	- защищённость от ошибки пользователя: запрос подтверждения пользователя при внесении им существенных изменений в объекты.

Данные атрибуты были взяты с учётом специфики приложения.

Тестируемое приложение содержит 8 страниц, обеспечивающих реализацию функциональных требований:
 - Стартовая страница
 - Страница входа
 - Страница регистрации
 - Страница профиля
 - Страница редактирования пользователей
 - Страница просмотра мероприятий
 - Страница добавления мероприятия
 - Страница изменения мероприятия

<a name="quality"/>

### 3. Атрибуты качества
- Функциональность: приложение должно соответствовать всем функциональным требованиям,
которые заявлены по ссылке: [SRS](https://github.com/ChiZ-z/Eventime/blob/master/Documents/FlowOfEvents.md);
- Анонимность: приложение не должно обязывать пользователя использовать персональные данные при
регистрации, а также любую иную информацию, позволяющую идентифицировать пользователя
- Простота пользовательского интерфейса: интерфейс должен быть достаточно простым для интуитивного
использования новым пользователем.
- Кроссбраузерность: приложение должно корректно работать независимо от используемого браузера

<a name="risk"/>

### 4. Риски
В случае потери данных, используемых при регистрации, пользователь окончательно теряет доступ к
сохраненным в аккаунте данным в связи с отсутствием привязки электронной почты к аккаунту пользователя.
В случае отсутствия интернет соединения пользователь не имеет возможности работать с приложением

<a name="features"/>

### 5. Аспекты тестирования
Данное тестирование является интеграционным, т. е. программные модули тестируются в группке, также является дымовым, 
и будет проводиться по типу "Black box", т. е. без доступа к исходному коду.
В ходе тестирования планируется проверить реализацию основных функций приложения, провести позитивные и 
негативные тесты, а также проверить нефункциональные требования. К основным функциям можно отнести следующие пункты:

- регистрация;
- вход в аккаунт;
- просмотр мероприятий:
- добавление мероприятий;
- изменение мероприятия;
- поиск мероприятий;
- выход из аккаунта.

#### Функциональные требования:

##### Возможность зарегистрироваться
Данный вариант использования небходимо протестировать на:
1. Реакцию приложения на попытку зарегистрироваться.
2. Выполнение данной операции (автоматический переход на страницу входа в аккаунт).

##### Возможность войти в аккаунт
Данный вариант использования небходимо протестировать на:
1. Реакцию приложения на попытку войти в аккаунт.
2. Выполнение данной операции (переход на страницу просмотра инвестиций).

##### Возможность просмотреть мероприятий
Данный вариант использования небходимо протестировать на:
1. Реакцию приложения на просмотр мероприятий.
2. Выполнение данной операции (отображение мероприятий, ранее добавленных пользователем
и подтверждённое администратором).

##### Возможность добавить мероприятие
Данный вариант использования небходимо протестировать на:
1. Переход на страницу добавления мероприятий.
2. Осуществление ввода данных во всех полях формы.
3. Добавление созданной мероприятий в список инвестиций.
4. Подтверждение мероприятия администратором.
5. Переход на страницу просмотра мероприятий.

##### Возможность отредактировать мероприятий
Данный вариант использования небходимо протестировать на:
1. Реакцию приложения на попытку изменить мероприятия.
2. Осуществление ввода данных во всех полях формы.
3. Выполнение данной операции (изменение мероприятия).
4. Отмену операции путем нажатия в браузере на кнопку перехода на предыдущую страницу.

##### Возможность осуществить поиск мероприятий
Данный вариант использования небходимо протестировать на:
1. Реакцию приложения на ввод тэга искомой мероприятий в поле ввода для поиска мероприятий.
2. Реакцию приложения нажаие кнопки "I GO".
3. Реакцию приложения выбор даты искомого мероприятия.
4. Выполнение данной операции (отображение мероприятий, соответствующих введенным в поле поиска).

##### Возможность выйти из аккаунта
Данный вариант использования небходимо протестировать на:
1. Реакцию приложения на нажатие кнопки Log out
2. Выполнение данной операции (переход на стартовую страницу, отсутствие возможности получить
доступ к данным аккаунта, минуя операцию входа в аккаунт).

#### Нефункциональные требования:
* Использование `UIkit` - все элементы интерфейса должны быть выполнены в стиле `UIkit`;
* Плавный интерфейс - отсутствие фризов во время использования приложения;
* Разделы должны быть подписаны.


<a name="approach"/>

### 6. Подходы к тестированию
Данный алгоритм тестирования представляет собой ручное интеграционное тестирование методом черного ящика

<a name="pass"/>

### 7. Представление результатов
[TestResult](https://github.com/ChiZ-z/Eventime/blob/master/Documents/Testing/TestResult.md)
<a name="conclusion"/>

### 8. Выводы
С помощью данного тестового плана тестировщик может протестировать функционал приложения.
Факт успешного прохождения всех тестов позволяет сделать вывод о работоспособности приложения
и выполнения им всех предъявляемых требований.
