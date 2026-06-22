# 🏫 校园二手交易平台 - 后端

基于 **Spring Boot 3.2.5** 的校园二手交易平台后端服务，为在校师生提供便捷的二手商品发布、浏览、交易与管理功能。

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
│   ├── Result.java               # 统一响应体封装
│   ├── ResultCode.java           # 状态码枚举(200/400/401/403/404/500/999)
│   └── PageResult.java           # 分页结果封装
├── config/                       # 配置类
│   ├── CorsConfig.java           # 跨域配置
│   ├── WebMvcConfig.java         # MVC 配置（JWT 拦截器注册与白名单）
│   └── StaticResourceConfig.java # 静态资源映射（upload 目录）
├── controller/                   # 控制器层（8个）
│   ├── PingController.java       # 健康检查
│   ├── UserController.java       # 用户：注册/登录/个人资料
│   ├── AddressController.java    # 收货地址：增删改查
│   ├── CategoryController.java   # 商品分类：列表
│   ├── ProductController.java    # 商品：发布/编辑/列表/详情/下架
│   ├── FileController.java       # 文件上传：图片
│   ├── ReportController.java     # 举报：提交举报
│   └── AdminController.java      # 后台管理：用户/商品/举报/统计
├── dto/                          # 数据传输对象
│   ├── LoginDTO.java             # 登录请求
│   ├── RegisterDTO.java          # 注册请求
│   ├── ProductForm.java          # 商品发布/编辑表单
│   └── ProductQuery.java         # 商品列表查询条件
├── entity/                       # 实体类（8个）
│   ├── User.java                 # 用户
│   ├── Address.java              # 收货地址
│   ├── Category.java             # 商品分类
│   ├── Product.java              # 商品
│   ├── ProductImage.java         # 商品图片
│   ├── Order.java                # 订单
│   ├── OrderItem.java            # 订单明细
│   └── Report.java               # 举报
├── enums/                        # 枚举类
│   ├── OrderStatus.java          # 订单状态(待付款/待发货/待收货/已完成/已取消)
│   └── ProductStatus.java        # 商品状态(已下架/在售/已售)
├── exception/                    # 异常处理
│   ├── BusinessException.java    # 自定义业务异常
│   └── GlobalExceptionHandler.java # 全局异常处理器
├── interceptor/                  # 拦截器
│   └── JwtInterceptor.java       # JWT 登录拦截器
├── mapper/                       # MyBatis 映射器接口（9个）
│   ├── UserMapper.java
│   ├── AddressMapper.java
│   ├── CategoryMapper.java
│   ├── ProductMapper.java
│   ├── ProductImageMapper.java
│   ├── OrderMapper.java
│   ├── OrderItemMapper.java
│   ├── ReportMapper.java
│   └── AdminMapper.java
├── service/                      # 业务接口 + 实现
│   ├── UserService.java / impl/UserServiceImpl.java
│   ├── AddressService.java / impl/AddressServiceImpl.java
│   ├── CategoryService.java / impl/CategoryServiceImpl.java
│   ├── ProductService.java / impl/ProductServiceImpl.java
│   ├── ReportService.java / impl/ReportServiceImpl.java
│   └── AdminService.java / impl/AdminServiceImpl.java
└── util/                         # 工具类
    └── JwtUtil.java              # JWT 生成/解析工具
