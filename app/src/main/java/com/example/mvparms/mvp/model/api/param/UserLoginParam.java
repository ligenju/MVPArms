package com.example.mvparms.mvp.model.api.param;



import com.example.mvparms.constants.Globals;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginParam implements Serializable {


    private static final long serialVersionUID = 2874821122663665372L;
    private String account;
    private String password;
    private String clientId = Globals.CLIENTTYPE;


    public UserLoginParam(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
