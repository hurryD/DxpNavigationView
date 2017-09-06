# DxpNavigationView
安卓主框架所用底部模块切换的导航栏，最多支持5个模块，简单易用。<br><br>
gradle依赖:<br>
```
	//Step 1. Add it in your root build.gradle at the end of repositories
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
        
	
	
	//Step 2. Add the dependency in build.gradle for app
	dependencies {
	
	        compile 'com.github.hurryD:DxpNavigationView:v1.1'
		
	}

```
<br>
使用方法：<br><br>

```java
//Xml
<com.hurry.custombottomnavigationview.customview.CustomBottomNavigationView
        android:id="@+id/ngView_main"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="@color/white_fc"
        app:defaltColor="@color/black_999"
        app:iconHeight="30dp"
        app:itemText1="首页"
        app:itemText2="定制"
        app:itemText3="消息"
        app:itemText4="我的"
        app:defIcon1="@drawable/bottom_icon_1"
        app:defIcon2="@drawable/bottom_icon_2"
        app:defIcon3="@drawable/bottom_icon_3"
        app:defIcon4="@drawable/bottom_icon_4"
        app:selIcon1="@drawable/bottom_icon_1_click"
        app:selIcon2="@drawable/bottom_icon_2_click"
        app:selIcon3="@drawable/bottom_icon_3_click"
        app:selIcon4="@drawable/bottom_icon_4_click"
        app:selectColor="@color/colorAccent"
        app:textSize="12sp" />
        
	
        
//Activity
CustomBottomNavigationView ngView = (CustomBottomNavigationView) findViewById(R.id.ngView_main);

//设置导航栏点击监听
ngView.setOnMenuClickListener(new CustomBottomNavigationView.OnMenuClickListener() {
       @Override
       public void onMenuClick(int position) {
           mTextMessage.setText(ngTexts[position]);
       }
 });
 
 //设置默认被选中的模块 0,1,2,3.. (可选)
 ngView.setCurrentIndex(0);
        
```
<br><br>
效果截图：<br><br>
![](https://github.com/hurryD/CustomBottomNavigationView/raw/master/screenshot/Screenshot_2017-09-04-14-59-17.jpg)  
![](https://github.com/hurryD/CustomBottomNavigationView/raw/master/screenshot/Screenshot_2017-09-04-14-59-25.jpg)  

