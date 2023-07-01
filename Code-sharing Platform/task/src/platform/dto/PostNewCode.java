package platform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostNewCode {
    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "time")
    private int secondsToExpire;

    @JsonProperty(value = "views")
    private int views;

    public PostNewCode() {
    }

    public PostNewCode(String code, int secondsToExpire, int views) {
        this.code = code;
        this.secondsToExpire = secondsToExpire;
        this.views = views;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSecondsToExpire() {
        return secondsToExpire;
    }

    public void setSecondsToExpire(int secondsToExpire) {
        this.secondsToExpire = secondsToExpire;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "PostNewCode{" +
                "code='" + code + '\'' +
                '}';
    }
}
