package team1.Daangn_Clonecoding.web.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

    private final T content;

    public CommonResponse(T content) {
        this.content = content;
    }
}
