# 🏫 校园二手交易平台 - 后端

基于 **Spring Boot 3.2.5** 的校园二手交易平台后端服务，为在校师生提供便捷的二手商品发布、浏览、交易、社交互动与 AI 智能导购功能。

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
| HTTP 客户端  | RestTemplate（调用 AI 接口）             |
| AI 大模型    | DeepSeek（deepseek-chat）               |
| 序列化       | Jackson (ObjectMapper)                  |
| 简化代码     | Lombok                                  |
| 构建工具     | Maven                                   |

---

## 📁 项目结构

```
src/main/java/com/hitwh/secondhand/
├── SecondhandApplication.java    # 启动类
├── common/                       # 公共模块（3 个文件）
│   ├── Result.java               # 统一响应体封装
│   ├── ResultCode.java           # 状态码枚举(200/400/401/403/404/500/999)
│   └── PageResult.java           # 分页结果封装
├── config/                       # 配置类（3 个文件）
│   ├── CorsConfig.java           # 跨域配置
│   ├── WebMvcConfig.java         # MVC 配置（JWT 拦截器注册与白名单）
│   └── StaticResourceConfig.java # 静态资源映射（uploads 目录）
├── controller/                   # 控制器层（13 个）
│   ├── PingController.java       # 健康检查
│   ├── UserController.java       # 用户：注册/登录/个人资料
│   ├── AddressController.java    # 收货地址：增删改查
│   ├── CategoryController.java   # 商品分类：列表
│   ├── ProductController.java    # 商品：发布/编辑/列表/详情/下架
│   ├── FileController.java       # 文件上传：图片
│   ├── OrderController.java      # 订单：下单/发货/收货/取消/查询
│   ├── FavoriteController.java   # 收藏：切换/列表/检查
│   ├── MessageController.java    # 聊天：发送/会话/最近消息
│   ├── ReviewController.java     # 评价：提交/查看
│   ├── ReportController.java     # 举报：提交举报
│   ├── AdminController.java      # 后台管理：用户/商品/举报/统计
│   └── AiController.java         # AI 导购：智能推荐
├── dto/                          # 数据传输对象（6 个文件）
│   ├── LoginDTO.java             # 登录请求
│   ├── RegisterDTO.java          # 注册请求
│   ├── ProductForm.java          # 商品发布/编辑表单
│   ├── ProductQuery.java         # 商品列表查询条件
│   ├── OrderForm.java            # 下单表单
│   └── AiRecommendGroup.java     # AI 推荐结果（商品+理由）
├── entity/                       # 实体类（11 个）
│   ├── User.java                 # 用户
│   ├── Address.java              # 收货地址
│   ├── Category.java             # 商品分类
│   ├── Product.java              # 商品（含 cover 封面字段）
│   ├── ProductImage.java         # 商品图片
│   ├── Order.java                # 订单
│   ├── OrderItem.java            # 订单明细
│   ├── Favorite.java             # 收藏
│   ├── Message.java              # 聊天消息
│   ├── Review.java               # 评价
│   └── Report.java               # 举报
├── enums/                        # 枚举类（2 个文件）
│   ├── OrderStatus.java          # 订单状态(0-4)
│   └── ProductStatus.java        # 商品状态(0-2)
├── exception/                    # 异常处理（2 个文件）
│   ├── BusinessException.java    # 自定义业务异常
│   └── GlobalExceptionHandler.java # 全局异常处理器
├── interceptor/                  # 拦截器（1 个文件）
│   └── JwtInterceptor.java       # JWT 登录拦截器
├── mapper/                       # MyBatis 映射器接口（12 个）
│   ├── UserMapper.java
│   ├── AddressMapper.java
│   ├── CategoryMapper.java
│   ├── ProductMapper.java
│   ├── ProductImageMapper.java
│   ├── OrderMapper.java
│   ├── OrderItemMapper.java
│   ├── FavoriteMapper.java
│   ├── MessageMapper.java
│   ├── ReviewMapper.java
│   ├── ReportMapper.java
│   └── AdminMapper.java
├── service/                      # 业务接口 + 实现（11 对）
│   ├── UserService.java       / impl/UserServiceImpl.java
│   ├── AddressService.java    / impl/AddressServiceImpl.java
│   ├── CategoryService.java   / impl/CategoryServiceImpl.java
│   ├── ProductService.java    / impl/ProductServiceImpl.java
│   ├── OrderService.java      / impl/OrderServiceImpl.java
│   ├── FavoriteService.java   / impl/FavoriteServiceImpl.java
│   ├── MessageService.java    / impl/MessageServiceImpl.java
│   ├── ReviewService.java     / impl/ReviewServiceImpl.java
│   ├── ReportService.java     / impl/ReportServiceImpl.java
│   ├── AdminService.java      / impl/AdminServiceImpl.java
│   └── AiService.java         / impl/AiServiceImpl.java
└── util/                         # 工具类（1 个文件）
    └── JwtUtil.java              # JWT 生成/解析工具
```

