# Hướng Dẫn Cài Đặt Môi Trường Phát Triển Hệ Thống

## Game Mario - CNPM

---

## 1. Yêu Cầu Hệ Thống

### Phần cứng tối thiểu:
- **CPU:** Intel Core i3 / AMD Ryzen 3 thế hệ thứ 8 trở lên
- **RAM:** 4 GB
- **Ổ cứng:** 500 MB trống
- **Màn hình:** Độ phân giải 1280x720 trở lên

### Phần mềm yêu cầu:
- **Hệ điều hành:** Windows 10/11, macOS 10.15+, Linux (Ubuntu 20.04+)
- **Java Development Kit (JDK):** Phiên bản 21 trở lên
- **Maven:** Phiên bản 3.8+ (đã có sẵn trong JDK 21)
- **IDE:** IntelliJ IDEA (khuyến nghị), Eclipse, hoặc VS Code

---

## 2. Cài Đặt Java Development Kit (JDK) 21

### Windows

#### Cách 1: Tải từ Oracle/OpenJDK
1. Truy cập https://adoptium.net/temurin/releases/ (OpenJDK 21 - miễn phí)
2. Chọn **Version:** 21, **Operating System:** Windows, **Architecture:** x64
3. Tải file `.msi` và cài đặt

#### Cách 2: Sử dụng Winget (Windows 10/11)
```powershell
winget install EclipseAdoptium.Temurin.21.JDK
```

#### Kiểm tra cài đặt:
```powershell
java -version
javac -version
```

**Kết quả mong đợi:**
```
java version "21.0.x"
javac 21.0.x"
```

---

### macOS

#### Sử dụng Homebrew:
```bash
brew install openjdk@21
```

#### Kiểm tra cài đặt:
```bash
java -version
```

---

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

#### Kiểm tra:
```bash
java -version
```

---

## 3. Cài Đặt Maven

Maven đã được tích hợp sẵn trong JDK 21. Nếu cần cài riêng:

### Windows
```powershell
# Tải từ https://maven.apache.org/download.cgi
# Giải nén và thêm vào PATH
$mvn -version
```

### macOS
```bash
brew install maven
```

### Linux
```bash
sudo apt install maven
```

---

## 4. Cài Đặt IDE (IntelliJ IDEA)

### IntelliJ IDEA (Khuyến nghị)

1. Tải IntelliJ IDEA Community từ: https://www.jetbrains.com/idea/download/
2. Cài đặt và khởi động

#### Import Project:
1. **File → Open** → Chọn thư mục `OOP_Mario-main`
2. Chọn **Open as Project**
3. IntelliJ sẽ tự động nhận diện Maven project (pom.xml)
4. Đợi IntelliJ tải dependencies

#### Cấu hình JDK trong IntelliJ:
1. **File → Project Structure → Project**
2. **SDK:** Chọn JDK 21
3. **Language level:** 21
4. **Apply → OK**

### Visual Studio Code

1. Cài đặt extensions:
   - **Extension Pack for Java** (Microsoft)
   - **Maven for Java** (Microsoft)

2. Mở thư mục project
3. VS Code sẽ tự động nhận diện Maven project

---

## 5. Clone Repository

```bash
git clone https://github.com/ManhTran-ai/Game-Mario-CNPM.git
cd Game-Mario-CNPM
git checkout LeVanTai
```

---

## 6. Cài Đặt Phụ Thuộc Maven

### Cách 1: Qua IDE
IntelliJ/VSC sẽ tự động tải dependencies khi import project.

### Cách 2: Qua Command Line
```bash
cd OOP_Mario-main
mvn clean install
```

---

## 7. Chạy Tests

### Tất cả tests:
```bash
mvn test
```

### Tests cụ thể:
```bash
# Development Tests (nguoi2)
mvn test -Dtest=DevelopmentTest

# Release Tests (nguoi2)
mvn test -Dtest=ReleaseTest

# Use Case Tests
mvn test -Dtest=UC08_MarioDeathInvincibleTest
```

### Kiểm tra coverage:
```bash
mvn test jacoco:report
```

---

## 8. Cấu Trúc Project

```
OOP_Mario-main/
├── src/
│   ├── controller/       # Điều khiển game
│   ├── event/            # Hệ thống sự kiện
│   ├── manager/          # Quản lý game
│   ├── model/            # Đối tượng game
│   │   ├── brick/        # Các loại gạch
│   │   ├── enemy/        # Kẻ thù
│   │   ├── hero/         # Mario
│   │   └── prize/        # Phần thưởng
│   ├── test/             # Unit tests
│   │   ├── nguoi1/
│   │   ├── nguoi2/
│   │   ├── nguoi3/
│   │   ├── nguoi4/
│   │   └── nguoi5/
│   ├── test_usecase/     # Integration tests
│   └── view/             # Giao diện game
├── src/pom.xml           # Maven configuration
└── README.md
```

---

## 9. Các Lệnh Maven Thường Dùng

| Lệnh | Mô tả |
|------|-------|
| `mvn clean` | Xóa thư mục target |
| `mvn compile` | Biên dịch source code |
| `mvn test` | Chạy tất cả tests |
| `mvn package` | Đóng gói thành JAR |
| `mvn clean package` | Clean và đóng gói |
| `mvn dependency:tree` | Xem cây phụ thuộc |
| `mvn dependency:resolve` | Tải dependencies |

---

## 10. Xử Lý Lỗi Thường Gặp

### Lỗi: "JAVA_HOME is not set"
```powershell
# Windows
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-21.0.x"
# Thêm vào PATH: %JAVA_HOME%\bin
```

### Lỗi: "Maven project import failed"
1. **File → Invalidate Caches → Invalidate and Restart**
2. Xóa thư mục `.idea` và import lại

### Lỗi: "Test failed"
```bash
mvn clean test -U  # -U: force update dependencies
```

---

## 11. Hỗ Trợ

- **Issues:** https://github.com/ManhTran-ai/Game-Mario-CNPM/issues
- **Email:** 23130283@st.hcmuaf.edu.vn
