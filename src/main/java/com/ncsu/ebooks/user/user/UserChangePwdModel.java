package com.ncsu.ebooks.user.user;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePwdModel {
    private String userID;
    private String oldPassword;
    private String newPassword;
}