**代码统计**：1 个 yml + 约 66 个 Java 源文件 + 12 个 XML Mapper + 7 个 SQL 脚本

---

## 🗄 数据库设计

全部 11 张表满足 **3NF** 范式，含主键、外键、唯一约束、CHECK 约束与索引：

| 表名             | 建表脚本            | 说明       | 关键约束                                   |
| ---------------- | ------------------- | ---------- | ------------------------------------------ |
| `user`           | `schema.sql`        | 用户表     | `uk_username` 唯一；role(0普通/1管理)；status(0封禁/1正常) |
| `address`        | `schema.sql`        | 收货地址表 | `fk_addr_user` → user                     |
| `category`       | `schema.sql`        | 商品分类表 | parent_id 自引用（2级树形结构）             |
| `product`        | `schema.sql`        | 商品表     | `fk_prod_seller`→user；`fk_prod_category`→category；`chk_prod_price` price≥0 |
| `product_image`  | `schema.sql`        | 商品图片表 | `fk_img_product`→product                  |
| `order_info`     | `order_tables.sql`  | 订单表     | `fk_order_buyer/seller/address`；`chk_order_amount` total≥0 |
| `order_item`     | `order_tables.sql`  | 订单明细表 | `fk_item_order`→order_info；`fk_item_product`→product |
| `favorite`       | `social_tables.sql` | 收藏表     | `uk_fav_user_product` (user_id+product_id) 唯一 |
| `message`        | `social_tables.sql` | 聊天消息表 | `fk_msg_sender/receiver`→user；`fk_msg_product`→product |
| `review`         | `social_tables.sql` | 评价表     | `chk_review_rating` rating BETWEEN 1 AND 5 |
| `report`         | `social_tables.sql` | 举报表     | `fk_report_reporter`→user；`fk_report_product`→product |

### 建表顺序

```
sql/schema.sql  →  sql/order_tables.sql  →  sql/social_tables.sql
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

**基础路径**：`/api`　　　**认证方式**：请求头 `Authorization: Bearer <token>`

### 统一响应格式

```json
{ "code": 200, "message": "操作成功", "data": {} }
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

### JWT 白名单（无需登录，共 7 个路径）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/ping` | 健康检查 |
| POST | `/api/user/register` | 用户注册 |
| POST | `/api/user/login` | 用户登录 |
| GET | `/api/product/list` | 商品列表（游客可见） |
| GET | `/api/product/detail/**` | 商品详情（游客可见） |
| GET | `/api/review/user/**` | 卖家评价（游客可见） |
| POST | `/api/ai/recommend` | AI 导购（游客可体验） |

---

### 接口一览（共 39 个 API）

#### 1. 健康检查

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/ping` | 服务健康检查，返回 service/status/time | 白名单 |

#### 2. 用户模块（UserController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/user/register` | 注册新用户 | 白名单 |
| POST | `/api/user/login` | 登录，返回 `{token, user}` | 白名单 |
| GET | `/api/user/profile` | 查询个人资料（不含密码） | 需登录 |
| PUT | `/api/user/profile` | 更新个人资料（昵称/头像） | 需登录 |

