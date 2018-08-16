# [ijkplayer](https://github.com/Bilibili/ijkplayer)
由浅入深学习使用ijkplayer

## step1：基本玩转起来
[参考代码](https://github.com/thiagooo0/lmnplayer)
### 修改记录
1. 模拟器使用了海马玩，所以调整到4.2可用。
2. 添加网络播放路径，模仿播放在线视频

*基本使用步骤*


1. 添加ijk依赖
```groovy

    // required, enough for most devices.
    implementation 'tv.danmaku.ijk.media:ijkplayer-java:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.8.8'

    // Other ABIs: optional
    implementation 'tv.danmaku.ijk.media:ijkplayer-armv5:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-arm64:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-x86:0.8.8'
    implementation 'tv.danmaku.ijk.media:ijkplayer-x86_64:0.8.8'

    // ExoPlayer as IMediaPlayer: optional, experimental
    implementation 'tv.danmaku.ijk.media:ijkplayer-exo:0.8.8'

```
2. 导入相关的SO库
``` 
 //加载so文件
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            //
        }
```
3. 实例化ijkplayer,以及相关的配置
```
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        //开启硬解码
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);

```
4. 将播放器与surfaceview关联
```
        ijkMediaPlayer.setDisplay(surfaceView.getHolder());
```
5. 播放器相关操作
``` 
        ijkMediaPlayer.setDataSource(mPath);
        ijkMediaPlayer.reset();
```
6. 结束,释放相关的资源
```
        IjkMediaPlayer.native_profileEnd();
```