package t.app.info.base;

import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.base.DevBaseActivity;
import dev.base.lib.DevBaseEvent;
import dev.lib.other.EventBusUtils;

/**
 * detail: 基类
 * Created by Ttt
 */
public class BaseActivity extends DevBaseActivity {

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销 Event 事件
        registerEventOperate(false);

        if (unbinder != null){
            unbinder.unbind();
        }
    }

    // ======= 内部处理 =========

    @Override
    public BaseConfigBean buildBaseConfig() {
        BaseConfigBean baseConfigBean = new BaseConfigBean();
        // 需要注册 EventBus
        baseConfigBean.isRegisterEvent = true;
//        /** 是否需要常亮 */
//        baseConfigBean.isAlwaysLight = false;
//        /** 是否需要沉浸式状态栏 */
//        baseConfigBean.isTranslucentBars = true;
//        /** 状态栏颜色 */
//        baseConfigBean.statusBarColor = android.R.color.white;
//        // --
//        String cName = this.getClass().getName();
//        // 特殊情况,例如单独判断类,做单独处理 , 例如 MainActivity 类状态栏颜色变成 红色
//        if (cName.equals(Activity.class.getName())) {
//            /** 状态栏颜色 */ // 首页不需要修改颜色
//            baseConfigBean.statusBarColor = android.R.color.black;
//        }
        return baseConfigBean;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        // 初始化View
        unbinder = ButterKnife.bind(this);
        // 注册 EventBus
        registerEventOperate(true);
        // 初始化
        initMethod();
    }

    // =

    // = Event 处理 =

    /**
     * 注册 Event 操作
     * @param isRegister
     */
    @Override
    protected final void registerEventOperate(boolean isRegister) {
        super.registerEventOperate(isRegister);

        if (isRegisterEvent()) {
            if (isRegister) {
                EventBusUtils.register(this);
            } else {
                EventBusUtils.unregister(this);
            }
        }
    }

    // == EventBus ==

    // 默认非粘性
    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onEventBus(DevBaseEvent event) {
        if (event != null) {
            receiveEvent(event);
        }
    }
}
