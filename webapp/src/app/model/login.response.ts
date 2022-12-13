export class LoginResponse {
    data?: {
        id: number;
        username: string;
        fName: string;
        lName: string;
        regDate: string;
        token: string
    };
    success?: boolean;
    code?: number;
}
