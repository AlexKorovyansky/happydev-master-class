## ANDROID РАЗРАБОТКА: FROM ZERO TO HERO

![Hero](https://dl.dropboxusercontent.com/u/4423440/happydev/hero.jpeg "Hero")

Мастер-класс будет интересен тем, кто давно хотел научиться программировать под Android, но еще не написал ни одного Android-приложения.

В ходе мастер-класса участники получат навыки использования основных инструментов Android-разработчика, навыки эффективной разработки и следования хорошим принципам программирования и навыки разработки собственного законченного Android приложения.

Для участие в мастер-классе крайне желательно знание языка [Java](http://en.wikipedia.org/wiki/Java_(programming_language)).

Участие в мастер-классе — командное. Рекомендуемый размер команды — 2 человека, но можно и в одиночку. 1 ноутбук на команду. Все команды будут разрабатывать одно и то же приложение — Погодный прогнозатор для Омска.

Перед мастер-классом каждая команда должна тщательно подготовить свой ноутбук! 

Ниже приведена подробная инструкция, шагов много, но они все простые, читайте вдумчиво, если будут вопросы — пишите на korovyansk@gmail.com.

## Подготовка к мастер-классу:
Вам понадобится ноутбук c операционной системой `Windows 7` или `Windows 8` или `Ubuntu 14.04` или `OS X 10.9.*`, удовлетворяющий [System Requirements](https://dl.dropboxusercontent.com/u/4423440/happydev/requirements.png).

##### На ноутбук необходимо установить:

1. [Oracle/Sun JDK 6 или JDK 7](http://en.wikipedia.org/wiki/Java_Development_Kit)  
*// [Ссылка для загрузки установщика](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)*  
*// [Инструкция по установке JDK 7](http://docs.oracle.com/javase/7/docs/webnotes/install/)*  
*// [Инструкция по установке JDK 7 на Ubuntu](https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get)*

2. [Android Studio версия 0.8.6 или выше](http://en.wikipedia.org/wiki/Android_Studio)  
*// [Ссылка для загрузки установщика](https://developer.android.com/sdk/installing/studio.html)*  
*// [Инструкция по установке](https://developer.android.com/sdk/installing/index.html?pkg=studio)*

3. Перечисленные ниже компоненты Android SDK (для установки компонентов нужно использовать [SDK Manager](http://developer.android.com/tools/help/sdk-manager.html), который доступен через меню Android Studio: *Tools -> Android -> SDK Manager*):
  * Tools: Android SDK Tools версия 23.0.2
  * Tools: Android SDK Platform Tools версия 20
  * Tools: Android SDK Build-Tools версия 20
  * API 19: SDK Platorm ревизия 3
  * API 19: Sources for Android SDK ревизия 2
  * Extra: Android Support Repository ревизия 6
  * Extra: Androod Support Library ревизия 20
  * Extra: Google Play Services ревизия 19
  * Extra: Google Repository ревизия 11

4. [Oracle VM VirtualBox версия 4.2.12 или выше](https://www.virtualbox.org/)  
*// Необходимо ставить отдельно только для Linux и Mac*

5. [Genymotion версия 2.2.2](https://cloud.genymotion.com/page/customer/login/)  
*// Для Linux и Mac после установки Genymotion необходимо указать путь к VirtualBox*

6. Genymotion Virtual Device "Google Nexus 4 - 4.4.2 - API 19 - 768x1280"  
*// Для загрузки устройства используйте аккаунт happydev / happydev*

7. [SmartGit версия 6.0.6 или выше](http://www.syntevo.com/smartgit/)  
*// Для Ubuntu перед установкой `SmartGit` нужно выполнить `sudo apt-get install git`*

## Подробная видео-инструкция
http://youtu.be/H1QDhvT7jE0

<a href="http://www.youtube.com/watch?feature=player_embedded&v=H1QDhvT7jE0" target="_blank"><img src="http://img.youtube.com/vi/H1QDhvT7jE0/0.jpg" width="240" height="180" border="10" /></a>

## Как проверить, что я все правильно сделал и готов к мастер-классу?

1. Запустить `Nexus 4` в Genymotion
2. Клонировать репозиторий `https://github.com/AlexKorovyansky/happydev-master-class.git` с помощью Smart Git
3. Сделать импорт проекта в `Android Studio`
4. Нажать `Run` в Android Studio 
5. Увидеть на экране виртуального Nexus 4 `секретный пароль для входа на мастер-класс`

**Удачи!**
