# xiaomilibrary
常用框架、UI、工具类

## 导入
### 1、gradle
```
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```
### 2、添加依赖和配置
```
api 'com.lixiaomi:baselib:1.0.7'
```
### 3、相关权限
```
    <!--网络权限-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <!--文件读写权限:选择照片，裁剪照片功能使用；网络请求缓存功能使用-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
    <!--相机：拍照功能使用-->
    <uses-permission android:name="android.permission.CAMERA"></uses-permission>
    <!--VISIBILITY_HIDDEN表示不显示任何通知栏提示的权限:更新App,下载apk文件使用-->
    <uses-permission
        android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION"/>
    <!--DownloadManager：更新App,下载apk文件使用-->
    <uses-permission
        android:name="android.permission.ACCESS_DOWNLOAD_MANAGER"/>
    <!--8.0未知来源的应用权限 更新App-->
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
    
```

### 4、使用文档
[传送门](https://github.com/LiQinglin007/BaseLibApplication/blob/master/doc/%E4%BD%BF%E7%94%A8%E6%96%87%E6%A1%A3.md)
  

## 重要更新说明
### v1.0.7 更新  2019-10-14
#### 更新
 * 添加net/HttpConfig类，用于配置网络模块参数
### v1.0.6 更新  2019-8-7
#### 更新
 * 修改ui/XiaomiLoader，优化Loading显示
### v1.0.5 更新  2019-7-31
#### 更新
 * 修改ui/XiaomiLoader，优化Loading显示
### v1.0.3 更新  2019-7-26
#### 增加功能
 * 删除Base包及basebottom包;两个包将放在新的mvpbaseLib中.
### v1.0.2 更新  2019-7-25
#### 增加功能
 * 动态权限，添加Fragment申请权限功能
### v1.0.1 更新  2019-7-17
#### 增加功能
 * Http模块，增加同步请求Get/Post方法，增加缓存配置项，网络拦截器配置项


