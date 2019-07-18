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
api 'com.lixiaomi:baselib:1.0.0'
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

### 4、使用
#### 4.1初始化
在程序入口(Application文件中)据需求添加如下代码
```
 AppConfigInIt.init(this)
                //设置调试模式，默认false
                .withDebug(true)
                //配置SharedPreferences
                .withSharedPreferences(getSharedPreferences(SharedPreferences, Activity.MODE_PRIVATE))
                //默认文件根地址(网络请求缓存使用)
                .withBaseFile("com.xiaomi.lib")
                //baseUrl(网络请求使用)
                .withBaseUrl("http://home.hbhanzhi.com:7056/")
                //添加拦截器(网络请求使用)
                .withHttpInterceptors(new TokenInterceptor())
                //是否信任全部证书,不信任全部，则传进去证书流(网络请求使用)
                .withHttpCertificateFlag(open != null ? false : true, open)
                //连接失败后是否重连(网络请求使用)
                .withHttpRetryConnection(false)
                .configure();                
```
#### 4.2 MVP框架

类介绍：

* BaseActivity：Activity基类
* BaseFragment：Fragment基类
* BaseView: view层接口基类
* BasePresenter：Persenter层基类
* BaseModel：Model层要实现的接口
* MyPresenterCallBack：P层调用M层方法，返回结果的回调类

用登陆模块做例子：
###### V层
LoginActivity.class
```
  LoginActivity extends BaseView{
  	void setLoginData( int code , String msg);
  }
```

LoginActivityImpl.class
```
     LoginActivityImpl extends BaseActivity<ILoginActivity, LoginActivityPersenter> implements ILoginActivity{
    
    //发起请求
    void getData(){
    	 mPersenter.login("admin", "123456");
    }
    
    //注册Persenter
    @Override
    protected LoginActivityPersenter createPersenter() {
        return new LoginActivityPersenter();
    }
    
    //拿到返回结果
    @Override
    public void setLoginData(int code, String response) {
        LogUtils.loge("拿到返回结果");
    }
    
}
```

###### P层

LoginActivityPersenter.class
```
public interface LoginActivityPersenter {
    void login(String loginName, String password);
}

```
LoginActivityPersenterImpl.class
```
public class MyHttpPersenter extends BasePresenter<LoginActivity, BaseModel> implements LoginActivityPersenter {

    @Override
    public void login(String loginName, String password) {
        final IMyHttpActivity view = getView();
        view.startLoading();
        ArrayList<BaseModel> models = getModelList();
		//调用model方法
        ((UserModel) models.get(0)).login(loginName, password, new MyPresenterCallBack() {
            @Override
            public void success(int code, String response) {
                //返回数据给view
                view.setData(code, response);
                view.stopLoading();
            }

            @Override
            public void failure(Throwable e) {
                 //返回数据给view
                view.setData(-100, e.toString());
                view.stopLoading();
            }
        });
    }
    
    //注册Model,这里可以添加多个Model
    @Override
    protected ArrayList<BaseModel> createModelList() {
        ArrayList<BaseModel> modelList = new ArrayList<>();
        modelList.add(new UserModel());
        return modelList;
    }
}
```

###### M层
UserModel.class
```
public interface UserModel extends BaseModel {
    /**
     * 登陆
     *
     * @param loginName
     * @param password
     * @return
     */
    void login(String loginName, String password, MyPresenterCallBack myPresenterCallBack);


    /**
     * 注册
     *
     * @return
     */
    void xxx( MyPresenterCallBack myPresenterCallBack);
}
```

UserModelImpl
```
public class UserModelImpl implements UserModel {

    @Override
    public void login(String loginName, String password, final MyPresenterCallBack myPresenterCallBack) {
    	//这里去发起网络请求，并回调给Persenter
        myPresenterCallBack.success(200, "111111111111111111111111111");
    }
    
    ...
}

```


#### 4.3 网络请求模块
* 支持同步GET/POST请求；
* 异步GET/POST请求；
* 异步文件上传；
* 异步文件上传携带参数；
* 支持添加请求头；
* 支持接口缓存数据，在缓存时间内，返回缓存数据，超出缓存时间，请求更新数据
* 支持取消请求
* 支持发送application/json格式请求

详细使用如下代码：

