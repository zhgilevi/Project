import { UserInfo } from "./user.info";

export class LoginResponse {
    code: number = 1;
    token: string = '';
    userInfo: UserInfo = new UserInfo();
    // TODO: add fields
}