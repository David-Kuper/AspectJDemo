package org.android10.gintonic.internal;

/**
 * Created by lingyi.mly on 2016/5/19.
 */
public class MethodMsg implements Comparable {
    public static int comparableType = 1; // 1表示按照时间排序    2表示按照ClassName排序    3表示按照method排序
    private String className;
    private String methodInfo;
    private long methodDuration;
    public MethodMsg(String className,String methodInfo,long methodDuration){
        this.className = className;
        this.methodDuration = methodDuration;
        this.methodInfo = methodInfo;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodDuration(long methodDuration) {
        this.methodDuration = methodDuration;
    }

    public void setMethodInfo(String methodInfo) {
        this.methodInfo = methodInfo;
    }

    public long getMethodDuration() {
        return methodDuration;
    }

    public String getMethodInfo() {
        return methodInfo;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return className + "     " + methodInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MethodMsg))
            return false;

        final MethodMsg other = (MethodMsg) obj;
        if (this.className.equals(other.getClassName()) && this.methodInfo.equals(other.methodInfo)
                && this.methodDuration == other.methodDuration)
            return true;
        else
            return false;
    };

    @Override
    public int compareTo(Object o) {
        MethodMsg other = (MethodMsg) o;
        int result = 0;
        switch (comparableType){
            case 2:
                result = compareByClassName(other);
                if (result == 0){
                    result = compareByMethodInfo(other);
                    if (result == 0)
                        result = compareByDuration(other);
                }
                break;
            case 3:
                result = compareByMethodInfo(other);
                if (result == 0){
                    result = compareByDuration(other);
                    if (result == 0){
                        result = compareByClassName(other);
                    }
                }
                break;
            case 1:
            default:
                result = compareByDuration(other);
                if (result == 0){
                    result = compareByClassName(other);
                    if (result == 0){
                        result = compareByMethodInfo(other);
                    }
                }
        }
        return result;
    }
    private int compareByDuration(MethodMsg other){
        if (this.methodDuration > other.getMethodDuration())
            return -1;
        else if (this.methodDuration < other.getMethodDuration()) {
            return 1;
        }
        return 0;
    }
    private int compareByMethodInfo(MethodMsg other){
        if (this.methodInfo .compareTo( other.getMethodInfo()) > 0)
            return -1;
        else if (this.methodInfo .compareTo( other.getMethodInfo()) < 0) {
            return 1;
        }
        return 0;
    }
    private int compareByClassName(MethodMsg other){
        if (this.className .compareTo( other.getClassName()) > 0)
            return -1;
        else if (this.className .compareTo( other.getClassName()) < 0) {
            return 1;
        }
        return 0;
    }
    @Override
    public int hashCode() {
        int result;
        result = (className == null ? 0 : className.hashCode()) + (methodInfo == null ? 0 : methodInfo.hashCode());
        result = (int) (29 * result + methodDuration);
        return result;
    }
}
