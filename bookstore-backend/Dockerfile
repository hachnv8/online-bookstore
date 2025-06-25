# Sử dụng base image là OpenJDK 17 với Alpine Linux (nhỏ gọn và bảo mật)
FROM openjdk:17-jdk-slim-buster

# Đặt thư mục làm việc bên trong container
WORKDIR /app

# Sao chép file JAR của ứng dụng vào container
# Bước này giả định bạn đã build project và có file JAR trong thư mục 'target/'
# Ví dụ: online-bookstore-0.0.1-SNAPSHOT.jar
COPY target/online-bookstore-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng mà ứng dụng Spring Boot đang lắng nghe (đã cấu hình là 1212)
EXPOSE 1212

# Lệnh để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]