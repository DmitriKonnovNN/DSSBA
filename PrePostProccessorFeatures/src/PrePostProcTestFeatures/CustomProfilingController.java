package io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures;

public class CustomProfilingController implements CustomProfilingControllerMBean {


    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
