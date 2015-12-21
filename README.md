[![Build Status](https://travis-ci.org/IO-z21/Friends-Locator.svg?branch=master)](https://travis-ci.org/IO-z21/Friends-Locator)

[Live application](http://friendslocator-romach.rhcloud.com/)

# Friends Locator

Веб-сервис, который позволяет друзьям отслеживать местоположение друг друга.

Требования для сборки и запуска проекта:
-Git
-Maven
-Java 7
-Linux

Для запуска сервера локально
1. выполните git clone https://github.com/IO-z21/Friends-Locator.git в той директории,
куда Вы хотите сохранить проект.
2. откройте командную строку и перейдите в директорию проекта
3. выполните команду mvn clean install.
4. перейдите в директорию friends-locator-base/target/friends-locator-dist
5. выполните команду chmod u+x run.sh
6. выполните команду  ./run.sh.

Для запуска тестов в корневой директории проекта запустите mvn clean test.