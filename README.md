# 💬 Super Chack Messenger

<div align="center">

**P2P защищенный мессенджер для двух пользователей без центрального сервера**

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://jdk.java.net/21/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

</div>

---

## 📋 О проекте

**Super Chack Messenger** — это децентрализованный P2P-мессенджер для двух пользователей.  
В отличие от обычных мессенджеров, здесь **нет центрального сервера** — сообщения идут напрямую между пользователями.

### 🔥 Особенности

- ✅ **Полная децентрализация** — никаких серверов, только прямое P2P-соединение
- ✅ **TCP-сокеты** — надёжная доставка сообщений
- ✅ **Автоматический поиск свободного порта** — не нужно настраивать вручную
- ✅ **Определение внешнего и локального IP** — легко поделиться адресом
- ✅ **UPnP поддержка** — автоматическое открытие портов (если роутер поддерживает)
- ✅ **Кроссплатформенность** — работает на Windows, Linux, Mac (с Java)
- ✅ **Сборка в EXE** — можно запускать без установленной Java

---

## 🚀 Быстрый старт

### Требования

- Java 21 или выше
- Maven 3.9+
- Git (опционально)

### Установка и запуск

```bash
# Клонировать репозиторий
git clone https://github.com/xxGonzalesxx/Super-Chack-Messenger.git
cd Super-Chack-Messenger

# Собрать проект
mvn clean compile assembly:single

# Запустить
java -jar target/Super-Chack-Messenger-1.0-SNAPSHOT-jar-with-dependencies.jar