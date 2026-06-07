# Hướng Dẫn Cài Đặt Môi Trường Chạy Hệ Thống

## Game Mario - CNPM

---

## 1. Tổng Quan

Tài liệu này hướng dẫn cách chạy game Mario sau khi đã hoàn thành phát triển hoặc nhận bản build từ nhà phát triển.

---

## 2. Yêu Cầu Hệ Thống

### Phần cứng tối thiểu:
- **CPU:** Intel Core i3 / AMD Ryzen 3 thế hệ thứ 6 trở lên
- **RAM:** 2 GB
- **Ổ cứng:** 200 MB trống
- **Màn hình:** Độ phân giải 1024x768 trở lên

### Phần mềm yêu cầu:
- **Hệ điều hành:** Windows 10/11, macOS 10.14+, Linux
- **Java Runtime Environment (JRE):** Phiên bản 21 trở lên

---

## 3. Các Phương Thức Chạy Game

### 3.1. Chạy Từ Source Code (Development)

#### Cách 1: Qua IntelliJ IDEA
1. Mở project trong IntelliJ
2. Mở file `GameWindow.java` tại `src/view/GameWindow.java`
3. Nhấn **Shift + F10** hoặc click nút **Run**
4. Game sẽ khởi động

#### Cách 2: Qua Maven
```bash
cd OOP_Mario-main
mvn clean compile
mvn exec:java -Dexec.mainClass="view.GameWindow"
```

#### Cách 3: Qua Command Line (Java)
```bash
cd OOP_Mario-main
# Biên dịch
javac -d out -sourcepath src src/view/GameWindow.java src/model/*.java src/controller/*.java src/manager/*.java src/event/*.java src/view/*.java

# Chạy
java -cp out view.GameWindow
```

---

### 3.2. Chạy File JAR đã đóng gói

#### Đóng gói JAR:
```bash
cd OOP_Mario-main
mvn clean package
```

**Output:** `target/oop-mario-1.0.jar`

#### Chạy JAR:
```bash
java -jar target/oop-mario-1.0.jar
```

---

### 3.3. Chạy Tests (Không cần giao diện)

```bash
cd OOP_Mario-main
mvn test
```

---

## 4. Hướng Dẫn Chi Tiết Theo Hệ Điều Hành

### 4.1. Windows

#### Cài đặt JRE 21:
```powershell
winget install EclipseAdoptium.Temurin.21.JRE
```

#### Chạy game:
```powershell
# Cách 1: Double-click vào file JAR
# Cách 2: Command line
java -jar OOP_Mario-main\target\oop-mario-1.0.jar

# Cách 3: Tạo shortcut
# Right-click JAR → Create Shortcut → Đặt trên Desktop
```

---

### 4.2. macOS

#### Cài đặt JRE:
```bash
brew install openjdk@21
```

#### Chạy game:
```bash
# Mở JAR bằng Java
java -jar OOP_Mario-main/target/oop-mario-1.0.jar

# Hoặc double-click vào file JAR
open oop-mario-1.0.jar
```

---

### 4.3. Linux

#### Cài đặt JRE:
```bash
sudo apt update
sudo apt install openjdk-21-jre
```

#### Chạy game:
```bash
java -jar OOP_Mario-main/target/oop-mario-1.0.jar
```

---

## 5. Cấu Hình Game

### 5.1. Tệp Cấu Hình
Game sử dụng các hằng số trong code tại:
- `src/manager/GameConstants.java` - Hằng số game
- `src/manager/GameStatus.java` - Trạng thái game

### 5.2. Thay Đổi Cấu Hình (Nâng cao)

#### Kích thước màn hình:
Sửa trong `GameConstants.java`:
```java
public static final int SCREEN_WIDTH = 1280;
public static final int SCREEN_HEIGHT = 720;
```

#### Tốc độ game:
```java
public static final double GRAVITY = 0.5;
public static final int MARIO_SPEED = 5;
```

---

## 6. Cấu Trúc File Khi Chạy

```
Game/
├── oop-mario-1.0.jar          # File game chính
├── README.md                    # Hướng dẫn sử dụng
├── src/                         # Source code
│   ├── view/                    # Giao diện
│   ├── model/                   # Đối tượng game
│   └── ...
└── target/                      # File đã build
    └── oop-mario-1.0.jar
```

---

## 7. Các Lỗi Thường Gặp Khi Chạy

### Lỗi: "Unable to access jarfile"
- Kiểm tra đường dẫn file JAR đúng
- Chạy từ đúng thư mục chứa JAR

### Lỗi: "Java version mismatch"
```
Error: A JNI error has occurred, please check your installation and try again
```
- Cài đặt Java 21 trở lên
- Kiểm tra: `java -version`

### Lỗi: "OutOfMemoryError"
- Tăng bộ nhớ heap:
```bash
java -Xmx512m -jar oop-mario-1.0.jar
```

### Lỗi: Game không hiển thị hình ảnh
- Kiểm tra thư mục `assets/` hoặc `resources/` có chứa hình ảnh
- Kiểm tra quyền truy cập file

---

## 8. Chạy Trong Môi Trường Khác

### 8.1. Docker (Linux Container)

```dockerfile
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY OOP_Mario-main/target/oop-mario-1.0.jar /app/

CMD ["java", "-jar", "/app/oop-mario-1.0.jar"]
```

Build và chạy:
```bash
docker build -t mario-game .
docker run mario-game
```

### 8.2. GitHub Actions (CI/CD)

Workflow mẫu:
```yaml
name: Run Game Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '21'
      - name: Run tests
        run: mvn test
```

---

## 9. Đóng Gói Và Phân Phối

### Tạo JAR với dependencies:
```bash
mvn clean package
# Hoặc sử dụng shade plugin cho JAR có dependencies
```

### JAR standalone:
```bash
mvn clean package shade:shade
```

---

## 10. Thông Tin Game

| Thông tin | Chi tiết |
|-----------|----------|
| **Tên game** | OOP Mario Game |
| **Phiên bản** | 1.0 |
| **Ngôn ngữ** | Java 21 |
| **Framework** | Maven |
| **Testing** | JUnit 5 |
| **GitHub** | https://github.com/ManhTran-ai/Game-Mario-CNPM |

---

## 11. Liên Hệ Hỗ Trợ

- **GitHub Issues:** https://github.com/ManhTran-ai/Game-Mario-CNPM/issues
- **Email:** 23130283@st.hcmuaf.edu.vn
