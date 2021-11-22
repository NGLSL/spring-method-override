package xin.codedream.spring.method.override;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nglsl
 */
public class Reimplement {
    private String targetBeanName;
    private Class<?> targetBeanClass;
    private String sourceBeanName;
    private List<ReplaceOverride> replaceOverrides = new ArrayList<>();

    public Class<?> getTargetBeanClass() {
        return targetBeanClass;
    }

    public void setTargetBeanClass(Class<?> targetBeanClass) {
        this.targetBeanClass = targetBeanClass;
    }

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public void setSourceBeanName(String sourceBeanName) {
        this.sourceBeanName = sourceBeanName;
    }

    public List<ReplaceOverride> getReplaceOverrides() {
        return replaceOverrides;
    }

    public void setReplaceOverrides(List<ReplaceOverride> replaceOverrides) {
        this.replaceOverrides = replaceOverrides;
    }
}