```

---

## 🗄 数据库设计

全部表满足 **3NF** 范式，含主键、外键、唯一约束、CHECK 约束与索引：

| 表名             | 建表脚本            | 说明       | 关键字段                                   |
| ---------------- | ------------------- | ---------- | ------------------------------------------ |
| `user`           | `schema.sql`        | 用户表     | 账号(唯一)、密码(BCrypt)、昵称、信用分、角色(0普通/1管理)、状态(0封禁/1正常) |
| `address`        | `schema.sql`        | 收货地址表 | 收货人、电话、校区楼栋、是否默认            |
| `category`       | `schema.sql`        | 商品分类表 | 分类名称、父级ID（树形结构，2级）           |
| `product`        | `schema.sql`        | 商品表     | 标题、描述、价格(≥0)、成色(1-4)、状态(0下架/1在售/2已售)、浏览量 |
| `product_image`  | `schema.sql`        | 商品图片表 | 图片URL、排序                              |
| `order_info`     | `order_tables.sql`  | 订单表     | 买家/卖家、总额(≥0)、状态(0-4)、收货地址    |
| `order_item`     | `order_tables.sql`  | 订单明细表 | 所属订单、商品、成交价格                    |
| `favorite`       | `social_tables.sql` | 收藏表     | 用户+商品(唯一)、收藏时间                   |
| `message`        | `social_tables.sql` | 聊天消息表 | 发送者/接收者、关联商品、内容、已读标记      |
| `review`         | `social_tables.sql` | 评价表     | 关联订单、评价人/被评价人、评分1-5          |
| `report`         | `social_tables.sql` | 举报表     | 举报人、被举报商品、原因、状态(0待处理/1已处理/2已驳回) |

### 建表顺序

```
schema.sql  →  order_tables.sql  →  social_tables.sql
```

### 测试数据

| 文件 | 说明 |
|---|---|
| `sql/init_data.sql` | 示例数据：4 用户、10 分类、2 地址、4 商品（密码均为 123456） |
| `sql/test/more_products.sql` | 商品测试数据：15 条（覆盖多分类，支持分页/搜索测试） |
| `sql/test/product_test.sql` | 商品模块功能查询(6条) + 约束验证(4条，默认注释) |
| `sql/test/integrity_test.sql` | 核心表完整性约束测试(6条，默认注释) |

### 示例账号

| 用户名 | 密码 | 角色 |
|---|---|---|
| `admin` | `123456` | 管理员 |
| `zhangsan` | `123456` | 普通用户 |
| `lisi` | `123456` | 普通用户 |
| `wangwu` | `123456` | 普通用户 |

---

## 📡 API 接口文档

**基础路径**：`/api`

### 统一响应格式

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 状态码

| 状态码 | 含义              |
| ------ | ----------------- |
| 200    | 操作成功          |
| 400    | 参数错误          |
| 401    | 未登录/Token 过期 |
| 403    | 无权限            |
| 404    | 资源不存在        |
| 500    | 业务异常          |
| 999    | 系统异常          |

### 认证方式

请求头携带 `Authorization: Bearer <token>`

### JWT 白名单（无需登录）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/ping` | 健康检查 |
| POST | `/api/user/register` | 用户注册 |
| POST | `/api/user/login` | 用户登录 |
| GET | `/api/product/list` | 商品列表（游客可见） |
| GET | `/api/product/detail/**` | 商品详情（游客可见） |

### 接口一览（共 24 个）

#### 健康检查

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/ping` | 服务健康检查 | 白名单 |

#### 用户模块

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/user/register` | 注册新用户 | 白名单 |
| POST | `/api/user/login` | 登录获取 token | 白名单 |
| GET | `/api/user/profile` | 查询个人资料 | 需登录 |
| PUT | `/api/user/profile` | 更新个人资料（昵称/头像） | 需登录 |

#### 地址模块

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/address/list` | 当前用户地址列表 | 需登录 |
| POST | `/api/address` | 新增地址 | 需登录 |
| PUT | `/api/address` | 修改地址 | 需登录 |
| DELETE | `/api/address/{id}` | 删除地址 | 需登录 |

#### 分类模块

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/category/list` | 全部分类列表 | 白名单 |

#### 商品模块

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/product` | 发布商品 | 需登录 |
| PUT | `/api/product` | 编辑商品（仅卖家） | 需登录 |
| GET | `/api/product/list` | 商品分页列表（支持 keyword/categoryId） | 白名单 |
| GET | `/api/product/detail/{id}` | 商品详情（含图片，浏览量+1） | 白名单 |
| PUT | `/api/product/offShelf/{id}` | 下架商品（仅卖家） | 需登录 |

#### 文件上传

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/file/upload` | 上传图片（multipart/form-data） | 需登录 |