#### 3. 地址模块（AddressController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/address/list` | 当前用户地址列表 | 需登录 |
| POST | `/api/address` | 新增地址（支持默认地址自动切换） | 需登录 |
| PUT | `/api/address` | 修改地址（校验归属权） | 需登录 |
| DELETE | `/api/address/{id}` | 删除地址（校验归属权） | 需登录 |

#### 4. 分类模块（CategoryController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/category/list` | 全部分类列表（按 parent_id 排序） | 白名单 |

#### 5. 商品模块（ProductController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/product` | 发布商品（含图片） | 需登录 |
| PUT | `/api/product` | 编辑商品（仅卖家本人） | 需登录 |
| GET | `/api/product/list` | 分页列表（支持 keyword/categoryId/page/size） | 白名单 |
| GET | `/api/product/detail/{id}` | 商品详情（含图片，浏览量+1） | 白名单 |
| PUT | `/api/product/offShelf/{id}` | 下架商品（仅卖家本人） | 需登录 |

#### 6. 文件上传（FileController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/file/upload` | 上传图片（multipart/form-data），返回 /uploads/{uuid}.ext | 需登录 |

#### 7. 订单模块（OrderController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/order` | 下单（校验在售/非自售/锁定商品为已售） | 需登录 |
| GET | `/api/order/bought` | 我买到的订单（含明细） | 需登录 |
| GET | `/api/order/sold` | 我卖出的订单（含明细） | 需登录 |
| GET | `/api/order/detail/{id}` | 订单详情（含明细） | 需登录 |
| PUT | `/api/order/ship/{id}` | 卖家发货（1→2 待收货） | 需登录 |
| PUT | `/api/order/confirm/{id}` | 买家确认收货（2→3 已完成） | 需登录 |
| PUT | `/api/order/cancel/{id}` | 取消订单（1→4 已取消，恢复商品在售） | 需登录 |

> 订单状态流转：**下单(1→待发货) → 发货(2→待收货) → 确认(3→已完成)**，待发货阶段可取消(4→已取消)。

#### 8. 收藏模块（FavoriteController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/favorite/toggle/{productId}` | 切换收藏（已收藏→取消，未收藏→添加），返回布尔值 | 需登录 |
| GET | `/api/favorite/list` | 我的收藏列表（含商品标题/价格/状态） | 需登录 |
| GET | `/api/favorite/check/{productId}` | 是否已收藏某商品 | 需登录 |

#### 9. 聊天消息模块（MessageController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/message` | 发送消息（receiverId, productId, content） | 需登录 |
| GET | `/api/message/conversation` | 与某人针对某商品的会话（自动标记已读） | 需登录 |
| GET | `/api/message/recent` | 与我相关的最近消息（会话列表数据源） | 需登录 |

#### 10. 评价模块（ReviewController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/review` | 提交评价（仅订单参与者、订单已完成、未评价过） | 需登录 |
| GET | `/api/review/user/{targetId}` | 查看某用户收到的评价（含评价人、商品信息） | 白名单 |

#### 11. 举报模块（ReportController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/report` | 举报商品（productId + reason） | 需登录 |

#### 12. 后台管理（AdminController，需管理员 role=1）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| GET | `/api/admin/users` | 全部用户列表（不含密码） | 管理员 |
| PUT | `/api/admin/user/{id}/status` | 封禁/解封用户（status=0封禁/1正常） | 管理员 |
| GET | `/api/admin/products` | 全部商品列表（含已下架/已售） | 管理员 |
| PUT | `/api/admin/product/offShelf/{id}` | 强制下架任意商品 | 管理员 |
| GET | `/api/admin/reports` | 全部举报列表（含商品标题/举报人） | 管理员 |
| PUT | `/api/admin/report/{id}` | 处理举报（status=1已处理→自动下架商品 / 2驳回） | 管理员 |
| GET | `/api/admin/stats` | 平台数据统计 | 管理员 |

