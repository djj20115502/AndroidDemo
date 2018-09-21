# CoordinatorLayout
[属性](https://blog.csdn.net/victor_fang/article/details/54691957)
[Behavior](https://www.jianshu.com/p/dd79ae898448)
[布局](../../../../../res/layout/coordinatorlayout_activity.xml)
## 基本结构
>android.support.design.widget.CoordinatorLayout
>>android.support.design.widget.AppBarLayout
>>>android.support.design.widget.CollapsingToolbarLayout
>>>>android.support.v7.widget.Toolbar

1. appbarlayout是一个LinearLayout,作为CoordinatorLayout的直接子布局。其中CollapsingToolbarLayout会折叠，其他的不会。
2. CollapsingToolbarLayout 折叠动画的执行布局，其中的子布局除了toolbar外都会折叠。
3. Toolbar的高度的2本默认决定了CollapsingToolbarLayout中app:contentScrim="#000000"折叠变色的时间。可通过设置监听来避免。AppBarLayout中设置addOnOffsetChangedListener