#### 举报模块

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/report` | 举报商品 | 需登录 |

#### 后台管理（需管理员 role=1）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/admin/users` | 全部用户列表 | 管理员 |
| PUT | `/api/admin/user/{id}/status` | 封禁/解封用户 | 管理员 |
| GET | `/api/admin/products` | 全部商品列表 | 管理员 |
| PUT | `/api/admin/product/offShelf/{id}` | 强制下架商品 | 管理员 |
| GET | `/api/admin/reports` | 全部举报列表 | 管理员 |
| PUT | `/api/admin/report/{id}` | 处理/驳回举报 | 管理员 |
| GET | `/api/admin/stats` | 平台数据统计 | 管理员 |

### 后台统计返回字段

```json
{
  "userCount": 4,              // 用户总数
  "productCount": 19,           // 商品总数
  "orderCount": 0,              // 订单总数
  "totalSales": 0.00,           // 已完成订单成交总额
  "hotCategories": [            // 热门分类（在售商品数倒序）
    { "name": "教材书籍", "cnt": 6 }
  ],
  "activeUsers": [              // 活跃用户 Top5
    { "name": "张三", "cnt": 3 }
  ]
}
```

---

## 🚀 快速启动

### 1. 环境要求

- **JDK 17+**
- **Maven 3.8+**
- **MySQL 8.0+**

### 2. 数据库初始化

```bash
# 按顺序执行建表脚本
mysql -u root -p < sql/schema.sql
mysql -u root -p < sql/order_tables.sql
mysql -u root -p < sql/social_tables.sql

# 导入示例数据
mysql -u root -p < sql/init_data.sql

# （可选）导入商品测试数据用于分页/搜索测试
mysql -u root -p < sql/test/more_products.sql
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

启动成功后访问健康检查：

```
GET http://localhost:8080/api/ping
```

---

## ⚙️ 配置说明

`application.yml` 关键配置项：

| 配置项                   | 说明                  | 默认值                               |
| ------------------------ | --------------------- | ------------------------------------ |
| `server.port`            | 服务端口              | `8080`                               |
| `spring.datasource.url`  | 数据库连接            | `jdbc:mysql://localhost:3306/campus_secondhand` |
| `spring.datasource.password` | 数据库密码        | `1234`（请修改）                     |
| `jwt.secret`             | JWT 签名密钥          | 生产环境务必修改！                   |
| `jwt.expire`             | Token 过期时间（毫秒） | `86400000`（24小时）                 |
| `file.upload-dir`        | 文件上传目录          | `uploads`                            |

---

## 🧪 测试文档

| 文档 | 路径 | 说明 |
|------|------|------|
| 测试计划 | `docs/测试计划.md` | 测试目标、完整性约束/功能/权限测试用例、执行记录 |
| 接口测试用例 | `docs/接口测试用例.md` | 全部 24 个 API 的正向/异常/权限测试用例 |

---

## 👥 团队分工

| 成员 | 职责 |
| ---- | ---- |
| 董炜文（dww） | 项目骨架、用户模块、地址模块、商品模块、分类模块、文件上传、JWT 认证 |
| 范振扬（fzy） | 订单模块实体/Mapper/XML（待实现业务层） |
| 霍俊豪（hjh） | 数据库设计（全部11张表）、举报模块、后台管理模块、数据统计、测试文档 |

> 后端代码负责人标记于各文件注释中。

---

## 📝 开发日志

| 日期   | 内容                                         |
| ------ | -------------------------------------------- |
| 6/17   | 项目初始化：Spring Boot 骨架、核心5表建表、JWT 认证、统一响应封装 |
| 6/18   | 用户模块（注册/登录/资料）、地址模块（增删改查） |
| 6/19   | 商品模块（发布/编辑/列表/详情/下架）、分类模块、文件上传、测试数据 |
| 6/20   | 完善测试计划、补充完整性约束测试用例、对齐文档与 SQL |
| 6/21   | 订单表+订单明细表建表（含外键/CHECK约束）、社交表建表（收藏/消息/评价）、举报表建表、举报模块、后台管理模块（用户管理/商品管理/举报处理/数据统计）、管理员权限校验 |
| 6/22   | 文档全面更新：README、测试计划、接口测试用例 |

---

## 📄 License

MIT

本项目仅用于课程学习与交流。为大三下小组课程设计作业。