* 发送一个异步GET请求<br>
包含请求头，请求参数以及缓存时间
```
WeakHashMap<String, String> head = new WeakHashMap<>();
head.put("xxx", "xxxx");
head.put("xxx", "xxxx");
WeakHashMap<String, Object> params = new WeakHashMap<>();
params.put("xxx", "xxxx");
params.put("xxx", "xxxx");
MiSendRequestOkHttp.sendGet(head, params, "http://xxx/xxx", 0, new MiOkHttpCallBack() {
    @Override
    public void onSuccess(int code, String response) {
        LogUtils.loge("code:" + code + "response:" + response);
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtils.loge("e:" + e.toString());
    }
});
```
* 发送异步POST参数请求<br>
含请求头(可为null)，请求参数(可为null),缓存时间
```
 WeakHashMap<String, String> head = new WeakHashMap<>();
 head.put("xxx", "xxxx");
 head.put("xxx", "xxxx");
 WeakHashMap<String, Object> params = new WeakHashMap<>();
 params.put("xxx", "xxxx");
 params.put("xxx", "xxxx");
 MiSendRequestOkHttp.sendPost(head, params, "http://xxx/xxx", 10, new MiOkHttpCallBack() {
     @Override
     public void onSuccess(int code, String response) {
         LogUtils.loge("code:" + code + "response:" + response);
     }

     @Override
     public void onFailure(Throwable e) {
         LogUtils.loge("e:" + e.toString());
     }
 });
```
* 发送application/json格式的异步请求
缓存时间为10s
```
MiSendRequestOkHttp.sendPost(head, new UserBean("loginUserName", "loginPassword"), "http://xxx/xxx", 10, new MiOkHttpCallBack() {
    @Override
    public void onSuccess(int code, String response) {
        LogUtils.loge("code:" + code + "response:" + response);
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtils.loge("e:" + e.toString());
    }
});
```
* 发送一个不含请求头的application/json格式的异步请求，缓存时间为0s，请求内容为json串
缓存时间为0s
```
ArrayList<String> mSendList = new ArrayList<>();
mSendList.add("1");
mSendList.add("2");
MiSendRequestOkHttp.sendPost(null, new Gson().toJson(mSendList), "http://xxx/xxx", 0, new MiOkHttpCallBack() {
    @Override
    public void onSuccess(int code, String response) {
        LogUtils.loge("code:" + code + "response:" + response);
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtils.loge("e:" + e.toString());
    }
});
```
* 发送一个不含请求头的application/json格式的异步请求，缓存时间为0s，请求内容为json串
缓存时间为0s
```
ArrayList<String> mSendList = new ArrayList<>();
mSendList.add("1");
mSendList.add("2");
MiSendRequestOkHttp.sendPost(null, new Gson().toJson(mSendList), "http://xxx/xxx", 0, new MiOkHttpCallBack() {
    @Override
    public void onSuccess(int code, String response) {
        LogUtils.loge("code:" + code + "response:" + response);
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtils.loge("e:" + e.toString());
    }
});
```


* 发送一个异步携带参数的文件上传请求
```
WeakHashMap<String, String> paramsString = new WeakHashMap<>();
paramsString.put("xxx", "xxxx");
paramsString.put("xxx", "xxxx");
WeakHashMap<String, File> fileParams = new WeakHashMap<>();
fileParams.put("xxx", new File("xxx"));
fileParams.put("xxx", new File("xxx"));
MiSendRequestOkHttp.sendPost(null, paramsString, fileParams, "http://xxx/xxx", new MiOkHttpCallBack() {
    @Override
    public void onSuccess(int code, String response) {
        LogUtils.loge("code:" + code + "response:" + response);
    }

    @Override
    public void onFailure(Throwable e) {
        LogUtils.loge("e:" + e.toString());
    }
});
```

* 发送一个同步get请求
```
WeakHashMap<String, String> head = new WeakHashMap<>();
head.put("xxx", "xxxx");
head.put("xxx", "xxxx");
WeakHashMap<String, Object> params = new WeakHashMap<>();
params.put("xxx", "xxxx");
params.put("xxx", "xxxx");
try {
    Response response = MiSendRequestOkHttp.sendGetSync(head, params, "http:xxx", 10);
} catch (IOException e) {
    e.printStackTrace();
}
```

* 取消一个请求
```
MiSendRequestOkHttp.cancel("http:xxx");
```
#### 4.4 MiDialog
文字弹框，Edittext弹框，单项选择和多项选择框，时间选择框

* 文字弹框<br>
![弹出文字框](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/MiDialog_Message.png)
```
new MiDialog(getActivity(), MiDialog.MESSAGE_TYPE)
                            .builder()
                            .setTitle("提示")
                            .setMsg("确定要退出么？")
                            .setCannleButton("取消", null)
                            .setOkButton("退出", new MiDialog.DialogCallBack() {
                                @Override
                                public void dialogCallBack(String connect) {
                                   LogUtils.loge(connect);
                                }
                            })
                            .show();
```

