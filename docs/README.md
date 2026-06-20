# 🏫 校园二手交易平台 - 后端

基于 **Spring Boot 3.2.5** 的校园二手交易平台后端服务，为在校师生提供便捷的二手商品发布、浏览与交易功能。

---

## 🛠 技术栈

| 类别         | 技术                                    |
| ------------ | --------------------------------------- |
| 核心框架     | Spring Boot 3.2.5                       |
| JDK 版本     | Java 17                                 |
| 持久层       | MyBatis 3.0.3                           |
| 数据库       | MySQL 8.0+                              |
| 身份认证     | JWT (jjwt 0.11.5)                       |
| 密码加密     | BCrypt (spring-security-crypto)         |
| 参数校验     | spring-boot-starter-validation          |
| 简化代码     | Lombok                                  |
| 构建工具     | Maven                                   |

---

## 📁 项目结构

```
src/main/java/com/hitwh/secondhand/
├── SecondhandApplication.java    # 启动类
├── common/                       # 公共模块
│   ├── Result.java               # 统一响应体
│   └── ResultCode.java           # 状态码枚举
├── config/                       # 配置类
│   ├── CorsConfig.java           # 跨域配置
│   └── WebMvcConfig.java         # MVC 配置（拦截器注册）
├── controller/                   # 控制器层
│   └── PingController.java       # 健康检查接口
├── exception/                    # 异常处理
│   ├── BusinessException.java    # 业务异常
│   └── GlobalExceptionHandler.java # 全局异常处理器
├── interceptor/                  # 拦截器
│   └── JwtInterceptor.java       # JWT 登录拦截器
└── util/                         # 工具类
    └── JwtUtil.java              # JWT 生成/解析工具
```
> 待建包：`entity/`（实体类）、`mapper/`（MyBatis 映射器）——随业务模块开发逐步补充。

---

## 🗄 数据库设计

已建核心表（`schema.sql`），满足 3NF 范式：

| 表名             | 说明       | 关键字段                                   |
| ---------------- | ---------- | ------------------------------------------ |
| `user`           | 用户表     | 账号、密码(BCrypt)、昵称、信用分、角色      |
| `address`        | 收货地址表 | 收货人、电话、校区楼栋、是否默认            |
| `category`       | 商品分类表 | 分类名称、父级ID（树形结构）               |
| `product`        | 商品表     | 标题、描述、价格、成色、状态、浏览量        |
| `product_image`  | 商品图片表 | 图片URL、排序                              |

> 后续待建表：`order_info`、`order_item`（订单模块）、`favorite`、`message`、`review`（社交模块）、`report`（后台模块）。

### 测试数据

| 文件 | 说明 |
|---|---|
| `sql/init_data.sql` | 示例数据：4 用户、10 分类、2 地址、4 商品 |
| `sql/test/more_products.sql` | 商品测试数据：15 条（覆盖多分类，支持分页/搜索测试） |
| `sql/test/integrity_test.sql` | 完整性约束测试脚本（6 条用例，默认注释） |
| `sql/test/product_test.sql` | 商品模块功能查询 + 约束验证（6+4 条） |

---

## 🚀 快速启动

### 1. 环境要求

- **JDK 17+**
- **Maven 3.8+**
- **MySQL 8.0+**

### 2. 数据库初始化

```bash
# 1. 连接 MySQL 并执行建表脚本
mysql -u root -p < schema.sql

# 2. 导入示例数据（可选）
mysql -u root -p < init_data.sql
```

> 示例用户密码统一为 `123456`。

### 3. 修改配置

编辑 `src/main/resources/application.yml`，将数据库密码改为本机 MySQL 密码：

```yaml
spring:
  datasource:
    password: 你的MySQL密码
```

### 4. 启动项目

```bash
# 编译并启动
mvn spring-boot:run

# 或先打包再运行
mvn clean package -DskipTests
java -jar target/secondhand-1.0.0.jar
```

启动成功后访问健康检查接口：

```
GET http://localhost:8080/api/ping
```

返回示例：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "service": "campus-secondhand",
    "status": "running",
    "time": "2026-06-19T10:30:00"
  }
}
```

---

## 📡 接口规范

- **基础路径**：`/api`
- **统一响应格式**：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

| 状态码 | 含义       |
| ------ | ---------- |
| 200    | 操作成功   |
| 400    | 参数错误   |
| 401    | 未登录/Token过期 |
| 403    | 无权限     |
| 500    | 服务器异常 |

- **认证方式**：请求头携带 `Authorization: Bearer <token>`

---

## ⚙️ 配置说明

`application.yml` 关键配置项：

| 配置项              | 说明                  | 默认值                           |
| ------------------- | --------------------- | -------------------------------- |
| `server.port`       | 服务端口              | `8080`                           |
| `spring.datasource.url` | 数据库连接          | `jdbc:mysql://localhost:3306/...` |
| `jwt.secret`        | JWT 签名密钥          | 生产环境务必修改！               |
| `jwt.expire`        | Token 过期时间（毫秒） | `86400000`（24小时）              |

---

## 👥 团队分工

| 成员 | 职责 |
| ---- | ---- |
| dww  | （待定） |
| fzy  | （待定） |
| hjh  | 数据库设计与测试 |

> 后端代码负责人标记于各文件注释中，随模块推进逐步实名。

---

## 📝 开发日志

| 日期   | 内容                                         |
| ------ | -------------------------------------------- |
| 6/17   | 项目初始化：Spring Boot 骨架、数据库建表、JWT |
| 6/19   | 补充 README 文档、商品测试数据与功能查询 SQL |
| 6/20   | 完善测试计划、补充完整性约束测试用例、对齐文档与 SQL |
| 6/21   | 待：订单模块（order_info、order_item）       |
| 6/23   | 待：社交模块（favorite、message、review）    |
| 6/25   | 待：后台模块（report）                       |

---

## 📄 License

本项目仅用于课程学习与交流。为大三下小组课程设计作业。
