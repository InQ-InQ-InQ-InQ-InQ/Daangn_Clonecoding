package team1.Daangn_Clonecoding.web.response;

import lombok.Getter;

@Getter
public class BasicResponse<T> {

    private final T content;

    public BasicResponse(T content) {
        this.content = content;
    }
}