#### 13. AI 智能导购（AiController）

| 方法 | 路径 | 说明 | 认证 |
|------|------|------|------|
| POST | `/api/ai/recommend` | 根据自然语言需求推荐商品（body: `{"need":"..."}`） | 白名单 |

> AI 导购实现原理：将全平台在售商品发给 DeepSeek 大模型，由 AI 直接挑选最相关商品并给出推荐理由，返回 `[{product, reason}]` 数组。

### 后台统计返回示例

```json
{
  "userCount": 4,
  "productCount": 19,
  "orderCount": 5,
  "totalSales": 2480.00,
  "hotCategories": [
    { "name": "教材书籍", "cnt": 6 }
  ],
  "activeUsers": [
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
- **DeepSeek API Key**（AI 导购功能需要，可选，见下方配置说明）

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

### 3. 修改配置

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    password: 你的MySQL密码   # 必改

# AI 导购功能（如不需要可将 deepseek.api-key 置空或跳过）
deepseek:
  api-key: 你的DeepSeek_API_Key   # 到 platform.deepseek.com 注册获取
  url: https://api.deepseek.com/chat/completions
  model: deepseek-chat
```

### 4. 启动项目

```bash
mvn spring-boot:run
# 或
mvn clean package -DskipTests && java -jar target/secondhand-1.0.0.jar
```

验证：
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
| `jwt.expire`             | Token 过期时间（ms）  | `86400000`（24小时）                 |
| `file.upload-dir`        | 文件上传目录          | `uploads`                            |
| `deepseek.api-key`       | DeepSeek API Key      | 需注册获取                           |
| `deepseek.url`           | DeepSeek API 地址     | `https://api.deepseek.com/chat/completions` |
| `deepseek.model`         | 模型名称              | `deepseek-chat`                      |

---

## 🧪 测试文档

| 文档 | 路径 | 说明 |
|------|------|------|
| 测试计划 | `docs/测试计划.md` | 15 条完整性约束 + 12 条业务校验 + 5 条 AI 测试 + 权限/统计/业务流程 |
| 接口测试用例（简明） | `docs/接口测试用例.md` | 用户+商品模块 23 条基础用例 |
| 接口测试用例（全模块） | `docs/接口测试用例-全模块.md` | 全部 13 个模块约 60 条详细用例 |

---

## 👥 团队分工

| 成员 | 职责 |
| ---- | ---- |
| 董炜文（dww） | 项目骨架、JWT 认证、用户模块、地址模块、商品模块、分类模块、文件上传 |
| 范振扬（fzy） | 订单模块、收藏模块、聊天消息模块、评价模块 |
| 霍俊豪（hjh） | 数据库设计（全部 11 张表）、举报模块、后台管理模块、数据统计、AI 智能导购、测试文档 |

---

## 📝 开发日志

| 日期   | 内容                                         |
| ------ | -------------------------------------------- |
| 6/17   | 项目初始化：Spring Boot 骨架、核心 5 表建表、JWT 认证、统一响应封装 |
| 6/18   | 用户模块（注册/登录/资料）、地址模块（增删改查）、实体类与枚举 |
| 6/19   | 商品模块（发布/编辑/列表/详情/下架）、分类模块、文件上传、测试数据 |
| 6/20   | 订单表+订单明细表建表、完善测试计划与约束测试用例 |
| 6/21   | 社交表建表（收藏/消息/评价）、举报表建表、订单模块（下单/发货/收货/取消）、收藏模块、聊天消息模块、评价模块、举报模块、后台管理模块（用户管理/商品管理/举报处理/数据统计） |
| 6/22   | AI 智能导购模块（DeepSeek 集成）、举报处理自动下架、列表封面图子查询、白名单扩展、文档全面更新 |

---

## 📄 License

MIT
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
