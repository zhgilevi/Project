export class LoginResponse {
    data?: {
        id: number;
        username: string;
        fName: string;
        lName: string;
        regDate: string;
    };
    success?: boolean = false;
    code?: number;
    token?: string;
}