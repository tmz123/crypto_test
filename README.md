
### 1. 效果图
![企业微信截图_7698135e-4ecc-4334-8f3a-76ef8e5ad700](https://github.com/user-attachments/assets/43f8880e-d19c-4e12-9df3-1b203d026f38)

### 2. 时序图
![企业微信截图_326219bf-7fef-414f-ac68-eb62ae572186](https://github.com/user-attachments/assets/5149dfb0-f8c5-41e3-9570-ea0507733014)

### 3. 架构设计 MVVM 架构  
- **View 层**：  
  - 使用 Jetpack Compose 构建 UI  
  - `WalletScreen.kt` 作为主要界面  
  - 实现了响应式 UI 更新  
- **ViewModel 层**：  
  - `WalletViewModel` 处理业务逻辑  
  - 使用 StateFlow 管理 UI 状态  
  - 通过 ViewModelFactory 实现依赖注入  
- **Model 层**：  
  - `WalletRepository` 接口定义数据操作  
  - 使用本地 JSON 文件模拟数据源  

### 4. 项目亮点  
1. **现代化 UI 框架**：  
   - 使用 Jetpack Compose 构建声明式 UI  
   - Material Design 3 设计规范  
   - 支持深色模式  
   - 流畅的动画效果  
2. **状态管理**：  
   - 使用 StateFlow 实现响应式数据流  
   - 清晰的状态封装（Loading/Success/Error）  
   - 统一的状态处理机制  
3. **可扩展性**：  
   - 接口化设计便于扩展  
   - 模块化的代码结构  
   - 依赖注入的使用  
4. **性能优化**：  
   - 使用协程处理异步操作  
   - LazyColumn 实现高效列表  
   - 图片加载优化  

### 5. 边界情况考虑  
1. **数据异常处理**：  
   - 网络请求失败处理  
   - JSON 解析异常处理  
   - 空数据展示  
   - 负数金额处理  
2. **UI 状态处理**：  
   - 加载中状态  
   - 错误状态展示  
   - 空列表状态  
   - 刷新机制  
3. **用户体验优化**：  
   - 错误提示（Toast）  
   - 加载指示器  
   - 点击反馈  
   - 返回键处理  
4. **资源管理**：  
   - 图片加载失败处理  
   - 内存泄漏预防  
   - 生命周期管理  
   - 异常日志记录  
5. **安全性考虑**：  
   - 数据验证  
   - 输入过滤  
   - 金额精度处理  
   - 数据格式化  
