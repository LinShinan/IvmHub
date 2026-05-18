<div align="center">

# IvmHub — 智能售货机运营管理系统

<img src="https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk" alt="Java">
<img src="https://img.shields.io/badge/Spring%20Boot-2.5.15-brightgreen?style=flat-square&logo=springboot" alt="Spring Boot">
<img src="https://img.shields.io/badge/MySQL-8.0.33-blue?style=flat-square&logo=mysql" alt="MySQL">
<img src="https://img.shields.io/badge/Redis-Lettuce-red?style=flat-square&logo=redis" alt="Redis">
<img src="https://img.shields.io/badge/MyBatis-3.5.x-blue?style=flat-square" alt="MyBatis">
<img src="https://img.shields.io/badge/JWT-0.9.1-black?style=flat-square&logo=jsonwebtokens" alt="JWT">
<img src="https://img.shields.io/badge/Maven-3.6+-red?style=flat-square&logo=apachemaven" alt="Maven">
<img src="https://img.shields.io/badge/Druid-1.2.20-blue?style=flat-square" alt="Druid">
<img src="https://img.shields.io/badge/Swagger-3.0.0-brightgreen?style=flat-square&logo=swagger" alt="Swagger">
<img src="https://img.shields.io/badge/OSS-aliyun-orange?style=flat-square" alt="OSS">
<img src="https://img.shields.io/badge/Lombok-1.18.30-red?style=flat-square" alt="Lombok">

</div>

---