* 编辑框<br>
![弹出编辑框](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/MiDialog_Edit.png)
```
 new MiDialog(getActivity(), MiDialog.EDIT_TYPE)
                            .builder()
                            .setTitle("提示")
                            //是否密码显示
                            .setPassword(true)
                            .setMsg("确定要退出么？")
                            .setCannleButton("取消", null)
                            .setOkButton("退出", new MiDialog.DialogCallBack() {
                                @Override
                                public void dialogCallBack(String connect) {
                                    LogUtils.loge(connect);
                                }
                            })
                            .show();
```

* 弹出单选框和多选框<br>

  ![弹出单选框](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/MiDialogList.png)

  ```java
   ArrayList<String> sexList = new ArrayList<>();
                  sexList.add("男");
                  sexList.add("女");
                  new MiDialogList(getActivity())
                          .builder()
                          //标题
                          .setTitle("选择性别")
                      	//数据源
                          .setData(sexList)
                      	//弹框位置
                          .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                      	//返回数据类型：单选/多选
                          .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                          .setCallBack(new MiDialogList.OnDialogListCallback() {
                              @Override
                              public void onListCallback(ArrayList<Integer> position) {
                                  //回调方法，单选时，position[0],就是选的第几个
                                  chooseSex.setText("选择性别:" + sexList.get(position.get(0)));
                              }
                          })
                          .show();
  ```

  ![弹出多选框](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/MiDialogList1.png)

  ```java
  //数据源 
  ArrayList<MiListInterface> mBeanArrayList = new ArrayList<>();
                  for (int i = 0; i < 20; i++) {
                      mBeanArrayList.add(new NoticeBean(null, "标题巴拉巴拉" + i, "内容巴拉巴拉" + i));
                  }
                  new MiDialogList(getActivity())
                          .builder()
                          .setData(mBeanArrayList)
                          .setTitle("标题巴拉巴拉")
                          .setGravity(MiDialogList.MILIST_DIALOG_CENTER)
                          .setReturnType(MiDialogList.MILIST_RETURN_MULTIPLE)
                          .setCallBack(new MiDialogList.OnDialogListCallback() {
                              @Override
                              public void onListCallback(ArrayList<Integer> position) {
                                  //回调方法，多选时，position,就是选的哪几个
                                  int size = position.size();
                                  for (int i = 0; i < size; i++) {
                                      LogUtils.loge(TAG, new Gson().toJson(mBeanArrayList.get(position.get(i))));
                                  }
                                  T.shortToast(getActivity(), "您选择了" + size + "个用户，去控制台查看详情吧");
  
                              }
                          })
                          .show();
  ```
这里要注意一点，setData方法，可以传递String类型的List进去，也可以传递一个任意对象(A)的List，但是这个A要实现MiListInterface接口.如NoticeBean类：

  ```
  public class NoticeBean implements MiListInterface {
      /**
       * 公告id
       */
      Long NoticeId;
      /**
       * 公告标题
       */
      String NoticeTitle;
      /**
       * 公告内容
       */
      String NoticeContent;
  
      @Override
      public String getMiDialigListShowData() {
      	//返回要通过MiDialogList展示的内容，这里可以先处理一下内容，再显示
          return NoticeTitle;
  		return NoticeTitle+"拼接内容";
      }
  }
  ```
  
* 选择年月日<br>
  
  ![选择年月日](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/Mi_Day_Time_Diolig.png)
  
  ```java
                  Date startDate = MiDateUtils.getDate(2018, 4, 1);
                  Date endDate = new Date();
                  Date selectDate = MiDateUtils.getDate(2018, 4, 28);            
                  new MiDayTime(getActivity())
                          .builder()
                          //设置开始时间
                          .setStartDate(startDate)
                          //设置截至时间
                          .setEndDate(endDate)
                          //设置选中时间
                          .setSelectDate(selectDate)
                          //时间类型：年/年月/年月日
                          .setType(MiDayTime.YEAR_MONTH_DAY)
                          //设置选中时间的颜色
                          .setTvColor(R.color.warning_color5)
                          .setCallBack(new MiDayTime.TimeDialogCallBack() {
                              @Override
                              public void callback(String year, String month, String day) {
                                  //回调，返回选中的年月日
                                  //时间类型为年的，默认返回month="01",day="01"
                                  //时间类型为年/月的，默认day="01"
                                  LogUtils.loge(year + month + day);
                              }
                          })
                          .show();
  ```
  
  
  