> **本项目为后端代码，基于若依 (RuoYi) 框架开发。**
>
> 前端项目：**[IvmHub-Vue](https://github.com/LinShinan/IvmHub-Vue)** &nbsp;|&nbsp; 后端仓库：**[IvmHub](https://github.com/LinShinan/IvmHub)**

---

## 项目简介

IvmHub 是一套面向智能售货机行业的运营管理系统，覆盖售货机从**投放到撤机**的全生命周期，包括点位管理、设备监控、商品定价、订单追踪、工单调度、合作商分成等完整业务链路。项目基于若依微框架二次开发，核心业务代码集中在 `stone-manage` 模块。

---

## 项目结构

| 模块 | 说明 |
|------|------|
| `stone-admin` | 管理后台启动入口 |
| `stone-common` | 通用模块：注解、工具类、常量、全局异常 |
| `stone-framework` | 框架模块：安全认证、数据源、AOP、拦截器 |
| `stone-generator` | 代码生成器（若依自带） |
| `stone-manage` | **核心业务模块：售货机全生命周期管理** |
| `stone-quartz` | 定时任务模块 |
| `stone-system` | 系统基础模块：用户、角色、菜单、字典 |
| `sql/` | 数据库建表及初始化脚本 |

---

## 业务架构

```
区域 Region
 │
 ├─ 合作商 Partner ──┐
 │                   │
 ├─ 点位 Node ───────┼── 售货机 VendingMachine ──── 策略 Policy
 │   │ 地址           │    │ 设备编号、状态
 │   │ 商圈类型       │    │ 型号、点位、区域
 │   │ 区域、合作商   │    │
 │                   │    ├─ 货道 Channel
 │                   │    │   编号、容量、关联商品
 │                   │    │
 │                   │    ├─ 订单 Order
 │                   │    │   订单号、支付金额、状态
 │                   │    │   合作商分成
 │                   │    │
 │                   │    └─ 工单 Task ──── 工单详情 TaskDetails
 │                   │        投放/补货/维修/撤机      货道补货明细
 │                   │        指派人、执行人、状态
 │
 ├─ 商品类型 SkuClass
 │   └─ 商品 Sku
 │        名称、图片(OSS)、品牌、价格(分)
 │
 ├─ 角色 Role
 │   └─ 员工 Emp
 │        姓名、角色、联系电话、区域
```

---

## 功能模块

### 1. 区域管理 —— `/manage/region`

管理地理区域划分，是所有点位、设备、员工的顶层归属单位。

- CRUD 及分页查询、Excel 导出

### 2. 合作商管理 —— `/manage/partner`

管理设备运营的合作方。

| 字段 | 说明 |
|------|------|
| `partner_name` | 合作商名称 |
| `contact_person` / `contact_phone` | 联系人及电话 |
| `profit_ratio` | 分成比例，如 30 表示 30% |
| `account` / `password` | 合作商登录凭证，可登录查看分成账单 |

### 3. 点位管理 —— `/manage/node`

设备部署的具体位置，关联区域、商圈类型（写字楼/社区/学校等）、合作商。

### 4. 设备类型管理 —— `/manage/vmType`

定义售货机硬件型号规格。

| 字段 | 说明 |
|------|------|
| `name` / `model` | 型号名称与编码 |
| `image` | 设备外观图（OSS） |
| `vm_row` / `vm_col` | 货道行列数，决定货道总数 |
| `channel_max_capacity` | 单个货道最大容量 |

### 5. 设备管理 —— `/manage/machine`

**核心模块。** 管理售货机全生命周期，设备状态流转：

```
未投放 (0) ──→ 运营 (1) ──→ 撤机 (3)
```

关键设计：

1. **新增自动建货道** —— 根据型号的 `vm_row × vm_col` 自动批量创建货道记录（如 6 行 × 10 列 = 60 条）
2. **信息冗余填充** —— 新增/修改时从点位复制区域 ID、合作商 ID、地址到设备表，避免多表 JOIN 查询
3. **设备详情聚合** (`GET /details/{innerCode}`) —— 一次查询返回该设备的累计销量/销售额、补货次数、维修次数、当月各商品销量排行

### 6. 货道管理 —— `/manage/channel`

设备与商品的桥梁层。

- 按设备编号查询货道列表（连带商品信息）
- 批量配置：一次请求批量设置多个货道的商品和容量
- 追踪当前容量、最大容量、上次补货时间

### 7. 商品管理 —— `/manage/sku` `/manage/skuClass`

- 商品类型支持两级树形分类（`class_id` ⟶ `parent_id`）
- 商品包含名称、图片（阿里云 OSS）、品牌、规格、价格
- **价格以"分"为单位存储** （`price=350` 即 3.5 元），避免浮点运算精度问题

### 8. 策略管理 —— `/manage/policy`

折扣策略，`discount=80` 即 8 折。设备通过 `policy_id` 关联策略，控制该设备上商品的折扣力度。

### 9. 员工管理 —— `/manage/emp`

运营员和维修员管理。

> **设计要点：** `tb_emp` 表冗余了 `region_name`、`role_code`、`role_name` 字段。
>
> - **收益：** 查询员工列表时无需 JOIN `tb_region` 和 `tb_role`，显著提升列表页性能
> - **代价：** 区域名或角色名变更时需同步更新员工表

### 10. 工单管理 —— `/manage/task`

**最复杂的业务模块，** 设备运维调度的核心。

**工单类型：**

| 类型 | 常量 | 说明 |
|------|------|------|
| 投放工单 | `TASK_TYPE_DEPLOY (1)` | 新设备安装上线 |
| 补货工单 | `TASK_TYPE_SUPPLY (2)` | 缺货补充，需附带详情 |
| 维修工单 | `TASK_TYPE_REPAIR (3)` | 设备故障维修 |
| 撤机工单 | `TASK_TYPE_REVOKE (4)` | 设备下架撤离 |

**状态流转：**

```
创建(待处理) ──→ 进行中 ──→ 完成
     │
     └──→ 已取消
```

**新增工单六步校验：**

| 步骤 | 校验内容 | 不通过处理 |
|------|----------|------------|
| 1 | 售货机是否存在 | 抛异常 "售货机不存在" |
| 2 | 指派的员工是否存在 | 抛异常 "员工不存在" |
| 3 | 员工区域与售货机区域是否一致 | 抛异常 "区域不一致" |
| 4 | 设备状态与工单类型是否匹配 | 运营中不可发投放工单；非运营中不可发补货/维修/撤机工单 |
| 5 | 是否有未完成的同类工单 | 分别检查"待处理"和"进行中"，防止重复派单 |
| 6 | 补货工单是否附带详情列表 | 抛异常 "补货清单不能为空" |

**工单编号生成：**

使用 Redis `INCR` 原子自增，按天生成全局唯一编号：

```
格式: yyyyMMdd + 5位序号
示例: 2026042000001

Redis Key: ivm:task:code20260420  (当天有效，自动过期)
```

### 11. 订单管理 —— `/manage/order`

记录完整的购买订单数据。

| 核心字段 | 说明 |
|----------|------|
| `order_no` / `third_no` | 订单编号 / 第三方支付单号 |
| `inner_code` / `channel_code` | 设备编号 / 货道编号 |
| `status` | 0-待支付, 1-支付完成, 2-出货成功, 3-出货失败, 4-已取消 |
| `amount` / `price` | 支付金额 / 商品金额（单位：分） |
| `pay_type` / `pay_status` | 支付类型（支付宝/微信） / 支付状态 |
| `bill` | 合作商分成金额 |

> **设计要点：** 订单表冗余了 `addr`、`region_name`、`node_name`、`business_type` 等字段，避免查询订单时多表 JOIN，以空间换查询性能。

---

## 数据库设计要点

| 要点 | 说明 |
|------|------|
| 逻辑外键 | 表间不使用物理外键约束，关联关系在应用层维护，避免级联操作风险 |
| 金额存储 | 所有金额字段使用 `int` 类型存储"分"，杜绝浮点数精度问题 |
| 冗余字段 | `tb_emp` 冗余区域名/角色名，`tb_order` 冗余地址/区域名/点位名，用存储换查询速度 |
| 工单编号 | Redis 自增 + 按天分区，兼顾唯一性和可读性 |

---

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Redis
- Maven 3.6+

### 初始化数据库

1. 创建数据库 `ivmhub`
2. 依次执行 `sql/` 目录下的建表脚本
3. 执行 `sql/ry_base.sql` 导入若依基础数据

### 配置修改

在 `stone-admin/src/main/resources/` 目录下创建 `application-secret.yml`（该文件已加入 `.gitignore`，不会被提交到仓库），填入以下敏感配置：

```yaml
secret:
  # 数据库密码（对应 application-druid.yml 中的 ${secret.db-password:}）
  db-password: your-db-password
  # Druid 控制台密码（对应 ${secret.druid-console-password:}）
  druid-console-password: your-druid-password
  # Redis 密码（对应 application.yml 中的 ${secret.redis-password:}），无密码可不填
  redis-password: your-redis-password
  # Token 密钥（对应 ${secret.token-secret:}）
  token-secret: your-token-secret
  # 阿里云 OSS AccessKey（对应 ${secret.oss-access-key:}）
  oss-access-key: your-oss-access-key
  # 阿里云 OSS SecretKey（对应 ${secret.oss-secret-key:}）
  oss-secret-key: your-oss-secret-key
```

> 所有敏感配置项已在 `application.yml` 和 `application-druid.yml` 中以 `${secret.xxx:}` 占位符引用，创建 `application-secret.yml` 后即可自动注入，无需修改原有配置文件。


## 定制说明

在若依框架基础上，本项目新增/定制的代码：

| 模块 | 内容 |
|------|------|
| `stone-common` | 新增 `IvmConstants.java` —— 工单类型、售货机状态、员工状态、角色编码等业务常量 |
| `stone-common` | 新增 `OSSTest.java` —— 阿里云 OSS 文件上传测试 |
| `stone-manage` | **完整的售货机运营业务代码** —— 17 个 Controller、17 个 Service、15 个 Domain、16 个 Mapper XML |

---

<div align="center">
  <sub>Built with <a href="https://www.ruoyi.vip/">RuoYi</a> · Author <a href="https://github.com/LinShinan">stone</a></sub>
</div>