* 选择时分<br>
  
  ![选择时分](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/Mi_Min_Time_Diolig.png)
  
  ```java
    				Date date = new Date();
                  int hours = date.getHours();
                  int minutes = date.getMinutes();
                  new MiMinTime(getActivity())
                          .builder()
                      	//设置选中时间
                          .setSelectHour(hours)
                          .setSelectMin(minutes)
  						//设置选中时间的颜色
                          .setTextColor(R.color.warning_color1)
                          .setTimeDialogCallBack(new MiMinTime.TimeDialogCallBack() {
                              @Override
                              public void callback(String min, String hour) {
                                  //回调，返回选中的年月日
                                  LogUtils.loge(hour + min );
                              }
                          })
                          .show();
  ```
  
  
  
* 仿照滴滴预约打车选择时间<br>
  
  适用于预约场景
  
  ![仿照滴滴选择时间](https://github.com/LiQinglin007/BaseLibApplication/blob/master/image/MiDialogList1.png)
  
  ```
   new MiDiDiTime(getActivity())
                          .builder()
                          //设置每天营业时间，从几点到几点
                          .setStartHour(2)
                          .setEndHour(20)
  						//设置可以提前多少天预约
  						//这里注意，如果当前时间超过了当天最晚营业时间，预约时间从第二天开始
  						//如果没有超过当天的最晚营业时间，预约时间从当天开始
                          .setDayNumber(5)
                          //设置选中文字颜色
                          .setTextColor(R.color.warning_color3)
                          .setTimeDialogCallBack(new MiDiDiTime.TimeDialogCallBack() {
                              @Override
                              public void callback(String dateWeek, String date, String time) {
                              	//dateWeek：带有星期的时间，例：7月18号(周四)
  								//date:当天日期，例：2019-07-18
  								//time：时间(小时)，例05
                                  LogUtils.logd("选择预约时间(仿滴滴)：    " + date);
                              }
                          })
                          .show();
  ```
  
#### 4.5 PermissionsUtil 申请权限工具类

  这里以拨打电话为例子演示，工具类包括版本判断，检查权限，申请权限，去设置页面手动开启权限功能。

* 首先要在清单文件中添加打电话的权限，
* 然后调用PermissionsUtil.getPermission方法。参数为Activity/Fragment,权限数组，权限描述，回调
* 重写onRequestPermissionsResult方法，在方法中把数据交给PermissionsUtil.onRequestPermissionsResult处理
* 重写onActivityResult方法，把数据交给PermissionsUtil.onActivityResult处理
  
  ```
   String[] permisssions = {Manifest.permission.CALL_PHONE};
  
      private void getPermission() {
          PermissionsUtil.getPermission(PermissionsActivity.this, permisssions, "此功能需要拨打电话", new PermissionsUtil.PermissionCallBack() {
              @Override
              public void onSuccess() {
                  LogUtils.loge("权限验证通过");
                  Intent intent = new Intent(Intent.ACTION_CALL);
                  Uri data = Uri.parse("tel:15284224244");
                  intent.setData(data);
                  startActivity(intent);
              }
  
              @Override
              public void onFail() {
  
              }
          });
      }
  
      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          PermissionsUtil.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
      }
  
      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          PermissionsUtil.onActivityResult(requestCode, resultCode, data);
      }
  ```
  
#### 4.6 MiLoadImageUtil 加载图片

这里使用Glide框架加载图片，后边如果换框架不会影像使用
主要功能如下
* 加载图片到ImageView
* 加载图片到背景
* 加载圆形图片
* 加载圆角图片
* 跳过缓存加载图片
* 设置加载失败/加载过程中图片
* 添加URL请求头

#### 4.7 其他工具

* CheckStringEmptyUtils：检查多个字符串长度，是否为空等
* GetResId：根据资源名称获取资源id,如'R.drawable.icon_test'获取R.drawable.icon_test
* LogUtils：日志工具类
* MD5Util：MD5加密工具
* MiDateUtils：时间类型转换工具
* MiJsonUtil：Json转换工具
* MiRandomUtils：随机数工具
* PreferenceUtils：SharedPreferences工具
* ScreenUtils：屏幕尺寸相关工具
* SHA1Utils：获取当前APK的SHA1值工具
* T：Toast工具
* UpdateAppUtils：更新Apk工具
  


## 重要更新说明
### v1.0.1 更新  2019-7-17
#### 增加功能
 * Http模块，增加同步请求Get/Post方法，增加缓存配置项，网络拦截器配置项


